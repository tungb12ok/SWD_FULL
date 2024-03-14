/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import model.Cart;
import model.Order;
import model.OrderDetail;
import model.User;
import service.ProductService;

/**
 *
 * @author tungl
 */
@WebServlet(name = "CheckOutController", urlPatterns = {"/checkout"})
public class CheckOutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ProductService ps = new ProductService();

        User u = (User) session.getAttribute("user");
        if (u == null) {
            u = (User) session.getAttribute("userCart");
        }
        Cart cart = (Cart) session.getAttribute("cart");

        Map<Integer, Integer> userCart = cart.getCartByUserId(u.getUserId());
        int totalProducts = 0;
        if (userCart != null) {
            for (int quantity : userCart.values()) {
                totalProducts += quantity;
            }
        }

        request.setAttribute("listCart", userCart);
        request.setAttribute("ps", ps);
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("total", cart.calculateTotalForUser(u.getUserId()));
        request.getRequestDispatcher("payment.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ProductService ps = new ProductService();

        User u = (User) session.getAttribute("user");

        String receiver = request.getParameter("Receiver");
        String email = request.getParameter("Email");
        String mobile = request.getParameter("Mobile");
        String address = request.getParameter("Address");
        String paymentMethod = request.getParameter("payment");

        OrderDAO oDAO = new OrderDAO();
        OrderDetailDAO odDAO = new OrderDetailDAO();
        String orderId;
        Random random = new Random();
        Cart cartC = (Cart) session.getAttribute("cart");
        
        Order o = new Order();
        User uc = (User) session.getAttribute("userCart");
        int id;
        if (u != null) {
            orderId = u.getUserId() + ((random.nextInt(900000) + 100000)) + "";
            o.setUserId(u.getUserId()); // Assuming getUserId() returns the user's ID
            id = u.getUserId();
        } else {
            orderId = receiver + random.nextInt(900000) + 100000;
            id = uc.getUserId();
        }
        
        o.setOrderId(orderId);
        o.setName(receiver);
        o.setEmail(email);
        o.setMobile(mobile);
        o.setAddress(address);
        o.setUpdateTime(Date.valueOf(LocalDate.now()));
        o.setPayment(paymentMethod);
        o.setStatus(8);
        o.setAmount(cartC.calculateTotalForUser(id));
        o.setTime(Date.valueOf(LocalDate.now()));
        try {

            oDAO.createOrder(o);
            Map<Integer, Integer> cartItems = ((Cart) session.getAttribute("cart")).getCartByUserId(id);
            int i = 0;
            for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                // Create an order detail object
                OrderDetail orderDetail = new OrderDetail();

                orderDetail.setOrderDetailId(o.getOrderId() + i);
                orderDetail.setAmount(ps.getProductById(productId).getProductPrice());
                orderDetail.setOrderId(o.getOrderId()); // Set the order ID for the order detail
                orderDetail.setProductId(productId); // Set the product ID for the order detail
                orderDetail.setQuantity(quantity); // Set the quantity for the order detail
                odDAO.addOrderDetail(orderDetail);
                i++;
            }
            
            cartC.removeUserFromCart(id);
            session.setAttribute("messSuccess", "checkout successfuly!");
        } catch (Exception e) {
            session.setAttribute("messError", e.getMessage());
            response.getWriter().print(e.getMessage());
            return;
        }
        response.sendRedirect("home");
    }

}

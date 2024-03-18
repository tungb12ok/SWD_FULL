/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDetailDAO;
import dao.ProductDAO;
import dao.SettingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Cart;
import model.OrderDetail;
import model.User;
import service.ProductService;

/**
 *
 * @author tungl
 */
@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ProductService ps = new ProductService();
        User u = (User) session.getAttribute("user");
        if (u == null) {
            u = (User) session.getAttribute("userCart");
        }
        String sStatus = request.getParameter("status");
        if (sStatus != null) {
            OrderDetailDAO odd = new OrderDetailDAO();
            SettingDAO sd = new SettingDAO();
            int status = Integer.parseInt(sStatus);
            boolean view = !sd.getSettingById(status).getName().equals("New");
            String orderId = request.getParameter("orderId");
            String type = "Admin";
            Cart cart = new Cart();
            System.out.println(orderId);
            List<OrderDetail> list = odd.getOrderDetailsByOrderId(orderId);
            if (list.size() == 0) {
                request.setAttribute("cart", null);
            } else {
                for (OrderDetail od : list) {
                    cart.addItem(u.getUserId(), od.getProductId(), od.getQuantity());
                }
                request.setAttribute("cart", cart);
            }
            request.setAttribute("ps", ps);
            request.setAttribute("type", type);
            request.setAttribute("view", view);
            request.getRequestDispatcher("cartDetails.jsp").forward(request, response);
        } else {
            String action = request.getParameter("action");
            String pId = request.getParameter("pid");
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart == null) {
                cart = new Cart();
                request.setAttribute("messError", "Cart null");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
            double a = cart.calculateTotalForUser(u.getUserId());
            request.setAttribute("total", a);
            request.setAttribute("ps", ps);
            request.setAttribute("cart", cart);
            request.getRequestDispatcher("cartDetails.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String updateItem = request.getParameter("updateItem");

        User u = (User) session.getAttribute("user");
        if (u == null) {
            u = (User) session.getAttribute("userCart");
        }

        String pId = request.getParameter("pid");
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        if (updateItem != null) {
            try {
                int update = Integer.parseInt(updateItem);
                if (update == 1) {
                    cart.addItem(u.getUserId(), Integer.parseInt(pId));
                } else if (update == -1) {
                    cart.decreaseItemQuantity(u.getUserId(), Integer.parseInt(pId), 1);
                } else {
                    cart.updateItemQuantity(u.getUserId(), Integer.parseInt(pId), update);
                }
                session.setAttribute("messSuccess", "Update cart successfuly!");
            } catch (Exception e) {
                session.setAttribute("messError", "Update cart failed!");
            }
            response.sendRedirect("cart");
            return;
        }
        if (action != null) {
            if (request.getParameter("action").equals("checkout")) {
                try {
                    cart.addItem(u.getUserId(), Integer.parseInt(pId));
                    session.setAttribute("messSuccess", "Add cart successfuly!");
                } catch (Exception e) {
                    session.setAttribute("messError", "Add cart failed!");
                }
                response.sendRedirect("checkout");
                return;
            }
            if (action.equals("decreaseItem")) {
                try {
                    cart.decreaseItemQuantity(u.getUserId(), Integer.parseInt(pId), 1);
                    request.setAttribute("messSuccess", "Decrease cart successfuly!");
                } catch (Exception e) {
                    request.setAttribute("messError", "Decrease cart failed!");
                }
            }
            if (action.equals("addToCart")) {
                try {
                    cart.addItem(u.getUserId(), Integer.parseInt(pId));
                    session.setAttribute("messSuccess", "Add cart successfuly!");
                } catch (Exception e) {
                    session.setAttribute("messError", "Add cart failed!");
                }
            }
            if (action.equals("removeFromCart")) {
                try {
                    cart.removeItem(u.getUserId(), Integer.parseInt(pId));
                    session.setAttribute("messSuccess", "Remove from cart successfuly!");
                    if (request.getParameter("mode") != null) {
                        response.sendRedirect("cart");
                        return;
                    }
                } catch (Exception e) {
                    session.setAttribute("messError", "Remove from cart failed!");
                }
            }
        }
        session.setAttribute("cart", cart);

        response.sendRedirect("home");
    }

}

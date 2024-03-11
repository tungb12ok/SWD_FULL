/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.User;
import services.ProductService;

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
        String action = request.getParameter("action");
        ProductService ps = new ProductService();
        User u = (User) session.getAttribute("user");
        if (u == null) {
            request.getRequestDispatcher("signIn").forward(request, response);
            return;
        }

        String pId = request.getParameter("pid");
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            request.setAttribute("messError", "Cart null");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        request.setAttribute("ps", ps);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("cartDetails.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String updateItem = request.getParameter("updateItem");

        User u = (User) session.getAttribute("user");
        if (u == null) {
            request.getRequestDispatcher("signIn").forward(request, response);
            return;
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
                } catch (Exception e) {
                    session.setAttribute("messError", "Remove from cart failed!");
                }
            }
        }
        session.setAttribute("cart", cart);

        response.sendRedirect("home");
    }

}

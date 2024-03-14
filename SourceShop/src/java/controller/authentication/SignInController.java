/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication;

import dao.ProductDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author tungl
 */
@WebServlet(name = "SignInController", urlPatterns = {"/signIn"})
public class SignInController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        UserDAO dao = new UserDAO();

        try {
            User u = dao.login(username, password);
            if (u == null) {
                session.setAttribute("messError", "Email or password incorect!");
            } else {
                session.setAttribute("user", u);
                session.setAttribute("userCart", u);
                session.setAttribute("messSuccess", "Login successfuly!");
                if(u.getRole() == 1){
                    response.sendRedirect("ProductController");
                    return;
                }
                response.sendRedirect("home");
                return;
            }
        } catch (Exception e) { 
            session.setAttribute("messError", "Error in processing login!");
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

}

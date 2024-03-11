/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication;

import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
import services.UserServices;

/**
 *
 * @author tungl
 */
@WebServlet(name = "SignUpController", urlPatterns = {"/signUp"})
public class SignUpController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// Retrieve form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String mobile = request.getParameter("mobile");
        String pincode = request.getParameter("pincode");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        User u = new User();
        u.setName(username);
        u.setAddress(address);
        u.setEmail(email);
        u.setPassword(password);
        u.setPincode(Integer.parseInt(pincode));
        u.setMobile(mobile);
        u.setStatus(1);
        u.setRole(2);

        // Basic form validation
        UserServices us = new UserServices();
        try {
            us.signUp(u);
            request.setAttribute("messSuccess", "Sign up successfuly!");
        } catch (Exception e) {
            request.setAttribute("messError", e.getMessage());
        }

        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

}

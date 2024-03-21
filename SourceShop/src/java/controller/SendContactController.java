/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ContactDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Contact;
import model.User;

/**
 *
 * @author tungl
 */
@WebServlet(name = "SendContactController", urlPatterns = {"/fansMessage"})
public class SendContactController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("messError", "Must be login!");
            response.sendRedirect("home");
            return;
        }
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String email = request.getParameter("email");
        String comments = request.getParameter("comments");
        User u = (User) request.getSession().getAttribute("user");
        ContactDAO dao = new ContactDAO();
        try {
            Contact c = new Contact();
            c.setDescription(comments);
            c.setSettingId(Integer.parseInt(type));
            c.setUserId(u.getUserId());
            dao.addContact(c);
            request.getSession().setAttribute("messSuccess", "Send Successfuly!");
        } catch (Exception e) {
            request.getSession().setAttribute("messError", "Send Failed!");
        }
        response.sendRedirect("home");
    }

}

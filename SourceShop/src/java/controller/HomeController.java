/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CateDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import service.ProductService;

/**
 *
 * @author tungl
 */
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductService ps = new ProductService();
//        User u = (User) request.getSession().getAttribute("user");
//        if (u == null) {
//            u = new User();
//            Random random = new Random();
//            int userId = random.nextInt(100000); // Giả sử userId là một số ngẫu nhiên trong khoảng từ 0 đến 999999
//            u.setUserId(2);
//            request.getSession().setAttribute("userGuest", u);
//        }

        CateDAO cDAO = new CateDAO();
        try {
            request.setAttribute("listCate", cDAO.getAll());
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("list", ps.getProduct("Active"));
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.SettingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;
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
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        SettingDAO sDAO = new SettingDAO();
        request.setAttribute("contact", sDAO.getSettingByType("Contact"));
        if (u == null) {
            int userId = 0; // Giá trị mặc định nếu không tìm thấy userId trong cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userId")) {
                        userId = Integer.parseInt(cookie.getValue());
                        break;
                    }
                }
            }

            if (userId == 0) {
                Random random = new Random();
                userId = random.nextInt(10000) + 1; // Tạo userId ngẫu nhiên trong phạm vi 1 - 10000
                // Lưu userId vào cookie
                Cookie userIdCookie = new Cookie("userId", String.valueOf(userId));
                userIdCookie.setMaxAge(24 * 60 * 60); // Thời gian sống của cookie (ở đây là 1 ngày)
                response.addCookie(userIdCookie);
            }
            u = new User();
            u.setUserId(userId);
            session.setAttribute("userCart", u);
        }

        request.setAttribute("listCate", sDAO.getSettingByType("Category"));

        request.setAttribute("list", ps.getProduct("12"));
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}

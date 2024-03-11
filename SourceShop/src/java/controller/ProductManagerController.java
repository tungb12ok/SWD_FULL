/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.*;
import model.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 * @author win
 */
@WebServlet(name = "ProductManagerController", urlPatterns = {"/ProductController"})
public class ProductManagerController extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String key = request.getParameter("search");
        String sCateId = request.getParameter("cateId");
        String status = request.getParameter("status");
        int pid = -1, cateId = 0;
        if (key != null) {
            try {
                pid = Integer.parseInt(key);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else {
            key = "";
        }
        if (sCateId != null) {
            try {
                cateId = Integer.parseInt(sCateId);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        String pageRaw = request.getParameter("page");
        int pageNumber = 1;
        if (pageRaw != null) {
            pageNumber = Integer.parseInt(pageRaw);

        }
        int pageSize = 10;
        int totalProducts = productDAO.getTotalProductsFilter(key, pid, cateId, status);
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
        int startIndex = (pageNumber - 1) * pageSize;

        List<Product> products = productDAO.getProducts(startIndex, pageSize, key, pid, cateId, status);
        List<Categories> listCate = productDAO.getAllCategories();
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("products", products);
        request.setAttribute("cates", listCate);
        request.getRequestDispatcher("adminHome.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        if (status != null) {
            String spid = request.getParameter("pid");    
            int pid = Integer.parseInt(spid);
            if (status.equals("Active")) {
                status = "InActive";
            } else {
                status = "Active";
            }
            productDAO.updateProductStatus(pid, status);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("Success");
            out.flush();
        } else {
            String sPid = request.getParameter("productId");
            if (sPid != null) {
                int pid = Integer.parseInt(sPid);
                Product p = productDAO.getProductById(pid);
                List<Categories> listCate = productDAO.getAllCategories();   
                    Gson gson = new Gson();
                    JsonObject responseData = new JsonObject();
                    responseData.addProperty("product", gson.toJson(p));
                    responseData.addProperty("categories", gson.toJson(listCate));
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print(responseData.toString());
                    out.flush();              
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mysql.cj.xdevapi.JsonParser;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.StringReader;
import model.Categories;
import model.Product;

/**
 *
 * @author win
 */
@WebServlet(name = "UpdateProductController", urlPatterns = {"/update-product"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
public class UpdateProductController extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProductController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productJson = request.getParameter("product");

        if (productJson != null && !productJson.isEmpty()) {
            Gson gson = new Gson();
            Product product = gson.fromJson(productJson, Product.class);
            int startIndex = productJson.indexOf("\"categoryId\":") + "\"categoryId\":".length();
            String categoryId = productJson.substring(startIndex, productJson.length());
            categoryId = categoryId.trim().replaceAll("\"", "");
            int cateId = 0;
            if(categoryId.contains("new")){
                cateId = productDAO.addCategory(categoryId.split(";")[0].toString());
            }else{
                cateId = Integer.parseInt(categoryId.split(";")[0].toString()); 
            }
            product.setCateId(cateId);
            product.setSale(product.getSale()/100);
            Product p = productDAO.getProductById(product.getProductId());
            if(p!= null){
                p = new Product(product);
                productDAO.updateProduct(p);
            }
            System.out.println(p);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("Success"); // Ví dụ: trả về một thông báo thành công
            out.flush();
        } else {
            // Xử lý khi không có dữ liệu gửi lên
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        Part filePart = request.getPart("file");
        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
        filePart.write(uploadPath + File.separator + fileName);
        String relativePath = "images/" + fileName;
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.write(relativePath);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

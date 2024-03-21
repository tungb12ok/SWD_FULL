/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import common.OrderResponse;
import dao.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import model.*;

/**
 *
 * @author win
 */
@WebServlet(name = "OrderListController", urlPatterns = {"/order-list"})
public class OrderListController extends HttpServlet {

    private OrderDAO orderDao = new OrderDAO();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    private SettingDAO sd = new SettingDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OrderListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderListController at " + request.getContextPath() + "</h1>");
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
        UserDAO udao = new UserDAO();
        String sStatus = request.getParameter("status");
        String key = request.getParameter("search");
        String admin = request.getParameter("admin");
        List<Order> listO = orderDao.getAllOrders();
        List<Order> listAfter = new ArrayList<>();
        if (key != null) {
            for (Order order : listO) {
                if (order.getEmail().contains(key) || order.getEmail().contains(key)
                        || order.getMobile().contains(key) || udao.getUserById(order.getUpdateBy()).getName().contains(key)) {
                    listAfter.add(order);
                }
                continue;
            }
        } else if (sStatus != null && sStatus != "") {
            int status = Integer.parseInt(sStatus);
            for (Order order : listO) {
                if (order.getStatus() == status) {
                    listAfter.add(order);
                }
                continue;
            }
            System.out.println(status + "vvvv");
            System.out.println(listAfter);
        } else if (admin != null  && admin != "") {
            int adminId = Integer.parseInt(admin);
            for (Order order : listO) {
                if (order.getUpdateBy() == adminId) {
                    listAfter.add(order);
                }
                continue;
            }
        } else {
            listAfter = listO;
        }
        List<User> listAdmin = udao.getAllUsersNameByRole(sd.getIdByName("Admin"));
        if (listAfter.size() == 0) {
            request.setAttribute("pageNumber", 1);
            request.setAttribute("totalPages", 0);
            request.setAttribute("listO", null);
        } else {
            List<OrderResponse> listOR = new ArrayList<OrderResponse>();
            for (Order order : listAfter) {
                List<OrderDetail> listOd = orderDetailDAO.getOrderDetailsByOrderId(order.getOrderId());
                double total = 0;
                OrderResponse oR = new OrderResponse(order);
                User cus = udao.getUserById(order.getUserId());
                User sale = udao.getUserById(order.getUpdateBy());
                if (cus != null) {
                    oR.setReceiver(cus.getName());
                }
                if (sale != null) {
                    oR.setSaler(sale.getName());
                }
                for (OrderDetail od : listOd) {
                    total += od.getAmount() * od.getQuantity();
                }
                oR.setTotal(total);
                listOR.add(oR);
            }
            String pageRaw = request.getParameter("page");
            int pageNumber = 1;
            if (pageRaw != null) {
                pageNumber = Integer.parseInt(pageRaw);

            }
            int pageSize = 10;
            int totalProducts = listOR.size();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
            int startIndex = (pageNumber - 1) * pageSize;
            System.out.println(listOR);
            List<OrderResponse> list = listOR.subList(startIndex, Math.min(startIndex + pageSize, totalProducts));
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("listO", list);
        }
        request.setAttribute("admins", listAdmin);
        List<Setting> list = sd.getSettingByType("Status");
        request.setAttribute("status", list);
        request.getRequestDispatcher("OrderList.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sStatus = request.getParameter("status");
        if (sStatus != null) {
            int status = Integer.parseInt(sStatus);
            String orderId = request.getParameter("oredrId");
            List<Setting> list = sd.getSettingByType("Status");
            for (Setting s : list) {
                if (s.getName().equals("Cancelled")) {
                    orderDao.updateOrderStatus(orderId, s.getId());
                }
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("Success");
            out.flush();

        } else {
            ProductDAO pd = new ProductDAO();
            String soId = request.getParameter("orderId");
            Order od = orderDao.getOrderById(soId);
            List<OrderDetail> listO = orderDetailDAO.getOrderDetailsByOrderId(od.getOrderId());
            Map<String, Product> mapP = new HashMap<>();
            for (OrderDetail odd : listO) {
                Product p = pd.getProductById(odd.getProductId());
                mapP.put(odd.getOrderDetailId(), p);
            }
            Map<Integer, String> mapS = new HashMap<>();
            List<Setting> list = sd.getSettingByType("Status");
            for (Setting s : list) {
                mapS.put(s.getId(), s.getName());
            }
            Gson gson = new Gson();
            JsonObject responseData = new JsonObject();
            responseData.addProperty("map", gson.toJson(mapP));
            responseData.addProperty("list", gson.toJson(listO));
            responseData.addProperty("mapS", gson.toJson(mapS));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(responseData.toString());
            out.flush();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import common.OrderResponse;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.SettingDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.OrderDetail;
import model.Setting;
import model.User;

/**
 *
 * @author win
 */
@WebServlet(name = "OrderHistoryController", urlPatterns = {"/order-history"})
public class OrderHistoryController extends HttpServlet {

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
            out.println("<title>Servlet OrderHistoryController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderHistoryController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
//        if (user == null || user.getRole() != 2) {
//            request.getRequestDispatcher("orderHistory.jsp").forward(request, response);
//        } else {
        UserDAO udao = new UserDAO();
        String sStatus = request.getParameter("status");
        List<Order> listO = orderDao.getAllOrdersByUserId(user.getUserId());
        List<Order> listAfter = new ArrayList<>();
        if(sStatus == "") sStatus = null;
        if (sStatus != null) {
            int status = Integer.parseInt(sStatus);
            for (Order order : listO) {
                if (order.getStatus() == status) {
                    listAfter.add(order);
                }
                continue;
            }
        } else {
            listAfter = listO;
        }
        List<User> listAdmin = udao.getAllUsersNameByRole(1);
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
        List<Setting> list = sd.getSettingByType("Status");
        request.setAttribute("status", list);
        request.setAttribute("admins", listAdmin);
        request.getRequestDispatcher("orderHistory.jsp").forward(request, response);
//        }
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
        UserDAO udao = new UserDAO();
        String orderId = request.getParameter("orderId");
        List<Setting> list = sd.getSettingByType("Status");
        int status = 0;
        for(Setting s : list){
            if(s.getName().equals("Cancelled")) status = s.getId();
        }
        orderDao.updateOrderStatus(orderId, status);
//        List<Order> listO = orderDao.getAllOrders();
//        List<OrderResponse> listOR = new ArrayList<OrderResponse>();
//        for (Order order : listO) {
//            List<OrderDetail> listOd = orderDetailDAO.getOrderDetailsByOrderId(order.getOrderId());
//            double total = 0;
//            OrderResponse oR = new OrderResponse(order);
//            User cus = udao.getUserById(order.getUserId());
//            User sale = udao.getUserById(order.getUpdateBy());
//            if (cus != null) {
//                oR.setReceiver(cus.getName());
//            }
//            if (sale != null) {
//                oR.setSaler(sale.getName());
//            }
//            for (OrderDetail od : listOd) {
//                total += od.getAmount() * od.getQuantity();
//            }
//            oR.setTotal(total);
//            listOR.add(oR);
//        }
//        int pageNumber = 1;
//        int pageSize = 10;
//        int totalProducts = listOR.size();
//        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
//        int startIndex = (pageNumber - 1) * pageSize;
//        System.out.println(listOR);
//        List<OrderResponse> list = listOR.subList(startIndex, Math.min(startIndex + pageSize, totalProducts));
//        request.setAttribute("pageNumber", pageNumber);
//        request.setAttribute("totalPages", totalPages);
//        request.setAttribute("listO", list);
//        request.getRequestDispatcher("orderHistory.jsp").forward(request, response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("Sucess");
        out.flush();
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

package dao;

import model.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DBcontext {

    public boolean addOrder(Order order, int userId) {
        String sql = "INSERT INTO [dbo].[orders] (orderid, userId, amount, status) VALUES (?, ?, ?, ?)";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, order.getOrderId());
            statement.setInt(2, userId);
            statement.setDouble(3, order.getAmount());
            statement.setString(4, order.getStatus());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT orderid, userId, amount, status, time, email, updateTime, updateBy, address, payment, mobile FROM orders";
        try ( PreparedStatement statement = connection.prepareStatement(sql);  ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getString("orderid"));
                order.setUserId(resultSet.getInt("userId"));
                order.setAmount(resultSet.getDouble("amount"));
                order.setStatus(resultSet.getString("status"));
                order.setTime(resultSet.getDate("time"));
                order.setEmail(resultSet.getString("email"));
                order.setUpdateTime(resultSet.getDate("updateTime"));
                order.setUpdateBy(resultSet.getInt("updateBy"));
                order.setAddress(resultSet.getString("address"));
                order.setPayment(resultSet.getString("payment"));
                order.setMobile(resultSet.getString("mobile"));

                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public Order getOrderById(String orderId) {
        String sql = "SELECT orderid, userId, amount, status FROM [dbo].[orders] WHERE orderid = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, orderId);
            try ( ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Order order = new Order();
                    order.setOrderId(resultSet.getString("orderid"));
                    order.setUserId(resultSet.getInt("userId"));
                    order.setAmount(resultSet.getDouble("amount"));
                    order.setStatus(resultSet.getString("status"));
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateOrder(Order order) {
        String sql = "UPDATE [dbo].[orders] SET userId=?, amount=?, status=? WHERE orderid=?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getUserId());
            statement.setDouble(2, order.getAmount());
            statement.setString(3, order.getStatus());
            statement.setString(4, order.getOrderId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrder(String orderId) {
        String sql = "DELETE FROM [dbo].[orders] WHERE orderid=?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, orderId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        OrderDAO dao = new OrderDAO();
        System.out.println(dao.getAllOrders());
        // Add order example
//        Order newOrder = new Order();
//        newOrder.setOrderId("123");
//        newOrder.setUserId(1);
//        newOrder.setAmount(50.00);
//        newOrder.setStatus("Pending");
//        dao.addOrder(newOrder, 1);
//
//        // Get all orders example
//        List<Order> orders = dao.getAllOrders();
//        System.out.println("All Orders: " + orders);
//
//        // Get order by id example
//        Order orderById = dao.getOrderById("123");
//        System.out.println("Order by ID: " + orderById);
//
//        // Update order example
//        Order orderToUpdate = new Order();
//        orderToUpdate.setOrderId("123");
//        orderToUpdate.setUserId(1);
//        orderToUpdate.setAmount(60.00);
//        orderToUpdate.setStatus("Completed");
//        dao.updateOrder(orderToUpdate);
//
//        // Get updated order
//        Order updatedOrder = dao.getOrderById("123");
//        System.out.println("Updated Order: " + updatedOrder);
//
//        // Delete order example
//        dao.deleteOrder("123");
    }
}

package dao;

import java.sql.Date;
import model.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DBcontext {

    public boolean createOrder(Order order) {
        String sql = "INSERT INTO orders (orderid, userId, amount, status, time, email, updateTime, updateBy, address, payment, mobile) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, order.getOrderId());
            if (order.getUserId() == 0) {
                statement.setNull(2, Types.INTEGER);
            } else {
                statement.setInt(2, order.getUserId());
            }
            statement.setDouble(3, order.getAmount());
            statement.setInt(4, order.getStatus());
            statement.setDate(5, new java.sql.Date(order.getTime().getTime()));
            statement.setString(6, order.getEmail());
            statement.setDate(7, new java.sql.Date(order.getUpdateTime().getTime()));
            if (order.getUpdateBy()== 0) {
                statement.setNull(8, Types.INTEGER);
            } else {
                statement.setInt(8, order.getUpdateBy());
            }
            statement.setString(9, order.getAddress());
            statement.setString(10, order.getPayment());
            statement.setString(11, order.getMobile());

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
                order.setStatus(resultSet.getInt("status"));
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

    public List<Order> getAllOrdersByUserId(int userId) {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT orderid, userId, amount, status, time, email, updateTime, updateBy, address, payment, mobile FROM orders where userId = ? order by time desc";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getString("orderid"));
                order.setUserId(resultSet.getInt("userId"));
                order.setAmount(resultSet.getDouble("amount"));
                order.setStatus(resultSet.getInt("status"));
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
        String sql = "SELECT orderid, userId, amount, status, time, email, updateTime, updateBy, address, payment, mobile FROM orders WHERE orderid like ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, orderId);
            try ( ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Order order = new Order();
                    order.setOrderId(resultSet.getString("orderid"));
                    order.setUserId(resultSet.getInt("userId"));
                    order.setAmount(resultSet.getDouble("amount"));
                    order.setStatus(resultSet.getInt("status"));
                    order.setTime(resultSet.getDate("time"));
                    order.setEmail(resultSet.getString("email"));
                    order.setUpdateTime(resultSet.getDate("updateTime"));
                    order.setUpdateBy(resultSet.getInt("updateBy"));
                    order.setAddress(resultSet.getString("address"));
                    order.setPayment(resultSet.getString("payment"));
                    order.setMobile(resultSet.getString("mobile"));
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
            statement.setInt(3, order.getStatus());
            statement.setString(4, order.getOrderId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateOrderStatus(String orderId, int status) {
        String sql = "UPDATE orders SET status=? WHERE orderid=?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, status);
            statement.setString(2, orderId);

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
        System.out.println(dao.getAllOrdersByUserId(1));
        OrderDAO orderDAO = new OrderDAO();

        // Create a sample order
        Order order = new Order();
        order.setOrderId("1211345"); // Set a unique order ID
        order.setAmount(100.0); // Set the order amount
        order.setStatus(8); // Set the order status
        order.setTime(new Date(System.currentTimeMillis())); // Set the current time as the order time
        order.setEmail("example@example.com"); // Set the user's email
        order.setUpdateTime(new Date(System.currentTimeMillis())); // Set the current time as the update time
        order.setAddress("123 Example St, City, Country"); // Set the delivery address
        order.setPayment("Credit Card"); // Set the payment method
        order.setMobile("1234567890"); // Set the user's mobile number

        // Call the createOrder method and check if the order was successfully created
        boolean success = orderDAO.createOrder(order);
        System.out.println(success);
    }
}

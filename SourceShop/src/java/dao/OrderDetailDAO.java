package dao;

import model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO extends DBcontext {

    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT odId, orderId, productId, quantity, amount FROM orderdetail WHERE orderId like ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, orderId.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderDetailId(resultSet.getString("odId"));
                    orderDetail.setOrderId(resultSet.getString("orderId"));
                    orderDetail.setProductId(resultSet.getInt("productId"));
                    orderDetail.setQuantity(resultSet.getInt("quantity"));
                    orderDetail.setAmount(resultSet.getDouble("amount"));

                    orderDetails.add(orderDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    public boolean addOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO [dbo].[orderDetail] (odId, orderId, productId, quantity, amount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, orderDetail.getOrderDetailId());
            statement.setString(2, orderDetail.getOrderId());
            statement.setInt(3, orderDetail.getProductId());
            statement.setInt(4, orderDetail.getQuantity());
            statement.setDouble(5, orderDetail.getAmount());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateOrderDetail(OrderDetail orderDetail) {
        String sql = "UPDATE [dbo].[orderDetail] SET productId=?, quantity=?, amount=? WHERE odId=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderDetail.getProductId());
            statement.setInt(2, orderDetail.getQuantity());
            statement.setDouble(3, orderDetail.getAmount());
            statement.setString(4, orderDetail.getOrderDetailId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrderDetail(int orderDetailId) {
        String sql = "DELETE FROM [dbo].[orderDetail] WHERE odId=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderDetailId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        OrderDetailDAO dao = new OrderDetailDAO();
        System.out.println(dao.getOrderDetailsByOrderId("ORD123456"));

        // Get order details by order ID example
//        List<OrderDetail> orderDetails = dao.getOrderDetailsByOrderId("your_order_id");
//        System.out.println("Order Details by Order ID: " + orderDetails);
//
//        // Add order detail example
//        OrderDetail newOrderDetail = new OrderDetail();
//        newOrderDetail.setOrderDetailId(1);  // Set your own order detail ID
//        newOrderDetail.setOrderId("your_order_id");
//        newOrderDetail.setProductId(1);  // Set your own product ID
//        newOrderDetail.setQuantity(2);  // Set quantity
//        newOrderDetail.setAmount(25.00);  // Set amount
//        dao.addOrderDetail(newOrderDetail);
//
//        // Update order detail example
//        OrderDetail orderDetailToUpdate = new OrderDetail();
//        orderDetailToUpdate.setOrderDetailId(1);  // Set the order detail ID to update
//        orderDetailToUpdate.setProductId(2);  // Set the new product ID
//        orderDetailToUpdate.setQuantity(3);  // Set the new quantity
//        orderDetailToUpdate.setAmount(30.00);  // Set the new amount
//        dao.updateOrderDetail(orderDetailToUpdate);
//
//        // Delete order detail example
//        dao.deleteOrderDetail(1);  // Specify the order detail ID to delete
    }
}

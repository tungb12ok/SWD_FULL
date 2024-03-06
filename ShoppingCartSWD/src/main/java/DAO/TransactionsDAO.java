package DAO;

import Model.Transactions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionsDAO extends DBcontext {

    public List<Transactions> getAllTransactions() {
        List<Transactions> transactionsList = new ArrayList<>();
        String sql = "SELECT transId, userId, time, amount, status, orderId, email, updateTime, updateBy, name, mobile, address, payment FROM [dbo].[transactions]";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Transactions transactions = new Transactions();
                transactions.setTransId(resultSet.getInt("transId"));
                transactions.setUserId(resultSet.getInt("userId"));
                transactions.setTime(resultSet.getDate("time"));
                transactions.setAmount(resultSet.getDouble("amount"));
                transactions.setStatus(resultSet.getString("status"));
                transactions.setOrderId(resultSet.getString("orderId"));
                transactions.setEmail(resultSet.getString("email"));
                transactions.setUpdateTime(resultSet.getDate("updateTime"));
                transactions.setUpdateBy(resultSet.getInt("updateBy"));
                transactions.setName(resultSet.getString("name"));
                transactions.setMobile(resultSet.getString("mobile"));
                transactions.setAddress(resultSet.getString("address"));
                transactions.setPayment(resultSet.getString("payment"));

                transactionsList.add(transactions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionsList;
    }

    public boolean addTransaction(Transactions transactions) {
        String sql = "INSERT INTO [dbo].[transactions] (userId, time, amount, status, orderId, email, updateTime, updateBy, name, mobile, address, payment) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, transactions.getUserId());
            statement.setDate(2, transactions.getTime());
            statement.setDouble(3, transactions.getAmount());
            statement.setString(4, transactions.getStatus());
            statement.setString(5, transactions.getOrderId());
            statement.setString(6, transactions.getEmail());
            statement.setDate(7, transactions.getUpdateTime());
            statement.setInt(8, transactions.getUpdateBy());
            statement.setString(9, transactions.getName());
            statement.setString(10, transactions.getMobile());
            statement.setString(11, transactions.getAddress());
            statement.setString(12, transactions.getPayment());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTransaction(Transactions transactions) {
        String sql = "UPDATE [dbo].[transactions] SET userId=?, time=?, amount=?, status=?, orderId=?, email=?, " +
                "updateTime=?, updateBy=?, name=?, mobile=?, address=?, payment=? WHERE transId=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, transactions.getUserId());
            statement.setDate(2, transactions.getTime());
            statement.setDouble(3, transactions.getAmount());
            statement.setString(4, transactions.getStatus());
            statement.setString(5, transactions.getOrderId());
            statement.setString(6, transactions.getEmail());
            statement.setDate(7, transactions.getUpdateTime());
            statement.setInt(8, transactions.getUpdateBy());
            statement.setString(9, transactions.getName());
            statement.setString(10, transactions.getMobile());
            statement.setString(11, transactions.getAddress());
            statement.setString(12, transactions.getPayment());
            statement.setInt(13, transactions.getTransId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTransaction(int transId) {
        String sql = "DELETE FROM [dbo].[transactions] WHERE transId=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, transId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        TransactionsDAO dao = new TransactionsDAO();

        // Get all transactions example
        List<Transactions> transactionsList = dao.getAllTransactions();
        System.out.println("All Transactions: " + transactionsList);

        // Add transaction example
        Transactions newTransaction = new Transactions();
        // Set data for the new transaction
        dao.addTransaction(newTransaction);

        // Update transaction example
        Transactions transactionToUpdate = new Transactions();
        // Set data for the transaction to update
        dao.updateTransaction(transactionToUpdate);

        // Delete transaction example
        dao.deleteTransaction(1);  // Specify the transId to delete
    }
}

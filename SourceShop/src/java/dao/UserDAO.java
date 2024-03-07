package dao;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBcontext {

    public boolean addUser(User user) {
        String sql = "INSERT INTO [dbo].[user] (email, name, mobile, address, pincode, password, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getMobile());
            statement.setString(4, user.getAddress());
            statement.setInt(5, user.getPincode());
            statement.setString(6, user.getPassword());
            statement.setString(7, user.getStatus());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT userId, email, name, mobile, address, pincode, password, status FROM [dbo].[user]";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setMobile(resultSet.getString("mobile"));
                user.setAddress(resultSet.getString("address"));
                user.setPincode(resultSet.getInt("pincode"));
                user.setPassword(resultSet.getString("password"));
                user.setStatus(resultSet.getString("status"));

                userList.add(user);
            }
        } catch (SQLException e) {
        }
        return userList;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT userId, email, name, mobile, address, pincode, password, status FROM [dbo].[user] WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getInt("userId"));
                    user.setEmail(resultSet.getString("email"));
                    user.setName(resultSet.getString("name"));
                    user.setMobile(resultSet.getString("mobile"));
                    user.setAddress(resultSet.getString("address"));
                    user.setPincode(resultSet.getInt("pincode"));
                    user.setPassword(resultSet.getString("password"));
                    user.setStatus(resultSet.getString("status"));
                    return user;
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE [dbo].[user] SET email=?, name=?, mobile=?, address=?, pincode=?, password=?, status=? WHERE userId=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getMobile());
            statement.setString(4, user.getAddress());
            statement.setInt(5, user.getPincode());
            statement.setString(6, user.getPassword());
            statement.setString(7, user.getStatus());
            statement.setInt(8, user.getUserId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteUser(User user) {
        String sql = "DELETE FROM [dbo].[user] WHERE userId=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getUserId());

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        // Test methods here
        System.out.println("All Users: " + dao.getAllUsers());
        System.out.println("User by Email: " + dao.getUserByEmail("test@example.com"));
    }
}

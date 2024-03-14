package dao;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBcontext {

    public boolean createUser(User user) {
        String sql = "INSERT INTO [dbo].[user] (email, name, mobile, address, pincode, password, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getMobile());
            statement.setString(4, user.getAddress());
            statement.setInt(5, user.getPincode());
            statement.setString(6, user.getPassword());
            statement.setInt(7, user.getStatus());
            statement.setInt(8, user.getRole());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT userId, email, name, mobile, address, pincode, password, status, role FROM user";
        try ( PreparedStatement statement = connection.prepareStatement(sql);  ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setMobile(resultSet.getString("mobile"));
                user.setAddress(resultSet.getString("address"));
                user.setPincode(resultSet.getInt("pincode"));
                user.setPassword(resultSet.getString("password"));
                user.setStatus(resultSet.getInt("status"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
        return userList;
    }

    public List<User> getAllUsersNameByRole(int role) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT name, userId FROM user where role= ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, role);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User u = new User();
                u.setName(resultSet.getString("name"));
                u.setUserId(resultSet.getInt("userId"));
                list.add(u);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT userId, email, name, mobile, address, pincode, password, status FROM [dbo].[user] WHERE email = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try ( ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getInt("userId"));
                    user.setEmail(resultSet.getString("email"));
                    user.setName(resultSet.getString("name"));
                    user.setMobile(resultSet.getString("mobile"));
                    user.setAddress(resultSet.getString("address"));
                    user.setPincode(resultSet.getInt("pincode"));
                    user.setPassword(resultSet.getString("password"));
                    user.setStatus(resultSet.getInt("status"));
                    return user;
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }

   

    

    // Update an existing user
    public boolean updateUser(User user) {
        String sql = "UPDATE user SET email = ?, name = ?, mobile = ?, address = ?, pincode = ?, password = ?, status = ?, role = ? WHERE userId = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getMobile());
            statement.setString(4, user.getAddress());
            statement.setInt(5, user.getPincode());
            statement.setString(6, user.getPassword());
            statement.setInt(7, user.getStatus());
            statement.setInt(8, user.getRole());
            statement.setInt(9, user.getUserId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return false;
        }
    }

    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT * FROM user WHERE userId = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try ( ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setUserId(resultSet.getInt("userId"));
                    user.setEmail(resultSet.getString("email"));
                    user.setName(resultSet.getString("name"));
                    user.setMobile(resultSet.getString("mobile"));
                    user.setAddress(resultSet.getString("address"));
                    user.setPincode(resultSet.getInt("pincode"));
                    user.setPassword(resultSet.getString("password"));
                    user.setStatus(resultSet.getInt("status"));
                    user.setRole(resultSet.getInt("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
        return user;
    }

    // Delete an existing user
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE userId = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return false;
        }
    }

    public User login(String email, String password) {
        User user = null;
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try ( ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setUserId(resultSet.getInt("userId"));
                    user.setEmail(resultSet.getString("email"));
                    user.setName(resultSet.getString("name"));
                    user.setMobile(resultSet.getString("mobile"));
                    user.setAddress(resultSet.getString("address"));
                    user.setPincode(resultSet.getInt("pincode"));
                    user.setPassword(resultSet.getString("password"));
                    user.setStatus(resultSet.getInt("status"));
                    user.setRole(resultSet.getInt("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
        return user;
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        // Tạo một đối tượng UserDAO
        UserDAO userDAO = new UserDAO();

        System.out.println(userDAO.login("user2@example.com", "23456"));

        // Thêm người dùng mới vào cơ sở dữ liệu
    }
}

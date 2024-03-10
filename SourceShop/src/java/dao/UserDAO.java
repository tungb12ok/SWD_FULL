package dao;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBcontext {

    // Retrieve all users from the database
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
                user.setStatus(resultSet.getString("status"));
                user.setRole(resultSet.getInt("role")); // Add role setting

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
        return userList;
    }

    // Create a new user
    public boolean createUser(User user) {
        String sql = "INSERT INTO user (email, name, mobile, address, pincode, password, status, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getMobile());
            statement.setString(4, user.getAddress());
            statement.setInt(5, user.getPincode());
            statement.setString(6, user.getPassword());
            statement.setString(7, user.getStatus());
            statement.setInt(8, user.getRole());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return false;
        }
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
            statement.setString(7, user.getStatus());
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
                    user.setStatus(resultSet.getString("status"));
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
                    user.setStatus(resultSet.getString("status"));
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

        // Tạo một đối tượng User mới để thêm vào cơ sở dữ liệu
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        newUser.setName("New User");
        newUser.setMobile("1234567890");
        newUser.setAddress("123 New Street");
        newUser.setPincode(12345);
        newUser.setPassword("password");
        newUser.setStatus("active");
        newUser.setRole(1);

        // Thêm người dùng mới vào cơ sở dữ liệu
        boolean success = userDAO.createUser(newUser);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Contact;

/**
 *
 * @author tungl
 */
public class ContactDAO extends DBcontext {

    // Create operation - Add a new contact
    public boolean addContact(Contact contact) {
        String sql = "INSERT INTO contact (userId, description, settingId) VALUES (?, ?, ?)";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, contact.getUserId());
            statement.setString(2, contact.getDescription());
            statement.setInt(3, contact.getSettingId());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return false;
        }
    }

    // Read operation - Retrieve all contacts
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contact";
        try ( PreparedStatement statement = connection.prepareStatement(sql);  ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setUserId(resultSet.getInt("userId"));
                contact.setDescription(resultSet.getString("description"));
                contact.setSettingId(resultSet.getInt("settingId"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
        return contacts;
    }

    // Update operation - Update a contact
    public boolean updateContact(Contact contact) {
        String sql = "UPDATE contacts SET description=?, settingId=? WHERE userId=?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contact.getDescription());
            statement.setInt(2, contact.getSettingId());
            statement.setInt(3, contact.getUserId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return false;
        }
    }

    // Delete operation - Delete a contact by userId
    public boolean deleteContact(int userId) {
        String sql = "DELETE FROM contact WHERE userId=?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return false;
        }
    }
    public static void main(String[] args) {
        ContactDAO dao = new ContactDAO();
        dao.getAllContacts();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Categories;

/**
 *
 * @author tungl
 */
public class CateDAO extends DBcontext {

    public List<Categories> getAll() throws SQLException, ClassNotFoundException {
        List<Categories> categories = new ArrayList<>();
        String query = "SELECT `id`, `name` FROM `categories`";
        try ( Connection connection = super.connection;  PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categories category = new Categories();
                category.setCateId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }
        }
        return categories;
    }

    public void addCategory(Categories category) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO `categories` (`name`) VALUES (?)";
        try ( Connection connection = super.connection;  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, category.getName());
            ps.executeUpdate();
        }
    }

    public void updateCategory(Categories category) throws SQLException, ClassNotFoundException {
        String query = "UPDATE `categories` SET `name` = ? WHERE `id` = ?";
        try ( Connection connection = super.connection;  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getCateId());
            ps.executeUpdate();
        }
    }

    public void deleteCategory(int cateId) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM `categories` WHERE `id` = ?";
        try ( Connection connection = super.connection;  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, cateId);
            ps.executeUpdate();
        }
    }

    public Categories getCategoryById(int cateId) throws SQLException, ClassNotFoundException {
        String query = "SELECT `id`, `name` FROM `categories` WHERE `id` = ?";
        Categories category = null;
        try ( Connection connection = super.connection;  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, cateId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                category = new Categories();
                category.setCateId(rs.getInt("id"));
                category.setName(rs.getString("name"));
            }
        }
        return category;
    }
    public static void main(String[] args) {
        try {
            CateDAO dao = new CateDAO();
            System.out.println(dao.getAll());
        } catch (SQLException ex) {
            Logger.getLogger(CateDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

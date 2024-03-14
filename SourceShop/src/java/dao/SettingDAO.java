/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author win
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

public class SettingDAO extends DBcontext {

    public List<Setting> getSettingByType(String type) {
        List<Setting> settings = new ArrayList<>();
        String sql = "SELECT id, name, type, status FROM setting WHERE type = ? and status = 'Active'";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, type);
            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Setting set = new Setting();
                    set.setName(resultSet.getString("name"));
                    set.setStatus(resultSet.getString("status"));
                    set.setId(resultSet.getInt("id"));
                    set.setType(resultSet.getString("type"));
                    settings.add(set);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settings;
    }

    public Setting getSettingById(int id) {
        Setting set = new Setting();
        String sql = "SELECT id, name, type, status FROM setting WHERE id = ? and status = 'Active'";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    set.setName(resultSet.getString("name"));
                    set.setStatus(resultSet.getString("status"));
                    set.setId(resultSet.getInt("id"));
                    set.setType(resultSet.getString("type"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return set;
    }

    public int getIdByName(String name) {
        int id = 0;
        String sql = "SELECT id FROM setting WHERE name = ? and status = 'Active'";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    id = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int addSetting(Setting setting) {
        String query = "INSERT INTO setting(name, type, status) VALUES (?, ?, ?)";
        int generatedId = -1;
        try ( PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, setting.getName());
            ps.setString(2, setting.getType());
            ps.setString(3, setting.getStatus());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                // Lấy các khóa được tạo ra
                try ( ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    public static void main(String[] args) {
        SettingDAO sd = new SettingDAO();
        System.out.println(sd.getSettingByType("Category"));
    }
}

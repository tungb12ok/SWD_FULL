/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

import dao.SettingDAO;
import java.util.List;
import model.Setting;

/**
 *
 * @author win
 */
public class SettingSystem {
    SettingDAO sd = new SettingDAO();
    public List<Setting> listRole = sd.getSettingByType("Role");
    public List<Setting> listCate = sd.getSettingByType("Category");
    public List<Setting> listStatus = sd.getSettingByType("Status");
    public List<Setting> listProduct = sd.getSettingByType("Product");
}

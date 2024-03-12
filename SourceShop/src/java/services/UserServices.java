/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.UserDAO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.User;

/**
 *
 * @author tungl
 */
public class UserServices {

    UserDAO dao = new UserDAO();

    public boolean signUp(User u) {
        if (u == null) {
            throw new IllegalArgumentException("User object is null.");
        }
        if (u.getEmail() == null || !isValidEmail(u.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (u.getName() == null || u.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (u.getMobile() == null || !isValidMobile(u.getMobile())) {
            throw new IllegalArgumentException("Invalid mobile number format.");
        }
        if (u.getAddress() == null || u.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Address is required.");
        }
        if (u.getPincode() <= 0) {
            throw new IllegalArgumentException("Invalid pincode.");
        }
        if (u.getPassword() == null || u.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
        }
        if (u.getStatus() == 0) {
            throw new IllegalArgumentException("Status is required.");
        }
        try {
            return dao.createUser(u);
        } catch (Exception e) {
            throw new IllegalAccessError("Username and email doesn't exits");
        }
    }

    // Method to validate email format using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to validate mobile number format using regex
    private boolean isValidMobile(String mobile) {
        String mobileRegex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        Pattern pattern = Pattern.compile(mobileRegex);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }
}

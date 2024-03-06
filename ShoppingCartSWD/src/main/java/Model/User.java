/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import lombok.Data;

@Data
public class User {
    private int userId;
    private String email;
    private String name;
    private String mobile;
    private String address;
    private int pincode;
    private String password;
    private String status;
}

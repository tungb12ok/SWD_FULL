/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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
    private int status;
    private int role;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import lombok.Data;

/**
 *
 * @author tungl
 */
@Data
public class Order {
    private String orderId;
    private int userId;
    private double amount;
    private String status;
    private Date time;
    private String email;
    private Date updateTime;
    private int updateBy;
    private String address;
    private String payment;
    private String mobile;
}

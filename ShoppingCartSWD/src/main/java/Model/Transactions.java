/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import lombok.Data;

/**
 *
 * @author tungl
 */
@Data
public class Transactions {
    private int transId;
    private int userId;
    private Date time;
    private double amount;
    private String status ;
    private String orderId;
    private String email;
    private Date updateTime;
    private int updateBy;
    private String name;
    private String mobile;
    private String address;
    private String payment;
    
}

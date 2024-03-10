/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.Data;

/**
 *
 * @author tungl
 */
@Data
public class OrderDetail {
    private String orderDetailId;
    private String orderId;
    private int productId;
    private int quantity;
    private double amount;
}

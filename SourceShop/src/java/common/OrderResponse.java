/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

import java.sql.Date;
import lombok.Data;
import model.Order;

/**
 *
 * @author win
 */
@Data
public class OrderResponse {

    private String orderId;
    private Date orderDate;
    private double total;
    private String receiver;
    private String mobile;
    private String email;
    private String address;
    private String saler;
    private String status;
    
    public OrderResponse(Order order){
        this.orderId = order.getOrderId();
        this.orderDate = order.getTime();
        this.mobile = order.getMobile();
        this.address = order.getAddress();
        this.status = order.getStatus();
        this.email = order.getEmail();
    }
}

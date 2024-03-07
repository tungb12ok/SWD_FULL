/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.Data;

@Data
public class Product {
    private int productId;
    private String productName;
    private int cateId;
    private String productInfo;
    private double productPrice;
    private int productQuantity;
    private String image;
    private String status;
    private double sale;
}

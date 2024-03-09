/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.ProductDAO;
import java.util.List;
import model.Product;

/**
 *
 * @author tungl
 */
public class ProductService {
    public List<Product> getProduct(String status){
        ProductDAO dao = new ProductDAO();
        return dao.getAllProducts(status);
    }
    
    public static void main(String[] args) {
        ProductService ps = new ProductService();
        System.out.println(ps.getProduct("Active").size());
    }
}

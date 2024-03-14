/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ProductDAO;
import java.util.List;
import model.Product;

/**
 *
 * @author tungl
 */
public class ProductService {

    ProductDAO dao = new ProductDAO();

    public List<Product> getProduct(String status) {
        return dao.getAllProducts(status);
    }

    public Product getProductById(int id) {
        return dao.getProductById(id);
    }

    public static void main(String[] args) {
        ProductService ps = new ProductService();
        System.out.println(ps.getProduct("12"));
    }
}

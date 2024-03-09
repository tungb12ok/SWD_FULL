package dao;

import model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Categories;

public class ProductDAO extends DBcontext {
    
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO product (pname, cateId, pinfo, pprice, pquantity, image, status, sale) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getCateId());
            statement.setString(3, product.getProductInfo());
            statement.setDouble(4, product.getProductPrice());
            statement.setInt(5, product.getProductQuantity());
            statement.setString(6, product.getImage());
            statement.setString(7, product.getStatus());
            statement.setDouble(8, product.getSale());
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT pid, pname, cateId, pinfo, pprice, pquantity, image, status, sale FROM product";
        try ( PreparedStatement statement = connection.prepareStatement(sql);  ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("pid"));
                product.setProductName(resultSet.getString("pname"));
                product.setCateId(resultSet.getInt("cateId"));
                product.setProductInfo(resultSet.getString("pinfo"));
                product.setProductPrice(resultSet.getDouble("pprice"));
                product.setProductQuantity(resultSet.getInt("pquantity"));
                product.setImage(resultSet.getString("image"));
                product.setStatus(resultSet.getString("status"));
                product.setSale(resultSet.getDouble("sale"));
                
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    
    public Product getProductById(int productId) {
        String sql = "SELECT pid, pname, cateId, pinfo, pprice, pquantity, image, status, sale FROM product WHERE pid = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try ( ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("pid"));
                    product.setProductName(resultSet.getString("pname"));
                    product.setCateId(resultSet.getInt("cateId"));
                    product.setProductInfo(resultSet.getString("pinfo"));
                    product.setProductPrice(resultSet.getDouble("pprice"));
                    product.setProductQuantity(resultSet.getInt("pquantity"));
                    product.setImage(resultSet.getString("image"));
                    product.setStatus(resultSet.getString("status"));
                    product.setSale(resultSet.getDouble("sale"));
                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean updateProduct(Product product) {
        String sql = "UPDATE product SET pname=?, cateId=?, pinfo=?, pprice=?, pquantity=?, image=?, status=?, sale=? WHERE pid=?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getCateId());
            statement.setString(3, product.getProductInfo());
            statement.setDouble(4, product.getProductPrice());
            statement.setInt(5, product.getProductQuantity());
            statement.setString(6, product.getImage());
            statement.setString(7, product.getStatus());
            statement.setDouble(8, product.getSale());
            statement.setInt(9, product.getProductId());
            
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM product WHERE pid=?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getTotalProducts() {
        String sql = "SELECT COUNT(*) AS total FROM product";
        int totalProducts = 0;
        try ( PreparedStatement statement = connection.prepareStatement(sql);  ResultSet resultSet = statement.executeQuery()) {
            
            if (resultSet.next()) {
                totalProducts = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return totalProducts;
    }
    
    public List<Product> getProducts(int startIndex, int pageSize) {
        List<Product> products = new ArrayList<>();
        // For MySQL, use LIMIT and OFFSET
        String sql = "SELECT * FROM product ORDER BY pid LIMIT ? OFFSET ?";
        
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, startIndex);
            
            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("pid"));
                    product.setProductName(resultSet.getString("pname"));
                    product.setCateId(resultSet.getInt("cateId"));
                    product.setProductInfo(resultSet.getString("pinfo"));
                    product.setProductPrice(resultSet.getDouble("pprice"));
                    product.setProductQuantity(resultSet.getInt("pquantity"));
                    product.setImage(resultSet.getString("image"));
                    product.setStatus(resultSet.getString("status"));
                    product.setSale(resultSet.getDouble("sale"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }
    
    public List<Categories> getAllCategories() {
        List<Categories> categorieses = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {            
            
            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Categories cate = new Categories();
                    cate.setCateId(resultSet.getInt("id"));
                    cate.setName(resultSet.getString("name"));
                    categorieses.add(cate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categorieses;
    }
    
    public Categories getCategoriesByName(String name) {
       Categories cate = new Categories();
        String sql = "SELECT * FROM categories where name =?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {            
            statement.setString(1, name);
            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cate.setCateId(resultSet.getInt("id"));
                    cate.setName(resultSet.getString("name"));                   
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cate;
    }
    
    public int addCategory(String name) {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try {
           PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);           
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {            
           ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        }
        return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        System.out.println(dao.addCategory("Ngoc111123"));
//        // Test addProduct method
//        Product newProduct = new Product();
//        newProduct.setProductName("Test Product");
//        newProduct.setCateId(1); // Assuming category ID 1 exists
//        newProduct.setProductInfo("This is a test product");
//        newProduct.setProductPrice(10.99);
//        newProduct.setProductQuantity(100);
//        newProduct.setImage("test_image.jpg");
//        newProduct.setStatus("Active");
//        newProduct.setSale(0.0); // No sale currently
//
//        boolean addSuccess = dao.addProduct(newProduct);
//        if (addSuccess) {
//            System.out.println("Product added successfully.");
//        } else {
//            System.out.println("Failed to add product.");
//        }
//
//        // Test getAllProducts method
//        List<Product> allProducts = dao.getAllProducts();
//        System.out.println("All Products:");
//        for (Product product : allProducts) {
//            System.out.println(product);
//        }
//
//        // Test updateProduct method
//        if (!allProducts.isEmpty()) {
//            Product productToUpdate = allProducts.get(0); // Update the first product
//            productToUpdate.setProductName("Updated Product Name");
//            boolean updateSuccess = dao.updateProduct(productToUpdate);
//            if (updateSuccess) {
//                System.out.println("Product updated successfully.");
//            } else {
//                System.out.println("Failed to update product.");
//            }
//        }
//
//        // Test deleteProduct method
//        if (!allProducts.isEmpty()) {
//            int productIdToDelete = allProducts.get(0).getProductId(); // Delete the first product
//            boolean deleteSuccess = dao.deleteProduct(productIdToDelete);
//            if (deleteSuccess) {
//                System.out.println("Product deleted successfully.");
//            } else {
//                System.out.println("Failed to delete product.");
//            }
//        }
    }
}

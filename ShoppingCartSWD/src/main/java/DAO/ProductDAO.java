package DAO;

import Model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DBcontext {

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO [dbo].[product] (pid, pname, ptype, pinfo, pprice, pquantity, image, status, sale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, product.getProductId());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getProductType());
            statement.setString(4, product.getProductInfo());
            statement.setDouble(5, product.getProductPrice());
            statement.setInt(6, product.getProductQuantity());
            statement.setString(7, product.getImage());
            statement.setString(8, product.getStatus());
            statement.setDouble(9, product.getSale());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read All
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT pid, pname, ptype, pinfo, pprice, pquantity, image, status, sale FROM [dbo].[product]";
        try ( PreparedStatement statement = connection.prepareStatement(sql);  ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("pid"));
                product.setProductName(resultSet.getString("pname"));
                product.setProductType(resultSet.getString("ptype"));
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

    // Read One by ID
    public Product getProductById(int productId) {
        String sql = "SELECT pid, pname, ptype, pinfo, pprice, pquantity, image, status, sale FROM [dbo].[product] WHERE pid = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try ( ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("pid"));
                    product.setProductName(resultSet.getString("pname"));
                    product.setProductType(resultSet.getString("ptype"));
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

    // Update
    public boolean updateProduct(Product product) {
        String sql = "UPDATE [dbo].[product] SET pname=?, ptype=?, pinfo=?, pprice=?, pquantity=?, image=?, status=?, sale=? WHERE pid=?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductType());
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

    public boolean deleteProduct(Product product) {
        String sql = "UPDATE [dbo].[product] SET status=? WHERE pid=?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getStatus());
            statement.setInt(2, product.getProductId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();

        System.out.println("sout" + dao.getAllProducts());
    }

    // get total number product
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
    
    // get list product by page
    public List<Product> getProducts(int startIndex, int pageSize){
         List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product ORDER BY pid OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"; 
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, startIndex);
            statement.setInt(2, pageSize);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("pid"));
                    product.setProductName(resultSet.getString("pname"));
                    product.setProductType(resultSet.getString("ptype"));
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
}

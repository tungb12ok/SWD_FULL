package dao;

import model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DBcontext {

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO product (pname, settingId, pinfo, pprice, pquantity, image, status, sale) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getCateId());
            statement.setString(3, product.getProductInfo());
            statement.setDouble(4, product.getProductPrice());
            statement.setInt(5, product.getProductQuantity());
            statement.setString(6, product.getImage());
            statement.setInt(7, product.getStatus());
            statement.setDouble(8, product.getSale());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> getFilteredProducts(String key, String productType) {
        List<Product> filteredProductList = new ArrayList<>();
        String sql = "SELECT pid, pname, settingId, pinfo, pprice, pquantity, image, status, sale FROM product WHERE 1=1";

        // Adjust the SQL query based on the input parameters
        if (key != null && !key.isEmpty()) {
            sql += " AND (pname LIKE ? OR pinfo LIKE ?)";
        }
        if (productType != null && !productType.isEmpty()) {
            sql += " AND cateId = (SELECT cid FROM category WHERE cat_name = ?)";
        }

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;

            // Set parameters based on the input values
            if (key != null && !key.isEmpty()) {
                String likeKey = "%" + key + "%";
                statement.setString(parameterIndex++, likeKey);
                statement.setString(parameterIndex++, likeKey);
            }
            if (productType != null && !productType.isEmpty()) {
                statement.setString(parameterIndex, productType);
            }

            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("pid"));
                    product.setProductName(resultSet.getString("pname"));
                    product.setCateId(resultSet.getInt("settingId"));
                    product.setProductInfo(resultSet.getString("pinfo"));
                    product.setProductPrice(resultSet.getDouble("pprice"));
                    product.setProductQuantity(resultSet.getInt("pquantity"));
                    product.setImage(resultSet.getString("image"));
                    product.setStatus(resultSet.getInt("status"));
                    product.setSale(resultSet.getDouble("sale"));
                    filteredProductList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filteredProductList;
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
                product.setStatus(resultSet.getInt("status"));
                product.setSale(resultSet.getDouble("sale"));

                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getAllProducts(String status) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT pid, pname, settingId, pinfo, pprice, pquantity, image, status, sale FROM product WHERE status = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("pid"));
                product.setProductName(resultSet.getString("pname"));
                product.setCateId(resultSet.getInt("settingId"));
                product.setProductInfo(resultSet.getString("pinfo"));
                product.setProductPrice(resultSet.getDouble("pprice"));
                product.setProductQuantity(resultSet.getInt("pquantity"));
                product.setImage(resultSet.getString("image"));
                product.setStatus(resultSet.getInt("status"));
                product.setSale(resultSet.getDouble("sale"));

                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public Product getProductById(int productId) {
        Product product = null;
        String sql = "SELECT pid, pname, settingId, pinfo, pprice, pquantity, image, status, sale FROM product WHERE pid = ?";

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                product = new Product();
                product.setProductId(resultSet.getInt("pid"));
                product.setProductName(resultSet.getString("pname"));
                product.setCateId(resultSet.getInt("settingId"));
                product.setProductInfo(resultSet.getString("pinfo"));
                product.setProductPrice(resultSet.getDouble("pprice"));
                product.setProductQuantity(resultSet.getInt("pquantity"));
                product.setImage(resultSet.getString("image"));
                product.setStatus(resultSet.getInt("status"));
                product.setSale(resultSet.getDouble("sale"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public boolean updateProduct(Product product) {
        String sql = "UPDATE product SET pname=?, settingId=?, pinfo=?, pprice=?, pquantity=?, image=?, status=?, sale=? WHERE pid=?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getCateId());
            statement.setString(3, product.getProductInfo());
            statement.setDouble(4, product.getProductPrice());
            statement.setInt(5, product.getProductQuantity());
            statement.setString(6, product.getImage());
            statement.setInt(7, product.getStatus());
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

    public int getTotalProductsFilter(String key, int pid, int cateId, int statusId) {
        String sql = "SELECT COUNT(*) AS total FROM product";
        if (key != null || pid != -1) {
            sql += " WHERE pid = ? OR pname LIKE ?";
        } else {
            sql += " where pname like '%%'";
        }
        if (cateId != 0) {
            sql += " AND settingId = ? ";
        }
        if (statusId != 0) {
            sql += " AND status = ? ;";
        }
        int totalProducts = 0;
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            if (key != null) {
                statement.setString(parameterIndex++, key);
                statement.setString(parameterIndex++, "%" + key + "%");
            }
            if (cateId != 0) {
                statement.setInt(parameterIndex++, cateId);
            }
            if (statusId != 0) {
                statement.setInt(parameterIndex++, statusId);
            }
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalProducts = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalProducts;
    }

    public List<Product> getProducts(int startIndex, int pageSize, String key, int pid, int cateId, int statusId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";
        if (key != null || pid != -1) {
            sql += " WHERE pid = ? OR pname LIKE ?";
        } else {
            sql += " where pname like '%%'";
        }
        if (cateId != 0) {
            sql += " and settingId = ?";
        }
        if (statusId != 0) {
            sql += " and status = ?";
        }
        sql += " ORDER BY pid LIMIT ? OFFSET ?";

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;

            // Thêm tham số cho key nếu nó không phải là null
            if (key != null) {
                statement.setString(parameterIndex++, key); // Thêm tham số cho pid hoặc pname
                statement.setString(parameterIndex++, "%" + key + "%");
            }
            if (cateId != 0) {
                statement.setInt(parameterIndex++, cateId);
            }
            if (statusId != 0) {
                statement.setInt(parameterIndex++, statusId);
            }
            statement.setInt(parameterIndex++, pageSize); // Đặt giá trị cho LIMIT
            statement.setInt(parameterIndex++, startIndex);

            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("pid"));
                    product.setProductName(resultSet.getString("pname"));
                    product.setCateId(resultSet.getInt("settingId"));
                    product.setProductInfo(resultSet.getString("pinfo"));
                    product.setProductPrice(resultSet.getDouble("pprice"));
                    product.setProductQuantity(resultSet.getInt("pquantity"));
                    product.setImage(resultSet.getString("image"));
                    product.setStatus(resultSet.getInt("status"));
                    product.setSale(resultSet.getDouble("sale"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public boolean updateProductStatus(int pid, int status) {
        String sql = "UPDATE product SET status=? WHERE pid=?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, status);
            statement.setInt(2, pid);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
//        System.out.println(dao.getTotalProductsFilter(null, -1, 0, 12));
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
        // Test getAllProducts method
//        List<Product> allProducts = dao.getAllProducts("Active");
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
        System.out.println(dao.getFilteredProducts("", ""));
    }
}

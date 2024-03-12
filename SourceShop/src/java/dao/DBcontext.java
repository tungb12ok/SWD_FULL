package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBcontext {

    public Connection connection;

    public DBcontext() {
        try {
            // Change the username, password, and URL to connect to your own MySQL database
            String username = "sa"; // Typical default username for MySQL
            String password = "123456"; // Your MySQL password
            // Update the URL for your MySQL database; specify your database name at the end
            String url = "jdbc:mysql://localhost:3306/ShoppingCart?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            // Use the MySQL JDBC driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBcontext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean testConnection() {
        return connection != null;
    }

    public static void main(String[] args) {
        DBcontext dbContext = new DBcontext();

        try {
            if (dbContext.testConnection()) {
                System.out.println("Database connection is successful!");
                // Perform additional database operations if needed
            } else {
                System.out.println("Failed to establish a database connection.");
            }
        } finally {
            // Close the connection in case it was opened
            try {
                if (dbContext.connection != null && !dbContext.connection.isClosed()) {
                    dbContext.connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

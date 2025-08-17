package com.cafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/cafe";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static void initialize() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            // Create transactions table
            String createTransactionsTable = 
                "CREATE TABLE IF NOT EXISTS transactions (" +
                "    id INT PRIMARY KEY AUTO_INCREMENT," +
                "    details TEXT," +
                "    total_amount DECIMAL(10,2)," +
                "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

            // Create transaction_items table
            String createTransactionItemsTable = 
                "CREATE TABLE IF NOT EXISTS transaction_items (" +
                "    id INT PRIMARY KEY AUTO_INCREMENT," +
                "    transaction_id INT," +
                "    item_name VARCHAR(100)," +
                "    quantity INT," +
                "    unit_price DECIMAL(10,2)," +
                "    gst_amount DECIMAL(10,2)," +
                "    FOREIGN KEY (transaction_id) REFERENCES transactions(id)" +
                ")";

            try (Statement stmt = con.createStatement()) {
                stmt.execute(createTransactionsTable);
                stmt.execute(createTransactionItemsTable);
            }
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean testConnection() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            return true;
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    public static int saveTransaction(String details, double total) {
        int id = -1;
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "INSERT INTO transactions (details, total_amount) VALUES (?, ?)";
            try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, details);
                pst.setDouble(2, total);
                pst.executeUpdate();

                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to save transaction: " + e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    public static void saveTransactionItems(int transactionId, List<TransactionItem> items) {
        if (transactionId == -1 || items == null || items.isEmpty()) {
            return;
        }

        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = 
                "INSERT INTO transaction_items " +
                "(transaction_id, item_name, quantity, unit_price, gst_amount) " +
                "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pst = con.prepareStatement(sql)) {
                con.setAutoCommit(false);
                try {
                    for (TransactionItem item : items) {
                        pst.setInt(1, transactionId);
                        pst.setString(2, item.getName());
                        pst.setInt(3, item.getQuantity());
                        pst.setDouble(4, item.getUnitPrice());
                        pst.setDouble(5, item.getGST());
                        pst.addBatch();
                    }
                    pst.executeBatch();
                    con.commit();
                } catch (SQLException e) {
                    con.rollback();
                    throw e;
                } finally {
                    con.setAutoCommit(true);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to save transaction items: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<TransactionItem> getTransactionItems(int transactionId) {
        List<TransactionItem> items = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = 
                "SELECT item_name, quantity, unit_price, gst_amount " +
                "FROM transaction_items " +
                "WHERE transaction_id = ?";
            
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, transactionId);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        String itemName = rs.getString("item_name");
                        int quantity = rs.getInt("quantity");
                        double unitPrice = rs.getDouble("unit_price");
                        TransactionItem item = new TransactionItem(itemName, quantity, unitPrice);
                        items.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve transaction items: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }
}

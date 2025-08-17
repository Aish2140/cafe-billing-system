package com.cafe;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize database
            DB.initialize();
            
            // Test database connection
            if (!DB.testConnection()) {
                JOptionPane.showMessageDialog(null,
                    "Failed to connect to database. Please check your MySQL server is running.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Start UI
            SwingUtilities.invokeLater(() -> new UI().createAndShowGUI());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "An error occurred while starting the application: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

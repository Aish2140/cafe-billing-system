package com.cafe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class UI {
    private JFrame frame;
    private JPanel mainPanel;
    private JTextArea billArea;
    private JComboBox<String> itemDropdown;
    private JTextField quantityField;
    private JTextField priceField;
    private List<TransactionItem> itemList = new ArrayList<>();
    private HashMap<String, Double> itemPrices;
    private static final Color PRIMARY_COLOR = new Color(51, 51, 51);
    private static final Color SECONDARY_COLOR = new Color(240, 240, 240);
    private static final Color ACCENT_COLOR = new Color(0, 120, 212);

    public void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize item prices
        itemPrices = new HashMap<>();
        itemPrices.put("Coffee", 50.0);
        itemPrices.put("Tea", 30.0);
        itemPrices.put("Sandwich", 80.0);
        itemPrices.put("Cake", 60.0);

        frame = new JFrame("Cafe Billing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setMinimumSize(new Dimension(800, 600));

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(SECONDARY_COLOR);
        frame.add(mainPanel);

        setupComponents();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setupComponents() {
        setupMenuBar();
        setupHeader();
        setupMainContent();
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(PRIMARY_COLOR);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fileMenu.setForeground(Color.WHITE);

        JMenuItem openPdfFolderItem = new JMenuItem("Open PDF Folder");
        openPdfFolderItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        openPdfFolderItem.addActionListener(e -> openPdfFolder());
        fileMenu.add(openPdfFolderItem);

        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
    }

    private void setupHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Cafe Billing System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void setupMainContent() {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(SECONDARY_COLOR);

        // Left panel for input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(SECONDARY_COLOR);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Item input
        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemPanel.setBackground(SECONDARY_COLOR);
        JLabel itemLabel = new JLabel("Item:");
        itemLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        itemPanel.add(itemLabel);
        itemDropdown = new JComboBox<>(new String[]{"Coffee", "Tea", "Sandwich", "Cake", "Custom"});
        itemDropdown.setPreferredSize(new Dimension(150, 30));
        itemDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        itemDropdown.addActionListener(e -> setPriceFromSelection());
        itemPanel.add(itemDropdown);
        inputPanel.add(itemPanel);

        // Quantity input
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quantityPanel.setBackground(SECONDARY_COLOR);
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        quantityPanel.add(quantityLabel);
        quantityField = new JTextField(10);
        quantityField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        quantityPanel.add(quantityField);
        inputPanel.add(quantityPanel);

        // Price input
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pricePanel.setBackground(SECONDARY_COLOR);
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pricePanel.add(priceLabel);
        priceField = new JTextField(10);
        priceField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        priceField.setEditable(true);
        pricePanel.add(priceField);
        inputPanel.add(pricePanel);

        // Add button
        JButton addButton = new JButton("Add Item");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(ACCENT_COLOR);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setPreferredSize(new Dimension(120, 35));
        addButton.addActionListener(e -> addItem());
        inputPanel.add(Box.createVerticalStrut(10)); // Add some space
        inputPanel.add(addButton);

        contentPanel.add(inputPanel, BorderLayout.WEST);

        // Right panel for bill
        JPanel billPanel = new JPanel(new BorderLayout());
        billPanel.setBackground(SECONDARY_COLOR);
        billPanel.setBorder(BorderFactory.createTitledBorder("Current Bill"));

        billArea = new JTextArea();
        billArea.setEditable(false);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(billArea);
        billPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(SECONDARY_COLOR);

        JButton printButton = new JButton("Print & Save");
        printButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        printButton.setBackground(ACCENT_COLOR);
        printButton.setForeground(Color.WHITE);
        printButton.setFocusPainted(false);
        printButton.setBorderPainted(false);
        printButton.setPreferredSize(new Dimension(150, 40));
        printButton.addActionListener(e -> printAndSave());
        buttonPanel.add(printButton);

        JButton clearButton = new JButton("Clear Bill");
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        clearButton.setBackground(ACCENT_COLOR);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setPreferredSize(new Dimension(150, 40));
        clearButton.addActionListener(e -> clearBill());
        buttonPanel.add(clearButton);

        billPanel.add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.add(billPanel, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Set initial price
        setPriceFromSelection();
    }

    private void setPriceFromSelection() {
        String selectedItem = (String) itemDropdown.getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.equals("Custom")) {
                priceField.setText("");
                priceField.setEditable(true);
            } else if (itemPrices.containsKey(selectedItem)) {
                priceField.setText(String.format("%.2f", itemPrices.get(selectedItem)));
                priceField.setEditable(true);
            }
        }
    }

    private void addItem() {
        try {
            String name = (String) itemDropdown.getSelectedItem();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price;
            
            if (name.equals("Custom")) {
                price = Double.parseDouble(priceField.getText().trim());
            } else {
                price = itemPrices.get(name);
            }

            if (quantity <= 0 || price <= 0) {
                JOptionPane.showMessageDialog(frame,
                    "Please enter valid quantity and price.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            TransactionItem item = new TransactionItem(name, quantity, price);
            itemList.add(item);
            updateBillArea();

            // Clear quantity and price fields
            quantityField.setText("");
            priceField.setText("");
            quantityField.requestFocus();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame,
                "Please enter valid numbers for quantity and price.",
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateBillArea() {
        StringBuilder bill = new StringBuilder();
        bill.append(String.format("%-20s %-10s %-10s %-10s\n", "Item", "Quantity", "Price", "Total"));
        bill.append("------------------------------------------------------------\n");

        double total = 0;
        for (TransactionItem item : itemList) {
            bill.append(String.format("%-20s %-10d %-10.2f %-10.2f\n",
                item.getName(), item.getQuantity(), item.getUnitPrice(), item.getTotal()));
            total += item.getTotal();
        }

        bill.append("------------------------------------------------------------\n");
        bill.append(String.format("%-20s %-10s %-10s %-10.2f\n", "Total", "", "", total));

        billArea.setText(bill.toString());
    }

    private void printAndSave() {
        if (itemList.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No items to print!",
                "Empty Bill", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Calculate total
            double total = 0;
            for (TransactionItem item : itemList) {
                total += item.getTotal();
            }

            // Save transaction
            int transactionId = DB.saveTransaction(billArea.getText(), total);
            if (transactionId != -1) {
                // Save transaction items
                DB.saveTransactionItems(transactionId, itemList);
                
                // Generate PDF
                String pdfPath = PDFGenerator.generateBill(transactionId, billArea.getText(), itemList);
                if (pdfPath != null) {
                    // Show success message with PDF location
                    String message = String.format(
                        "Transaction saved successfully!\n\n" +
                        "Transaction ID: %d\n\n" +
                        "PDF saved in project folder:\n" +
                        "bills/bill_%d_*.pdf\n\n" +
                        "Full path: %s",
                        transactionId, transactionId, pdfPath);
                    
                    JOptionPane.showMessageDialog(frame,
                        message,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Clear the bill
                    itemList.clear();
                    updateBillArea();
                } else {
                    JOptionPane.showMessageDialog(frame,
                        "Transaction saved but PDF generation failed.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                    "Failed to save transaction. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                "An error occurred while saving the transaction: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void clearBill() {
        itemList.clear();
        updateBillArea();
        quantityField.setText("");
        priceField.setText("");
    }

    private void openPdfFolder() {
        try {
            File pdfDir = new File("./bills");
            if (!pdfDir.exists()) {
                pdfDir.mkdirs();
            }
            
            // Get the absolute path
            String absolutePath = pdfDir.getAbsolutePath();
            
            // Open the folder using the system's file explorer
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                Runtime.getRuntime().exec("explorer.exe " + absolutePath);
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                Runtime.getRuntime().exec("open " + absolutePath);
            } else {
                Runtime.getRuntime().exec("xdg-open " + absolutePath);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                "Error opening PDF folder: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

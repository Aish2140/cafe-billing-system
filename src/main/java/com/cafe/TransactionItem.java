package com.cafe;

public class TransactionItem {
    private String item;
    private int quantity;
    private double unitPrice;
    private double gst;

    public TransactionItem(String item, int quantity, double unitPrice) {
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.gst = calculateGST(quantity * unitPrice);
    }

    private double calculateGST(double amount) {
        return amount * 0.18; // 18% GST
    }

    public String getName() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getGST() {
        return gst;
    }

    public double getTotal() {
        return quantity * unitPrice + gst;
    }
} 
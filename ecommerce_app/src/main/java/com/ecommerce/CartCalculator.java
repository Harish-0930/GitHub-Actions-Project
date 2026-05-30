package com.ecommerce;

import java.util.Map;

public class CartCalculator {

    public double calculateSubtotal(Map<String, Double> items) {
        if (items == null || items.isEmpty()) return 0.0;
        return items.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public double calculateTax(double subtotal, double taxRate) {
        if (taxRate < 0 || taxRate > 100) {
            throw new IllegalArgumentException("Tax rate must be between 0 and 100");
        }
        return subtotal * (taxRate / 100);
    }

    public double applyDiscount(double amount, double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100");
        }
        return amount - (amount * discountPercent / 100);
    }

    public double calculateTotal(double subtotal, double discount, double tax) {
        return subtotal - discount + tax;
    }

    public double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

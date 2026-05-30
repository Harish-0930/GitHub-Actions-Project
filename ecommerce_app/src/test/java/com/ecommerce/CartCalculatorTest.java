package com.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CartCalculatorTest {

    private CartCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new CartCalculator();
    }

    @Test
    void testCalculateSubtotalWithMultipleItems() {
        Map<String, Double> items = new HashMap<>();
        items.put("Laptop", 999.99);
        items.put("Mouse", 29.99);
        items.put("Keyboard", 79.99);
        assertEquals(1109.97, calculator.roundToTwoDecimals(calculator.calculateSubtotal(items)));
    }

    @Test
    void testCalculateSubtotalWithSingleItem() {
        Map<String, Double> items = new HashMap<>();
        items.put("Headphones", 149.99);
        assertEquals(149.99, calculator.calculateSubtotal(items));
    }

    @Test
    void testCalculateSubtotalWithEmptyCart() {
        Map<String, Double> items = new HashMap<>();
        assertEquals(0.0, calculator.calculateSubtotal(items));
    }

    @Test
    void testCalculateSubtotalWithNullCart() {
        assertEquals(0.0, calculator.calculateSubtotal(null));
    }

    @Test
    void testCalculateTax() {
        assertEquals(10.0, calculator.calculateTax(100.0, 10.0));
    }

    @Test
    void testCalculateTaxWithZeroRate() {
        assertEquals(0.0, calculator.calculateTax(100.0, 0.0));
    }

    @Test
    void testCalculateTaxWithFullRate() {
        assertEquals(100.0, calculator.calculateTax(100.0, 100.0));
    }

    @Test
    void testCalculateTaxWithNegativeRateThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateTax(100.0, -5.0));
    }

    @Test
    void testCalculateTaxWithRateOver100ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateTax(100.0, 110.0));
    }

    @Test
    void testApplyDiscount() {
        assertEquals(90.0, calculator.applyDiscount(100.0, 10.0));
    }

    @Test
    void testApplyDiscountWithZeroPercent() {
        assertEquals(100.0, calculator.applyDiscount(100.0, 0.0));
    }

    @Test
    void testApplyDiscountWithFullPercent() {
        assertEquals(0.0, calculator.applyDiscount(100.0, 100.0));
    }

    @Test
    void testApplyDiscountWithNegativePercentThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.applyDiscount(100.0, -10.0));
    }

    @Test
    void testApplyDiscountWithPercentOver100ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.applyDiscount(100.0, 110.0));
    }

    @Test
    void testCalculateTotal() {
        // subtotal=100, discount=10, tax=9 => 100 - 10 + 9 = 99
        assertEquals(99.0, calculator.calculateTotal(100.0, 10.0, 9.0));
    }

    @Test
    void testCalculateTotalWithNoDiscountOrTax() {
        assertEquals(100.0, calculator.calculateTotal(100.0, 0.0, 0.0));
    }

    @Test
    void testRoundToTwoDecimals() {
        assertEquals(10.56, calculator.roundToTwoDecimals(10.555));
        assertEquals(99.99, calculator.roundToTwoDecimals(99.99));
        assertEquals(0.0, calculator.roundToTwoDecimals(0.0));
    }
}

package com.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductValidatorTest {

    private ProductValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ProductValidator();
    }

    // --- Name Validation ---

    @Test
    void testValidName() {
        assertTrue(validator.isValidName("Laptop"));
        assertTrue(validator.isValidName("USB Cable"));
        assertTrue(validator.isValidName("4K Monitor"));
    }

    @Test
    void testNameWithExactMinLength() {
        assertTrue(validator.isValidName("Hub"));
    }

    @Test
    void testInvalidNameNull() {
        assertFalse(validator.isValidName(null));
    }

    @Test
    void testInvalidNameEmpty() {
        assertFalse(validator.isValidName(""));
    }

    @Test
    void testInvalidNameWhitespaceOnly() {
        assertFalse(validator.isValidName("   "));
    }

    @Test
    void testInvalidNameTooShort() {
        assertFalse(validator.isValidName("AB"));
    }

    // --- Price Validation ---

    @Test
    void testValidPrice() {
        assertTrue(validator.isValidPrice(9.99));
        assertTrue(validator.isValidPrice(0.01));
        assertTrue(validator.isValidPrice(1299.99));
    }

    @Test
    void testInvalidPriceZero() {
        assertFalse(validator.isValidPrice(0.0));
    }

    @Test
    void testInvalidPriceNegative() {
        assertFalse(validator.isValidPrice(-5.0));
    }

    // --- Stock Validation ---

    @Test
    void testValidStockZero() {
        assertTrue(validator.isValidStock(0));
    }

    @Test
    void testValidStockPositive() {
        assertTrue(validator.isValidStock(100));
    }

    @Test
    void testInvalidStockNegative() {
        assertFalse(validator.isValidStock(-1));
    }

    // --- Full Product Validation ---

    @Test
    void testValidProduct() {
        assertTrue(validator.isValidProduct("Gaming Laptop", 1299.99, 10));
    }

    @Test
    void testInvalidProductNullName() {
        assertFalse(validator.isValidProduct(null, 999.99, 10));
    }

    @Test
    void testInvalidProductBadPrice() {
        assertFalse(validator.isValidProduct("Laptop", -1.0, 10));
    }

    @Test
    void testInvalidProductNegativeStock() {
        assertFalse(validator.isValidProduct("Laptop", 999.99, -1));
    }

    @Test
    void testInvalidProductOutOfStockIsValid() {
        assertTrue(validator.isValidProduct("Laptop", 999.99, 0));
    }

    // --- Category Classification ---

    @Test
    void testGetCategoryBudget() {
        assertEquals("Budget", validator.getCategory(29.99));
        assertEquals("Budget", validator.getCategory(49.99));
    }

    @Test
    void testGetCategoryMidRange() {
        assertEquals("Mid-Range", validator.getCategory(50.0));
        assertEquals("Mid-Range", validator.getCategory(149.99));
        assertEquals("Mid-Range", validator.getCategory(199.99));
    }

    @Test
    void testGetCategoryPremium() {
        assertEquals("Premium", validator.getCategory(200.0));
        assertEquals("Premium", validator.getCategory(999.99));
    }
}

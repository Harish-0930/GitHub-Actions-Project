package com.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CouponServiceTest {

    private CouponService couponService;

    @BeforeEach
    void setUp() {
        couponService = new CouponService();
    }

    // --- Coupon Validity ---

    @Test
    void testAllValidCoupons() {
        assertTrue(couponService.isValidCoupon("SAVE10"));
        assertTrue(couponService.isValidCoupon("SAVE20"));
        assertTrue(couponService.isValidCoupon("WELCOME5"));
        assertTrue(couponService.isValidCoupon("SUMMER15"));
    }

    @Test
    void testCouponCaseInsensitive() {
        assertTrue(couponService.isValidCoupon("save10"));
        assertTrue(couponService.isValidCoupon("Save10"));
        assertTrue(couponService.isValidCoupon("SAVE10"));
    }

    @Test
    void testInvalidCouponCode() {
        assertFalse(couponService.isValidCoupon("INVALID"));
        assertFalse(couponService.isValidCoupon("EXPIRED"));
    }

    @Test
    void testNullCouponCode() {
        assertFalse(couponService.isValidCoupon(null));
    }

    @Test
    void testEmptyCouponCode() {
        assertFalse(couponService.isValidCoupon(""));
    }

    // --- Discount Percent ---

    @Test
    void testGetDiscountPercentSave10() {
        assertEquals(10.0, couponService.getDiscountPercent("SAVE10"));
    }

    @Test
    void testGetDiscountPercentSave20() {
        assertEquals(20.0, couponService.getDiscountPercent("SAVE20"));
    }

    @Test
    void testGetDiscountPercentWelcome5() {
        assertEquals(5.0, couponService.getDiscountPercent("WELCOME5"));
    }

    @Test
    void testGetDiscountPercentSummer15() {
        assertEquals(15.0, couponService.getDiscountPercent("SUMMER15"));
    }

    @Test
    void testGetDiscountPercentForInvalidCoupon() {
        assertEquals(0.0, couponService.getDiscountPercent("INVALID"));
    }

    // --- Apply Coupon ---

    @Test
    void testApplyCouponSave10() {
        assertEquals(90.0, couponService.applyCoupon("SAVE10", 100.0));
    }

    @Test
    void testApplyCouponSave20() {
        assertEquals(80.0, couponService.applyCoupon("SAVE20", 100.0));
    }

    @Test
    void testApplyCouponWelcome5() {
        assertEquals(95.0, couponService.applyCoupon("WELCOME5", 100.0));
    }

    @Test
    void testApplyCouponSummer15() {
        assertEquals(85.0, couponService.applyCoupon("SUMMER15", 100.0));
    }

    @Test
    void testApplyCouponWithInvalidCodeReturnsOriginalAmount() {
        assertEquals(100.0, couponService.applyCoupon("INVALID", 100.0));
    }

    @Test
    void testApplyCouponCaseInsensitive() {
        assertEquals(90.0, couponService.applyCoupon("save10", 100.0));
    }

    @Test
    void testApplyCouponOnLargerAmount() {
        assertEquals(1079.99, couponService.applyCoupon("SAVE20", 1349.99), 0.01);
    }

    // --- Savings ---

    @Test
    void testGetSavingsSave10() {
        assertEquals(10.0, couponService.getSavings("SAVE10", 100.0));
    }

    @Test
    void testGetSavingsSave20() {
        assertEquals(20.0, couponService.getSavings("SAVE20", 100.0));
    }

    @Test
    void testGetSavingsWithInvalidCoupon() {
        assertEquals(0.0, couponService.getSavings("INVALID", 100.0));
    }

    @Test
    void testGetSavingsOnLargerAmount() {
        assertEquals(149.99, couponService.getSavings("SAVE10", 1499.9), 0.01);
    }
}

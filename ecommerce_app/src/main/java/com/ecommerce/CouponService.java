package com.ecommerce;

import java.util.HashMap;
import java.util.Map;

public class CouponService {

    private static final Map<String, Double> COUPONS = new HashMap<>();

    static {
        COUPONS.put("SAVE10", 10.0);
        COUPONS.put("SAVE20", 20.0);
        COUPONS.put("WELCOME5", 5.0);
        COUPONS.put("SUMMER15", 15.0);
    }

    public boolean isValidCoupon(String code) {
        return code != null && COUPONS.containsKey(code.toUpperCase());
    }

    public double getDiscountPercent(String code) {
        if (!isValidCoupon(code)) return 0.0;
        return COUPONS.get(code.toUpperCase());
    }

    public double applyCoupon(String code, double amount) {
        if (!isValidCoupon(code)) return amount;
        double discountPercent = getDiscountPercent(code);
        return amount - (amount * discountPercent / 100);
    }

    public double getSavings(String code, double amount) {
        return amount - applyCoupon(code, amount);
    }
}

package com.ecommerce;

public class ProductValidator {

    public boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.trim().length() >= 3;
    }

    public boolean isValidPrice(double price) {
        return price > 0;
    }

    public boolean isValidStock(int stock) {
        return stock >= 0;
    }

    public boolean isValidProduct(String name, double price, int stock) {
        return isValidName(name) && isValidPrice(price) && isValidStock(stock);
    }

    public String getCategory(double price) {
        if (price < 50) return "Budget";
        else if (price < 200) return "Mid-Range";
        else return "Premium";
    }
}

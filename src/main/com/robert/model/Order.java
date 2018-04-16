package main.com.robert.model;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Order {
    public static final LinkedList<Product> productsList = new LinkedList<>();
    public static BigDecimal totalPrice = new BigDecimal(0);
    public static String orderID = "";

    public static String getProducts() {
        StringBuilder products = new StringBuilder();
        for (Product x : productsList
                ) {
            products.append(x.getFormattedProduct()).append(System.getProperty("line.separator"));
        }
        return products.toString();
    }

    @SuppressWarnings("SameReturnValue")
    public static LinkedList<Product> getProductsList() {
        return productsList;
    }

}

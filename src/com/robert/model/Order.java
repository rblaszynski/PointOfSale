package com.robert.model;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Order {
    public static final LinkedList<Product> productsList = new LinkedList<>();
    public static BigDecimal totalPrice=new BigDecimal(0);

    public static String getProducts()
    {
        StringBuilder products= new StringBuilder();
        for (Product x:productsList
                ) {
            products.append(x.getProduct()).append(System.getProperty("line.separator"));
        }
        return products.toString();
    }

    public static void printProductsList()
    {
        for (Product x:productsList
                ) {
            x.printProduct();
        }
    }

    @SuppressWarnings("SameReturnValue")
    public static LinkedList<Product> getProductsList() {
        return productsList;
    }

}

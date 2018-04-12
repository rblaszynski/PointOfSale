package com.robert.model;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Order {
    public static LinkedList<Product> productsList = new LinkedList<>();
    public static BigDecimal totalPrice=new BigDecimal(0);

    public static String getProducts()
    {
        String products="";
        for (Product x:productsList
                ) {
            products+=(x.getProduct()+System.getProperty("line.separator"));
        }
        return products;
    }

    public static void printProductsList()
    {
        for (Product x:productsList
                ) {
            x.printProduct();
        }
    }

    public static LinkedList<Product> getProductsList() {
        return productsList;
    }

}

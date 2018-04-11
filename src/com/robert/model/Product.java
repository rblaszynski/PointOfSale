package com.robert.model;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Product {
    private String productName;
    private BigDecimal unitPrice;

    public Product(String productName, BigDecimal unitPrice) {
        this.productName = productName;
        this.unitPrice = unitPrice;
    }

    public Product() {

    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProduct()
    {
        return productName + " " + unitPrice.toString();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getProductName() {
        return productName;
    }

    public static LinkedList<Product> getProductsList() {
        return productsList;
    }

    public static BigDecimal getTotal() {
        return total;
    }

    public void printProduct()
    {
        System.out.println(productName+" "+unitPrice);
    }

    public static void printProductsList()
    {
        for (Product x:productsList
             ) {
            x.printProduct();
        }
    }


    public static String getProducts()
    {
        String products="";
        for (Product x:productsList
             ) {
            products+=(x.getProduct()+System.getProperty("line.separator"));
        }
        return products;
    }

 public static LinkedList<Product> productsList = new LinkedList<>();
    public static BigDecimal total=new BigDecimal(0);
}
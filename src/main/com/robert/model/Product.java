package main.com.robert.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
    private String productName;
    private BigDecimal unitPrice;

    public Product(String productName, BigDecimal unitPrice) {
        this.productName = productName;
        this.unitPrice = unitPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Product() {
        this.productName = "";
        this.unitPrice = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);

    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getFormattedProduct() {
        return String.format("%-15.15s %10.2f", productName, unitPrice);
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getProductName() {
        return productName;
    }

}
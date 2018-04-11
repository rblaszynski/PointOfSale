package com.robert.dao;

import com.robert.model.Product;

import java.math.BigDecimal;
import java.sql.*;

public class JDBCDriver {
    private String url = "jdbc:sqlite:db\\pointofsale.db";
    private Connection connection = null;
    public static String ORDER="\n";
    public static BigDecimal TOTAL=new BigDecimal(0);

    public JDBCDriver() {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to the database");
        } catch (SQLException s) {
            System.out.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void select() {
        String sql = "SELECT * FROM Products";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("ProductID") + "\t" +
                        rs.getString("ProductName") + "\t" +
                        rs.getDouble("UnitPrice") + "\t" +
                        rs.getInt("Barcode"));
            }
        } catch (SQLException s) {
            System.out.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
        }
    }

    public Product select(String barcode) {
            String query="SELECT ProductName, UnitPrice FROM Products WHERE Barcode="+barcode;
        Product product = new Product();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (!rs.isBeforeFirst()) System.out.print("No such product");
            while (rs.next()) {
                product.setProductName(rs.getString("ProductName"));
                product.setUnitPrice(new BigDecimal(rs.getString("UnitPrice")));
                if(!product.equals(null))
                {
                    Product.productsList.add(product);
                    Product.total = Product.total.add(new BigDecimal(rs.getString("UnitPrice")));
                }
            }
        } catch (SQLException s) {
            System.out.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
        }

        return product;
    }
}

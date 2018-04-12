package com.robert.dao;

import com.robert.model.Product;

import java.math.BigDecimal;
import java.sql.*;

public class JDBCDriver {
    private Connection connection = null;
    // --Commented out by Inspection (12.04.2018 22:07):public static String ORDER="\n";
    // --Commented out by Inspection (12.04.2018 22:09):public static BigDecimal TOTAL=new BigDecimal(0);
    public static boolean productFound;

    public JDBCDriver() {
        try {
            String url = "jdbc:sqlite:db\\pointofsale.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to the database");
        } catch (SQLException s) {
            System.out.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
    }

// --Commented out by Inspection START (12.04.2018 22:05):
//    public Connection getConnection() {
//        return connection;
//    }
// --Commented out by Inspection STOP (12.04.2018 22:05)

// --Commented out by Inspection START (12.04.2018 22:08):
//    public void select() {
//        String sql = "SELECT * FROM Products";
//        try (
//                Statement stmt = connection.createStatement();
//                ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                System.out.println(rs.getInt("ProductID") + "\t" +
//                        rs.getString("ProductName") + "\t" +
//                        rs.getDouble("UnitPrice") + "\t" +
//                        rs.getInt("Barcode"));
//            }
//        } catch (SQLException s) {
//            System.out.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
//        }
//    }
// --Commented out by Inspection STOP (12.04.2018 22:08)

    public void select(String barcode) {
            String query="SELECT ProductName, UnitPrice FROM Products WHERE Barcode="+barcode;
        Product product = new Product();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (!rs.isBeforeFirst())
            {
                productFound=false;
            }
            else
            {
                productFound=true;
                while (rs.next()) {
                    product.setProductName(rs.getString("ProductName"));
                    product.setUnitPrice(new BigDecimal(rs.getString("UnitPrice")));
                    com.robert.model.Order.productsList.add(product);
                    com.robert.model.Order.totalPrice = com.robert.model.Order.totalPrice.add(new BigDecimal(rs.getString("UnitPrice")));
                }
            }

        } catch (SQLException s) {
            System.out.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
        }

    }
}

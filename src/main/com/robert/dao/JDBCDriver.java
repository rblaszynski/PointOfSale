package main.com.robert.dao;

import main.com.robert.model.Order;
import main.com.robert.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;

public class JDBCDriver {
    private Connection connection = null;
    public static boolean productFound;

    public JDBCDriver() {
        try {
            String url = "jdbc:sqlite:db\\pointofsale.db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException s) {
            System.err.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
        } catch (Exception e) {
            System.err.println("Error: " + e.toString() + e.getMessage());
        }
    }

    public void select(String barcode) {
        String query = "SELECT ProductName, UnitPrice FROM Products WHERE Barcode=" + barcode;
        Product product = new Product();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            if (!rs.isBeforeFirst()) {
                productFound = false;
            } else {
                productFound = true;
                while (rs.next()) {
                    product.setProductName(rs.getString("ProductName"));
                    product.setUnitPrice(new BigDecimal(rs.getString("UnitPrice")));
                    main.com.robert.model.Order.productsList.add(product);
                    main.com.robert.model.Order.totalPrice = main.com.robert.model.Order.totalPrice.add(new BigDecimal(rs.getString("UnitPrice"))).setScale(2, RoundingMode.HALF_EVEN);
                }
            }
        } catch (SQLException s) {
            System.err.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
        }
    }

    public void insertOrder() {
        String OQuery;
        StringBuilder ODQuery;
        String ID;
        Order.orderID = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        ID = Order.orderID;
        OQuery = "insert into \"Orders\" values (\'" + ID + "\'," + "\'" + (java.sql.Date.valueOf(LocalDate.now()) + " " + Time.valueOf(LocalTime.now())) + "'" + "," + main.com.robert.model.Order.totalPrice.toString() + ")";
        ODQuery = new StringBuilder("insert into \"Order Details\" (OrderID, ProductID) VALUES" + (System.getProperty("line.separator")));
        int i = 0;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(OQuery);
            for (Product x : main.com.robert.model.Order.getProductsList()) {
                String pName;
                pName = x.getProductName();
                ODQuery.append(" (\'").append(ID).append("\',(select ProductID from Products where ProductName='").append(pName).append("'))");
                if (i++ == Order.productsList.size() - 1)
                    ODQuery.append(";").append(System.getProperty("line.separator"));
                else ODQuery.append(",").append(System.getProperty("line.separator"));
            }
            statement.executeUpdate(ODQuery.toString());
        } catch (SQLException s) {
            System.err.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
        }
    }

    public void deleteOrder(String orderID) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from 'Orders' where OrderID = '" + orderID + "';");
            statement.executeUpdate("delete from 'Order Details' where OrderID = '" + orderID + "';");
        } catch (SQLException s) {
            System.err.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
        }
    }

    public Connection getConnection() {
        return connection;
    }

}

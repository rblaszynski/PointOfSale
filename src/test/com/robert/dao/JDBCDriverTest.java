package test.com.robert.dao;

import main.com.robert.dao.JDBCDriver;
import main.com.robert.model.Order;
import main.com.robert.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

import static main.com.robert.model.Order.orderID;
import static org.junit.jupiter.api.Assertions.*;

class JDBCDriverTest {
    private final JDBCDriver driver = new JDBCDriver();

    @Test
    void select() {
        String query = "SELECT ProductName, UnitPrice FROM Products WHERE Barcode=" + 111;
        try (
                Statement stmt = driver.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                assertEquals(rs.getString("ProductName"), "Tomato");
                assertEquals(rs.getString("UnitPrice"), "3.2");
            }
        } catch (SQLException s) {
            System.err.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
        }
    }

    @Test
    void insertOrder() {
        String OQuery;
        StringBuilder ODQuery;
        String ID;
        Order.orderID = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        ID = Order.orderID;
        Product tomato = new Product("Tomato", new BigDecimal(3.2));
        String date = (java.sql.Date.valueOf(LocalDate.now()) + " " + Time.valueOf(LocalTime.now()));

        OQuery = "insert into \"Orders\" values (\'" + ID + "\'," + "\'" + date + "'" + "," + tomato.getUnitPrice() + ")";
        ODQuery = new StringBuilder("insert into \"Order Details\" (OrderID, ProductID) VALUES" + (System.getProperty("line.separator")));
        int i = 0;
        try {
            Statement statement = driver.getConnection().createStatement();
            statement.executeUpdate(OQuery);
            ODQuery.append(" (\'").append(ID).append("\',(select ProductID from Products where ProductName='").append(tomato.getProductName()).append("'))");
            statement.executeUpdate(ODQuery.toString());

            try (ResultSet rs = statement.executeQuery("select * from \'Order Details\' where OrderID=\'" + Order.orderID + "\'")) {
                assertEquals(rs.getString("OrderId"), Order.orderID);
                assertEquals(rs.getString("ProductID"), "1");
            }

            try (ResultSet rs = statement.executeQuery("select * from Orders where OrderID=\'" + Order.orderID + "\'")) {
                assertEquals(rs.getString("OrderID"), Order.orderID);
                assertEquals(rs.getString("OrderDate"), date);
                assertEquals(rs.getString("TotalPrice"), "3.2");
            }

        } catch (SQLException s) {
            System.err.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
        }
        clear();
    }

    private void clear() {
        try {
            Statement statement = driver.getConnection().createStatement();
            statement.executeUpdate("delete from 'Orders' where OrderID = '" + orderID + "';");
            statement.executeUpdate("delete from 'Order Details' where OrderID = '" + orderID + "';");
        } catch (SQLException s) {
            System.err.println("SQL Error: " + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
        }
    }
}
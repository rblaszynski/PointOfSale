package main.com.robert.model;


import main.com.robert.dao.JDBCDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;

public class Model {
    private final JDBCDriver driver = new JDBCDriver();
    private final TableView<Product> productTableView = new TableView<>();

    public void checkValid(TextField input) {
        String barcode = input.getText();
        if (barcode.matches("^\\d{3}$")) {
            driver.select(barcode);
            if (JDBCDriver.productFound) {
                productTableView.setItems(getProduct());
            } else {
                productTableView.setItems(FXCollections.observableArrayList(new Product("Product Not Found", new BigDecimal(0))));
            }
        } else if (barcode.isEmpty()) {
            productTableView.setItems(FXCollections.observableArrayList(new Product("No barcode", new BigDecimal(0))));
        } else {
            productTableView.setItems(FXCollections.observableArrayList(new Product("Invalid barcode", new BigDecimal(0))));
        }
    }

    public void update() {
        productTableView.setItems(getProducts());
    }

    public TableView<Product> getProductTableView() {
        return productTableView;
    }

    public void printOrder() {
        try {
            driver.insertOrder();
            productTableView.setItems(getProducts());
            String rName = Order.orderID + ".txt";

            FileWriter writer = new FileWriter("receipts/" + rName, false);

            writer.write(String.format("%-15s  %10s" + (System.getProperty("line.separator")), "Item", "Price"));
            writer.write(String.format("%-15s  %10s" + (System.getProperty("line.separator")), "----", "-----"));
            writer.write(main.com.robert.model.Order.getProducts());
            writer.write(String.format("%-15s  %10s" + (System.getProperty("line.separator")), "", "-----"));
            writer.write(String.format("%-15.15s %10.2f", "Total: ", main.com.robert.model.Order.totalPrice));
            writer.close();

        } catch (IOException e) {
            System.err.println("IO Error: " + e.toString());
        }
    }

    public void clearOrder() {
        Order.getProductsList().clear();
        Order.totalPrice = new BigDecimal(0);
    }

    public String getProductFromTableView(int row) {
        return getProductTableView().getColumns().get(0).getCellObservableValue(row).getValue().toString();
    }

    public void deleteOrder(String orderID) {
        driver.deleteOrder(orderID);
        Path path = Paths.get(("receipts/" + orderID + ".txt"));
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (NoSuchFileException n) {
                System.err.println("No such file: " + path);
            } catch (IOException e) {
                System.err.println("IO Error: " + e.toString());
            }
        }

    }

    private ObservableList<Product> getProduct() {
        return FXCollections.observableArrayList(Order.getProductsList().getLast());
    }

    private ObservableList<Product> getProducts() {
        return FXCollections.observableArrayList(main.com.robert.model.Order.getProductsList());
    }
}

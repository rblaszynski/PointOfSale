package com.robert.model;


import com.robert.dao.JDBCDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import com.robert.model.Product.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

public class Model {
    JDBCDriver driver = new JDBCDriver();
    private TableView<Product> productTableView = new TableView<>();
    private ObservableList<Product> productObservableList;
    private static int flag;

    public void checkValid(TextField input){
        String barcode = input.getText();
        if (barcode.matches("^\\d{3}$"))
        {
            driver.select(barcode);
            if(JDBCDriver.productFound)
            {
                productTableView.setItems(getProduct());
            }
            else
            {
                productTableView.setItems(FXCollections.observableArrayList(new Product("Product Not Found",new BigDecimal(0))));
            }
        }
        else if(barcode.matches("exit"))
        {
            printOrder();
        }
        else if(barcode.isEmpty())
        {
            productTableView.setItems(FXCollections.observableArrayList(new Product("No barcode",new BigDecimal(0))));
        }
        else
        {
            productTableView.setItems(FXCollections.observableArrayList(new Product("Invalid barcode",new BigDecimal(0))));
        }
    }

    public TableView<Product> getProductTableView() {
        return productTableView;
    }

    public void printOrder()
    {
        try
        {
            productTableView.setItems(getProducts());
            FileWriter writer = new FileWriter("Receipt.txt",false);
            writer.write(com.robert.model.Order.getProducts());
            writer.write("TOTAL: "+com.robert.model.Order.totalPrice);
            writer.close();

            ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "Receipt.txt");
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        com.robert.model.Order.printProductsList();
    }

    private ObservableList<Product> getProduct() {
        ObservableList<Product> products = FXCollections.observableArrayList(com.robert.model.Order.getProductsList().getLast());
        return products;
    }
    private ObservableList<Product> getProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList(com.robert.model.Order.getProductsList());
        return products;
    }
}

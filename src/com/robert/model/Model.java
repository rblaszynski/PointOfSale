package com.robert.model;


import com.robert.dao.JDBCDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Formatter;

public class Model {
    private final JDBCDriver driver = new JDBCDriver();
    private final TableView<Product> productTableView = new TableView<>();
    // --Commented out by Inspection (12.04.2018 22:09):private ObservableList<Product> productObservableList;
    // --Commented out by Inspection (12.04.2018 22:09):private static int flag;
    //TODO wstawic paragon do bazy danych
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
            //TODO "exit" same functionality as print button
            printOrder();
            update();
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

    public void update()
    {
        productTableView.setItems(getProducts());
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

            writer.write(String.format("%-15s  %10s"+(System.getProperty("line.separator")), "Item", "Price"));
            writer.write(String.format("%-15s  %10s"+(System.getProperty("line.separator")), "----", "-----"));
            writer.write(com.robert.model.Order.getProducts());
            writer.write(String.format("%-15s %10s"+(System.getProperty("line.separator")), "", "-----"));
            writer.write(String.format("%-15.15s %10.2f", "Total: ",com.robert.model.Order.totalPrice));
            writer.close();

            ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "Receipt.txt");
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        com.robert.model.Order.printProductsList();
    }

    private ObservableList<Product> getProduct() {
        return FXCollections.observableArrayList(Order.getProductsList().getLast());
    }
    private ObservableList<Product> getProducts() {
        return FXCollections.observableArrayList(com.robert.model.Order.getProductsList());
    }
}

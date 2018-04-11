package com.robert.model;


import com.robert.dao.JDBCDriver;
import javafx.scene.control.TextField;
import com.robert.model.Product.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Model {
    JDBCDriver driver = new JDBCDriver();


    public void checkValid(TextField input){
        String barcode = input.getText();
        if (barcode.matches("^\\d{3}$"))
        {
            System.out.println("Valid barcode");
            driver.select(barcode).printProduct();
            System.out.println("TOTAL: $"+Product.total);

        }
        else if(barcode.matches("exit"))
        {
            printOrder();
        }
        else if(barcode.isEmpty()) System.out.println("No barcode");
        else System.out.println("Invalid barcode");
    }

    public void printOrder()
    {
        System.out.println("Printing...");
        try
        {
            FileWriter writer = new FileWriter("Receipt.txt",false);
            writer.write(Product.getProducts());
            writer.write("TOTAL: "+Product.total);
            writer.close();

            ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "Receipt.txt");
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product.printProductsList();
        System.out.println("TOTAL: $"+Product.total);
    }
}

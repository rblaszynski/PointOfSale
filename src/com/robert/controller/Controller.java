package com.robert.controller;

import com.robert.model.Model;
import com.robert.model.Product;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class Controller {
    private final Model model = new Model();

    public void check(TextField textField)
    {
        model.checkValid(textField);
    }

    public void printOrder()
    {
        model.printOrder();
    }

    public TableView<Product> getTableView()
    {
        return model.getProductTableView();
    }



}

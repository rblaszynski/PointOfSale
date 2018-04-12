package com.robert.controller;

import com.robert.dao.JDBCDriver;
import com.robert.model.Model;
import com.robert.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class Controller {
    Model model = new Model();

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

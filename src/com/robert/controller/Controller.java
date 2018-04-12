package com.robert.controller;

import com.robert.model.Model;
import com.robert.model.Order;
import com.robert.model.Product;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.math.BigDecimal;


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

    public void clearOrder()
    {
        Order.getProductsList().clear();
        Order.totalPrice = new BigDecimal(0);
    }

    public void updateTable()
    {
        model.update();
    }

}

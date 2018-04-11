package com.robert.controller;

import com.robert.dao.JDBCDriver;
import com.robert.model.Model;
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
}

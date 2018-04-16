package main.com.robert.controller;

import main.com.robert.model.Model;
import main.com.robert.model.Order;
import main.com.robert.model.Product;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.math.BigDecimal;


public class Controller {
    private final Model model = new Model();

    public void check(TextField textField) {
        model.checkValid(textField);
    }

    public void printOrder() {
        model.printOrder();
    }

    public TableView<Product> getTableView() {
        return model.getProductTableView();
    }

    public void clearOrder() {
        model.clearOrder();
    }

    public BigDecimal getTotalPrice()
    {
        return Order.totalPrice;
    }

    public void updateTable() {
        model.update();
    }
}

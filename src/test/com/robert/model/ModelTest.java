package test.com.robert.model;

import javafx.application.Application;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.com.robert.model.Model;
import main.com.robert.model.Order;
import main.com.robert.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static main.com.robert.model.Order.orderID;
import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    private final Model model = new Model();


    @BeforeAll
    static void initToolkit() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(NonApp.class);
            }
        };
        t.setDaemon(true);
        t.start();
    }

    @BeforeEach
    void init() {
        TableColumn<Product, String> nameColumn = new TableColumn<>("ProductName");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("UnitPrice");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        model.getProductTableView().getColumns().addAll(nameColumn, priceColumn);
    }

    @Test
    void checkValid() {
        model.checkValid(new TextField("111"));
        assertEquals(model.getProductFromTableView(0), "Tomato");
        assertEquals(Order.totalPrice, new BigDecimal(3.2).setScale(2, RoundingMode.HALF_EVEN));

        model.checkValid(new TextField("211"));
        assertEquals(model.getProductFromTableView(0), "Product Not Found");

        model.checkValid(new TextField(""));
        assertEquals(model.getProductFromTableView(0), "No barcode");

        model.checkValid(new TextField("1211"));
        assertEquals(model.getProductFromTableView(0), "Invalid barcode");

        model.checkValid(new TextField("abc"));
        assertEquals(model.getProductFromTableView(0), "Invalid barcode");
    }

    @Test
    void printOrder() {
        model.checkValid(new TextField("111"));
        model.checkValid(new TextField("135"));

        model.printOrder();
        Path path = Paths.get(("receipts/" + orderID + ".txt"));

        assertEquals(model.getProductFromTableView(0), "Tomato");
        assertEquals(model.getProductFromTableView(1), "Apple");
        assertEquals(Order.totalPrice, new BigDecimal(5.8).setScale(2, RoundingMode.HALF_EVEN));
        assertTrue(Files.exists(path));
    }

    @AfterEach
    void clear() {
        model.clearOrder();
        model.deleteOrder(orderID);
    }
}
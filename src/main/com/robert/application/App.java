package main.com.robert.application;

import main.com.robert.controller.Controller;
import main.com.robert.model.Order;
import main.com.robert.model.Product;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends javafx.application.Application {
    private final Controller controller = new Controller();

    private final Text totalText = new Text("Total: ");
    private Stage window;

    private FlowPane addFlowPane() {
        FlowPane layout = new FlowPane();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setHgap(10);
        layout.setVgap(10);
        Label barcodeLabel = new Label("Enter barcode:");
        TextField barcodeInput = new TextField();
        barcodeInput.setPromptText("123");

        Button enterButton = new Button("ENTER");

        layout.getChildren().addAll(barcodeLabel, barcodeInput, enterButton);

        enterButton.setOnAction(e -> {
            controller.check(barcodeInput);
            totalText.setText("TOTAL: " + Order.totalPrice);
            barcodeInput.clear();
        });

        barcodeInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                controller.check(barcodeInput);
                totalText.setText("TOTAL: " + Order.totalPrice);
                barcodeInput.clear();
            }
        });
        return layout;
    }

    private GridPane addGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        totalText.setFont(Font.font("ARIAL", FontWeight.BOLD, 20));

        Button printButton = new Button("PRINT");
        printButton.setOnAction(event -> {
                    controller.printOrder();
                    again();
                    totalText.setText("TOTAL: " + Order.totalPrice);
                    controller.updateTable();
                }
        );

        TableColumn<Product, String> nameColumn = new TableColumn<>("ProductName");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("UnitPrice");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        controller.getTableView().getColumns().addAll(nameColumn, priceColumn);

        gridPane.add(controller.getTableView(), 0, 0);
        gridPane.add(printButton, 0, 1);
        gridPane.add(totalText, 0, 1);
        GridPane.setHalignment(printButton, HPos.RIGHT);


        return gridPane;
    }

    private void closeWindow(String msg) {
        if (AlertBox.display("Quit", msg)) {
            Platform.exit();
        }
    }

    private void again() {
        if (AlertBox.display("Print", "Do you want to make new order?")) {
            controller.clearOrder();
        } else {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
                System.err.println(ie.toString());
            }
            closeWindow("Do you want to quit?");
        }
    }

    @Override
    public void start(Stage primaryStage) {
        window = new Stage();
        window.setTitle("LCD Display");
        window.setScene(new Scene(addGridPane()));
        window.show();
        window.setResizable(false);
        window.setX(window.getX() + 200);

        window.setOnCloseRequest(event -> {
            event.consume();
            closeWindow("Are you sure you want to quit?");
        });

        primaryStage.setTitle("Barcode scanner");
        primaryStage.setScene(new Scene(addFlowPane(), 350, 75));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setX(window.getX() - 375);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            closeWindow("Are you sure you want to quit?");
        });

    }

}


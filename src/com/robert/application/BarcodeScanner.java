package com.robert.application;

import com.robert.controller.Controller;
import com.robert.model.Order;
import com.robert.model.Product;
import javafx.application.Application;
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


public class BarcodeScanner extends Application {
    private Stage window, secondWindow;
    private Controller controller = new Controller();

    Text totalText = new Text("Total: ");


    public FlowPane addFlowPane()
    {
        FlowPane layout = new FlowPane();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setHgap(10);
        layout.setVgap(10);
        Label barcodeLabel = new Label("Enter barcode:");
        TextField barcodeInput = new TextField();
        barcodeInput.setPromptText("12345");

        Button enterButton = new Button("ENTER");

        layout.getChildren().addAll(barcodeLabel, barcodeInput, enterButton);

        enterButton.setOnAction(e -> {
            controller.check(barcodeInput);
            totalText.setText("TOTAL: "+Order.totalPrice);
            barcodeInput.clear();
        } );

        barcodeInput.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.ENTER)
            {
                controller.check(barcodeInput);
                totalText.setText("TOTAL: "+Order.totalPrice);
                barcodeInput.clear();
            }
        });
        return layout;
    }

    public GridPane addGridPane()
    {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));

        totalText.setFont(Font.font("ARIAL",FontWeight.BOLD, 20));

        Button printButton = new Button("PRINT");
        printButton.setOnAction(event -> {
            controller.printOrder();
        });


        TableColumn<Product, String> nameColumn = new TableColumn<>("ProductName");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("UnitPrice");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));


        controller.getTableView().getColumns().addAll(nameColumn, priceColumn);

        gridPane.add(controller.getTableView(),0,0);
        gridPane.add(printButton,0,1);
        gridPane.add(totalText,0,1);
        GridPane.setHalignment(printButton,HPos.RIGHT);


        return gridPane;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = new Stage();
        window.setTitle("LCD Display");
        window.setScene(new Scene(addGridPane()));
        window.show();
        window.setX(window.getX()+200);


        secondWindow = primaryStage;
        secondWindow.setTitle("Barcode scanner");
        secondWindow.setScene(new Scene(addFlowPane(),350,75));
        secondWindow.show();
        secondWindow.setX(window.getX()-375);

    }
}


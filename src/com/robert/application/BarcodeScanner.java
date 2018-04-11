package com.robert.application;

import com.robert.controller.Controller;
import com.robert.model.Product;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.print.Printer;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.print.PrinterJob;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static javafx.scene.input.KeyCode.V;

public class BarcodeScanner extends Application {
    private Stage window, secondWindow;
    private Scene scene;
    private Button enterButton, printButton;
    private Controller controller = new Controller();
    private TableView<Product> productTableView;
    private ObservableList<Product> productObservableList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Barcode scanner");
        TextArea textArea = new TextArea();



        FlowPane layout = new FlowPane();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setHgap(10);
        layout.setVgap(10);

        Label barcodeLabel = new Label("Enter barcode:");

        TextField barcodeInput = new TextField();
        barcodeInput.setPromptText("12345");

        enterButton = new Button("ENTER");
        enterButton.setOnAction(e -> {
            textArea.clear();
            controller.check(barcodeInput);
            barcodeInput.clear();
        } );

        barcodeInput.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.ENTER)
            {
                controller.check(barcodeInput);
                productTableView.setItems(getProduct());
                barcodeInput.clear();
            }
        });

        printButton = new Button("PRINT");
        printButton.setOnAction(event -> {
            productTableView.setItems(getProduct());
            controller.printOrder();
        });


        layout.getChildren().addAll(barcodeLabel, barcodeInput, enterButton, printButton);
        barcodeInput.setLayoutY(100);
        scene = new Scene(layout,300,150);
        window.setScene(scene);
        window.show();
//        scene = new Scene(layout, 300, 150);
//        secondWindow.setScene(scene);
//        secondWindow.show();

        /*

         */
//        secondWindow = new Stage();
//        secondWindow.setTitle("LCD Display");
//
//        TableColumn<Product,String> productCol = new TableColumn<>("Product");
//        productCol.setMaxWidth(200);
//        productCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
//
//        TableColumn<Product,String> priceCol = new TableColumn<>("Price");
//        priceCol.setMaxWidth(200);
//        priceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
//
//        productTableView=new TableView<>();
//        productTableView.setItems(getProductObservableList());
//        productTableView.getColumns().addAll(productCol,priceCol);
//
//        VBox vBox = new VBox();
//        vBox.getChildren().addAll(productTableView);
//
//
////        System.setOut(new PrintStream(new OutputStream()
////        {
////            @Override
////            public void write(int b) {
////                textArea.appendText("" +((char)b));
////            }
////
////            @Override
////            public void write(byte[] b) throws IOException {
////                textArea.appendText(new String(b));
////            }
////
////            @Override
////            public void write(byte[] b, int off, int len) throws IOException {
////                textArea.appendText(new String(b, off, len));
////            }
////        }));
//
//        FlowPane lay = new FlowPane();
//        lay.getChildren().add(productTableView);
//        Scene secondScene = new Scene(vBox);
//        secondWindow.setScene(secondScene);
//        secondWindow.show();
//       // secondWindow.setX(secondWindow.getX()-300);
//        secondWindow.setX(secondWindow.getX()+100);

        secondWindow = new Stage();
        secondWindow.setTitle("LCD Display");

        TableColumn<Product, String> nameColumn = new TableColumn<>("ProductName");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("UnitPrice");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        productTableView = new TableView<>();
        productTableView.getColumns().addAll(nameColumn, priceColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(productTableView);

        Scene scene = new Scene(vBox);
        secondWindow.setScene(scene);
        secondWindow.show();
    }

    public ObservableList<Product> getProduct() {
        ObservableList<Product> products = FXCollections.observableArrayList(com.robert.model.Product.getProductsList().getLast());
        return products;
    }

}


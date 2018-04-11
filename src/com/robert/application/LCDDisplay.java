package com.robert.application;

import com.robert.model.Product;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LCDDisplay extends Application {

    Stage secondWindow;
    TableView<Product> table;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        secondWindow = primaryStage;
        secondWindow.setTitle("LCD Display");

        TableColumn<Product, String> nameColumn = new TableColumn<>("ProductName");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("UnitPrice");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        table = new TableView<>();
        table.setItems(getProduct());
        table.getColumns().addAll(nameColumn, priceColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table);

        Scene scene = new Scene(vBox);
        secondWindow.setScene(scene);
        secondWindow.show();
    }

    public ObservableList<Product> getProduct() {
        ObservableList<Product> products = FXCollections.observableArrayList(com.robert.model.Product.getProductsList());
        return products;
    }

}

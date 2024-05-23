package org.example.src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import entity.ProductCard;
import entity.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.util.Map;


public class StoreController {

    @FXML
    private GridPane storeGrid;

    private Store store;

    public void setStore(Store store) {
        this.store = store;
        populateStoreGrid();
    }

    private void populateStoreGrid() {
        int row = 0;
        for (Map.Entry<ProductCard, Integer> entry : store.getStoreInformation().entrySet()) {
            ProductCard product = entry.getKey();
            int quantity = entry.getValue();

            StackPane cell = new StackPane();
            cell.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #f0f0f0;-fx-border-radius:10");
            cell.setPadding(new Insets(10));

            Label nameLabel = new Label("Name: " + product.getName());
            nameLabel.setFont(new Font(16));
            Label priceLabel = new Label("Price: " + product.getPrice());
            priceLabel.setFont(new Font(16));
            Label quantityLabel = new Label("Quantity: " + quantity);
            quantityLabel.setFont(new Font(16));

            GridPane productInfo = new GridPane();
            productInfo.addRow(0, nameLabel);
            productInfo.addRow(1, priceLabel);
            productInfo.addRow(2, quantityLabel);
            productInfo.setVgap(5);

            cell.getChildren().add(productInfo);
            storeGrid.add(cell, 0, row++);
        }
    }


//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("Store.fxml"));
//        Parent root = loader.load();
//
//        StoreController controller = loader.getController();
////        controller.setStore(createDummyStore());
//
//
//        primaryStage.setTitle("Store");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
//    }
//    public static void main (String[]args){
//            launch(args);
//    }
}


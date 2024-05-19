package org.example.src;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.input.ClipboardContent;

// private void populateGrid() throws Exception {
//     int rows = 9;
//     int cols = 9;
//     for (int i = 0; i < rows; i++) {
//         for (int j = 0; j < cols; j++) {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
//             Node card = loader.load();
//             StackPane stack = new StackPane();
//             Pane borderPane = new Pane();
//             borderPane.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: transparent;");
//             stack.getChildren().addAll(borderPane, card);
//             StackPane.setMargin(card, new javafx.geometry.Insets(10));
//             grid.add(stack, j, i);
//         }
//     }

public class GridController {
    @FXML
    private GridPane grid;

    @FXML
    public void initialize() {
        try {
            populateGrid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateGrid() throws Exception {
        int rows = 4;
        int cols = 5;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Pane cell = new Pane();

                // Apply border and background styling directly
                cell.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #f0f0f0; -fx-padding: 10;");
                // Set minimum size to ensure visibility even if empty
                cell.setMinSize(100, 100);

                // Label to display the cell state
                Label label = new Label("Drag me!");
                label.setLayoutX(10);  // Positioning the label inside the Pane
                label.setLayoutY(10);
                cell.getChildren().add(label);

                // Add drag handling capabilities
                setupDragHandlers(cell, label);

                // Add the styled cell to the grid
                grid.add(cell, col, row);
            }
        }
    }

    private void setupDragHandlers(Pane cell, Label label) {
        cell.setOnDragDetected(event -> {
            Dragboard db = cell.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString("Dragging");
            db.setContent(content);

            label.setText("Dragging...");  // Update label text when dragging starts
            event.consume();
        });

        cell.setOnDragOver(event -> {
            if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        cell.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                label.setText("Dropped!");  // Update label text when dropped
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        cell.setOnDragDone(event -> {
            label.setText("Drag me!");  // Reset label text after drag is done
            event.consume();
        });
    }
}

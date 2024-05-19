package org.example.src;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ActiveHandsController {
    @FXML
    private GridPane handsGrid;

    @FXML
    public void initialize() {
        try {
            populateHandsGrid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateHandsGrid() throws Exception {
        int rows = 1;
        int cols = 6;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                StackPane cell = new StackPane();
                cell.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #f0f0f0;");
                cell.setPadding(new Insets(10));
                cell.setMinSize(100, 150);
    
                // No card is added initially
                setupDragHandlers(cell);
                handsGrid.add(cell, col, row);
            }
        }
    }

    private void setupDragHandlers(Pane cell) {
        cell.setOnDragDetected(event -> {
            if (!cell.getChildren().isEmpty()) { // Only initiate drag if there is a card
                Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(""); // Optionally add content, possibly include identification data
                db.setContent(content);
                DragContext.getInstance().setDragSource(cell);
                event.consume();
            }
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
            Node dragSource = DragContext.getInstance().getDragSource();
            if (db.hasString() && dragSource != null) {
                Pane sourcePane = (Pane) dragSource;
                Pane targetPane = (Pane) cell;
                try {
                    // Handle both non-empty source and target or empty target
                    Node sourceCard = sourcePane.getChildren().isEmpty() ? null : sourcePane.getChildren().get(0);
                    Node targetCard = targetPane.getChildren().isEmpty() ? null : targetPane.getChildren().get(0);
    
                    if (sourceCard != null) {
                        if (targetCard != null) {
                            sourcePane.getChildren().remove(sourceCard);
                            targetPane.getChildren().remove(targetCard);
                            sourcePane.getChildren().add(targetCard);
                        }
                        targetPane.getChildren().add(sourceCard);
                        success = true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    
        cell.setOnDragDone(event -> {
            DragContext.getInstance().setDragSource(null);
            event.consume();
        });
    }
}

package org.example.src;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.scene.input.*;

public class GridController {
    @FXML
    private GridPane grid;

    @FXML
    public void initialize() {
        try {
            populateGrid();
            printCellStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateGrid() throws Exception {
        int rows = 4;
        int cols = 5;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                StackPane cell = new StackPane();  
                cell.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #f0f0f0;");
                cell.setPadding(new Insets(10));  // Set padding around the content
                cell.setMinSize(100, 150);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load();

                StackPane.setAlignment(card, Pos.CENTER);  // Ensure the card is centered
                cell.getChildren().add(card);

                setupDragHandlers(cell);
                grid.add(cell, col, row);
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

    private void printCellStatus() {
        for (Node child : grid.getChildren()) {
            if (child instanceof Pane) {
                Pane cell = (Pane) child;
                if (cell.getChildren().isEmpty()) {
                    System.out.println("Cell is empty.");
                } else {
                    System.out.println("Cell has children.");
                }
            }
        }
    }
}

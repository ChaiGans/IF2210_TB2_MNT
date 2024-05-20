package org.example.src;

import entity.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            UIUpdateService.getInstance().setHandsController(this);
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
                setupDragHandlers(cell);
                handsGrid.add(cell, col, row);
            }
        }
    }

    public void updateGrid(Hands hands) {
        handsGrid.getChildren().clear();
        int maxColumns = 6;
    
        for (int i = 0; i < maxColumns; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/src/Card.fxml"));
                Node cardNode = loader.load();
                CardController controller = loader.getController();
                if (i < hands.getCardCount() && hands.getCards().get(i) != null) {
                    Card card = hands.getCards().get(i);
                    controller.setCardInfo(card.getName() + ".png", card.getName());
                }
                StackPane cell = new StackPane();
                cell.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #f0f0f0;");
                cell.setPrefSize(100, 150);
                cell.getChildren().add(cardNode);  
                setupDragHandlers(cell);
                handsGrid.add(cell, i, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void setupDragHandlers(Pane cell) {
        cell.setOnDragDetected(event -> {
            if (!cell.getChildren().isEmpty()) {
                Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString("");
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

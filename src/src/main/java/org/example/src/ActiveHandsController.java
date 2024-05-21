package org.example.src;

import entity.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ActiveHandsController {
    @FXML
    private GridPane handsGrid;
    private Hands hands;
    private Grid gridData;

    @FXML
    public void initialize() {
        try {
            hands = GameData.getInstance().getHands();
            gridData = GameData.getInstance().getGridData();
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
            
                setupDragHandlers(cell, col, 0);
                handsGrid.add(cell, col, 0);
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
                
                setupDragHandlers(cell,i,0);
                handsGrid.add(cell, i, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void setupDragHandlers(Pane cell, int col, int row) {
        cell.setOnDragDetected(event -> {
            if (!cell.getChildren().isEmpty()) {
                Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString("hands," + handsGrid.getChildren().indexOf(cell));
                db.setContent(content);
                DragContext.getInstance().setDragSource(cell);
                System.out.println("Drag detected at col: " + col);
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
            Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
            Dragboard db = event.getDragboard();
            boolean success = false;
            Node dragSource = DragContext.getInstance().getDragSource();
            System.out.println("hands before: " + hands.getCards());
            if (db.hasString() && dragSource != null) {
                String[] parts = db.getString().split(",");
                int sourceIndex = Integer.parseInt(parts[1]);
        
                Pane sourcePane = (Pane) dragSource;
                Pane targetPane = (Pane) event.getGestureTarget();
        
                int targetCol = GridPane.getColumnIndex(targetPane);
        
                Card card = hands.getCard(sourceIndex);
                if (card != null) {
                    hands.moveCard(sourceIndex, targetCol);  
                    GameData.getInstance().uploadGridData(currentPlayer);
                    Node cardNode = sourcePane.getChildren().remove(0);
                    if (targetPane.getChildren().isEmpty()) {
                        targetPane.getChildren().add(cardNode);  
                        success = true; 
                    }
        
                    System.out.println("Moved card from hands index " + sourceIndex + " to grid column " + targetCol);
                }
                validateHands();
            }
            System.out.println("hands after: " + hands.getCards());
        
            event.setDropCompleted(success);
            event.consume();
        });
        

    
        cell.setOnDragDone(event -> {
            DragContext.getInstance().setDragSource(null);
            event.consume();
        });
    }

    private void validateHands() {
        System.out.println("Ini dari hands");
        System.out.println("Current hands state:");
        System.out.println(hands.length());
        for (int i = 0; i < hands.length(); i++) {
            if (hands.getCard(i) != null) {
                System.out.println("Hand " + i + ": " + hands.getCard(i).getName());
            } else {
                System.out.println("Hand " + i + " is empty.");
            }
        }
    }
}

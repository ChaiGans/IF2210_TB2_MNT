package org.example.src;

import entity.AnimalCard;
import entity.Card;
import entity.Grid;
import entity.Hands;
import entity.PlantCard;
import entity.Player;
import entity.ProductCard;
import entity.StandardCard;
import entity.GameData;
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
    private Grid gridData;
    private Hands hands;

    @FXML
    public void initialize() {
        try {
            gridData = GameData.getInstance().getGridData();
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
                    Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
                    Card cards = new StandardCard(currentPlayer, "Cardname" + row + " " +  col);
                    gridData.setCard(col, row, cards);
                setupDragHandlers(cell, col, row);
                grid.add(cell, col, row);
            }
        }
    }

    private void setupDragHandlers(Pane cell, int col, int row) {
        cell.setOnDragDetected(event -> {
            if (!cell.getChildren().isEmpty()) {
                Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString("grid," + col + "," + row);
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
            hands = GameData.getInstance().getHands();
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
                String[] dragData = db.getString().split(",");
                String sourceType = dragData[0];
                int sourceIndex = Integer.parseInt(dragData[1]);
                int sourceRow = sourceType.equals("grid") ? Integer.parseInt(dragData[2]) : 0;
    
                Pane sourcePane = (Pane) DragContext.getInstance().getDragSource();
                Pane targetPane = (Pane) event.getGestureTarget();
    
                int targetCol = GridPane.getColumnIndex(targetPane);
                int targetRow = GridPane.getRowIndex(targetPane);
    
                if ("hands".equals(sourceType)) {
                    System.out.println("masuk");
                    Card card = hands.getCard(sourceIndex);
                    System.out.println("source index: " +  sourceIndex);
                    if (card != null && targetCol >= 0 && targetCol < gridData.getWidth() && targetRow >= 0 && targetRow < gridData.getHeight()) {
                        if (targetPane.getChildren().isEmpty()) {
                            if (card instanceof AnimalCard || card instanceof PlantCard) {
                                System.out.println("this is plant or animal");

                                hands.deleteCard(sourceIndex);
                                gridData.setCard(targetCol, targetRow, card);
                                GameData.getInstance().uploadGridData(currentPlayer);
    
                                Node cardNode = sourcePane.getChildren().remove(0);
                                targetPane.getChildren().add(cardNode);
                                success = true;
                            }
                        } else {
                            Card targetCard = gridData.getCard(targetCol, targetRow);
                            if (targetCard instanceof AnimalCard && card instanceof ProductCard) {
                                AnimalCard targetAnimalCard = (AnimalCard) targetCard;
                                ProductCard sourcePlantCard = (ProductCard) card;
                                if (targetAnimalCard.isCarnivore() && sourcePlantCard.isNonVeganProduct()) {
                                    hands.deleteCard(sourceIndex);
                                    gridData.setCard(targetCol, targetRow, card);
                                    GameData.getInstance().uploadGridData(currentPlayer);
    
                                    sourcePane.getChildren().remove(0);
                                    success = true;
                                } else if (targetAnimalCard.isHerbivore() && sourcePlantCard.isVeganProduct()) {
                                    hands.deleteCard(sourceIndex);
                                    gridData.setCard(targetCol, targetRow, card);
                                    GameData.getInstance().uploadGridData(currentPlayer);
    
                                    sourcePane.getChildren().remove(0);
                                    success = true;
                                } else if (targetAnimalCard.isOmnivore() && card instanceof ProductCard) {
                                    hands.deleteCard(sourceIndex);
                                    gridData.setCard(targetCol, targetRow, card);
                                    GameData.getInstance().uploadGridData(currentPlayer);
    
                                    sourcePane.getChildren().remove(0);
                                    success = true;
                                }
                            }
                        }
                    }
                } else if ("grid".equals(sourceType)) {
                    Card card = gridData.getCard(sourceIndex, sourceRow);
                    if (card != null && (sourceIndex != targetCol || sourceRow != targetRow)) {
                        if (targetPane.getChildren().isEmpty()) {
                            gridData.removeCard(sourceIndex, sourceRow);
                            gridData.setCard(targetCol, targetRow, card);
    
                            GameData.getInstance().uploadGridData(currentPlayer);
    
                            Node cardNode = sourcePane.getChildren().remove(0);
                            targetPane.getChildren().add(cardNode);
                            success = true;
                        } else {
                            Card targetCard = gridData.getCard(targetCol, targetRow);
                            if (targetCard instanceof AnimalCard && card instanceof PlantCard) {
                                AnimalCard targetAnimalCard = (AnimalCard) targetCard;
                                ProductCard sourcePlantCard = (ProductCard) card;
                                if (targetAnimalCard.isCarnivore() && sourcePlantCard.isNonVeganProduct()) {
                                    gridData.removeCard(sourceIndex, sourceRow);
                                    gridData.setCard(targetCol, targetRow, card);
                                    GameData.getInstance().uploadGridData(currentPlayer);
    
                                    sourcePane.getChildren().remove(0);
                                    success = true;
                                } else if (targetAnimalCard.isHerbivore() && sourcePlantCard.isVeganProduct()) {
                                    gridData.removeCard(sourceIndex, sourceRow);
                                    gridData.setCard(targetCol, targetRow, card);
                                    GameData.getInstance().uploadGridData(currentPlayer);
    
                                    sourcePane.getChildren().remove(0);
                                    success = true;
                                } else if (targetAnimalCard.isOmnivore() && card instanceof ProductCard) {
                                    gridData.removeCard(sourceIndex, sourceRow);
                                    gridData.setCard(targetCol, targetRow, card);
                                    GameData.getInstance().uploadGridData(currentPlayer);
    
                                    sourcePane.getChildren().remove(0);
                                    success = true;
                                }
                            }
                        }
                    }
                }
                GameData.getInstance().printPlayerStateInfo(currentPlayer);
            }
            System.out.println("list of hands:  " + hands.getCards());
    
            event.setDropCompleted(success);
            event.consume();
        });
    
        cell.setOnDragDone(event -> {
            DragContext.getInstance().setDragSource(null);
            event.consume();
        });
    }
    

    private void validateGrid() {
        System.out.println("ini dari grid");
        for (int row = 0; row < gridData.getHeight(); row++) {
            for (int col = 0; col < gridData.getWidth(); col++) {
                if (gridData.getCard(col, row) != null) {
                    System.out.println("Card at row " + row + ", dan col " + col + " name: " +gridData.getCard(col, row).getName());
                } else {
                    System.out.println("No card at " + row + "," + col);
                }
            }
        }
    }

    private void validateHands() {
        System.out.println("Ini dari grid");
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

package org.example.src;

import java.io.IOException;

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
import javafx.scene.Parent;
import javafx.scene.input.*;

public class GridController {
    @FXML
    private GridPane grid;
    private Grid gridData;
    private Hands hands;

    @FXML
    public void initialize() {
        try {
            gridData = PlayerManager.getInstance().getCurrentPlayer().getField();
            UIUpdateService.getInstance().setGridsController(this);
            updateGrids(gridData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateGrids(Grid gridData) {
        grid.getChildren().clear();
        int rows = 4;
        int cols = 5;
        for (int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++){
                try {
                    StackPane cell = new StackPane();
                    cell.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #f0f0f0; -fx-border-radius:10");
                    cell.setPadding(new Insets(10));
                    cell.setMinSize(80, 100); 
    
                    Card card = gridData.getCard(col, row);
                    if (card != null) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/src/Card.fxml"));
                        Node cardNode = loader.load();
                        CardController controller = loader.getController();
                        controller.setCardInfo(card.getName() + ".png", card.getName());  
                        cell.getChildren().add(cardNode); 
                    }
    
                    setupDragHandlers(cell, col, row);
                    grid.add(cell, col, row);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setupDragHandlers(Pane cell, int col, int row) {
        gridData = PlayerManager.getInstance().getCurrentPlayer().getField();
        cell.setOnMouseClicked(event -> {
            GameData.getInstance().getCoordinates(col, row);
            System.out.println("This is clicked");
            Card targetCard = gridData.getCard(col, row);
            gridData.printInformation();
            if (targetCard != null) {
                System.out.println("Card at (" + col + ", " + row + "): " + targetCard.getName());
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/src/Card.fxml"));
                    Parent root = loader.load();
                    System.out.println("Namanya adalah"+targetCard.getName());
                    CardController controller = loader.getController();
                    controller.showCardDetails(targetCard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No card at (" + col + ", " + row + ")");
            }
        });
    
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
            Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
            gridData = currentPlayer.getField();
            hands = currentPlayer.getHands();
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
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
    
                                Node cardNode = sourcePane.getChildren().remove(0);
                                targetPane.getChildren().add(cardNode);
                                success = true;
                            }
                        } else {
                            Card targetCard = gridData.getCard(targetCol, targetRow);
                            System.out.println("target card" + targetCard);
                            if (targetCard instanceof AnimalCard && card instanceof ProductCard) {
                                AnimalCard targetAnimalCard = (AnimalCard) targetCard;
                                ProductCard sourcePlantCard = (ProductCard) card;
                                if (targetAnimalCard.isCarnivore() && sourcePlantCard.isNonVeganProduct()) {
                                    hands.deleteCard(sourceIndex);
                                    System.out.println("sebelum makan: " + targetAnimalCard.getCurrentWeight());
                                    targetAnimalCard.eat(targetAnimalCard, sourcePlantCard);
                                    System.out.println("sesudah makan: " + targetAnimalCard.getCurrentWeight());


    
                                    sourcePane.getChildren().remove(0);
                                    success = true;
                                } else if (targetAnimalCard.isHerbivore() && sourcePlantCard.isVeganProduct()) {
                                    hands.deleteCard(sourceIndex);
                                    // gridData.setCard(targetCol, targetRow, card);
                                    System.out.println("sebelum makan: " + targetAnimalCard.getCurrentWeight());
                                    targetAnimalCard.eat(targetAnimalCard, sourcePlantCard);
                                    System.out.println("sesudah makan: " + targetAnimalCard.getCurrentWeight());
                                    

    
                                    sourcePane.getChildren().remove(0);
                                    success = true;
                                } else if (targetAnimalCard.isOmnivore() && card instanceof ProductCard) {
                                    hands.deleteCard(sourceIndex);
                                    // gridData.setCard(targetCol, targetRow, card);
                                    System.out.println("sebelum makan: " + targetAnimalCard.getCurrentWeight());
                                    targetAnimalCard.eat(targetAnimalCard, sourcePlantCard);
                                    System.out.println("sesudah makan: " + targetAnimalCard.getCurrentWeight());


    
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

    
                                    sourcePane.getChildren().remove(0);
                                    success = true;
                                } else if (targetAnimalCard.isHerbivore() && sourcePlantCard.isVeganProduct()) {
                                    gridData.removeCard(sourceIndex, sourceRow);
                                    gridData.setCard(targetCol, targetRow, card);

    
                                    sourcePane.getChildren().remove(0);
                                    success = true;
                                } else if (targetAnimalCard.isOmnivore() && card instanceof ProductCard) {
                                    gridData.removeCard(sourceIndex, sourceRow);
                                    gridData.setCard(targetCol, targetRow, card);

    
                                    sourcePane.getChildren().remove(0);
                                    success = true;
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("list of hands:  " + hands.getCards());
            System.out.println("list of grids: " );
            currentPlayer.getField().printInformation();
    
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

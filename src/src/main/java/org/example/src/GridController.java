package org.example.src;

import java.io.IOException;

import entity.AnimalCard;
import entity.Card;
import entity.GameData;
import entity.Grid;
import entity.Hands;
import entity.IItemEffect;
import entity.PlantCard;
import entity.Player;
import entity.ProductCard;
import javafx.application.Platform;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridController {
    @FXML
    private GridPane grid;
    private Grid currentGrid;
    private Hands hands;

    @FXML
    public void initialize() {
        try {
            currentGrid = PlayerManager.getInstance().getCurrentPlayer().getField();
            UIUpdateService.getInstance().setGridsController(this);
            updateGrids(currentGrid, new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toggleGridDisplay(boolean enemy) {
        if (enemy == false) {
            currentGrid = PlayerManager.getInstance().getCurrentPlayer().getField();
        } else {
            currentGrid = PlayerManager.getInstance().getEnemyPlayer().getField();
        }
        updateGrids(currentGrid, new ArrayList<>());
    }

    public void updateGrids(Grid gridData, List<List<Integer>> attackArea) {
        grid.getChildren().clear();
        if (attackArea == null) {
            attackArea = new ArrayList<>();
        }
        int rows = 4;
        int cols = 5;
        for (int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++){
                try {
                    StackPane cell = new StackPane();

                    List<Integer> positions = Arrays.asList(row, col);
                    if (attackArea.contains(positions)) {
                        cell.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-background-color: #f0f0f0; -fx-border-radius:10");
                    } else {
                        cell.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #f0f0f0; -fx-border-radius:10");
                    }
                    cell.setPadding(new Insets(10));
                    cell.setMinSize(80, 100);

                    Card card = gridData.getCard(col, row);
                    if (card != null) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/src/Card.fxml"));
                        Node cardNode = loader.load();
                        CardController controller = loader.getController();
                        controller.setCard(card);
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
        // currentGrid = PlayerManager.getInstance().getCurrentPlayer().getField();
        cell.setOnMouseClicked(event -> {
            GameData.getInstance().getCoordinates(col, row);
            Card targetCard = currentGrid.getCard(col, row);
            if (targetCard != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/src/Card.fxml"));
                    Parent root = loader.load();
                    CardController controller = loader.getController();
                    controller.openCardDetailWindow(targetCard);
//                    CardController controller = loader.getController();
//                    controller.showCardDetails(targetCard);
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
            // currentGrid = currentPlayer.getField();
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
                    if (card != null && targetCol >= 0 && targetCol < currentGrid.getWidth() && targetRow >= 0 && targetRow < currentGrid.getHeight()) {
                        if (targetPane.getChildren().isEmpty()) {
                            if (card instanceof AnimalCard || card instanceof PlantCard) {
                                System.out.println("this is plant or animal");

                                hands.deleteCard(sourceIndex);
                                currentGrid.setCard(targetCol, targetRow, card);
    
                                Node cardNode = sourcePane.getChildren().remove(0);
                                targetPane.getChildren().add(cardNode);
                                success = true;
                            }
                        } else {
                            Card targetCard = currentGrid.getCard(targetCol, targetRow);
                            System.out.println("target card: " + targetCard);
                            if (targetCard instanceof AnimalCard && card instanceof ProductCard) {
                                AnimalCard targetAnimalCard = (AnimalCard) targetCard;
                                ProductCard sourcePlantCard = (ProductCard) card;
                                if (targetAnimalCard.isCarnivore() && sourcePlantCard.isNonVeganProduct()) {
                                    hands.deleteCard(sourceIndex);
                                    targetAnimalCard.eat(targetAnimalCard, sourcePlantCard);
                                    System.out.println("tes 1");
                                    System.out.println("sesudah makan " + targetAnimalCard.getCurrentWeight());
                                    sourcePane.getChildren().remove(0);
                                    success = true;  
                                } else if (targetAnimalCard.isHerbivore() && sourcePlantCard.isVeganProduct()) {
                                    hands.deleteCard(sourceIndex);
                                    targetAnimalCard.eat(targetAnimalCard, sourcePlantCard);
                                    System.out.println("tes 2");
                                    System.out.println("sesudah makan " + targetAnimalCard.getCurrentWeight());
                                    sourcePane.getChildren().remove(0);
                                    success = true;  
                                } else if (targetAnimalCard.isOmnivore() && card instanceof ProductCard) {
                                    hands.deleteCard(sourceIndex);
                                    targetAnimalCard.eat(targetAnimalCard, sourcePlantCard);
                                    System.out.println("tes 3");
                                    System.out.println("sesudah makan " + targetAnimalCard.getCurrentWeight());
                                    sourcePane.getChildren().remove(0);
                                    success = true;  
                                }
                                                              
                            }else if (card instanceof IItemEffect){
                                IItemEffect itemCard = (IItemEffect) card;
                        
                                if (targetCard instanceof PlantCard) {
                                    itemCard.useOn((PlantCard) targetCard, currentGrid, targetCol, targetRow, currentPlayer);
                                } else if (targetCard instanceof AnimalCard) {
                                    itemCard.useOn((AnimalCard) targetCard, currentGrid, targetCol, targetRow, currentPlayer);
                                }
                                
                                hands.deleteCard(sourceIndex);
                                sourcePane.getChildren().remove(0);
                                success = true;
                            }
                        }
                    }
                } else if ("grid".equals(sourceType)) {
                    Card card = currentGrid.getCard(sourceIndex, sourceRow);
                    if (card != null && (sourceIndex != targetCol || sourceRow != targetRow)) {
                        if (targetPane.getChildren().isEmpty()) {
                            currentGrid.removeCard(sourceIndex, sourceRow);
                            currentGrid.setCard(targetCol, targetRow, card);
                            Node cardNode = sourcePane.getChildren().remove(0);
                            targetPane.getChildren().add(cardNode);
                            success = true;
                        } 
                    }
                }
            }
            if(currentPlayer == PlayerManager.getInstance().getPlayer1()){
                System.out.println("inii sekarang masih player 1");
            }else{
                System.out.println("sekarang player 2");
            }
            System.out.println("list of hands player:  " + hands.getCards());
            System.out.println("list of grids current player: " );
            currentPlayer.getField().printInformation();
            System.out.println("=================================");
            System.out.println("grids musuh");
            PlayerManager.getInstance().getEnemyPlayer().getField().printInformation();
            System.out.println("hands musuh");
            PlayerManager.getInstance().getEnemyPlayer().getHands().getCards();

            event.setDropCompleted(success);
            event.consume();
        });
    
        cell.setOnDragDone(event -> {
            DragContext.getInstance().setDragSource(null);
            event.consume();
        });
    }
}

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
    private boolean isEnemyGridActive = false;
    private static GridController instance;

    @FXML
    public void initialize() {
        try {
            instance = this;
            currentGrid = PlayerManager.getInstance().getCurrentPlayer().getField();
            UIUpdateService.getInstance().setGridsController(this);
            updateGrids(currentGrid, new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GridController() {}

    public static synchronized GridController getInstance() {
        return instance;
    }

    public boolean isEnemyGridActive() {
        return isEnemyGridActive;
    }

    private boolean isNegativeEffect(Card card) {
        if ("Destroy".equals(card.getName()) || "Delay".equals(card.getName())) {
            return true;
        } else {
            return false;
        }
    }

    public void toggleGridDisplay(boolean enemy) {
        if (enemy == false) {
            currentGrid = PlayerManager.getInstance().getCurrentPlayer().getField();
            this.isEnemyGridActive = false;
        } else {
            currentGrid = PlayerManager.getInstance().getEnemyPlayer().getField();
            this.isEnemyGridActive = true;
        }
        updateGrids(currentGrid, DrawsController.getBearAttack().getTargetSubgrid());
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
                        controller.setIsStorePage(false);
                        controller.setCard(card);
                        controller.setCardInfo(card.getName() + ".png", card.getName());
                        if (card instanceof PlantCard) {
                            if (((PlantCard) card).getCurrentAge() >= ((PlantCard) card).getHarvestAge()) {
                                if (card.getName() == "Corn Seed") {
                                    controller.setCardInfo("Corn.png", "Corn");
                                } else if (card.getName() == "Pumpkin Seed") {
                                    controller.setCardInfo("Pumpkin.png", "Pumpkin");
                                } else if (card.getName() == "Strawberry Seed") {
                                    controller.setCardInfo("Strawberry.png", "Strawberry");
                                }
                            }
                        }
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
                            if ((card instanceof AnimalCard || card instanceof PlantCard) && !isEnemyGridActive()) {
                                System.out.println("this is plant or animal");

                                hands.deleteCard(sourceIndex);
                                currentGrid.setCard(targetCol, targetRow, card);
    
                                Node cardNode = sourcePane.getChildren().remove(0);
                                targetPane.getChildren().add(cardNode);
                                success = true;
                            }
                            else{
                                GameController.getInstance().showErrorPopup("Error: Can't drag");
                            }
                        } else {
                            Card targetCard = currentGrid.getCard(targetCol, targetRow);
                            System.out.println("target card: " + targetCard);
                            if (targetCard instanceof AnimalCard && card instanceof ProductCard && !isEnemyGridActive()) {
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
                                }else{
                                    GameController.getInstance().showErrorPopup("Error: Can't drag");
                                }
                                                              
                            }else if (card instanceof IItemEffect){
                                IItemEffect itemCard = (IItemEffect) card;
                        
                                if (!isEnemyGridActive && !isNegativeEffect(card)) {
                                        if (targetCard instanceof PlantCard) {
                                            itemCard.useOn((PlantCard) targetCard, currentGrid, targetCol, targetRow, currentPlayer);
                                        } else if (targetCard instanceof AnimalCard) {
                                            itemCard.useOn((AnimalCard) targetCard, currentGrid, targetCol, targetRow, currentPlayer);
                                        }
                                        hands.deleteCard(sourceIndex);
                                        sourcePane.getChildren().remove(0);
                                        success = true;
                                    } else if (isEnemyGridActive && isNegativeEffect(card)) {
                                        Player enemyPlayer = PlayerManager.getInstance().getEnemyPlayer();
                                        if (targetCard instanceof PlantCard) {
                                            itemCard.useOn((PlantCard) targetCard, currentGrid, targetCol, targetRow, enemyPlayer);
                                        } else if (targetCard instanceof AnimalCard) {
                                            itemCard.useOn((AnimalCard) targetCard, currentGrid, targetCol, targetRow, enemyPlayer);
                                        }
                                        hands.deleteCard(sourceIndex);
                                        sourcePane.getChildren().remove(0);
                                        success = true;
                                }else{
                                    GameController.getInstance().showErrorPopup("Error: Can't drag");
                                }
                            }
                        }
                    }
                } else if ("grid".equals(sourceType)) {
                    Card sourceCard = currentGrid.getCard(sourceIndex, sourceRow);
                    Card targetCard = currentGrid.getCard(targetCol, targetRow);

                    if (sourceCard != null && targetCard != null) {
                        currentGrid.setCard(sourceIndex, sourceRow, targetCard);
                        currentGrid.setCard(targetCol, targetRow, sourceCard);

                        Node sourceNode = sourcePane.getChildren().remove(0);
                        Node targetNode = targetPane.getChildren().remove(0);
                        sourcePane.getChildren().add(targetNode);
                        targetPane.getChildren().add(sourceNode);

                        success = true;
                    } else if (sourceCard != null && targetPane.getChildren().isEmpty()) {
                        currentGrid.setCard(targetCol, targetRow, sourceCard);
                        currentGrid.removeCard(sourceIndex, sourceRow);

                        Node movedNode = sourcePane.getChildren().remove(0);
                        targetPane.getChildren().add(movedNode);

                        success = true;
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

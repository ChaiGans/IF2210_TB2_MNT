package org.example.src;

import entity.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import java.util.Random;

public class DrawsController {
    @FXML
    private GridPane cardGrid;
    private Hands hands;
    private List<Card> draws;
    private static BearAttack bearAttack;
    String name;
    private static Thread bearAttackThread;

    public void initialize() {
        bearAttack = new BearAttack();
        PlayerManager.getInstance().getCurrentPlayer().ShowHand();
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        int size = (6 - PlayerManager.getInstance().getCurrentPlayer().getHands().getCardCount());
        if (size >0){
            if(size >4){
                size = 4;
            }
            if (size > PlayerManager.getInstance().getCurrentPlayer().getDeck().getCurrentDeckCardCount()){
                System.out.println("Ga cukup");
                size = PlayerManager.getInstance().getCurrentPlayer().getDeck().getCurrentDeckCardCount();
            }
            System.out.println("Yang digacha"+size);
            draws = currentPlayer.draw4(size);
            updateCardGrid(draws);
            }
            System.out.println("Player ini jumlah kartu deck :"+PlayerManager.getInstance().getCurrentPlayer().getDeck().getCurrentDeckCardCount());
        }

        public static BearAttack getBearAttack() {
            return bearAttack;
        }

    public synchronized void startBearAttack(Grid field) {
        if (!bearAttack.isBearAttackHappening() && bearAttack.getRandom().nextBoolean()) {
            bearAttack.setBearAttackHappening(true);
            bearAttack.setSubgridPosition(bearAttack.determineSubgridPosition(field));
            int duration = bearAttack.getRandom().nextInt(bearAttack.maxDuration - bearAttack.minDuration + 1) + bearAttack.minDuration;
            startTimer(duration);
            System.out.println("Bear attack started! Duration: " + duration + " seconds");
            MusicManager.stopMusic("mainTheme");
            MusicManager.playMusic("battleTheme", true);
            MusicManager.playMusic("alarmSound", false);
        }
    }

    public void startTimer(int duration) {
        bearAttackThread = new Thread(() -> {
            int remainingTime = duration * 10; // Convert seconds to tenths of a second
            while (remainingTime > 0) {
                try {
                    Thread.sleep(100); // Sleep for 0.1 seconds
                    remainingTime--;
                    float remainingTimeInSeconds = remainingTime / 10.0f;
                    Platform.runLater(() -> GameController.getInstance().updateCounterLabel1(remainingTimeInSeconds));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Preserve interrupt status
                    return; // Stop the thread on interruption
                }
            }
            concludeBearAttack();
        });
        bearAttackThread.start();
    }

    public static void stopBearAttack() {
        if (bearAttack.isBearAttackHappening()) {
            bearAttackThread.interrupt();  
            concludeBearAttack(); 
        }
    }

    private static void concludeBearAttack() {
        bearAttack.setBearAttackHappening(false);
        List<List<Integer>> attack = bearAttack.getTargetSubgrid();
        if (!PlayerManager.getInstance().getCurrentPlayer().getField().isAreaTrap(attack)) {
            PlayerManager.getInstance().getCurrentPlayer().getField().removeCardArea(attack);
        } else {
            PlayerManager.getInstance().getCurrentPlayer().getHands().addCard(CardFactory.createCard("Bear", PlayerManager.getInstance().getCurrentPlayer()));
        }
        bearAttack.setSubgridPosition(new ArrayList<>());
        UIUpdateService.getInstance().updateRealGrid();
        UIUpdateService.getInstance().updateHandsGrid();
        MusicManager.stopAllMusic();
        MusicManager.playMusic("mainTheme", true);
        GameController.getInstance().updateCounterLabel1(0.0F);
        GameController.getInstance().enableButtonBearAtack();
    }

    public void shuffle(){
        int handCardCount = PlayerManager.getInstance().getCurrentPlayer().getHands().getCardCount();
        int initialSize = 6 - handCardCount;
        final int size = Math.min(initialSize, 4);
        System.out.println("Maunya sejumlah"+size);
        System.out.println("Player ini jumlah kartu deck :"+PlayerManager.getInstance().getCurrentPlayer().getDeck().getCurrentDeckCardCount());
        draws = PlayerManager.getInstance().getCurrentPlayer().shuffleDeal(size);
        updateCardGrid(draws);
    }
    public void updateCardGrid(List<Card> draws) {
        cardGrid.getChildren().clear();
        int col = 0;
        int row = 0;

        for (Card card : draws) {
            String name = card.getName();
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/org/example/src/assets/" + name + ".png")));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            GridPane.setHalignment(imageView, HPos.CENTER);
            GridPane.setValignment(imageView, VPos.CENTER); 
            Label label = new Label(name);
            label.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHalignment(label, HPos.CENTER);
            cardGrid.add(imageView, col, row);
            cardGrid.add(label, col, row + 1);
            col++;
            if (col > 1) {
                col = 0;
                row += 2;
            }
        }
        cardGrid.setAlignment(Pos.CENTER);
    }

    @FXML
    private void handleGoBack(ActionEvent event) {
        int handCardCount = PlayerManager.getInstance().getCurrentPlayer().getHands().getCardCount();
        int initialSize = 6 - handCardCount;
        final int size = Math.min(initialSize, 4);
        if(size > 0){
            PlayerManager.getInstance().getCurrentPlayer().ShowHand();
            Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
            currentPlayer.save(draws);
            UIUpdateService.getInstance().updateHandsGrid();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            GameController.getInstance().updateDeckLabel();
            bearAttack = new BearAttack();
            this.startBearAttack(PlayerManager.getInstance().getCurrentPlayer().getField());
            if (bearAttack.isBearAttackHappening()) {
                GameController.getInstance().disableButtonBearAttack();
                UIUpdateService.getInstance().updateGridColorAttack(bearAttack.getTargetSubgrid());
            }
        }
        else{
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
        
    }
}

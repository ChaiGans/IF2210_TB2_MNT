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
import java.util.List;

import javafx.event.ActionEvent;
import java.util.Random;

public class DrawsController {
    @FXML
    private GridPane cardGrid;
    private Hands hands;
    private List<Card> draws;
    String name;

    public void initialize() {
        System.out.println("Ayok gacha");
        hands = GameData.getInstance().getHands();
        System.out.println("Tangan awal:");
        PlayerManager.getInstance().getCurrentPlayer().ShowHand();
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        System.out.println("Before:");
        PlayerManager.getInstance().getCurrentPlayer().ShowHand();
        draws = currentPlayer.draw4();
        updateCardGrid(draws);
    }

    public synchronized void startBearAttack(BearAttack bearAttack, Grid field) {
        if (!bearAttack.isBearAttackHappening() && bearAttack.getRandom().nextBoolean()) {
            bearAttack.setBearAttackHappening(true);
            bearAttack.setSubgridPosition(bearAttack.determineSubgridPosition(field));
            int duration = bearAttack.getRandom().nextInt(bearAttack.maxDuration - bearAttack.minDuration + 1) + bearAttack.minDuration;
            startTimer(duration, bearAttack);
            System.out.println("Bear attack started! Duration: " + duration + " seconds");
            MusicManager.stopMusic("mainTheme");
            MusicManager.playMusic("battleTheme", true);
            MusicManager.playMusic("alarmSound", false);
        }
    }

    public void startTimer(int duration, BearAttack bearAttack) {
        Thread timerThread = new Thread(() -> {
            int remainingTime = duration * 10; // Convert seconds to tenths of a second
            while (remainingTime > 0) {
                try {
                    Thread.sleep(100); // Sleep for 0.1 seconds
                    remainingTime--;
                    float remainingTimeInSeconds = remainingTime / 10.0f;
                    Platform.runLater(() -> GameController.getInstance().updateCounterLabel1(remainingTimeInSeconds));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            bearAttack.setBearAttackHappening(false);
            if (!PlayerManager.getInstance().getCurrentPlayer().getField().isAreaTrap(bearAttack.getTargetSubgrid())) {
                PlayerManager.getInstance().getCurrentPlayer().getField().removeCardArea(bearAttack.getTargetSubgrid());
            } else {
                PlayerManager.getInstance().getCurrentPlayer().getHands().addCard(CardFactory.createCard("Bear", PlayerManager.getInstance().getCurrentPlayer()));
            }
            UIUpdateService.getInstance().updateRealGrid();
            UIUpdateService.getInstance().updateHandsGrid();
            MusicManager.stopAllMusic();
            MusicManager.playMusic("mainTheme", true);
        });
        timerThread.start();
    }

    public void shuffle(){
        draws = PlayerManager.getInstance().getCurrentPlayer().draw4();
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
        System.out.println("ditekan sekali");
        PlayerManager.getInstance().getCurrentPlayer().ShowHand();
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        currentPlayer.save(draws);
        System.out.println("Hands adalah:"+hands);
        UIUpdateService.getInstance().updateHandsGrid();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        BearAttack bearAttack = new BearAttack();
        this.startBearAttack(bearAttack, PlayerManager.getInstance().getCurrentPlayer().getField());
        if (bearAttack.isBearAttackHappening()) {
            UIUpdateService.getInstance().updateGridColorAttack(bearAttack.getTargetSubgrid());
        }
    }
}

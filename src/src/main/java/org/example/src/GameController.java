package org.example.src;

import entity.Card;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.List;

public class GameController {

    @FXML
    private StackPane arrowImageView; 
    @FXML
    private Label counterLabel;
    private int counter = 1;

    @FXML
    private Label counterLabel1;
    private float counterTime = 0.0F;

    private static GameController instance;

    public GameController() {
        instance = this;
    }

    public static GameController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        PlayerManager manager = PlayerManager.getInstance();
        manager.switchPlayer();
        counterLabel.setText(String.valueOf(counter));
        arrowImageView.setOnMouseClicked(event -> nextTurn());
        GameApp.openNewWindow("None", "Draws.fxml");
    }

    private void nextTurn() {
        PlayerManager manager = PlayerManager.getInstance();
        PlayerManager.getInstance().getCurrentPlayer().nextDay();
        manager.switchPlayer(); // Switch to the next player
        counter++;
        counterLabel.setText(String.valueOf(counter));
        UIUpdateService.getInstance().updateRealGrid();
        UIUpdateService.getInstance().updateHandsGrid();
        // List<Card> draws = manager.getCurrentPlayer().draw4();
        // manager.getCurrentPlayer().save(draws);
        GameApp.openNewWindow("None", "Draws.fxml");
    }

    public void updateCounterLabel1(float newValue) {
        counterTime = newValue;
        Platform.runLater(() -> counterLabel1.setText("Time :" + String.valueOf(counterTime)));
    }

    @FXML
    public void ShowEnemy(MouseEvent event){
        UIUpdateService.getInstance().updateEnemyGrid();
    }
    @FXML
    public void ShowCurrent(MouseEvent event){
        UIUpdateService.getInstance().updateRealGrid();
    }
}

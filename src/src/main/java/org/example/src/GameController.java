package org.example.src;

import entity.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.util.List;

public class GameController {

    @FXML
    private ImageView arrowImageView; 
    @FXML
    private Label counterLabel;
    private int counter = 1; 

    @FXML
    public void initialize() {
        counterLabel.setText(String.valueOf(counter));
        arrowImageView.setOnMouseClicked(event -> nextTurn());
    }

    private void nextTurn() {
        PlayerManager manager = PlayerManager.getInstance();
        counter++;
        counterLabel.setText(String.valueOf(counter));
        // List<Card> draws = manager.getCurrentPlayer().draw4();
        // manager.getCurrentPlayer().save(draws);
        manager.switchPlayer(); // Switch to the next player
        GameApp.openNewWindow("None", "Draws.fxml");
    }
}

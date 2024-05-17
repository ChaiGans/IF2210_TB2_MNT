package org.example.src;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
        counter++;
        counterLabel.setText(String.valueOf(counter));
    }
}

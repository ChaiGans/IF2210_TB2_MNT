package org.example.src;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.List;
import entity.Card;
import javafx.event.ActionEvent;
import entity.Player;

public class DrawsController {
    @FXML
    private GridPane cardGrid;
    String name;

    public void initialize() {
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        List<Card> draws = currentPlayer.draw4();

        updateCardGrid(draws);
    }

    private void updateCardGrid(List<Card> draws) {
        cardGrid.getChildren().clear(); // Clear previous contents
        int col = 0;
        int row = 0;

        for (Card card : draws) {
            name = card.getName();
            System.out.println(name);
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/org/example/src/assets/" + name + ".png")));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            Label label = new Label(name);

            cardGrid.add(imageView, col, row);
            cardGrid.add(label, col, row + 1); 
            col++;
            if (col > 1) {
                col = 0;
                row += 2; 
            }
        }
    }

    @FXML
    private void handleGoBack(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}

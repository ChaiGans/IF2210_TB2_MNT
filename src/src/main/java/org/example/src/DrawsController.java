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
import entity.GameData;
import entity.Hands;
import javafx.event.ActionEvent;
import entity.Player;

public class DrawsController {
    @FXML
    private GridPane cardGrid;
    private Hands hands;
    private List<Card> draws;
    String name;

    public void initialize() {
        hands = GameData.getInstance().getHands();
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        System.out.println("Before:");
        PlayerManager.getInstance().getCurrentPlayer().ShowHand();
        draws = currentPlayer.draw4();
        // System.out.println("Gacga"+draws);
        // System.out.println("isi:");
        // PlayerManager.getInstance().getCurrentPlayer().ShowHand();
        // System.out.println("==============");
        updateCardGrid(draws);
    }

    public void updateCardGrid(List<Card> draws) {
        cardGrid.getChildren().clear(); 
        int col = 0;
        int row = 0;

        for (Card card : draws) {
            name = card.getName();
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
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        currentPlayer.save(draws);
        // System.out.println("Yang disimpan:");
        // currentPlayer.ShowHand();
        // System.out.println("After adding draws to saved data.");
        // System.out.println("After");
        for (Card card : draws) {
            System.out.println("after " + card);
            if (hands.getCardCount() < 6) {  
                int index = hands.findNullIndex();
                System.out.println("index:"+index);
                hands.addbyFirstNull(card);
            } else {
                System.out.println("Hands are full. Cannot add more cards.");
                break;
            }
        }

        // System.out.println("Current hands state after adding new cards:");
        // PlayerManager.getInstance().getCurrentPlayer().ShowHand();
        UIUpdateService.getInstance().updateHandsGrid();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}

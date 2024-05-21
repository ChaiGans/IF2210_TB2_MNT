package org.example.src;

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
    }
}

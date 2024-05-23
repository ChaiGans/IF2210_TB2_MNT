package org.example.src;


import entity.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class DetailsController {
    @FXML
    private Label cardNameText;
    @FXML
    private StackPane PanenButton;
    @FXML
    private Label boostLabel;

    @FXML
    private ImageView cardImageView;
    
    @FXML
    private Label cardInfoLabel;

    public void setCardDetails(Card card) {
        cardNameText.setText(card.getName());
        boostLabel.setText("Boost: " + (card.getActiveEffect().isEmpty() ? "None" : String.join(", ", card.getActiveEffect())));
        int ready = 0;
        int current = 0;
        if (card instanceof AnimalCard) {
            AnimalCard animalCard = (AnimalCard) card;
            cardInfoLabel.setText("Weight: " + animalCard.getCurrentWeight());
            current = animalCard.getCurrentWeight();
            ready = animalCard.getHarvestWeight();
        } else if (card instanceof PlantCard) {
            PlantCard plantCard = (PlantCard) card;
            cardInfoLabel.setText("Duration: " + plantCard.getCurrentAge());
            current = plantCard.getCurrentAge();
            ready = plantCard.getHarvestAge();
        } else {
            cardInfoLabel.setText("No additional information available");
        }
        // if (current >=ready){
        //     PanenButton.setVisible(true);
        // }
        // else{
        //     PanenButton.setVisible(false);
        // }

        try {
            Image image = new Image(getClass().getResourceAsStream("/org/example/src/assets/" + card.getName() + ".png"));
            cardImageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            cardImageView.setImage(new Image(getClass().getResourceAsStream("/org/example/src/assets/default.png")));
        }
    }

    @FXML
    private void handleClose(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void DoPanen(MouseEvent event){
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        int col = GameData.getInstance().ColClicked();
        int row = GameData.getInstance().rowClciked();
        Card card = currentPlayer.Panen(col, row);
        currentPlayer.AddHand(card);
        UIUpdateService.getInstance().updateHandsGrid();
        UIUpdateService.getInstance().updateRealGrid();
        Stage stage = (Stage) PanenButton.getScene().getWindow();
        stage.close();
    }

}

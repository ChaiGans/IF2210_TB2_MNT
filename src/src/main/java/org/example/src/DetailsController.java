package org.example.src;
import entity.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class DetailsController {
    @FXML
    private Label cardNameLabel;

    @FXML
    private Label boostLabel;

    @FXML
    private ImageView cardImageView;
    @FXML
    private Label cardInfoLabel; 
    public void setCardDetails(Card card) {
        cardNameLabel.setText(card.getName());
        boostLabel.setText("Boost: " + (card.getActiveEffect().isEmpty() ? "None" : String.join(", ", card.getActiveEffect())));

        if (card instanceof AnimalCard) {
            AnimalCard animalCard = (AnimalCard) card;
            cardInfoLabel.setText("Weight: " + animalCard.getCurrentWeight());
        } else if (card instanceof PlantCard) {
            PlantCard plantCard = (PlantCard) card;
            cardInfoLabel.setText("Duration: " + plantCard.getCurrentAge());
        } else {
            cardInfoLabel.setText("No additional information available");
        }

        try {
            Image image = new Image(getClass().getResourceAsStream("/org/example/src/assets/" + card.getName() + ".png"));
            cardImageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            cardImageView.setImage(new Image(getClass().getResourceAsStream("/org/example/src/assets/default.png")));
        }
        if (card instanceof AnimalCard && card instanceof PlantCard);
    }
}

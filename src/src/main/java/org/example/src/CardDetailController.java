package org.example.src;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import entity.*;

public class CardDetailController {

    @FXML
    private Text cardNameText;

    @FXML
    private Button backButton31;
    @FXML
    private Button panenButton11;

    @FXML
    private ImageView cardImageView;

    @FXML
    private Text infoText;

    @FXML
    private Text Accelerate;

    @FXML
    private Text Protect;

    @FXML
    private Text Destroy;

    @FXML
    private Text InstantHarvest;

    @FXML
    private Text Trap;

    @FXML
    private Text Delay;


    public void setCardDetails(Card card) {
        cardNameText.setText(card.getName());
        //boostLabel.setText("Boost: " + (card.getActiveEffect().isEmpty() ? "None" : String.join(", ", card.getActiveEffect())));
        int ready = 0;
        int current = 0;
        int acc, pro, del, des, ins, trap = 0;
        if (card instanceof AnimalCard) {
            AnimalCard animalCard = (AnimalCard) card;
            infoText.setText("Berat: " + animalCard.getCurrentWeight() + " / Target: " + animalCard.getHarvestWeight());
            current = animalCard.getCurrentWeight();
            ready = animalCard.getHarvestWeight();
        } else if (card instanceof PlantCard) {
            PlantCard plantCard = (PlantCard) card;
            infoText.setText("Berat: " + plantCard.getCurrentAge() + " / Target: " + plantCard.getHarvestAge());
            current = plantCard.getCurrentAge();
            ready = plantCard.getHarvestAge();
        } else {
            infoText.setText(" ");
//            goalText.setText("");
        }
        if (current >= ready) {
            System.out.println("Current weight is " + current + " and ready weight is " + ready + "READY");
//            PanenButton.setVisible(true);
        } else {
            System.out.println("Current weight is " + current + " and ready weight is " + ready);
//            PanenButton.setVisible(false);
        }

        try {
            Image image = new Image(getClass().getResourceAsStream("/org/example/src/assets/" + card.getName() + ".png"));
            cardImageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            cardImageView.setImage(new Image(getClass().getResourceAsStream("/org/example/src/assets/default.png")));
        }

        acc = card.getEffectCount("Accelerate");
        pro = card.getEffectCount("Protect"); Protect.setText(pro + " ");
        des = card.getEffectCount("Destroy"); Destroy.setText(des + " ");
        del = card.getEffectCount("Delay"); Delay.setText(del + " ");
        ins = card.getEffectCount("Instant Harvest"); InstantHarvest.setText(ins + " ");
        trap = card.getEffectCount("Trap"); Trap.setText(trap + " ");
    }


    public void setCardDetails(String cardName, Image cardImage) {
        cardNameText.setText(cardName);
        cardImageView.setImage(cardImage);
    }

    @FXML
    private void handleBackButtonAction() {
        // Close the current window
        backButton31.getScene().getWindow().hide();
    }

    @FXML
    private void DoPanen(){
        System.out.println("Hello");
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        int col = GameData.getInstance().ColClicked();
        int row = GameData.getInstance().rowClciked();
        Card card = currentPlayer.Panen(col, row);
        currentPlayer.AddHand(card);
        UIUpdateService.getInstance().updateHandsGrid();
        UIUpdateService.getInstance().updateRealGrid();
        Stage stage = (Stage) panenButton11.getScene().getWindow();
        stage.close();
    }

}

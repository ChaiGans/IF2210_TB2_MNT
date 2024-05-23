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
    private int current;
    private int ready;
    private Card card;

    @FXML
    private Text cardNameText;

    @FXML
    private Button backButton31;
    @FXML
    private Button panenButton11;

    @FXML
    private ImageView cardImageView;

    @FXML
    private Button PanenButton;

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
        this.card = card;
        cardNameText.setText(card.getName());
        int acc, pro, del, des, ins, trap = 0;
        if (card instanceof AnimalCard) {
            AnimalCard animalCard = (AnimalCard) card;
            infoText.setText("Berat: " + animalCard.getCurrentWeight() + " / Target: " + animalCard.getHarvestWeight());
            this.current = animalCard.getCurrentWeight();
            this.ready = animalCard.getHarvestWeight();

        } else if (card instanceof PlantCard) {
            PlantCard plantCard = (PlantCard) card;
            infoText.setText("Berat: " + plantCard.getCurrentAge() + " / Target: " + plantCard.getHarvestAge());
            this.current = plantCard.getCurrentAge();
            this.ready = plantCard.getHarvestAge();

        } else {
            infoText.setText(" ");
        }

        try {
            Image image = new Image(getClass().getResourceAsStream("/org/example/src/assets/" + card.getName() + ".png"));
            cardImageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            cardImageView.setImage(new Image(getClass().getResourceAsStream("/org/example/src/assets/default.png")));
        }

        acc = card.getEffectCount("Accelerate"); Accelerate.setText(acc + " ");
        pro = card.getEffectCount("Protect"); Protect.setText(pro + " ");
        des = card.getEffectCount("Destroy"); Destroy.setText(des + " ");
        del = card.getEffectCount("Delay"); Delay.setText(del + " ");
        ins = card.getEffectCount("Instant Harvest"); InstantHarvest.setText(ins + " ");
        trap = card.getEffectCount("Trap"); Trap.setText(trap + " ");

        panenButton11.setVisible(!GridController.getInstance().isEnemyGridActive());
    }


    public void setCardDetails(String cardName, Image cardImage) {
        cardNameText.setText(cardName);
        cardImageView.setImage(cardImage);
    }
    @FXML
    private void handleBackButtonAction() {
        backButton31.getScene().getWindow().hide();
    }

    @FXML
    private void DoPanen(){
        System.out.println("Kepanggil");
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        int col = GameData.getInstance().ColClicked();
        int row = GameData.getInstance().rowClciked();
        Card card = currentPlayer.getField().getCard(col, row);
        if (card instanceof AnimalCard){
            AnimalCard animalCard = (AnimalCard) card;
            if (animalCard.isReadyToHarvest()){
                ProductCard cards = animalCard.harvest();
                currentPlayer.AddHand(cards);
                currentPlayer.Panen(col, row);
            }
            else{
                GameController.getInstance().showErrorPopup("Not Ready To Harvest");
            }
        }else if (card instanceof PlantCard){
            PlantCard plantCard = (PlantCard) card;
            if (plantCard.isReadyToHarvest()){
                ProductCard cards = plantCard.harvest();
                currentPlayer.AddHand(cards);
                currentPlayer.Panen(col, row);
            }
            else{
                GameController.getInstance().showErrorPopup("Not Ready To Harvest");
            }
        }
        UIUpdateService.getInstance().updateHandsGrid();
        UIUpdateService.getInstance().updateRealGrid();
        Stage stage = (Stage) panenButton11.getScene().getWindow();
        stage.close();
    }

}

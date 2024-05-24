package org.example.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import entity.*;

public class SellCardController {
    private Card card;
    private Store store;
    private StoreController storeController;
    @FXML private Text cardNameText;
    @FXML private Button backButton;
    @FXML private ImageView cardImageView;
    @FXML private Text infoPrice;

    public void setCardDetails(Card card) {
        this.card = card;
        cardNameText.setText(card.getName());
        if (card instanceof ProductCard) {
            ProductCard productSell = (ProductCard) card;
            infoPrice.setText("$ " + productSell.getPrice());

        } else {
            infoPrice.setText("OOps! Not a product. Can't sell this card");
        }

        try {
            Image image = new Image(getClass().getResourceAsStream("/org/example/src/assets/" + card.getName() + ".png"));
            cardImageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            cardImageView.setImage(new Image(getClass().getResourceAsStream("/org/example/src/assets/default.png")));
        }
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setStoreController(StoreController storeController) {
        this.storeController = storeController;
    }

    @FXML
    private void handleBackButtonAction() {
        backButton.getScene().getWindow().hide();
    }

    @FXML
    public void DoSell(ActionEvent actionEvent) {
        System.out.println("Bisa tekan");
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        Hands hands = currentPlayer.getHands();
        System.out.println(hands.findCardLocation(card));
        int price, currentMoney, idx = 0;
        currentMoney = currentPlayer.getCash();
        if (card instanceof ProductCard) {
            ProductCard productSell = (ProductCard) card;
            price = productSell.getPrice();
            currentPlayer.setCash(currentMoney + price);
            String index = hands.findCardLocation(card);

            idx = hands.getCardIndexFromHands(index);
            System.out.println(idx);
            hands.deleteCard(idx);
            currentPlayer.setHands(hands);
            UIUpdateService.getInstance().updateHandsGrid();
//            storeController.updateStoreGrid(hands);
            if (store == null) {
                store = GameController.getInstance().getStore();
            }

            store.addItem(productSell, 1);
            store.printStoreInformation();
            if (storeController != null) {
                storeController.updateStoreGrid(hands);
            }
            updateStoreHands();
            updateStock();
            GameController.getInstance().updateMoneyUI();
            showSuccessMessage();
//            else {
//                System.out.println("Store is not initialized.");
//            }
        } else {
            System.out.println("Harusnya ga keluar pagenya atau keluar pagenya tapi tulisannya oops");
        }
    }

    private void updateStoreHands() {
        UIUpdateService.getInstance().updateStoreHandsGrid();
    }

    private void updateStock() {
        UIUpdateService.getInstance().UpdateStockProduct();
    }

    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Sell operation succeeded!");
        alert.showAndWait();
    }


}
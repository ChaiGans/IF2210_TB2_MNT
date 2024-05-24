package org.example.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import entity.ProductCard;
import entity.Store;
import entity.*;

import java.util.Map;

public class StoreController {
    @FXML private Button buyPumpkin;
    @FXML private Button buyBearMeat;
    @FXML private Button buyEgg;
    @FXML private Button buyStrawberry;
    @FXML private Button buyHorseMeat;
    @FXML private Button buyMilk;
    @FXML private Button buyCorn;
    @FXML private Button buySheepMeat;
    @FXML private Button buySharkFin;
    @FXML private Text pricePumpkin;
    @FXML private Text stockPumpkin;
    @FXML private Text priceBearMeat;
    @FXML private Text stockBearMeat;
    @FXML private Text priceEgg;
    @FXML private Text stockEgg;
    @FXML private Text stockStrawberry;
    @FXML private Text stockHorseMeat;
    @FXML private Text stockMIlk;
    @FXML private Text stockCorn;
    @FXML private Text stockSheepMeat;
    @FXML private Text stockSharkFin;
    @FXML private Text priceStrawberry;
    @FXML private Text priceHorseMeat;
    @FXML private Text priceMilk;
    @FXML private Text priceCorn;
    @FXML private Text priceSheepMeat;
    @FXML private Text priceSharkFIn;
    @FXML private ImageView backButton;
    @FXML private GridPane storeHandsGrid;
    private Store store;
    private Hands hands;
    private Map<ProductCard, Integer> products;


    @FXML
    public void initialize() {
        try {
            this.store = new Store();
            Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
            hands = currentPlayer.getHands();
            CardController controller = new CardController();
            controller.setIsStorePage(true);
            if (storeHandsGrid == null) {
                System.out.println("storeHandsGrid is null!");
            } else {
                System.out.println("storeHandsGrid is initialized.");
            }
            UIUpdateService.getInstance().updateStoreHandsGrid();
            updateStoreGrid(hands);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        backButton.getScene().getWindow().hide();
    }

    public void setStore(Store store) {
        this.products = store.getStoreInformation();
        updateUI(store);
    }

    public void updateUI(Store store) {
        if (products != null) {
            for (Map.Entry<ProductCard, Integer> entry : store.getStoreInformation().entrySet()) {
                ProductCard product = entry.getKey();
                int quantity = entry.getValue();
                updateProductInfo(product, quantity);
            }
        }
    }

    public void updateStoreGrid(Hands hands) {
        storeHandsGrid.getChildren().clear();
        int maxColumns = 6;

        for (int i = 0; i < maxColumns; i++) {
            try {
                StackPane cell = new StackPane();
                cell.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #f0f0f0;-fx-border-radius:10");
                cell.setMinSize(80, 100);
                Card cards = hands.getCard(i);
                if(this.hands.getCards() != null){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/src/Card.fxml"));
                    Node cardNode = loader.load();
                    CardController controller = loader.getController();
                    if (hands.getCards().get(i) != null) {
                        Card card = hands.getCards().get(i);
                        controller.setIsStorePage(true);
                        controller.setCard(card);
                        controller.setCardInfo(card.getName() + ".png", card.getName());
                        cell.getChildren().add(cardNode);
                    }
                }
                storeHandsGrid.add(cell, i, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateProductInfo(ProductCard product, int quantity) {
        String productName = product.getName();
        int price = product.getPrice();

        switch (productName) {
            case "Pumpkin":
                pricePumpkin.setText(price + "");
                stockPumpkin.setText(quantity + "");
                break;
            case "Bear Meat":
                priceBearMeat.setText(price + "");
                stockBearMeat.setText(quantity + "");
                break;
            case "Egg":
                priceEgg.setText(price + "");
                stockEgg.setText(quantity + "");
                break;
            case "Strawberry":
                priceStrawberry.setText(price + "");
                stockStrawberry.setText(quantity + "");
                break;
            case "Horse Meat":
                priceHorseMeat.setText(price + "");
                stockHorseMeat.setText(quantity + "");
                break;
            case "Milk":
                priceMilk.setText(price + "");
                stockMIlk.setText(quantity + "");
                break;
            case "Corn":
                priceCorn.setText(price + "");
                stockCorn.setText(quantity + "");
                break;
            case "Sheep Meat":
                priceSheepMeat.setText(price + "");
                stockSheepMeat.setText(quantity + "");
                break;
            case "Shark Fin":
                priceSharkFIn.setText(price + "");
                stockSharkFin.setText(quantity + "");
                break;
            default:
                break;
        }
    }

}

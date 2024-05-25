package org.example.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
//    private Store store;
    private Hands hands;
    private Map<ProductCard, Integer> products;


    @FXML
    public void initialize() {
        try {
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
            UIUpdateService.getInstance().setStoreController(this);
            UIUpdateService.getInstance().UpdateStockProduct();
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

    public void updateProductInfo(ProductCard product, int quantity) {
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

    @FXML
    private void handleBuyAction(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        String productName = null;

        switch (sourceButton.getId()) {
            case "buyPumpkin":
                productName = "Pumpkin";
                break;
            case "buyStrawberry":
                productName = "Strawberry";
                break;
            case "buyCorn":
                productName = "Corn";
                break;
            case "buySheepMeat":
                productName = "Sheep Meat";
                break;
            case "buyHorseMeat":
                productName = "Horse Meat";
                break;
            case "buyBearMeat":
                productName = "Bear Meat";
                break;
            case "buyMilk":
                productName = "Milk";
                break;
            case "buyEgg":
                productName = "Egg";
                break;
            case "buySharkFin":
                productName = "Shark Fin";
                break;
        }

        if (productName != null) {
            try {
                DoBuy(productName);
            } catch (Exception e) {
                showErrorMessage(e.getMessage());
            }
        }
    }

    private void DoBuy(String productName) throws Exception {
        System.out.println("Bisa tekan beli");
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        ProductCard productCard = GameController.getInstance().getStore().findProductByName(productName);
        System.out.println("Product found: " + productCard);

        if (productCard != null) {
            if (currentPlayer.getHands().getCardCount() < 6){
                GameController.getInstance().getStore().purchaseItem(productCard, currentPlayer, 1);
                UIUpdateService.getInstance().updateHandsGrid();
                UIUpdateService.getInstance().UpdateStockProduct();
                updateStoreHands();
                updateStock();
                showSuccessMessage("Purchase operation succeeded!");
                GameController.getInstance().getStore().printStoreInformation();
                UIUpdateService.getInstance().updateStoreHandsGrid();
                GameController.getInstance().updateMoneyUI();
            } else {
                throw new Exception("Your hands are too full!.");
            }

        } else {
            throw new Exception("The product is unavailable now.");
        }
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        UIUpdateService.getInstance().updateStoreHandsGrid();
        GameController.getInstance().updateMoneyUI();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateStoreHands() {
        UIUpdateService.getInstance().updateStoreHandsGrid();
    }

    private void updateStock() {
        UIUpdateService.getInstance().updateHandsGrid();
    }

}

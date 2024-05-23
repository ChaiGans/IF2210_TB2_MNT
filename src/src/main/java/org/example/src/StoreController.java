package org.example.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import entity.ProductCard;
import entity.Store;

import java.util.Map;

public class StoreController {
    @FXML private Button buyPumpkin;
    @FXML private Button buyBearMeat;
    @FXML private Text pricePumpkin;
    @FXML private Text stockPumpkin;
    @FXML private Text priceBearMeat;
    @FXML private Text stokBearMeat;
    @FXML private Text priceEgg;
    @FXML private Text stockEgg;
    @FXML private Text stockStrawberry;
    @FXML private Text stokHorseMeat;
    @FXML private Text stockMIlk;
    @FXML private Text stockCorn;
    @FXML private Text stockSheepMeat;
    @FXML private Text stockSharkFin;
    @FXML private Text priceStarwberry;
    @FXML private Text priceHorseMeat;
    @FXML private Text priceMilk;
    @FXML private Text priceCorn;
    @FXML private Text priceSheepMeat;
    @FXML private Text priceSharkFIn;
    @FXML private ImageView backButton;
    private Map<ProductCard, Integer> products;


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

    private void setPrices(ProductCard product) {

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
                stokBearMeat.setText(quantity + "");
                break;
            case "Egg":
                priceEgg.setText(price + "");
                stockEgg.setText(quantity + "");
                break;
            case "Strawberry":
                priceStarwberry.setText(price + "");
                stockStrawberry.setText(quantity + "");
                break;
            case "Horse Meat":
                priceHorseMeat.setText(price + "");
                stokHorseMeat.setText(quantity + "");
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

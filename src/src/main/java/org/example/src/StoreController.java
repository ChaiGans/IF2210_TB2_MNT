package org.example.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
    @FXML private GridPane storeHandsGrid;
    private Hands hands;
    private Map<ProductCard, Integer> products;


    @FXML
    public void initialize() {
        try {
            Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
            hands = currentPlayer.getHands();
            UIUpdateService.getInstance().setStoreController(this);
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

//    private void populateStoreHandsGrid() throws Exception {
//        int rows = 1;
//        int cols = 6;
//        updateStoreGrid(hands);
//        for (int row = 0; row < rows; row++) {
//            for (int col = 0; col < cols; col++) {
//                StackPane cell = new StackPane();
//                cell.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #f0f0f0;-fx-border-radius:10");
//                cell.setPadding(new Insets(10));
//                cell.setMinSize(80, 100);
//                //setupStoreDragHandlers(cell, col, 0);
//                //storeHandsGrid.add(cell, col, 0);
//            }
//        }
//    }

    public void updateStoreGrid(Hands hands) {
        //storeHandsGrid.getChildren().clear();
        int maxColumns = 6;

        for (int i = 0; i < maxColumns; i++) {
            try {
                StackPane cell = new StackPane();
                cell.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #f0f0f0;-fx-border-radius:10");
                cell.setMinSize(80, 100);
                Card cards = hands.getCard(i);
                if(cards != null){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/src/Card.fxml"));
                    Node cardNode = loader.load();
                    CardController controller = loader.getController();
                    if (hands.getCards().get(i) != null) {
                        Card card = hands.getCards().get(i);
                        controller.setCard(card);
                        controller.setCardInfo(card.getName() + ".png", card.getName());
                        cell.getChildren().add(cardNode);
                    }
                }

                //setupStoreDragHandlers(cell, i, 0);
                //storeHandsGrid.add(cell, i, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    private void setupStoreDragHandlers(Pane cell, int col, int row) {
//        cell.setOnDragDetected(event -> {
//            if (!cell.getChildren().isEmpty()) {
//                Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
//                ClipboardContent content = new ClipboardContent();
//                content.putString("hands," + storeHandsGrid.getChildren().indexOf(cell));
//                db.setContent(content);
//                DragContext.getInstance().setDragSource(cell);
//                event.consume();
//            }
//        });
//
//        cell.setOnDragOver(event -> {
//            if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
//                event.acceptTransferModes(TransferMode.MOVE);
//            }
//            event.consume();
//        });
//
//        cell.setOnDragDropped(event -> {
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            Node dragSource = DragContext.getInstance().getDragSource();
//            if (db.hasString() && dragSource != null) {
//                String[] parts = db.getString().split(",");
//                int sourceIndex = Integer.parseInt(parts[1]);
//
//                Pane sourcePane = (Pane) dragSource;
//                Pane targetPane = (Pane) event.getGestureTarget();
//
//                int targetIndex = GridPane.getColumnIndex(targetPane);
//                if (sourceIndex != targetIndex) {
//                    Card sourceCard = hands.getCard(sourceIndex);
//                    Card targetCard = hands.getCard(targetIndex);
//                    if (sourceCard != null) {
//                        if (targetCard != null) {
//                            hands.setCard(targetIndex, sourceCard);
//                            hands.setCard(sourceIndex, targetCard);
//                        } else {
//                            hands.moveCard(sourceIndex, targetIndex);
//                        }
//
//                        Node sourceNode = sourcePane.getChildren().remove(0);
//                        Node targetNode = targetPane.getChildren().isEmpty() ? null : targetPane.getChildren().remove(0);
//
//                        targetPane.getChildren().add(sourceNode);
//                        if (targetNode != null) {
//                            sourcePane.getChildren().add(targetNode);
//                        }
//
//                        success = true;
//                    }
//                }
//            }
//
//            event.setDropCompleted(success);
//            event.consume();
//        });
//
//        cell.setOnDragDone(event -> {
//            DragContext.getInstance().setDragSource(null);
//            event.consume();
//        });
//    }



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
//
//    @FXML
//    private void handleBuyPumpkinAction(ActionEvent event) {
//        buyProduct(new Pumpkin(player));
//    }
//    @FXML
//    private void handleBuyBearMeatAction(ActionEvent event) {
//        buyProduct(new BearMeat(player));
//    }
//
//    @FXML
//    private void handleBuyEggAction(ActionEvent event) {
//        buyProduct(new Egg(player));
//    }
//
//    @FXML
//    private void handleBuyStrawberryAction(ActionEvent event) {
//        buyProduct(new Strawberry(player));
//    }
//
//    @FXML
//    private void handleBuyHorseMeatAction(ActionEvent event) {
//        buyProduct(new HorseMeat(player));
//    }
//
//    @FXML
//    private void handleBuyMilkAction(ActionEvent event) {
//        buyProduct(new Milk(player));
//    }
//
//    @FXML
//    private void handleBuyCornAction(ActionEvent event) {
//        buyProduct(new Corn(player));
//    }
//
//    @FXML
//    private void handleBuySheepMeatAction(ActionEvent event) {
//        buyProduct(new SheepMeat(player));
//    }
//
//    @FXML
//    private void handleBuySharkFinAction(ActionEvent event) {
//        buyProduct(new SharkFin(player));
//    }

//    private void buyProduct(ProductCard product) {
//        try {
//            store.purchaseItem(product, player, 1); // Purchase one item
//            updateUI();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}

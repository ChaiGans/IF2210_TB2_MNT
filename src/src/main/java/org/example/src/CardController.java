package org.example.src;
import java.io.IOException;

import entity.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
public class CardController {
    @FXML
    private VBox card;
    @FXML
    private ImageView cardImage;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Label cardLabel;
    @FXML
    private Label cardNameLabel;

    private String cardName;
    private Card cardEntity;
    private Image cardImg;

    @FXML
    private Label boostLabel;
    public void initialize() {
        // makeDraggable();
        // makeClickable();
    }

    private void makeDraggable() {
        card.setOnMousePressed(event -> {
            xOffset = event.getSceneX() - card.getTranslateX();  // Using translateX/Y for dragging
            yOffset = event.getSceneY() - card.getTranslateY();
            card.toFront();
            System.out.println("Mouse pressed - x: " + xOffset + ", y: " + yOffset);
        });

        card.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - xOffset;
            double newY = event.getSceneY() - yOffset;
            System.out.println("Dragging to - x: " + newX + ", y: " + newY);
            card.setTranslateX(newX);
            card.setTranslateY(newY);
        });
    }
//    private void makeClickable() {
//        card.setOnMouseClicked(event -> {
//            System.out.println("Mouse clicked - x: " + event.getSceneX() + ", y: " + event.getSceneY());
//            openCardDetailWindow();
//        });
//    }

    public void showCardDetails(Card card) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/src/Details.fxml"));
            Parent root = loader.load();
            DetailsController controller = loader.getController();
            controller.setCardDetails(card);
            Stage detailStage = new Stage();
            Scene scene = new Scene(root);
            detailStage.setScene(scene);
            detailStage.setOnShown(event -> {
                double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
                double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
                detailStage.setX((screenWidth - detailStage.getWidth()) / 2);
                detailStage.setY((screenHeight - detailStage.getHeight()) / 2);
            });
            detailStage.initStyle(StageStyle.UNDECORATED);
            detailStage.setTitle("Card Details");
            detailStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageView getCardImage() {
        return cardImage;
    }

    public void setCard(Card card) {
        this.cardEntity = card;  // Store the Card entity
        Image image = new Image(getClass().getResourceAsStream("/org/example/src/assets/" + card.getName() + ".png"));
        cardImage.setImage(image);
        cardLabel.setText(card.getName());
        this.cardImg = image;
    }

    public void setCardInfo(String imageName, String cardName) {
        Image image = new Image(getClass().getResourceAsStream("/org/example/src/assets/" + imageName));
        cardImage.setImage(image);
        cardLabel.setText(cardName);
        this.cardName = cardName;
        this.cardImg = image;
    }

    public void openCardDetailWindow(Card card) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/src/CardDetail.fxml"));
            Parent root = loader.load();

            // Get the controller instance and pass the card information
            CardDetailController controller = loader.getController();
            controller.setCardDetails(card);
            // controller.setCardDetails(cardName, cardImg);

            Stage stage = new Stage();
            stage.setTitle("Card Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
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
import javafx.stage.Stage;
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

    @FXML
    private Label boostLabel;
    public void initialize() {
        // makeDraggable();
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
    public void showCardDetails(Card card) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/src/Details.fxml"));
            Parent root = loader.load();
            DetailsController controller = loader.getController();
            controller.setCardDetails(card);
            Stage detailStage = new Stage();
            Scene scene = new Scene(root);
            detailStage.setScene(scene);
            detailStage.setTitle("Card Details");
            detailStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageView getCardImage() {
        return cardImage;
    }
    public void setCardInfo(String imageName, String cardName) {
        Image image = new Image(getClass().getResourceAsStream("/org/example/src/assets/" + imageName));
        cardImage.setImage(image);
        cardLabel.setText(cardName);
    }
}
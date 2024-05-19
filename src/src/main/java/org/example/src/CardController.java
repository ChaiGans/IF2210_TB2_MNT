package org.example.src;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class CardController {
    @FXML
    private VBox card;
    @FXML
    private ImageView cardImage;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
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
    public ImageView getCardImage() {
        return cardImage;
    }
}
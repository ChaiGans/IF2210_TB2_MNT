package org.example.src;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
public class GridController {
    @FXML
    private GridPane grid;
    @FXML
    public void initialize() {
        try {
            populateGrid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void populateGrid() throws Exception {
        int rows = 9;
        int cols = 9;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load();
                StackPane stack = new StackPane();
                Pane borderPane = new Pane();
                borderPane.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: transparent;");
                stack.getChildren().addAll(borderPane, card);
                StackPane.setMargin(card, new javafx.geometry.Insets(10));
                grid.add(stack, j, i);
            }
        }
    }
}


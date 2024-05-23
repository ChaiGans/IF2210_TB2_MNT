package org.example.src;
import javafx.scene.Scene;
import entity.Card;
import javafx.application.Platform;
import entity.GameState;
import entity.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class GameController {
//    @FXML
//    private Text SaveState;
//    @FXML
//    private Text LoadState;
    @FXML
    private Text Player1Money;
    @FXML
    private Text Player2Money;
    @FXML
    private StackPane arrowImageView; 
    @FXML
    private Label counterLabel;
    private int counter = 1;

    private PlayerManager manager;

    @FXML
    private Label counterLabel1;
    private float counterTime = 0.0F;

    private static GameController instance;

    public GameController() {
        instance = this;
    }

    public static GameController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        this.manager = PlayerManager.getInstance();
        updateMoneyDisplay();
        manager.switchPlayer();
        counterLabel.setText(String.valueOf(counter));
        arrowImageView.setOnMouseClicked(event -> nextTurn());
        GameApp.openNewWindow("None", "Draws.fxml");
    }

    private void nextTurn() {
        manager = PlayerManager.getInstance();
        PlayerManager.getInstance().getCurrentPlayer().nextDay();
        manager.switchPlayer(); // Switch to the next player
        counter++;
        counterLabel.setText(String.valueOf(counter));
        UIUpdateService.getInstance().updateRealGrid();
        UIUpdateService.getInstance().updateHandsGrid();
        // List<Card> draws = manager.getCurrentPlayer().draw4();
        // manager.getCurrentPlayer().save(draws);
        GameApp.openNewWindow("None", "Draws.fxml");
        updateMoneyDisplay();
    }

    public void updateCounterLabel1(float newValue) {
        counterTime = newValue;
        Platform.runLater(() -> counterLabel1.setText("Time :" + String.valueOf(counterTime)));
    }

    @FXML
    public void ShowEnemy(MouseEvent event){
        UIUpdateService.getInstance().updateEnemyGrid();
    }
    @FXML
    public void ShowCurrent(MouseEvent event){
        UIUpdateService.getInstance().updateRealGrid();
    }

    @FXML
    public void SaveState(MouseEvent event){
        GameApp.openNewWindow("SaveState", "SaveState.fxml");
    }

    @FXML
    public void LoadState(MouseEvent event){
        GameApp.openNewWindow("LoadState", "LoadState.fxml");
    }

    @FXML
    public void LoadPlugin(MouseEvent event){
        GameApp.openNewWindow("LoadPlugin", "LoadPlugin.fxml");
    }

    private void updateMoneyDisplay() {
        Player1Money.setText("$ " + manager.getCurrentPlayer().getCash());
        Player2Money.setText("$ " + manager.getEnemyPlayer().getCash());
    }





    public void updateGameState(GameState gameState) {
        // Update UI or perform necessary actions with the loaded game state
        PlayerManager.getInstance().setPlayers(gameState.getPlayers());
        UIUpdateService.getInstance().updateRealGrid();
        UIUpdateService.getInstance().updateHandsGrid();
    }

    @FXML
    private void viewToko(MouseEvent event) {
        try {
            System.out.println("Dioteklan");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Store.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Toko");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

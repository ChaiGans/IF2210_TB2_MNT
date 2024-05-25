package org.example.src;
import entity.*;
import javafx.scene.Scene;
import javafx.application.Platform;
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

import java.io.IOException;
import java.util.List;

public class GameController {
    @FXML private Label deckCountLabel;
    @FXML private Text Player1Money;
    @FXML private Text Player2Money;
    @FXML private StackPane arrowImageView;
    @FXML private Label counterLabel;
    @FXML private Label currentPlayerLabel;
    @FXML private StackPane ShowEnemy;
    @FXML private StackPane CurrentButton;
    @FXML private StackPane TokoButton;
    @FXML private Label counterLabel1;
    private PlayerManager manager;
    private Store store;
    private int counter = 1;
    private int currentView = 1; // if currentView == 1 (means current player view)
    private float counterTime = 0.0F;
    private static GameController instance;

    public void updateCurrentPlayerLabel() {
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        if (currentPlayer == PlayerManager.getInstance().getPlayer1()){
            currentPlayerLabel.setText("Currently Playing : Player 1");
        }
        else{
            currentPlayerLabel.setText("Currently Playing : Player 2");
        }
    }

    public int getCurrentView() {
        return this.currentView;
    }

    public void setCurrentView(int newCurrentView) {
        if (newCurrentView == 1) {
            ShowEnemy.setStyle("-fx-background-color: #312D27; -fx-background-radius: 10;");
            CurrentButton.setStyle("-fx-background-color: #99582a; -fx-background-radius: 10;");
            UIUpdateService.getInstance().updateRealGrid();
        } else if (newCurrentView == 2) {
            CurrentButton.setStyle("-fx-background-color: #312D27; -fx-background-radius: 10;");
            ShowEnemy.setStyle("-fx-background-color: #99582a; -fx-background-radius: 10;");
            UIUpdateService.getInstance().updateEnemyGrid();
        }
        this.currentView = newCurrentView;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store newStore) {
        this.store = newStore;
    }

    public void disableButtonBearAttack() {
        arrowImageView.setDisable(true);
        ShowEnemy.setDisable(true);
        TokoButton.setDisable(true);
    }

    public void enableButtonBearAtack() {
        arrowImageView.setDisable(false);
        ShowEnemy.setDisable(false);
        TokoButton.setDisable(false);
    }

    public int getCurrentTurn() {
        return this.counter;
    }

    public void setCurrentPlayerLabel(int currentTurn) {
        if (currentTurn % 2 == 1) {
            currentPlayerLabel.setText("Currently Playing : Player 1");
        } else {
            currentPlayerLabel.setText("Currently Playing : Player 2");
        }
    }

    public void setCurrentTurn(int currentTurn) {
        this.counter = currentTurn;
    }

    public GameController() {
        instance = this;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public static GameController getInstance() {
        return instance;
    }


    @FXML
    public void initialize() {
        updateCurrentPlayerLabel();
        this.store = new Store();
        this.manager = PlayerManager.getInstance();
        updateMoneyDisplay();
        counterLabel.setText(String.valueOf(counter));
        arrowImageView.setOnMouseClicked(event -> {
            try {
                nextTurn();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        GameApp.openNewWindow("None", "Draws.fxml");
        GameController.getInstance().updateDeckLabel();
    }

    public Player currentWinningPlayer() {
        if (PlayerManager.getInstance().getPlayer1().getCash() > PlayerManager.getInstance().getPlayer2().getCash()) {
            return PlayerManager.getInstance().getPlayer1();
        } else if (PlayerManager.getInstance().getPlayer1().getCash() < PlayerManager.getInstance().getPlayer2().getCash()) {
            return PlayerManager.getInstance().getPlayer2();
        }
        return null; // drawState
    }

    private void nextTurn() throws IOException {
        counter++;
        if (this.getCurrentTurn() > 20) {
            Player winningPlayer = currentWinningPlayer();
            Stage stage = new Stage();
            if (winningPlayer != null) { // tidak draw
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayerWon.fxml"));
                Parent root = fxmlLoader.load();
                PlayerWonController playerWonController = fxmlLoader.getController();
                playerWonController.setWinnerName(winningPlayer.getName());
                stage.setTitle("PlayerWon");
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ItsADraw.fxml"));
                Parent root = fxmlLoader.load();
                stage.setTitle("DrawMatch");
                stage.setScene(new Scene(root));
                stage.show();
            }

            return;
        }
        manager = PlayerManager.getInstance();
        PlayerManager.getInstance().getCurrentPlayer().nextDay();
        PlayerManager.getInstance().getEnemyPlayer().nextDay();
        manager.switchPlayer(); // Switch to the next player
        GameController.getInstance().updateDeckLabel();
        GameController.getInstance().
        counterLabel.setText(String.valueOf(counter));
        currentView = 1;
        ShowEnemy.setStyle("-fx-background-color: #312D27; -fx-background-radius: 10;");
        CurrentButton.setStyle("-fx-background-color: #99582a; -fx-background-radius: 10;");
        UIUpdateService.getInstance().updateRealGrid();
        UIUpdateService.getInstance().updateHandsGrid();
        UIUpdateService.getInstance().updateStoreHandsGrid();
        int handCardCount = PlayerManager.getInstance().getCurrentPlayer().getHands().getCardCount();
        int initialSize = 6 - handCardCount;
        final int size = Math.min(initialSize, 4);
        // if (size > 0){
        GameApp.openNewWindow("None", "Draws.fxml");
        // }
        updateMoneyDisplay();
        updateCurrentPlayerLabel();
    }

    public void updateCounterLabel1(float newValue) {
        counterTime = newValue;
        Platform.runLater(() -> counterLabel1.setText("Time :" + String.valueOf(counterTime)));
    }

    @FXML
    public void ShowEnemy(MouseEvent event){
        currentView = 2;
        CurrentButton.setStyle("-fx-background-color: #312D27; -fx-background-radius: 10;");
        ShowEnemy.setStyle("-fx-background-color: #99582a; -fx-background-radius: 10;");
        UIUpdateService.getInstance().updateEnemyGrid();
    }
    @FXML
    public void ShowCurrent(MouseEvent event){
        currentView = 1;
        ShowEnemy.setStyle("-fx-background-color: #312D27; -fx-background-radius: 10;");
        CurrentButton.setStyle("-fx-background-color: #99582a; -fx-background-radius: 10;");
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
        counterLabel.setText(String.valueOf(GameController.getInstance().getCurrentTurn()));
        Player1Money.setText("$ " + manager.getPlayer1().getCash());
        Player2Money.setText("$ " + manager.getPlayer2().getCash());
    }

    public void updateMoneyUI() {
        updateMoneyDisplay();
    }

    public void updateGameState(GameState gameState) {
        // Update UI or perform necessary actions with the loaded game state
        PlayerManager.getInstance().setPlayers(gameState.getPlayers());
        UIUpdateService.getInstance().updateRealGrid();
        UIUpdateService.getInstance().updateHandsGrid();
    }
    @FXML
    public void showErrorPopup(String errorMessage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ErrorMessage.fxml"));
            Parent root = loader.load();
            ErrorMessageController controller = loader.getController();
            controller.setErrorMessage(errorMessage);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Error");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void viewToko(MouseEvent event) {
        try {
            System.out.println("Dioteklan");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Store.fxml"));
            Parent root = fxmlLoader.load();
            StoreController storeController = fxmlLoader.getController();
            storeController.setStore(store);
            Stage stage = new Stage();
            stage.setTitle("Store");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateDeckLabel() {
        Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
        int deckCount = currentPlayer.getDeck().getCurrentDeckCardCount();
        deckCountLabel.setText(deckCount + "/" + "40");
    }

}

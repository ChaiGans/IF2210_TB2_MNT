package org.example.src;

import entity.*;
import entity.plugin.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;


public class LoadStateController {


    @FXML
    private ChoiceBox<String> formatChoiceBox;

    @FXML
    private TextField folderTextField;

    @FXML
    private Label statusLabel;

    @FXML
    private Button backButton;

    @FXML
    public Button browseButton;

    @FXML
    private Button loadButton;

    @FXML
    private Pane draggablePane;

    private double xOffset = 0;
    private double yOffset = 0;

    private File selectedDirectory;
    private String selectedFormat;

    @FXML
    private void initialize() {
        if (GameData.getInstance().getPluginManager().getAllPluginName().contains("com.plugin.TxtConfigLoader")){
            formatChoiceBox.getItems().add("TXT");
        }

        if (GameData.getInstance().getPluginManager().getAllPluginName().contains("com.plugin.JsonConfigLoader")){
            formatChoiceBox.getItems().add("JSON");
        }

        if (GameData.getInstance().getPluginManager().getAllPluginName().contains("com.plugin.XMLConfigLoader")){
            formatChoiceBox.getItems().add("XML");
        }

        browseButton.setOnAction(e -> handleBrowse());
        loadButton.setOnAction(e -> handleLoad());
        backButton.setOnAction(e -> handleBack());
//        makeDraggable();
    }

    private void makeDraggable() {
        draggablePane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        draggablePane.setOnMouseDragged(event -> {
            Stage stage = (Stage) draggablePane.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    private void handleBack() {
        backButton.getScene().getWindow().hide();
        // Implement the action to go back to the previous screen if necessary
    }

    @FXML
    private void handleBrowse() {
        System.out.println("Load dipencet");
        this.selectedFormat = formatChoiceBox.getValue();

        if (selectedFormat == null || selectedFormat.isEmpty()) {
            statusLabel.setText("Please select a format.");
            return;
        }

        // Open a directory chooser dialog to select the folder
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder to Load");

        Stage stage = (Stage) formatChoiceBox.getScene().getWindow();
        this.selectedDirectory = directoryChooser.showDialog(stage);

        if (this.selectedDirectory != null) {
            folderTextField.setText(this.selectedDirectory.getAbsolutePath());
        } else {
            folderTextField.setText("No directory selected.");
        }
    }

//    @FXML
//    public void handleLoad(String directoryPath, String jarPath, String className) {
//        try {
//            GameData game = new GameData();
//            game.loadPlugins(jarPath, className);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @FXML
    private void handleLoad() {
        if (selectedDirectory == null || selectedFormat == null || selectedFormat.isEmpty()) {
            statusLabel.setText("Please select a format and folder.");
            return;
        }

        String folder = selectedDirectory.getAbsolutePath();

        try {
            GameState gameState = null;
            switch (selectedFormat) {
                case "XML":
//                    gameState = new XMLConfigLoader().loadGameState(gameFilePath, player1FilePath, player2FilePath);
                    break;
                case "TXT":
                    GameData.getInstance().usePlugin("com.plugin.TxtConfigLoader");
                    break;
                case "JSON":
                    GameData.getInstance().usePlugin("com.plugin.JsonConfigLoader");
                    break;
                default:
                    statusLabel.setText("Invalid format selected.");
                    return;
            }

            gameState = GameData.getInstance().getPlugin().loadGameState(folder);

            if (gameState != null) {
                statusLabel.setText("State Loaded Successfully");
                GameData.getInstance().setGameState(gameState);  // Set the loaded game state into GameData
                GameData.getInstance().uploadGridData(PlayerManager.getInstance().getCurrentPlayer());
                refreshGameComponents();
            } else {
                statusLabel.setText("Failed to Load State");
            }
        } catch (IOException e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshGameComponents() {
        // Assuming GameData and GameState have methods to update the game's state and player data.
        GameData gameData = GameData.getInstance();
        GameState gameState = gameData.getGameState();

        // Update player data
        PlayerManager playerManager = PlayerManager.getInstance();
        playerManager.setPlayers(gameState.getPlayers());
//        playerManager.setCurrentTurn(gameState.getCurrentTurn());

        // Update hands and grid data for the current player
//        gameData.uploadGridData(playerManager.getCurrentPlayer());

        // Additional logic to refresh the UI or game components can be added here
//        gameData.printPlayerStateInfo(playerManager.getCurrentPlayer());

        UIUpdateService.getInstance().updateHandsGrid();
        UIUpdateService.getInstance().updateRealGrid();
        UIUpdateService.getInstance().updateDrawsGrid();
    }
}

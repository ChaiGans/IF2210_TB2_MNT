package org.example.src;

import entity.GameData;
import entity.GameState;
import entity.Player;
import entity.plugin.BasePlugin;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SaveStateController {


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
        private Button saveButton;

        @FXML
        private Pane draggablePane;

        private double xOffset = 0;
        private double yOffset = 0;

        private File selectedDirectory;
        private String selectedFormat;


        private BasePlugin configLoader;
        private GameController gameController;



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
            saveButton.setOnAction(e -> handleSave());
            backButton.setOnAction(e -> handleBack());
//            makeDraggable();
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
        private void handleBrowse() {
            System.out.println("browse dipencet");
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

            folderTextField.setText(String.valueOf(this.selectedDirectory));
        }

        private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

    @FXML
    private void handleBack() {
        backButton.getScene().getWindow().hide();
        // Implement the action to go back to the previous screen if necessary
    }

    @FXML
    private void handleSave() {
        if (selectedDirectory == null || selectedFormat == null || selectedFormat.isEmpty()) {
            statusLabel.setText("Please select a format and folder.");
            return;
        }

        String folder = selectedDirectory.getAbsolutePath();
        List<Player> tempPlayerList = new ArrayList<>();
        tempPlayerList.add(PlayerManager.getInstance().getPlayer1());
        tempPlayerList.add(PlayerManager.getInstance().getPlayer2());
        GameData.getInstance().getGameState().setPlayers(tempPlayerList);
        // set toko di gamestate
        // set current turn di gamestate
        GameData.getInstance().getGameState().setCurrentTurn(GameController.getInstance().getCurrentTurn());

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

        if (GameData.getInstance().getGameState() != null) {
            GameData.getInstance().saveGame(String.valueOf(selectedDirectory));
            statusLabel.setText("State Save Successfully");
        } else {
            statusLabel.setText("Failed to Save State");
        }
    }
}

package org.example.src;

import entity.plugin.BasePlugin;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;


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
            formatChoiceBox.getItems().addAll("XML", "TXT", "JSON");
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
        }


        @FXML
        private void handleSave() {}


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
//
//    @FXML
//    private void handleSave() {
//        String format = formatChoiceBox.getValue();
//
//        if (format == null || format.isEmpty()) {
//            statusLabel.setText("Please select a format.");
//            return;
//        }
//
//        // Open a directory chooser dialog to select the folder
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        directoryChooser.setTitle("Select Folder to Save");
//        Stage stage = (Stage) formatChoiceBox.getScene().getWindow();
//        File selectedDirectory = directoryChooser.showDialog(stage);
//
//        if (selectedDirectory == null) {
//            statusLabel.setText("No folder selected.");
//            return;
//        }
//
//        String folder = selectedDirectory.getAbsolutePath();
//
//        try {
//            saved = false;
//            switch (format) {
//                case "XML":
//                    saved = ((XMLConfigLoader) configLoader).saveGameState(gameState, folder, player1FilePath, player2FilePath);
//                    break;
//                case "TXT":
//                    saved = ((TxtConfigLoader) configLoader).saveGameState(gameState, folder, player1FilePath, player2FilePath);
//                    break;
//                case "JSON":
//                    saved = ((JsonConfigLoader) configLoader).saveGameState(gameState, folder, player1FilePath, player2FilePath);
//                    break;
//                default:
//                    statusLabel.setText("Invalid format selected.");
//                    return;
//            }
//
//            if (saved) {
//                statusLabel.setText("State Saved Successfully");
//            } else {
//                statusLabel.setText("Failed to Save State");
//            }
//        } catch (IOException e) {
//            statusLabel.setText("Error: " + e.getMessage());
//        }
//    }
//
//    public void setConfigLoader(BasePlugin configLoader) {
//        this.configLoader = configLoader;
//    }
//
//    public void setGameState(Object gameState) {
//        this.gameState = gameState;
//    }
//
//    public void setPlayer1FilePath(String player1FilePath) {
//        this.player1FilePath = player1FilePath;
//    }
//
//    public void setPlayer2FilePath(String player2FilePath) {
//        this.player2FilePath = player2FilePath;
//    }
}

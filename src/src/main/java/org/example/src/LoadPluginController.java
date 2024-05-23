package org.example.src;

import entity.GameData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class LoadPluginController {

    @FXML
    private Button backButton;

    @FXML
    private Button browseButton;

    @FXML
    private TextField folderTextField;

    @FXML
    private Button loadButton;

    @FXML
    private Label statusLabel;

    private File selectedPluginJar;

    @FXML
    private void initialize() {
        browseButton.setOnAction(e -> handleBrowse());
        loadButton.setOnAction(e -> {
            try {
                handleLoad();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        backButton.setOnAction(e -> handleBack());
    }

    @FXML
    void handleBrowse() {
        // Open a file chooser dialog to select the JAR file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Plugin to Load");

        // Add a filter for JAR files
        FileChooser.ExtensionFilter jarFilter = new FileChooser.ExtensionFilter("JAR Files", "*.jar");
        fileChooser.getExtensionFilters().add(jarFilter);

        // Get the current stage (owner window) from a control on the scene
        Stage stage = (Stage) browseButton.getScene().getWindow();
        this.selectedPluginJar = fileChooser.showOpenDialog(stage);

        if (this.selectedPluginJar != null) {
            folderTextField.setText(this.selectedPluginJar.getAbsolutePath());
        } else {
            folderTextField.setText("No plugin selected.");
        }
    }

    @FXML
    void handleBack() {
        backButton.getScene().getWindow().hide();
    }

    @FXML
    void handleLoad() throws Exception {
        if (selectedPluginJar == null) {
            statusLabel.setText("Please select a format and folder.");
            return;
        }

        String jarPath = selectedPluginJar.getAbsolutePath();
        GameData.getInstance().addNewPlugin(jarPath);
        statusLabel.setText("Load plugin success");
    }

}
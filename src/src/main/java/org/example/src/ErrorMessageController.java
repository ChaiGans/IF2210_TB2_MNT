package org.example.src;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ErrorMessageController {

    @FXML
    private Label errorMessageLabel;

    public void setErrorMessage(String message) {
        errorMessageLabel.setText(message);
    }

    public void handleGoBack(MouseEvent event){
        Stage stage = (Stage) errorMessageLabel.getScene().getWindow();
        stage.close();
    }
}

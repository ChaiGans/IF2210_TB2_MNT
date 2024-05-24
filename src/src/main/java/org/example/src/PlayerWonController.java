package org.example.src;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PlayerWonController {
    @FXML
    private Text winnerName;

    public void setWinnerName(String name) {
        winnerName.setText(name);
    }
}

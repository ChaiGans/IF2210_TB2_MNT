package org.example.src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApp extends Application {

    private static Stage primaryStage; // Primary stage

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("Game Scene");
        primaryStage.setScene(createScene("Game.fxml"));
        primaryStage.show();
    }

    public static Scene createScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(GameApp.class.getResource(fxmlFile));
        Parent root = loader.load();
        return new Scene(root);
    }

    public static void openNewWindow(String title, String fxmlFile) {
        try {
            Scene scene = createScene(fxmlFile);
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

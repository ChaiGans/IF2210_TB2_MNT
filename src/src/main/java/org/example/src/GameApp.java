package org.example.src;

import entity.MusicManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GameApp extends Application {

    private static Stage primaryStage; // Primary stage

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml")); // Ensure the path is correct
        primaryStage.setTitle("MNT Farming Game");
        primaryStage.setScene(new Scene(root, 1000, 700)); // Adjust the width and height as needed
        MusicManager.loadMusic("mainTheme", "/org/example/src/assets/main-music.mp3"); // Adjust the path based on your resources folder
        MusicManager.loadMusic("battleTheme", "/org/example/src/assets/battle.mp3");
        MusicManager.loadMusic("alarmSound", "/org/example/src/assets/alarm.mp3");
        MusicManager.loadMusic("eatSound", "/org/example/src/assets/eat.mp3");
        MusicManager.playMusic("mainTheme", true);
        primaryStage.show();
    }

    public static Scene createScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(GameApp.class.getResource(fxmlFile));
        Parent root = loader.load();
        return new Scene(root);
    }
    
    public static void openNewWindow(String title, String fxmlFile) {
        try {
            Scene scene = createScene   (fxmlFile);
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.initStyle(StageStyle.UNDECORATED); 
            newStage.setScene(scene);
            newStage.setAlwaysOnTop(true);
            newStage.setOnShown(event -> {
                double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
                double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
                newStage.setX((screenWidth - newStage.getWidth()) / 2);
                newStage.setY((screenHeight - newStage.getHeight()) / 2);
            });
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }    
}

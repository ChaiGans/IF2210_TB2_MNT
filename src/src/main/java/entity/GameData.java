package entity;

import java.io.IOException;

import entity.plugin.PluginInterface;
import entity.plugin.PluginManager;

public class GameData {
    private PluginManager pluginManager;
    private PluginInterface plugin;
    private GameState gameState;
    private static GameData instance;
    private Hands hands;
    private Grid gridData;

    private GameData() {
        pluginManager = new PluginManager();
        hands = new Hands();
        gridData = new Grid(5, 4);
    }

    public void loadPlugins(String jarPath, String className) throws Exception {
        pluginManager.loadPlugin(jarPath, className);
        plugin = pluginManager.getPlugin(className);
    }

    public void loadGame(String directoryPath) {
        try {
            if (plugin.verifyDirectory(directoryPath)) {
                gameState = plugin.loadGameState(directoryPath + "/gamestate.txt", directoryPath + "/player1.txt", directoryPath + "/player2.txt");
            } else {
                System.out.println("Required files are missing in the directory.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGame(String directoryPath) {
        try {
            plugin.saveGameState(gameState, directoryPath + "/gamestate.txt",  directoryPath + "/player1.txt",  directoryPath + "/player2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    public Hands getHands() {
        return hands;
    }

    public Grid getGridData() {
        return gridData;
    }

    public void printInformation() {
        this.gameState.toString();
    }

     public static void main(String[] args) {
        try {
            GameData game = new GameData();
            String jarPath = "path/to/plugin.jar";
            String className = "entity.plugin.TxtConfigLoader";
            game.loadPlugins(jarPath, className);

            String directoryPath = "path/to/directory";
            game.loadGame(directoryPath);
            game.printInformation();
            // Perform game operations...
            // game.saveGame(directoryPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

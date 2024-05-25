package entity;

import java.io.IOException;

import entity.plugin.PluginInterface;
import entity.plugin.PluginManager;

public class GameData {
    private final PluginManager pluginManager;
    private PluginInterface plugin;
    private GameState gameState;
    private static GameData instance;
    private int currentClickedRow;
    private int currentClickedCol;

    public GameData() {
        pluginManager = new PluginManager();
        this.usePlugin("com.plugin.TxtConfigLoader");
        this.gameState = new GameState();
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public PluginInterface getPlugin() {
        return this.plugin;
    }

    public void addNewPlugin(String jarPath) throws Exception {
        pluginManager.loadPlugin(jarPath);
    }

    public void usePlugin(String className) {
        plugin = pluginManager.getPlugin(className);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void getCoordinates(int col,int row){
        this.currentClickedCol = col;
        this.currentClickedRow = row;
    }
    public int ColClicked(){
        return this.currentClickedCol;
    }
    public int rowClciked(){
        return this.currentClickedRow;
    }

    public void saveGame(String directoryPath) {
        try {
            plugin.saveGameState(gameState, directoryPath);
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

    public GameState getGameState() {
        return gameState;
    }
}

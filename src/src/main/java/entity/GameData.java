package entity;

import java.io.IOException;

import org.example.src.PlayerManager;

import entity.plugin.PluginInterface;
import entity.plugin.PluginManager;

public class GameData {
    private final PluginManager pluginManager;
    private PluginInterface plugin;
    private GameState gameState;
    private static GameData instance;
    private Hands hands;
    private Grid gridData;
    private int currentClickedRow;
    private int currentClickedCol;

    public GameData() {
        pluginManager = new PluginManager();
        this.usePlugin("com.plugin.TxtConfigLoader");
        this.gameState = new GameState();
        hands = new Hands();
        gridData = new Grid(5, 4);
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

    public void uploadGridData(Player currPlayer) {
        if (currPlayer != null) {
            currPlayer.setHands(this.hands);  
            currPlayer.setField(this.gridData);
        }
    }

    public void printPlayerStateInfo(Player player) {
        if (player == null) {
            System.out.println("No player data available.");
            return;
        }
    
        Hands playerHands = player.getHands();
        if (playerHands != null) {
            System.out.println("Player Hands:");
            int handIndex = 0;
            for (Card card : playerHands.getCards()) {
                if (card != null) {
                    System.out.println("Hand " + (handIndex++) + ": " + card.getName() + " (Owner: " + card.getOwnerName() + ")");
                } else {
                    System.out.println("Hand " + (handIndex++) + ": Empty");
                }
            }
        } else {
            System.out.println("Player hands are empty or not initialized.");
        }
    
        // Print player's grid information
        Grid playerGrid = player.getField();
        if (playerGrid != null) {
            System.out.println("Player Grid:");
            for (int i = 0; i < playerGrid.getHeight(); i++) {
                for (int j = 0; j < playerGrid.getWidth(); j++) {
                    Card card = playerGrid.getCard(j, i);
                    if (card != null) {
                        System.out.println("Grid Position [" + i + "," + j + "]: " + card.getName() + " (Owner: " + card.getOwnerName() + ")");
                    } else {
                        System.out.println("Grid Position [" + i + "," + j + "]: Empty");
                    }
                }
            }
        } else {
            System.out.println("Player grid is empty or not initialized.");
        }
    }


    public void loadGame(String directoryPath) {
        try {
            if (plugin.verifyDirectory(directoryPath)) {
                this.gameState = plugin.loadGameState(directoryPath);
            } else {
                System.out.println("Required files are missing in the directory.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void switchSavedData(){
        if (PlayerManager.getInstance().getCurrentPlayer() == PlayerManager.getInstance().getPlayer1() ){
            System.out.println("Mengambil dari player 1");
            hands = PlayerManager.getInstance().getPlayer1().getHands();
        }else{
            hands = PlayerManager.getInstance().getPlayer2().getHands();
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

    public GameState getGameState() {
        return gameState;
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
            String jarPath = "Plugin-XML-Loader/target/Plugin-XML-Loader-1.0-SNAPSHOT.jar";
//            String className = "com.plugin.JsonConfigLoader";
            game.addNewPlugin(jarPath);
            System.out.println(game.getPluginManager().getAllPluginName());
            String directoryPath = "src/src/main/java/entity/plugin/statefiles";
//            game.usePlugin("com.plugin.TxtConfigLoader");
//            game.loadGame(directoryPath);
            // Perform game operations...
//            System.out.println("loaded sucess");
//            game.saveGame(directoryPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

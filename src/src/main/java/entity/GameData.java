package entity;

import java.io.IOException;

import org.example.src.PlayerManager;

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
            String className = "com.plugin.TxtConfigLoader";
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

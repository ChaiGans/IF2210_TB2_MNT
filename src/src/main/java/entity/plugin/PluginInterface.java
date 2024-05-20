package entity.plugin;

import java.io.IOException;
import entity.*;

public interface PluginInterface {
   String getName();
   GameState loadGameState(String gameFilePath, String player1FilePath, String player2FilePath) throws IOException;
   void saveGameState(GameState gameState, String gameFilePath, String player1FilePath, String player2FilePath) throws IOException;
   Player loadPlayer(String filePath) throws IOException;
   void savePlayer(Player player, String filePath) throws IOException;
   boolean verifyDirectory(String directoryPath) throws IOException;
}
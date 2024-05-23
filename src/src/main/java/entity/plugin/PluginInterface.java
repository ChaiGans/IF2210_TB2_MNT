package entity.plugin;

import java.io.IOException;
import entity.*;

public interface PluginInterface {
   String getName();
   GameState loadGameState(String directoryPath) throws IOException ;
   void saveGameState(GameState gameState, String directory) throws IOException;
   Player loadPlayer(String filePath) throws IOException;
   void savePlayer(Player player, String filePath) throws IOException;
   boolean verifyDirectory(String directoryPath) throws IOException;
}
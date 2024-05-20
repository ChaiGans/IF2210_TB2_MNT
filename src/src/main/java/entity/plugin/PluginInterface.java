package entity.plugin;

import java.util.*;
import java.io.IOException;
import entity.*;

public interface PluginInterface {
   GameState loadGameState(String filePath) throws IOException;
   void saveGameState(GameState gameState, String filePath) throws IOException;
   List<Player> loadPlayers(String filePath) throws IOException;
   void savePlayers(List<Player> players, String filePath) throws IOException;
   boolean verifyDirectory(String directoryPath) throws IOException;
}
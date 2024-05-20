package entity;

import java.util.*;
 
public class GameState {
    private List<Player> players;
    private int currentTurn;
    private Store store;
    private GameSettings settings; // Another class representing configurable settings.

    public GameState() {
        this.currentTurn = 0;
        this.store = new Store();
        players = new ArrayList<>();
        settings = new GameSettings();
    }

    public GameState(int currentTurn, Store store, List<Player> players, GameSettings settings) {
        this.currentTurn = currentTurn;
        this.store = store;
        this.players = players;
        this.settings = settings;
    }

    // Getters and setters for each field
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public GameSettings getSettings() {
        return settings;
    }

    public void setSettings(GameSettings settings) {
        this.settings = settings;
    }
    
    public Store getStore() {
        return this.store;
    }

    public int getCurrentTurn() {
        return this.currentTurn;
    }
}

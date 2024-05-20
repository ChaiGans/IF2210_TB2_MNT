package entity;

import java.util.*;
 
public class GameState {
    private List<Player> players;
    private int currentTurn;
    private Store store;

    public GameState() {
        this.currentTurn = 0;
        this.store = new Store();
        players = new ArrayList<>();
    }

    public GameState(int currentTurn, Store store, List<Player> players) {
        this.currentTurn = currentTurn;
        this.store = store;
        this.players = players;
    }

    // Getters and setters for each field
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    
    public Store getStore() {
        return this.store;
    }

    public int getCurrentTurn() {
        return this.currentTurn;
    }
}

package org.example.src;

import entity.Player;

public class PlayerManager {
    private static final PlayerManager instance = new PlayerManager();
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean isFirstTurn = true;

    private PlayerManager() {
        player1 = new Player();
        player2 = new Player();
        currentPlayer = player1;  // Initially set to player1
    }

    public static PlayerManager getInstance() {
        return instance;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        if (isFirstTurn) {
            currentPlayer = player1;
            isFirstTurn = false;
        } else {
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
            isFirstTurn = true;
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}

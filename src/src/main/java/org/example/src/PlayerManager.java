package org.example.src;

import entity.GameData;
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
        currentPlayer = player1;
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
            System.out.println("Pemain saat ini : player 1");
            isFirstTurn = false;
            GameData.getInstance().switchSavedData();
        } else {
            System.out.println("player 2");
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
            isFirstTurn = true;
            GameData.getInstance().switchSavedData();
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}

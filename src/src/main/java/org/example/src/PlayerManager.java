package org.example.src;

import entity.Card;
import entity.GameData;
import entity.Grid;
import entity.Hands;
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

    public Player getEnemyPlayer() {
        return (currentPlayer == player1) ? player2 : player1;
    }

    public void switchPlayer() {
        if (isFirstTurn) {
            currentPlayer = player1;
            System.out.println("Pemain saat ini : player 1");
            isFirstTurn = false;
            playerInformation();
        } else {
            System.out.println("Pemain saat ini : player 2");
            currentPlayer = player2;
            isFirstTurn = true;
            playerInformation();
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void playerInformation() {
        printPlayerInformation(player1, "Player 1");
        printPlayerInformation(player2, "Player 2");
    }

    private void printPlayerInformation(Player player, String playerName) {
        System.out.println(playerName + " Information:");
        printHands(player);
        printGrid(player);
    }

    private void printHands(Player player) {
        Hands hands = player.getHands();
        System.out.println("Hands:");
        for (int i = 0; i < 6; i++) {
            Card card = hands.getCard(i);
            if (card == null) {
                System.out.println("  - " + "Null");
            }
            if (card != null) {
                System.out.println("  - " + card.getName());
            }
        }
    }

    private void printGrid(Player player) {
        Grid grid = player.getField();
        System.out.println("Grid:");
        for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                Card card = grid.getCard(col, row);
                String cellContent = (card != null) ? card.getName() : "Empty";
                System.out.print(cellContent + "\t");
            }
            System.out.println();
        }
    }
}

package com.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Scanner;

import entity.plugin.BasePlugin;
import entity.plugin.PluginInterface;
import entity.*;

public class TxtConfigLoader extends BasePlugin implements PluginInterface {
    public TxtConfigLoader() {
        super();
    }

    @Override
    public String getName() {
        return "TXT";
    }

    @Override
    public boolean verifyDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new FileNotFoundException("Directory not found: " + directoryPath);
        }

        File gameStateFile = new File(directoryPath, "gamestate.txt");
        File player1File = new File(directoryPath, "player1.txt");
        File player2File = new File(directoryPath, "player2.txt");

        return gameStateFile.exists() && player1File.exists() && player2File.exists();
    }

    @Override
    public GameState loadGameState(String gameFilePath, String player1FilePath, String player2FilePath) throws IOException {
        Store tempStore = new Store();
        Player unknownPlayer = new Player(); // Assuming you have a default constructor for Player

        File myObj = new File(gameFilePath);
        if (!myObj.exists()) {
            throw new FileNotFoundException();
        }
        Scanner myReader = new Scanner(myObj);
        int currentTurn = Integer.parseInt(myReader.nextLine());
        int itemNumber = Integer.parseInt(myReader.nextLine());

        for (int i = 0; i < itemNumber; i++) {
            String itemInformation = myReader.nextLine();
            String[] parts = itemInformation.split(" ");

            String itemName = this.keyToValueMap.get(parts[0]);
            int itemQuantity = Integer.parseInt(parts[1]);

            ProductCard itemCard = (ProductCard) CardFactory.createCard(itemName, unknownPlayer);
            tempStore.addItem(itemCard, itemQuantity);
        }

        List<Player> listPlayers = new ArrayList<>();
        Player player1 = this.loadPlayer(player1FilePath);
        Player player2 = this.loadPlayer(player2FilePath);
        listPlayers.add(player1);
        listPlayers.add(player2);

        myReader.close();
        return new GameState(currentTurn, tempStore, listPlayers);
    }

    @Override
    public void saveGameState(GameState gameState, String gameFilePath, String player1FilePath, String player2FilePath) throws IOException {
        File file = new File(gameFilePath);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(gameState.getCurrentTurn());
            Map<ProductCard, Integer> itemList = gameState.getStore().getStoreInformation();
            writer.println(itemList.size());
            for (Map.Entry<ProductCard, Integer> entry : itemList.entrySet()) {
                writer.println(this.keyToValueMap.get(entry.getKey().getName()) + " " + entry.getValue());
            }
        }

        this.savePlayer(gameState.getPlayers().get(0), player1FilePath);
        this.savePlayer(gameState.getPlayers().get(1), player2FilePath);
    }

    @Override
    public Player loadPlayer(String filePath) throws IOException {
        Player player = new Player();
        File myObj = new File(filePath);
        if (!myObj.exists()) {
            throw new FileNotFoundException();
        }
        Scanner myReader = new Scanner(myObj);

        int gulden = Integer.parseInt(myReader.nextLine());
        player.setCash(gulden);
        int deckSize = Integer.parseInt(myReader.nextLine());
        player.reshuffleDeck(deckSize);
        int activeHandCard = Integer.parseInt(myReader.nextLine());

        for (int i = 0; i < activeHandCard; i++) {
            String[] parts = myReader.nextLine().split(" ");
            String location = parts[0];
            String cardName = parts[1];
            Card card = CardFactory.createCard(cardName, player);
            player.getHands().addCardByLocation(card, location);
        }

        int farmCardCount = Integer.parseInt(myReader.nextLine());

        for (int i = 0; i < farmCardCount; i++) {
                String[] parts = myReader.nextLine().split(" ");
                String location = parts[0];
                String cardName = parts[1];
                int weightOrAge = Integer.parseInt(parts[2]);
                int itemCount = Integer.parseInt(parts[3]);
                Card card = CardFactory.createCard(cardName, player);

                if (card instanceof AnimalCard) {
                    AnimalCard animalCard = (AnimalCard) card;
                    animalCard.setWeight(weightOrAge); // Assuming setCurrentWeight method exists
                } else if (card instanceof PlantCard) {
                    PlantCard plantCard = (PlantCard) card;
                    plantCard.setAge(weightOrAge); // Assuming setAge method exists
                }

                for (int j = 0; j < itemCount; j++) {
                    String itemName = parts[4 + j];
                    card.addEffect(itemName);
                }
                player.addCardToField(location, card);
        }
    

        myReader.close();
        return player;
    }

    @Override
    public void savePlayer(Player player, String filePath) throws IOException {
        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(player.getCash());
            Deck deck = player.getDeck();
            writer.println(deck.getSize());
            Hands activeHands = player.getHands();
            writer.println(activeHands.getCardCount());

            for (Card card : activeHands.getCards()) {
                writer.println(activeHands.findCardLocation(card) + " " + card.getName());
            }

            Grid field = player.getField();
            writer.println(field.countCardInField());

            for (int i = 0; i < field.getWidth(); i++) {
                for (int j = 0; j < field.getHeight(); j++) {
                    Card currentCard = field.getCard(i, j);
                    if (currentCard != null) {
                        if (currentCard instanceof AnimalCard) {
                            writer.print(Grid.convertIndicesToLocation(i, j) + " " + currentCard.getName() + " " + ((AnimalCard) currentCard).getCurrentWeight() + " " + currentCard.getActiveEffect().size());
                        }
                    }
                    
                    for (int k = 0; k < currentCard.getActiveEffect().size(); k++) {
                        writer.print(" " + currentCard.getActiveEffect().get(k));
                    }

                    writer.println();
                }
            }
        }
    }

    // public static void main(String[] args) {
        // try {
        //     TxtConfigLoader loader = new TxtConfigLoader();
        //     String directoryPath = "test_directory";

        //     // Verify the directory contains the necessary files
        //     if (!loader.verifyDirectory(directoryPath)) {
        //         System.out.println("Required files are missing in the directory.");
        //         return;
        //     }

        //     // Create sample game state
        //     Store store = new Store();
        //     Player player1 = new Player();
        //     Player player2 = new Player();

        //     ProductCard product1 = (ProductCard) CardFactory.createCard("Product1", player1);
        //     ProductCard product2 = (ProductCard) CardFactory.createCard("Product2", player2);
        //     store.addItem(product1, 5);
        //     store.addItem(product2, 10);

        //     GameState gameState = new GameState(1, store, null, null);

        //     // Save game state to file
        //     loader.saveGameState(gameState, directoryPath + "/gamestate.txt");

        //     // Save players to file
        //     List<Player> players = new ArrayList<>();
        //     players.add(player1);
        //     players.add(player2);
        //     loader.savePlayers(players, directoryPath + "/player1.txt");

        //     // Load game state from file
        //     GameState loadedGameState = loader.loadGameState(directoryPath + "/gamestate.txt");

        //     // Load players from file
        //     List<Player> loadedPlayers = loader.loadPlayers(directoryPath + "/player1.txt");

        //     // Verify the loaded game state
        //     System.out.println("Original GameState: " + gameState);
        //     System.out.println("Loaded GameState: " + loadedGameState);

        //     // Verify the loaded players
        //     System.out.println("Original Players: " + players);
        //     System.out.println("Loaded Players: " + loadedPlayers);

        // } catch (IOException e) {
        //     e.printStackTrace();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    // }
}

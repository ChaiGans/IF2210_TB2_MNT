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
        return "com.plugin.TxtConfigLoader";
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

            ProductCard itemCard = (ProductCard) CardFactory.createCard(itemName, new Player());
            tempStore.addItem(itemCard, itemQuantity);
        }

        tempStore.printStoreInformation();

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
            String cardName = this.keyToValueMap.get(parts[1]);
            Card card = CardFactory.createCard(cardName, player);
            player.getHands().addCardByLocation(card, parts[0]);
        }

        int farmCardCount = Integer.parseInt(myReader.nextLine());

        for (int i = 0; i < farmCardCount; i++) {
            String[] parts = myReader.nextLine().split(" ");
            String location = parts[0];
            String cardName = this.keyToValueMap.get(parts[1]);
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
                card.addEffect(this.keyToValueMap.get(itemName));
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
            writer.println(deck.getCurrentDeckCardCount());
            Hands activeHands = player.getHands();
            writer.println(activeHands.getCardCount());

            for (Card card : activeHands.getCards()) {
                if (card != null) {
                    writer.println(activeHands.findCardLocation(card) + " " + this.keyToValueMap.get(card.getName()));
                }            
            }

            Grid field = player.getField();
            writer.println(field.countCardInField());

            for (int i = 0; i < field.getWidth(); i++) {
                for (int j = 0; j < field.getHeight(); j++) {
                    Card currentCard = field.getCard(i, j);
                    if (currentCard != null) {
                        if (currentCard instanceof AnimalCard) {
                            writer.print(Grid.convertIndicesToLocation(j, i) + " " + this.keyToValueMap.get(currentCard.getName()) + " " + ((AnimalCard) currentCard).getCurrentWeight() + " " + currentCard.getActiveEffect().size());
                        } else if (currentCard instanceof PlantCard) {
                            writer.print(Grid.convertIndicesToLocation(j, i) + " " + this.keyToValueMap.get(currentCard.getName()) + " " + ((PlantCard) currentCard).getCurrentAge() + " " + currentCard.getActiveEffect().size());
                        } else {
                            writer.print(Grid.convertIndicesToLocation(j, i) + " " + this.keyToValueMap.get(currentCard.getName()) + " " + 0 + " " + currentCard.getActiveEffect().size());
                        }

                        for (int k = 0; k < currentCard.getActiveEffect().size(); k++) {
                            writer.print(" " + this.keyToValueMap.get(currentCard.getActiveEffect().get(k)));
                        }

                        writer.println();
                    }
                }
            }
        }
    }
}

package entity.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Scanner;

import entity.*;

public class TxtConfigLoader extends BasePlugin implements PluginInterface {
    public TxtConfigLoader() {
        super();
    }

    @Override
    public GameState loadGameState(String filePath) throws IOException {
        Store tempStore = new Store();
        Player unknownPlayer = new Player(); // Assuming you have a default constructor for Player

        File myObj = new File(filePath);
        if (!myObj.exists()) {
            throw new FileNotFoundException();
        }
        Scanner myReader = new Scanner(myObj);
        int currentTurn = Integer.parseInt(myReader.nextLine());
        int itemNumber = Integer.parseInt(myReader.nextLine());

        for (int i = 0; i < itemNumber; i++) {
            String itemInformation = myReader.nextLine();
            String[] parts = itemInformation.split(" ");

            String itemName = parts[0];
            int itemQuantity = Integer.parseInt(parts[1]);

            ProductCard itemCard = (ProductCard) CardFactory.createCard(itemName, unknownPlayer);
            tempStore.addItem(itemCard, itemQuantity);
        }

        myReader.close();
        return new GameState(currentTurn, tempStore, null, null);
    }

    @Override
    public void saveGameState(GameState gameState, String filePath) throws IOException {
        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(gameState.getCurrentTurn());
            Map<ProductCard, Integer> itemList = gameState.getStore().getStoreInformation();
            writer.println(itemList.size());
            for (Map.Entry<ProductCard, Integer> entry : itemList.entrySet()) {
                writer.println(entry.getKey().getName() + " " + entry.getValue());
            }
        }
    }

    @Override
    public List<Player> loadPlayers(String filePath) throws IOException {
        List<Player> players = new ArrayList<>();
        File myObj = new File(filePath);
        if (!myObj.exists()) {
            throw new FileNotFoundException();
        }
        Scanner myReader = new Scanner(myObj);

        while (myReader.hasNextLine()) {
            int gulden = Integer.parseInt(myReader.nextLine());
            Player player = new Player();
            player.setCash(gulden);
            int deckSize = Integer.parseInt(myReader.nextLine());
            int activeDeckSize = Integer.parseInt(myReader.nextLine());

            for (int i = 0; i < activeDeckSize; i++) {
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
                    ProductCard itemCard = (ProductCard) CardFactory.createCard(itemName, player);
                    card.addItem(itemCard);
                }
                player.addCardToFarm(card, location);
            }
            players.add(player);
        }

        myReader.close();
        return players;
    }

    @Override
    public void savePlayers(List<Player> players, String filePath) throws IOException {
        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Player player : players) {
                writer.println(player.getCash());
                List<Card> deck = player.getDeck();
                writer.println(deck.size());
                Deck activeDeck = player.getDeck();
                writer.println(activeDeck.size());

                for (Card card : activeDeck.getCards()) {
                    writer.println(card.getLocation() + " " + card.getName());
                }

                List<Card> farm = player.getFarm();
                writer.println(farm.size());

                for (Card card : farm) {
                    writer.print(card.getLocation() + " " + card.getName() + " " + card.getWeightOrAge() + " " + card.getItems().size());
                    for (ProductCard item : card.getItems()) {
                        writer.print(" " + item.getName());
                    }
                    writer.println();
                }
            }
        }
    }
}

//package entity.plugin;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.List;
//import java.util.Scanner;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//
//
//import entity.*;
//
//public class JsonConfigLoader extends BasePlugin implements PluginInterface {
//    public JsonConfigLoader() {
//        super();
//    }
//
//    @Override
//    public String getName() {
//        return "com.plugin.JsonConfigLoader";
//    }
//
//    @Override
//    public boolean verifyDirectory(String directoryPath) throws IOException {
//        File directory = new File(directoryPath);
//        if (!directory.exists() || !directory.isDirectory()) {
//            throw new FileNotFoundException("Directory not found: " + directoryPath);
//        }
//
//        File gameStateFile = new File(directoryPath, "gamestate.json");
//        File player1File = new File(directoryPath, "player1.json");
//        File player2File = new File(directoryPath, "player2.json");
//
//        return gameStateFile.exists() && player1File.exists() && player2File.exists();
//    }
//
//    @Override
//    public GameState loadGameState(String gameFilePath, String player1FilePath, String player2FilePath) throws IOException {
//        Store tempStore = new Store();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(new File(gameFilePath));
//        if (jsonNode == null) {
//            throw new FileNotFoundException();
//        }
//
//        JsonNode array = jsonNode.get("shop_item");
//        for (int i = 0; i < array.size(); i++) {
//            JsonNode parts = array.get(i);
//            String itemName = parts.get(0).textValue();
//            String parsedItemName = this.keyToValueMap.get(itemName);
//            Integer itemQuantity = parts.get(1).intValue();
//            tempStore.addItem((ProductCard) CardFactory.createCard(parsedItemName, new Player()), itemQuantity);
//        }
//
//        List<Player> listPlayers = new ArrayList<>();
//        Player player1 = this.loadPlayer(player1FilePath);
//        Player player2 = this.loadPlayer(player2FilePath);
//        listPlayers.add(player1);
//        listPlayers.add(player2);
//
//        return new GameState(jsonNode.get("current_turn").intValue(), tempStore, listPlayers);
//    }
//
//    @Override
//    public void saveGameState(GameState gameState, String gameFilePath, String player1FilePath, String player2FilePath) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode jsonNode = objectMapper.createObjectNode();
//        jsonNode.put("current_turn", gameState.getCurrentTurn());
//
//        // Create an ArrayNode for shop items
//        ArrayNode shopItemsArray = objectMapper.createArrayNode();
//        Map<ProductCard, Integer> itemList = gameState.getStore().getStoreInformation();
//
//        for (Map.Entry<ProductCard, Integer> entry : itemList.entrySet()) {
//            ArrayNode itemArray = objectMapper.createArrayNode();
//            itemArray.add(this.keyToValueMap.get(entry.getKey().getName()));
//            itemArray.add(entry.getValue());
//            shopItemsArray.add(itemArray);
//        }
//
//        // Add the shop items array to the main JSON node
//        jsonNode.set("shop_item", shopItemsArray);
//
//        // Serialize and write the main JSON node to the file
//        objectMapper.writeValue(new File(gameFilePath), jsonNode);
//
//        // Save player states
//        this.savePlayer(gameState.getPlayers().get(0), player1FilePath);
//        this.savePlayer(gameState.getPlayers().get(1), player2FilePath);
//    }
//
//    @Override
//    public Player loadPlayer(String filePath) throws IOException {
//        Player player = new Player();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(new File(filePath));
//        if (jsonNode == null) {
//            throw new FileNotFoundException();
//        }
//
//        int gulden = jsonNode.get("gulden").intValue();
//        player.setCash(gulden);
//        int deckSize = jsonNode.get("active_deck_count").intValue();
//        player.reshuffleDeck(deckSize);
//
//        JsonNode array = jsonNode.get("active_hand");
//        for (int i = 0; i < array.size(); i++) {
//            JsonNode parts = array.get(i);
//            String cardName = this.keyToValueMap.get(parts.get(1).textValue());
//            Card card = CardFactory.createCard(cardName, player);
//            player.getHands().addCardByLocation(card, parts.get(0).textValue());
//        }
//
//        JsonNode farmCard = jsonNode.get("in_field_card");
//        for (int i = 0; i < farmCard.size(); i++) {
//            JsonNode parts = farmCard.get(i);
//            String location = parts.get(0).textValue();
//            String cardName = this.keyToValueMap.get(parts.get(1).textValue());
//            int weightOrAge = parts.get(2).intValue();
//            Card card = CardFactory.createCard(cardName, player);
//
//            if (card instanceof AnimalCard) {
//                AnimalCard animalCard = (AnimalCard) card;
//                animalCard.setWeight(weightOrAge); // Assuming setCurrentWeight method exists
//            } else if (card instanceof PlantCard) {
//                PlantCard plantCard = (PlantCard) card;
//                plantCard.setAge(weightOrAge); // Assuming setAge method exists
//            }
//
//            JsonNode skills = objectMapper.readTree(parts.get(3).traverse());
//            for (int j = 0; j < skills.get("skills").size(); j++) {
//                String itemName = skills.get("skills").get(j).textValue();
//                card.addEffect(this.keyToValueMap.get(itemName));
//            }
//            player.addCardToField(location, card);
//        }
//
//        return player;
//    }
//
//    @Override
//    public void savePlayer(Player player, String filePath) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode jsonNode = objectMapper.createObjectNode();
//        jsonNode.put("gulden", player.getCash());
//        jsonNode.put("active_deck_count", player.getDeck().getCurrentDeckCardCount());
//
//        ArrayNode activeHandsArray = objectMapper.createArrayNode();
//        for (Card card : player.getHands().getCards()) {
//            if (card != null) {
//                ArrayNode activeArray = objectMapper.createArrayNode();
//                activeArray.add(player.getHands().findCardLocation(card));
//                activeArray.add(this.keyToValueMap.get(card.getName()));
//                activeHandsArray.add(activeArray);
//            }
//        }
//        jsonNode.put("active_hand", activeHandsArray);
//
//        Grid field = player.getField();
//        ArrayNode globalFieldCard = objectMapper.createArrayNode();
//
//        for (int i = 0; i < field.getWidth(); i++) {
//            for (int j = 0; j < field.getHeight(); j++) {
//                ArrayNode inFieldCard = objectMapper.createArrayNode();
//                Card currentCard = field.getCard(i, j);
//                if (currentCard != null) {
//                    inFieldCard.add(Grid.convertIndicesToLocation(j, i));
//                    inFieldCard.add(this.keyToValueMap.get(currentCard.getName()));
//                    if (currentCard instanceof AnimalCard) {
//                        inFieldCard.add(((AnimalCard) currentCard).getCurrentWeight());
//                    } else if (currentCard instanceof PlantCard) {
//                        inFieldCard.add(((PlantCard) currentCard).getCurrentAge());
//                    } else {
//                        inFieldCard.add(0);
//                    }
//
//                    ObjectNode skillObject = objectMapper.createObjectNode();
//                    ArrayNode skills = objectMapper.createArrayNode();
//                    for (int k = 0; k < currentCard.getActiveEffect().size(); k++) {
//                        skills.add(this.keyToValueMap.get(currentCard.getActiveEffect().get(k)));
//                    }
//                    skillObject.put("skills", skills);
//                    inFieldCard.add(skillObject);
//                    globalFieldCard.add(inFieldCard);
//                }
//            }
//        }
//        jsonNode.put("in_field_card", globalFieldCard);
//        objectMapper.writeValue(new File(filePath), jsonNode);
//    }
//
//    public static void main(String[] args) {
//        try {
//            JsonConfigLoader loader = new JsonConfigLoader();
//            String directoryPath = "src/src/main/java/entity/plugin/statefiles";
//
//            // Verify the directory contains the necessary files
//            if (!loader.verifyDirectory(directoryPath)) {
//                System.out.println("Required files are missing in the directory.");
//                return;
//            }
//
//            // Create sample game state
//            // Store store = new Store();
//
//            // Player player2 = new Player();
//
//            GameState game = loader.loadGameState("src/src/main/java/entity/plugin/statefiles/gamestate.json", "src/src/main/java/entity/plugin/statefiles/player1.json", "src/src/main/java/entity/plugin/statefiles/player2.json");
//            loader.saveGameState(game, "src/src/main/java/entity/plugin/statefiles/gamestate.txt", "src/src/main/java/entity/plugin/statefiles/player1.txt", "src/src/main/java/entity/plugin/statefiles/player2.txt");
//            // System.out.println(game.toString());
//            // game.getStore().printStoreInformation();
//            // store.addItem(product1, 5);
//            // store.addItem(product2, 10);
//
//            // GameState gameState = new GameState(1, store, null, null);
//
//            // Save game state to file
//            // loader.saveGameState(gameState, directoryPath + "/gamestate.txt");
//
//            // Save players to file
//            // List<Player> players = new ArrayList<>();
//            // players.add(player1);
//            // players.add(player2);
//            // Player player1 = loader.loadPlayer(directoryPath + "/player1.txt");
//            // Player player2 = loader.loadPlayer(directoryPath + "/player2.txt");
//
//
//            // Load game state from file
//            // GameState loadedGameState = loader.loadGameState(directoryPath + "/gamestate.txt");
//
//            // // Load players from file
//            // List<Player> loadedPlayers = loader.loadPlayers(directoryPath + "/player1.txt");
//
//            // // Verify the loaded game state
//            // System.out.println("Original GameState: " + gameState);
//            // System.out.println("Loaded GameState: " + loadedGameState);
//
//            // // Verify the loaded players
//            // System.out.println("Original Players: " + players);
//            // System.out.println("Loaded Players: " + loadedPlayers);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

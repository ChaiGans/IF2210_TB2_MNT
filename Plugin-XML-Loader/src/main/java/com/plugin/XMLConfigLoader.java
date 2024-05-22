package com.plugin;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import entity.*;
import entity.plugin.*;
import org.xml.sax.SAXException;

public class XMLConfigLoader extends BasePlugin implements PluginInterface {

    @Override
    public String getName() {
        return "XML";
    }

    @Override
    public boolean verifyDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IOException("Directory not found: " + directoryPath);
        }

        File gameStateFile = new File(directoryPath, "gamestate.xml");
        File player1File = new File(directoryPath, "player1.xml");
        File player2File = new File(directoryPath, "player2.xml");

        return gameStateFile.exists() && player1File.exists() && player2File.exists();
    }

    @Override
    public GameState loadGameState(String gameFilePath, String player1FilePath, String player2FilePath) throws IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document gameStateDoc = builder.parse(new File(gameFilePath));
            int currentTurn = Integer.parseInt(gameStateDoc.getElementsByTagName("currentTurn").item(0).getTextContent());
            NodeList items = gameStateDoc.getElementsByTagName("item");

            Store tempStore = new Store();
            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                String itemName = item.getElementsByTagName("name").item(0).getTextContent();
                int quantity = Integer.parseInt(item.getElementsByTagName("quantity").item(0).getTextContent());

                ProductCard itemCard = (ProductCard) CardFactory.createCard(keyToValueMap.get(itemName), new Player());
                tempStore.addItem(itemCard, quantity);
            }

            List<Player> listPlayers = new ArrayList<>();
            listPlayers.add(loadPlayer(player1FilePath));
            listPlayers.add(loadPlayer(player2FilePath));

            return new GameState(currentTurn, tempStore, listPlayers);
        } catch (ParserConfigurationException e) {
            throw new IOException("Parser configuration error", e);
        } catch (SAXException e) {
            throw new IOException("Error parsing XML file", e);
        } catch (NumberFormatException e) {
            throw new IOException("Number format exception in XML file", e);
        }
    }


    @Override
    public void saveGameState(GameState gameState, String gameFilePath, String player1FilePath, String player2FilePath) throws IOException {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();
            Element root = document.createElement("gameState");
            document.appendChild(root);

            Element currentTurn = document.createElement("currentTurn");
            currentTurn.appendChild(document.createTextNode(String.valueOf(gameState.getCurrentTurn())));
            root.appendChild(currentTurn);

            Element store = document.createElement("store");
            Map<ProductCard, Integer> itemList = gameState.getStore().getStoreInformation();
            for (Map.Entry<ProductCard, Integer> entry : itemList.entrySet()) {
                Element item = document.createElement("item");
                Element name = document.createElement("name");
                name.appendChild(document.createTextNode(keyToValueMap.get(entry.getKey().getName())));
                item.appendChild(name);
                Element quantity = document.createElement("quantity");
                quantity.appendChild(document.createTextNode(String.valueOf(entry.getValue())));
                item.appendChild(quantity);
                store.appendChild(item);
            }
            root.appendChild(store);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(gameFilePath));
            transformer.transform(domSource, streamResult);

            savePlayer(gameState.getPlayers().get(0), player1FilePath);
            savePlayer(gameState.getPlayers().get(1), player2FilePath);
        } catch (Exception e) {
            throw new IOException("Error writing XML file", e);
        }
    }

    @Override
    public Player loadPlayer(String filePath) throws IOException {
        try {
            Document playerDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filePath));
            Player player = new Player();
            player.setCash(Integer.parseInt(playerDoc.getElementsByTagName("cash").item(0).getTextContent()));

            NodeList handCards = playerDoc.getElementsByTagName("handCard");
            for (int i = 0; i < handCards.getLength(); i++) {
                Element cardElement = (Element) handCards.item(i);
                String location = cardElement.getAttribute("location");
                String cardName = keyToValueMap.get(cardElement.getTextContent());
                Card card = CardFactory.createCard(cardName, player);
                player.getHands().addCardByLocation(card, location);
            }

            NodeList farmCards = playerDoc.getElementsByTagName("farmCard");
            for (int i = 0; i < farmCards.getLength(); i++) {
                Element cardElement = (Element) farmCards.item(i);
                String location = cardElement.getAttribute("location");
                String cardName = keyToValueMap.get(cardElement.getElementsByTagName("name").item(0).getTextContent());
                int weightOrAge = Integer.parseInt(cardElement.getElementsByTagName("weightOrAge").item(0).getTextContent());
                NodeList effects = cardElement.getElementsByTagName("effect");

                Card card = CardFactory.createCard(cardName, player);
                if (card instanceof AnimalCard) {
                    ((AnimalCard) card).setWeight(weightOrAge);
                } else if (card instanceof PlantCard) {
                    ((PlantCard) card).setAge(weightOrAge);
                }

                for (int j = 0; j < effects.getLength(); j++) {
                    String effectName = keyToValueMap.get(effects.item(j).getTextContent());
                    card.addEffect(effectName);
                }
                player.addCardToField(location, card);
            }

            return player;
        } catch (Exception e) {
            throw new IOException("Error parsing XML file", e);
        }
    }

    @Override
    public void savePlayer(Player player, String filePath) throws IOException {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("player");
            document.appendChild(root);

            Element cash = document.createElement("cash");
            cash.appendChild(document.createTextNode(String.valueOf(player.getCash())));
            root.appendChild(cash);

            Hands hands = player.getHands();
            for (Card card : hands.getCards()) {
                if (card != null && hands.findCardLocation(card) != null) {
                    Element handCard = document.createElement("handCard");
                    handCard.setAttribute("location", hands.findCardLocation(card));
                    handCard.appendChild(document.createTextNode(keyToValueMap.get(card.getName())));
                    root.appendChild(handCard);
                }
            }

            Grid field = player.getField();
            for (int i = 0; i < field.getWidth(); i++) {
                for (int j = 0; j < field.getHeight(); j++) {
                    Card card = field.getCard(i, j);
                    if (card != null) {
                        Element farmCard = document.createElement("farmCard");
                        farmCard.setAttribute("location", Grid.convertIndicesToLocation(j, i));
                        Element name = document.createElement("name");
                        name.appendChild(document.createTextNode(keyToValueMap.get(card.getName())));
                        farmCard.appendChild(name);
                        Element weightOrAge = document.createElement("weightOrAge");
                        weightOrAge.appendChild(document.createTextNode(card instanceof AnimalCard ? String.valueOf(((AnimalCard) card).getCurrentWeight()) : String.valueOf(((PlantCard) card).getCurrentAge())));
                        farmCard.appendChild(weightOrAge);

                        for (String effect : card.getActiveEffect()) {
                            Element effectElement = document.createElement("effect");
                            effectElement.appendChild(document.createTextNode(keyToValueMap.get(effect)));
                            farmCard.appendChild(effectElement);
                        }
                        root.appendChild(farmCard);
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            throw new IOException("Error writing XML file", e);
        }
    }
}

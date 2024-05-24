package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hands {
    private ArrayList<Card> cards;
    private int active;
    private static final Map<String, Integer> locationIndexMap = new HashMap<>();

    static {
        locationIndexMap.put("A01", 0);
        locationIndexMap.put("A02", 1);
        locationIndexMap.put("A03", 2);
        locationIndexMap.put("A04", 3);
        locationIndexMap.put("A05", 4);
        locationIndexMap.put("A06", 5);
    }

    public Hands() {
        cards = new ArrayList<>();
        active = 0;
        for (int i = 0; i < 6; i++) { 
            this.cards.add(null);
        }
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public int length(){
        return this.active;
    }

    public void addCard(Card card) {
        if (active < 6) {
            int index = findNullIndex();
            this.cards.set(index, card);
            active++;
        } else {
            System.out.println("Hand is full, cannot add more cards.");
        }
    }

    public int findNullIndex() {
        for(int i = 0; i < 6; i++){
            if(cards.get(i) == null){
                return i;
            }
        }
        return -1;
    }

    public void addbyFirstNull(Card card) {
        int index = findNullIndex();
        // System.out.println("Sistem disi:"+index);
        this.cards.set(index, card);
        // System.out.println(this.cards);
    }

    public void addCardByLocation(Card card, String location) {
        Integer index = locationIndexMap.get(location);
        if (index == null) {
            throw new IndexOutOfBoundsException("Invalid location: " + location);
        }
        this.cards.set(index, card);
    }

    public void setCard(int index, Card card) {
        if (index >= 0 && index < cards.size()) {
            cards.set(index, card);
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for hand size " + cards.size());
        }
    }

    public void deleteCard(int index) {
        if (index >= 0 && index < cards.size() && cards.get(index) != null) {
            cards.set(index, null);
            // cards.remove(index);
            active--;
        } else {
            System.out.println("Index out of bounds.");
        }
    }

    public void moveCard(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= cards.size() || toIndex < 0 || toIndex >= cards.size() || fromIndex == toIndex) {
            return;
        }
    
        Card temp = cards.get(toIndex); 
        cards.set(toIndex, cards.get(fromIndex)); 
        cards.set(fromIndex, temp); 
    }
    
    
    public Card getCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.get(index);
        } else {
            System.out.println("Index out of bounds.");
            return null;
        }
    }
    
    public String findCardLocation(Card card) {
        for (int i = 0; i < this.getCardCount(); i++) {
            if (this.cards.get(i) == card) {
                return String.format("A%02d", i + 1);
            }
        }
        throw new IllegalArgumentException("Card not found in the player's deck");
    }

    public int getCardIndexFromLocation(String location) {
        if (location != null && location.length() == 3 && location.charAt(0) == 'A') {
            try {
                int index = Integer.parseInt(location.substring(1)) - 1;
                if (index >= 0 && index < cards.size()) {
                    return index;
                } else {
                    System.out.println("Location out of bounds.");
                    return -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid location format.");
                return -1;
            }
        } else {
            System.out.println("Invalid location format.");
            return -1;
        }
    }

    public int getCardCount() {
        int count = 0;
        for (Card card : cards) {
            if (card != null) {
                count++;
            }
        }
        return count;
    }

    public void clearCards() {
        cards.clear(); 
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            if (card != null) {
                sb.append(card.toString()).append("\n");
            } else {
                sb.append("Empty Slot\n");
            }
        }
        return sb.toString();
    }
    
    public void printHand() {
        for (Card card : cards) {
            if (card != null) {
                System.out.println(card.getName());
            }
        }
    }
    public void addSet(List<Card> cardSet) {
        for (Card card : cardSet) {
            if (active < 6) {
                int index = findNullIndex();
                this.cards.set(index, card);
                active++;
            } else {
                System.out.println("Hand is full, cannot add more cards.");
                break;
            }
        }
    }
}

package entity;

import java.util.ArrayList;

public class Hands {
    private ArrayList<Card> cards;

    public Hands() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void deleteCard(int index) {
        index ++;
        if (index >= 0 && index < cards.size()) {
            cards.set(index, null);
        } else {
            System.out.println("Index out of bounds.");
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

}

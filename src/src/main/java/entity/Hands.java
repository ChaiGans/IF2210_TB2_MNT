package entity;

import java.util.ArrayList;
import java.util.List;

public class Hands {
    private ArrayList<Card> cards;
    private int active;

    public Hands() {
        cards = new ArrayList<>();
        active = 0;
    }

    public void addCard(Card card) {
        cards.add(card);
        active++;
    }

    public void deleteCard(int index) {
        index ++;
        if (index >= 0 && index < cards.size()) {
            cards.set(index, null);
            active--;
        } else {
            System.out.println("Index out of bounds.");
        }
    }
    public ArrayList<Card> getCards(){
        return this.cards;
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
        System.out.println("Your hands:");
        for (Card card : cards) {
            if (card != null) {
                System.out.println(card.getName());
            }
        }
    }
    public void addSet(List<Card> cardSet) {
        for (Card card : cardSet) {
            if (active < 6) {
                cards.add(card);
                active++;
            } else {
                System.out.println("Hand is full, cannot add more cards.");
                break;
            }
        }
    }
}

package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Deck {
    private List<Card> cards;
    private Player owner;
    private int countCard;
    private int max;
    private static final int DEAL_SIZE = 4;

    public Deck(Player owner) {
        this.owner = owner;
        this.cards = new ArrayList<>();
        this.countCard = 0;
        initializeDeck();
    }

    private void initializeDeck() {
        for (int i = 0; i < 40; i++) {
            Card card = CardFactory.createRandomCard(owner);
            while(card.getName() == "Bear"){
                card = CardFactory.createRandomCard(owner);
            }
            cards.add(card);
            this.countCard++;
        }
    }

    void initializeDeckBySize(int size) {
        for (int i = 0; i < size; i++) {
            cards.add(CardFactory.createRandomCard(owner));
            this.countCard++;
        } 
    }

    public int getCurrentDeckCardCount() {
        return this.countCard;
    }

    public void emptyDeck() {
        for (int i = 0; i < this.cards.size(); i++) {
            cards.remove(0);
            this.countCard = 0;
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public List<Card> deal(int size) {
        List<Card> dealtCards = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int index = random.nextInt(cards.size());
            dealtCards.add(cards.remove(index));
            this.countCard--; 
        }
        return dealtCards;
    }
    public List<Card> shuffleSingular(int size) {
        List<Card> dealtCards = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int index = random.nextInt(cards.size());
            // System.out.println("Ukuran"+cards);
            dealtCards.add(cards.get(index));
        }
        return dealtCards;
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public int getSize() {
        return cards.size();
    }

    public void printCardNames() {
        for (Card card : cards) {
            System.out.println(card.getName());
        }
    }

    public List<Card> shuffleDeal(int size) {
        Set<Card> uniqueDealtCards = new HashSet<>(deal(size));
        while (uniqueDealtCards.size() < DEAL_SIZE) {
            uniqueDealtCards.addAll(shuffleDeal(size));
        }
        return new ArrayList<>(uniqueDealtCards);
    }
}

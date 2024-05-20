package entity;

import java.util.*;

public class Player {
    private int cash;
    private String name;
    private Deck deck;
    private Grid field;
    private Hands hands;

    public Player() {
        this.cash = 0;
        this.name = "default";
        this.deck = new Deck(this);
        this.hands = new Hands();
    }

    public int getCash() {
        return this.cash;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public Hands Hand() {
        return this.hands;
    }

    public int getCardInHandCount() {
        return this.hands.getCardCount();
    }

    public String getName() {
        return this.name;
    }

    public void deductCash(int amount) {
        this.cash -= amount;
    }

    public void addCash(int amount) {
        this.cash += amount;
    }

    public void AddHand(Card card){
        this.hands.addCard(card);
    }
    public void ShowHand(){
        this.hands.printHand();;
    }
    public void useCard(int position){
        this.hands.deleteCard(position);
    }
    public void DeckInfo(){
        this.deck.printCardNames();
    }
    public List<Card> draw4(){
        return this.deck.deal();
    }
    public void save(List<Card> draws){
        this.hands.addSet(draws);
    }
}



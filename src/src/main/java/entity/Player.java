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
        this.field = new Grid(5,4);
        this.hands = new Hands();
    }

    public void reshuffleDeck(int size) {
        this.deck.emptyDeck();
        this.deck.initializeDeckBySize(size);
    }

    // location is based on state like 'A01', 'B01', etc.
    public void addCardToField(String location, Card card) {
        int[] xy = Grid.convertLocation(location);
        this.field.setCard(xy[0], xy[1], null);
    }

    public int getCash() {
        return this.cash;
    }

    public Hands getHands() {
        return this.hands;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public Grid getField() {
        return this.field;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCash(int cash) {
        this.cash = cash;
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

    public void setField(Grid field){
        this.field = field;
    }

    public void setHands(Hands hands) {
        this.hands = hands;
    }
}



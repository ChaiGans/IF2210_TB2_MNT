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

    public void printInformation() {
        this.getHands().printHand();
        this.getField().printInformation();
        System.out.println(this.field.countCardInField());
    }

    public void reshuffleDeck(int size) {
        this.deck.emptyDeck();
        this.deck.initializeDeckBySize(40-size);
    }

    // location is based on state like 'A01', 'B01', etc.
    public void addCardToField(String location, Card card) {
        int[] xy = Grid.convertLocation(location);
        this.field.setCard(xy[1], xy[0], card);
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
    public List<Card> draw4(int size){
        return this.deck.deal(size);
    }
    public List<Card> shuffleDeal(int size){
        return this.deck.shuffleSingular(size);
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

    public void nextDay(){
        this.field.nextDay();
    }
    public Card Panen(int col,int row){
        return this.field.Panen(col,row);
    }
}



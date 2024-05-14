package entity;

import java.util.*;

public class Player {
    private int cash;
    private String name;
    private ArrayList<Card> deck;
    private ArrayList<?> field;
    private ArrayList<Card> hand;
    private int cardInHandCount;

    public Player() {
        this.cash = 0;
        this.name = "default";
        this.deck = new ArrayList<Card>();
        // Inisialisasi field
        this.hand = new ArrayList<Card>();
        this.cardInHandCount = 0;
    }



    public int getCash() {
        return this.cash;
    }

    public ArrayList<Card> getDeck() {
        return this.deck;
    }

    public ArrayList<Card> Hand() {
        return this.hand;
    }

    public int getCardInHandCount() {
        return this.cardInHandCount;
    }

    public String getName() {
        return this.name;
    }
}



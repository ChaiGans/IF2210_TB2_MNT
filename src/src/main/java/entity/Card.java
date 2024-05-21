package entity;

import java.util.*;

public abstract class Card {
    protected Player owner;
    protected List<String> activeEffect;

    public Card(Player owner) {
        this.owner = owner;
        this.activeEffect = new ArrayList<>();
    }

    public void addEffect(String skillName) {
        this.activeEffect.add(skillName);
    }

    public List<String> getActiveEffect() {
        return this.activeEffect;
    }

    public String getOwnerName() {
        return this.owner.getName();
    }

    public void printInformation() {
        System.out.println("This card named " + this.getName() + " is owned by " + this.owner.getName() + " and have these effects " + this.activeEffect.toString());
    }

    public abstract String getName();

}
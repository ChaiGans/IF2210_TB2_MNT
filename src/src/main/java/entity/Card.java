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

    @Override
    public String toString() {
        return "This card is owned by " + this.owner + " and have these effects " + this.activeEffect.toString();
    }

    public abstract String getName();

}
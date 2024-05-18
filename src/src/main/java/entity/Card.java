package entity;

abstract class Card {
    protected Player owner;
    public Card(Player owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return this.owner.getName();
    }

    public abstract String getName();

}
package entity;

public class StandardCard extends Card {
    private String name;

    public StandardCard(Player owner, String name) {
        super(owner);
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

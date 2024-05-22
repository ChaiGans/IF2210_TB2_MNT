package entity;

abstract public class PlantCard extends Card {
    protected int currentAge;
    protected int harvestAge;
    protected boolean inProtection;

    public PlantCard(int currentAge, int harvestAge, Player owner) {
        super(owner);
        this.currentAge = currentAge;
        this.harvestAge = harvestAge;
        this.inProtection = false;
    }

    public boolean isReadyToHarvest() {
        return this.currentAge >= this.harvestAge;
    }

    public int getCurrentAge() {
        return this.currentAge;
    }

    public int getHarvestAge() {
        return this.harvestAge;
    }
    public abstract ProductCard harvest();
    public void addAge(int age) {
        this.currentAge += age;
    }

    public void setAge(int age) {
        this.harvestAge =age;
    }

    public void decrementAge(int age) {
        if (age > this.currentAge) {
            this.currentAge = 0;
        } else {
            this.currentAge -= age;
        }
    }

    public void setProtection(boolean dest) {
        this.inProtection = dest;
    }
}

class CornSeed extends PlantCard {
    private String name;
    public CornSeed(Player owner) {
        super(0, 3, owner);
        this.name = "Corn Seed";
    }

    public Corn harvest() {
        return new Corn(owner);
    }
    public String getName(){
        return name;
    }
}

class PumpkinSeed extends PlantCard {
    private String name;
    public PumpkinSeed(Player owner) {
        super(0, 5, owner);
        this.name = "Pumpkin Seed";
    }

    public Pumpkin harvest() {
        return new Pumpkin(owner);
    }
    public String getName(){
        return name;
    }
}

class StrawberrySeed extends PlantCard {
    private String name;
    public StrawberrySeed(Player owner) {
        super(0, 4, owner);
        this.name = "Strawberry Seed";
    }

    public Strawberry harvest() {
        return new Strawberry(owner);
    }
    public String getName(){
        return name;
    }
}

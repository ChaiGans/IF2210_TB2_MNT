package entity;

abstract class PlantCard extends Card {
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

    public void addAge(int age) {
        this.currentAge += age;
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
    public CornSeed(Player owner) {
        super(0, 3, owner);
    }

    public Corn harvest() {
        return new Corn();
    }
    public String getName(){
        return "Corn Seed";
    }
}

class PumpkinSeed extends PlantCard {
    public PumpkinSeed(Player owner) {
        super(0, 5, owner);
    }

    public Pumpkin harvest() {
        return new Pumpkin();
    }
    public String getName(){
        return "Pumpkin Seed";
    }
}

class StrawberrySeed extends PlantCard {
    public StrawberrySeed(Player owner) {
        super(0, 4, owner);
    }

    public Strawberry harvest() {
        return new Strawberry();
    }
    public String getName(){
        return "Strawberry Seed";
    }
}

package entity;

abstract class AbstractPlant {
    protected int currentAge;
    protected int harvestAge;

    public AbstractPlant(int currentAge, int harvestAge) {
        this.currentAge = currentAge;
        this.harvestAge = harvestAge;
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

    public void addAge() {
        this.currentAge++;
    }
}

class CornSeed extends AbstractPlant {
    public CornSeed() {
        super(0, 3);
    }

    public Corn harvest() {
        return new Corn();
    }
}

class PumpkinSeed extends AbstractPlant {
    public PumpkinSeed() {
        super(0, 5);
    }

    public Pumpkin harvest() {
        return new Pumpkin();
    }
}

class StrawberrySeed extends AbstractPlant {
    public StrawberrySeed() {
        super(0, 4);
    }

    public Strawberry harvest() {
        return new Strawberry();
    }
}

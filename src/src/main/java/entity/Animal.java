package entity;

interface EatingStrategy {
    void eat(AbstractAnimal animal, AbstractProduct food);
}

class CarnivoreEatingStrategy implements EatingStrategy {
    public void eat(AbstractAnimal animal, AbstractProduct food) {
        // Increase weight, only meat
        if (food instanceof NonVeganProduct) {
            animal.currentWeight += food.addedWeight;
        } else {
            System.out.println("Punten bro bukan makanan lu, lu karnivor gabisa makan sayur");
        }
    }
}

class HerbivoreEatingStrategy implements EatingStrategy {
    public void eat(AbstractAnimal animal, AbstractProduct food) {
        // Increase weight, only vegetables
        if (food instanceof VeganProduct) {
            animal.currentWeight += food.addedWeight;
        } else {
            System.out.println("Punten bro bukan makanan lu, lu herbivor gabisa makan daging");
        }
    }
}

class OmnivoreEatingStrategy implements EatingStrategy {
    public void eat(AbstractAnimal animal, AbstractProduct food) {
        // Increase weight, both meat and vegetables
        if (food instanceof VeganProduct || food instanceof NonVeganProduct) {
            animal.currentWeight += food.addedWeight;
        } else {
            System.out.println("Punten bro bukan makanan lu, lu herbivor gabisa makan daging");
        }
    }
}

abstract class AbstractAnimal {
    protected int currentWeight;
    protected int harvestWeight;
    private EatingStrategy eatingStrategy;

    public AbstractAnimal(int currentWeight, int harvestWeight, EatingStrategy eatingStrategy) {
        this.currentWeight = currentWeight;
        this.harvestWeight = harvestWeight;
        this.eatingStrategy = eatingStrategy;
    }

    public void eat(AbstractProduct food) {
        eatingStrategy.eat(this, food);
    }

    public boolean isReadyToHarvest() {
        return this.currentWeight >= this.harvestWeight;
    }

    public int getCurrentWeight() {
        return this.currentWeight;
    }

    public int getHarvestWeight() {
        return this.harvestWeight;
    }
}

abstract class Carnivore extends AbstractAnimal {
    public Carnivore(int currentWeight, int harvestWeight) {
        super(currentWeight, harvestWeight, new CarnivoreEatingStrategy());
    }
}

abstract class Herbivore extends AbstractAnimal {
    public Herbivore(int currentWeight, int harvestWeight) {
        super(currentWeight, harvestWeight, new HerbivoreEatingStrategy());
    }
}

abstract class Omnivore extends AbstractAnimal {
    public Omnivore(int currentWeight, int harvestWeight) {
        super(currentWeight, harvestWeight, new OmnivoreEatingStrategy());
    }
}

class LandShark extends Carnivore {
    public LandShark() {
        super(0, 20);
    }

    public SharkFin harvest() {
        return new SharkFin();
    }
}

class Sheep extends Herbivore {
    public Sheep() {
        super(0, 12);
    }

    public SheepMeat harvest() {
        return new SheepMeat();
    }
}

class Horse extends Herbivore {
    public Horse() {
        super(0, 14);
    }

    public HorseMeat harvest() {
        return new HorseMeat();
    }
}

class Cow extends Herbivore {
    public Cow() {
        super(0, 10);
    }

    public Milk harvest() {
        return new Milk();
    }
}

class Chicken extends Omnivore {
    public Chicken() {
        super(0, 5);
    }

    public Egg harvest() {
        return new Egg();
    }
}

class Bear extends Omnivore {
    public Bear() {
        super(0, 25);
    }

    public BearMeat harvest() {
        return new BearMeat();
    }
}

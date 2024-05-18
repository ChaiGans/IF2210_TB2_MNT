package entity;

interface EatingStrategy {
    void eat(AnimalCard animal, ProductCard food);
}

class CarnivoreEatingStrategy implements EatingStrategy {
    public void eat(AnimalCard animal, ProductCard food) {
        // Increase weight, only meat
        if (food instanceof NonVeganProduct) {
            animal.currentWeight += food.addedWeight;
        } else {
            System.out.println("Punten bro bukan makanan lu, lu karnivor gabisa makan sayur");
        }
    }
}

class HerbivoreEatingStrategy implements EatingStrategy {
    public void eat(AnimalCard animal, ProductCard food) {
        // Increase weight, only vegetables
        if (food instanceof VeganProduct) {
            animal.currentWeight += food.addedWeight;
        } else {
            System.out.println("Punten bro bukan makanan lu, lu herbivor gabisa makan daging");
        }
    }
}

class OmnivoreEatingStrategy implements EatingStrategy {
    public void eat(AnimalCard animal, ProductCard food) {
        // Increase weight, both meat and vegetables
        if (food instanceof VeganProduct || food instanceof NonVeganProduct) {
            animal.currentWeight += food.addedWeight;
        } else {
            System.out.println("Punten bro bukan makanan lu, lu herbivor gabisa makan daging");
        }
    }
}

abstract class AnimalCard extends Card {
    protected int currentWeight;
    protected int harvestWeight;
    protected boolean inProtection;
    private EatingStrategy eatingStrategy;

    public AnimalCard(int currentWeight, int harvestWeight, EatingStrategy eatingStrategy, Player owner) {
        super(owner);
        this.currentWeight = currentWeight;
        this.harvestWeight = harvestWeight;
        this.eatingStrategy = eatingStrategy;
        this.inProtection = false;
    }

    public void eat(ProductCard food) {
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

    public void addWeight(int weight) {
        this.currentWeight += weight;
    }

    public void decrementWeight(int weight) {
        if (weight > this.currentWeight) {
            this.currentWeight = 0;
        } else {
            this.currentWeight -= weight;
        }
    }

    public void setProtection(boolean dest) {
        this.inProtection = dest;
    }
}

abstract class Carnivore extends AnimalCard {
    public Carnivore(int currentWeight, int harvestWeight, Player owner) {
        super(currentWeight, harvestWeight, new CarnivoreEatingStrategy(), owner);
    }
}

abstract class Herbivore extends AnimalCard {
    public Herbivore(int currentWeight, int harvestWeight, Player owner) {
        super(currentWeight, harvestWeight, new HerbivoreEatingStrategy(), owner);
    }
}

abstract class Omnivore extends AnimalCard {
    public Omnivore(int currentWeight, int harvestWeight, Player owner) {
        super(currentWeight, harvestWeight, new OmnivoreEatingStrategy(), owner);
    }
}

class LandShark extends Carnivore {
    public LandShark(Player owner) {
        super(0, 20, owner);
    }

    public SharkFin harvest() {
        return new SharkFin(owner);
    }

    public String getName(){
        return "Land Shark";
    }
}

class Sheep extends Herbivore {
    public Sheep(Player owner) {
        super(0, 12, owner);
    }

    public SheepMeat harvest() {
        return new SheepMeat(owner);
    }
    public String getName(){
        return "Sheep";
    }
}

class Horse extends Herbivore {
    public Horse(Player owner) {
        super(0, 14, owner);
    }

    public HorseMeat harvest() {
        return new HorseMeat(owner);
    }
    public String getName(){
        return "Horse";
    }
}

class Cow extends Herbivore {
    public Cow(Player owner) {
        super(0, 10, owner);
    }

    public Milk harvest() {
        return new Milk(owner);
    }
    public String getName(){
        return "Cow";
    }
}

class Chicken extends Omnivore {
    public Chicken(Player owner) {
        super(0, 5, owner);
    }

    public Egg harvest() {
        return new Egg(owner);
    }
    public String getName(){
        return "Chicken";
    }
}

class Bear extends Omnivore {
    public Bear(Player owner) {
        super(0, 25, owner);
    }

    public BearMeat harvest() {
        return new BearMeat(owner);
    }
    public String getName(){
        return "Bear";
    }
}

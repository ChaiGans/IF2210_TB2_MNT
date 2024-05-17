package entity;

// Strategy pattern
interface ItemEffect {
    void applyEffect(PlantCard plant);
    void applyEffect(AnimalCard animal);
    String getName();
}

class ItemCard extends Card {
    protected ItemEffect effect;

    public ItemCard(ItemEffect effect, Player owner) {
        super(owner);
        this.effect = effect;
    }

    public void useOn(PlantCard plant) {
        effect.applyEffect(plant);
    }

    public void useOn(AnimalCard animal) {
        effect.applyEffect(animal);
    }

    public String getName(){
        return this.effect.getName();
    }
}

class AccelerateEffect implements ItemEffect {
    public void applyEffect(PlantCard plant) {
        plant.addAge(2);
    }

    public void applyEffect(AnimalCard animal) {
        animal.addWeight(8);
    }
    public String getName(){
        return "Accelerate";
    }
    
}

class DelayEffect implements ItemEffect {
    public void applyEffect(PlantCard plant) {
        plant.decrementAge(2);
    }

    public void applyEffect(AnimalCard animal) {
        animal.decrementWeight(5);
    }
    public String getName(){
        return "Accelerate";
    }
}

class InstantHarvestEffect implements ItemEffect {
    public void applyEffect(PlantCard plant) {
        // Trigger harvest logic
    }

    public void applyEffect(AnimalCard animal) {
        // Trigger harvest logic
    }
    public String getName(){
        return "Instant Harvest";
    }
}

class DestroyEffect implements ItemEffect {
    public void applyEffect(PlantCard plant) {
        // Remove from field
    }

    public void applyEffect(AnimalCard animal) {
        // Remove from field
    }
    public String getName(){
        return "Destroy";
    }
}

class ProtectEffect implements ItemEffect {
    public void applyEffect(PlantCard plant) {
        plant.setProtection(true);
    }

    public void applyEffect(AnimalCard animal) {
        animal.setProtection(true);
    }
    public String getName(){
        return "Protect";
    }
}

class TrapEffect implements ItemEffect {
    public void applyEffect(PlantCard plant) {
        // Transform bear attack logic
    }

    public void applyEffect(AnimalCard animal) {
        // Transform bear attack logic
    }
    public String getName(){
        return "Trap";
    }
}

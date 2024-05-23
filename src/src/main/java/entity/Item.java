package entity;

import org.example.src.PlayerManager;
import org.example.src.UIUpdateService;
// Strategy pattern
interface ItemEffect {
    void applyEffect(PlantCard plant, Grid grid, int col, int row,Player currentPlayer );
    void applyEffect(AnimalCard animal, Grid grid, int col, int row, Player currentPlayer);
    String getName();
}

class ItemCard extends Card implements IItemEffect {
    protected ItemEffect effect;

    public ItemCard(ItemEffect effect, Player owner) {
        super(owner);
        this.effect = effect;
    }

    public void useOn(PlantCard plant, Grid grid, int col, int row, Player currentPlayer) {
        effect.applyEffect(plant, grid, col, row, currentPlayer);
    }

    public void useOn(AnimalCard animal, Grid grid, int col, int row, Player currentPlayer) {
        effect.applyEffect(animal, grid, col, row, currentPlayer);
    }

    public String getName() {
        return this.effect.getName();
    }
}

class AccelerateEffect implements ItemEffect {
    private String name = "Accelerate";
    public void applyEffect(PlantCard plant, Grid grid, int col, int row,  Player currentPlayer) {
        plant.addAge(2);
        plant.addEffect(name);
    }

    public void applyEffect(AnimalCard animal, Grid grid, int col, int row, Player currentPlayer) {
        animal.addWeight(8);
        animal.addEffect(name);
    }
    public String getName(){
        return name;
    }
}

class DelayEffect implements ItemEffect {
    private String name = "Delay";
    public void applyEffect(PlantCard plant, Grid grid, int col, int row, Player currentPlayer) {
        if (!plant.isProtected()) {
            plant.decrementAge(2);
            plant.addEffect(name);
        } else {
            System.out.println("Plant at " + col + ", " + row + " is protected and cannot be delayed.");
        }
    }

    public void applyEffect(AnimalCard animal, Grid grid, int col, int row, Player currentPlayer) {
        if (!animal.isProtected()) {
            animal.decrementWeight(5);
            animal.addEffect(name);
        } else {
            System.out.println("animal at " + col + ", " + row + " is protected and cannot be delayed.");
        }
    }

    public String getName(){
        return name;
    }
}

class InstantHarvestEffect implements ItemEffect {
    private String name = "Instant Harvest";
    public void applyEffect(PlantCard plant, Grid grid, int col, int row, Player currentPlayer) {
        if (currentPlayer.getHands().length() <= 6) { 
            ProductCard product = grid.Panen(col, row); 
            if (product != null) {
                currentPlayer.AddHand(product);
                UIUpdateService.getInstance().updateHandsGrid(); 
                UIUpdateService.getInstance().updateRealGrid();
            }
        } else {
            System.out.println("Hands are full, cannot perform Instant Harvest.");
        }
        plant.addEffect(name);
    }

    public void applyEffect(AnimalCard animal, Grid grid, int col, int row, Player currentPlayer) {
        if (currentPlayer.getHands().length() <= 6) { 
            ProductCard product = grid.Panen(col, row);
            if (product != null) {
                currentPlayer.AddHand(product); 
                UIUpdateService.getInstance().updateHandsGrid(); 
                UIUpdateService.getInstance().updateRealGrid(); 
            }
        } else {
            System.out.println("Hands are full, cannot perform Instant Harvest.");
        }
        animal.addEffect(name);
    }
    public String getName(){
        return name;
    }
}

class DestroyEffect implements ItemEffect {
    private String name = "Destroy";
    public void applyEffect(PlantCard plant, Grid grid, int col, int row, Player currentPlayer) {
        currentPlayer = PlayerManager.getInstance().getEnemyPlayer();
        if (!plant.isProtected()) {
            currentPlayer.getField().removeCard(col, row);
        } else {
            System.out.println("Plant at " + col + ", " + row + " is protected and cannot be destroyed.");
        }
        UIUpdateService.getInstance().updateRealGrid();
    }

    public void applyEffect(AnimalCard animal, Grid grid, int col, int row, Player currentPlayer) {
        currentPlayer = PlayerManager.getInstance().getEnemyPlayer();
        if (!animal.isProtected()) {
            currentPlayer.getField().removeCard(col, row);
        } else {
            System.out.println("Animal at " + col + ", " + row + " is protected and cannot be destroyed.");
        }
        UIUpdateService.getInstance().updateRealGrid();
        UIUpdateService.getInstance().updateEnemyGrid();

    }
    public String getName(){
        return name;
    }
}

class ProtectEffect implements ItemEffect {
    private String name = "Protect";
    public void applyEffect(PlantCard plant, Grid grid, int col, int row, Player currentPlayer) {
        plant.setProtection(true);
        plant.addEffect(name);
    }

    public void applyEffect(AnimalCard animal, Grid grid, int col, int row, Player currentPlayer) {
        animal.setProtection(true);
        animal.addEffect(name);
    }
    public String getName(){
        return name;
    }
}

class TrapEffect implements ItemEffect {
    private String name = "Trap";
    public void applyEffect(PlantCard plant, Grid grid, int col, int row, Player currentPlayer) {
        // Transform bear attack logic
        plant.addEffect(name);
    }

    public void applyEffect(AnimalCard animal, Grid grid, int col, int row, Player currentPlayer) {
        // Transform bear attack logic
        animal.addEffect(name);
    }
    public String getName(){
        return name;
    }
}

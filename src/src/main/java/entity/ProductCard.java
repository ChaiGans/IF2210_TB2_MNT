package entity;

public abstract class ProductCard extends Card{
    protected int productPrice;
    protected int addedWeight;

    public ProductCard(int productPrice, int addedWeight,Player owner) {
        super(owner);
        this.productPrice = productPrice;
        this.addedWeight = addedWeight;
    }

    abstract public String getName();

    public int getPrice() {
        return this.productPrice;
    }

    public int getAddedWeight() {
        return this.addedWeight;
    }

    public boolean isVeganProduct() {
        return this instanceof VeganProduct;
    }

    public boolean isNonVeganProduct() {
        return this instanceof NonVeganProduct;
    }
}

abstract class VeganProduct extends ProductCard {
    public VeganProduct(int productPrice, int addedWeight,Player owner) {
        super(productPrice, addedWeight, owner);
    }
}

abstract class NonVeganProduct extends ProductCard {
    public NonVeganProduct(int productPrice, int addedWeight,Player owner) {
        super(productPrice, addedWeight, owner);
    }
}

class SharkFin extends NonVeganProduct {
    private String name;
    public SharkFin(Player owner) {
        super(500, 12,owner);
        this.name = "Shark Fin";
    }
    public String getName(){
        return name;
    }
}

class Milk extends NonVeganProduct {
    private String name;
    public Milk(Player owner) {
        super(100, 4,owner);
        this.name = "Milk";
    }
    public String getName(){
        return name;
    }
}

class SheepMeat extends NonVeganProduct {
    private String name;
    public SheepMeat(Player owner) {
        super(120, 6,owner);
        this.name = "Sheep Meat";
    }
    public String getName(){
        return name;
    }
}

class HorseMeat extends NonVeganProduct {
    private String name;
    public HorseMeat(Player owner) {
        super(150, 8,owner);
        this.name = "Horse Meat";
    }
    public String getName(){
        return name;
    }
}

class Egg extends NonVeganProduct {
    private String name;
    public Egg(Player owner) {
        super(50, 2,owner);
        this.name = "Egg";
    }
    public String getName(){
        return name;
    }
}

class BearMeat extends NonVeganProduct {
    private String name;
    public BearMeat(Player owner) {
        super(500, 12,owner);
        this.name = "Bear Meat";
    }
    public String getName(){
        return name;
    }
}

class Strawberry extends VeganProduct {
    private String name;
    public Strawberry(Player owner) {
        super(350, 5,owner);
        this.name = "Strawberry";
    }
    public String getName(){
        return name;
    }
}

class Pumpkin extends VeganProduct {
    private String name;
    public Pumpkin(Player owner) {
        super(500, 10,owner);
        this.name = "Pumpkin";    }
    public String getName(){
        return name;
    }
}

class Corn extends VeganProduct {
    private String name;
    public Corn(Player owner) {
        super(150, 3,owner);
        this.name = "Corn";
    }
    public String getName(){
        return name;
    }
}
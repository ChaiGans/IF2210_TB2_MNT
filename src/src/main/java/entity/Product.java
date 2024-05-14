package entity;

abstract class AbstractProduct extends Card{
    protected int productPrice;
    protected int addedWeight;

    public AbstractProduct(int productPrice, int addedWeight,Player owner) {
        super(owner);
        this.productPrice = productPrice;
        this.addedWeight = addedWeight;
    }
}

abstract class VeganProduct extends AbstractProduct {
    public VeganProduct(int productPrice, int addedWeight,Player owner) {
        super(productPrice, addedWeight,owner);
    }
}

abstract class NonVeganProduct extends AbstractProduct {
    public NonVeganProduct(int productPrice, int addedWeight,Player owner) {
        super(productPrice, addedWeight,owner);
    }
}

class SharkFin extends NonVeganProduct {
    public SharkFin(Player owner) {
        super(500, 12,owner);
    }
    public String getName(){
        return "Shark Fin";
    }
}

class Milk extends NonVeganProduct {
    public Milk(Player owner) {
        super(100, 4,owner);
    }
    public String getName(){
        return "Milk";
    }
}

class SheepMeat extends NonVeganProduct {
    public SheepMeat(Player owner) {
        super(120, 6,owner);
    }
    public String getName(){
        return "Sheep Meat";
    }
}

class HorseMeat extends  NonVeganProduct {
    public HorseMeat(Player owner) {
        super(150, 8,owner);
    }
    public String getName(){
        return "Horse Meat";
    }
}

class Egg extends  NonVeganProduct {
    public Egg(Player owner) {
        super(50, 2,owner);
    }
    public String getName(){
        return "Egg";
    }
}

class BearMeat extends  NonVeganProduct {
    public BearMeat(Player owner) {
        super(500, 12,owner);
    }
    public String getName(){
        return "Bear Meat";
    }
}

class Strawberry extends  VeganProduct {
    public Strawberry(Player owner) {
        super(350, 5,owner);
    }
    public String getName(){
        return "Strawberry";
    }
}

class Pumpkin extends  VeganProduct {
    public Pumpkin(Player owner) {
        super(500, 10,owner);
    }
    public String getName(){
        return "Pumpkin";
    }
}

class Corn extends  VeganProduct {
    public Corn(Player owner) {
        super(150, 3,owner);
    }
    public String getName(){
        return "Corn";
    }
}
package entity;

abstract class AbstractProduct {
    protected int productPrice;
    protected int addedWeight;

    public AbstractProduct(int productPrice, int addedWeight) {
        this.productPrice = productPrice;
        this.addedWeight = addedWeight;
    }
}

abstract class VeganProduct extends AbstractProduct {
    public VeganProduct(int productPrice, int addedWeight) {
        super(productPrice, addedWeight);
    }
}

abstract class NonVeganProduct extends AbstractProduct {
    public NonVeganProduct(int productPrice, int addedWeight) {
        super(productPrice, addedWeight);
    }
}

class SharkFin extends NonVeganProduct {
    public SharkFin() {
        super(500, 12);
    }
}

class Milk extends NonVeganProduct {
    public Milk() {
        super(100, 4);
    }
}

class SheepMeat extends NonVeganProduct {
    public SheepMeat() {
        super(120, 6);
    }
}

class HorseMeat extends  NonVeganProduct {
    public HorseMeat() {
        super(150, 8);
    }
}

class Egg extends  NonVeganProduct {
    public Egg() {
        super(50, 2);
    }
}

class BearMeat extends  NonVeganProduct {
    public BearMeat() {
        super(500, 12);
    }
}

class Strawberry extends  VeganProduct {
    public Strawberry() {
        super(350, 5);
    }
}

class Pumpkin extends  VeganProduct {
    public Pumpkin() {
        super(500, 10);
    }
}

class Corn extends  VeganProduct {
    public Corn() {
        super(150, 3);
    }
}
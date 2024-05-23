package entity;

import java.util.Random;

public class CardFactory {
    private static final String[] CARD_TYPES = {
        "Corn Seed", "Pumpkin Seed", "Strawberry Seed", "Land Shark", "Sheep", "Horse", "Cow", "Chicken",
        "Accelerate", "Delay", "Instant Harvest", "Destroy", "Protect", "Trap",
        "Shark Fin", "Milk", "Sheep Meat", "Horse Meat", "Egg", "Bear Meat",
        "Strawberry", "Pumpkin", "Corn"
    };

    public static Card createCard(String cardType, Player owner) {
        switch (cardType) {
            case "Corn Seed":
                return new CornSeed(owner);
            case "Pumpkin Seed":
                return new PumpkinSeed(owner);
            case "Strawberry Seed":
                return new StrawberrySeed(owner);
            case "Land Shark":
                return new LandShark(owner);
            case "Sheep":
                return new Sheep(owner);
            case "Horse":
                return new Horse(owner);
            case "Cow":
                return new Cow(owner);
            case "Chicken":
                return new Chicken(owner);
            case "Bear":
                return new Bear(owner);
            case "Accelerate":
                return new ItemCard(new AccelerateEffect(), owner);
            case "Delay":
                return new ItemCard(new DelayEffect(), owner);
            case "Instant Harvest":
                return new ItemCard(new InstantHarvestEffect(), owner);
            case "Destroy":
                return new ItemCard(new DestroyEffect(), owner);
            case "Protect":
                return new ItemCard(new ProtectEffect(), owner);
            case "Trap":
                return new ItemCard(new TrapEffect(), owner);
            case "Shark Fin":
                return new SharkFin(owner);
            case "Milk":
                return new Milk(owner);
            case "Sheep Meat":
                return new SheepMeat(owner);
            case "Horse Meat":
                return new HorseMeat(owner);
            case "Egg":
                return new Egg(owner);
            case "Bear Meat":
                return new BearMeat(owner);
            case "Strawberry":
                return new Strawberry(owner);
            case "Pumpkin":
                return new Pumpkin(owner);
            case "Corn":
                return new Corn(owner);
            default:
                throw new IllegalArgumentException("Unknown card type: " + cardType);
        }
    }

    public static Card createRandomCard(Player owner) {
        Random random = new Random();
        String cardType = CARD_TYPES[random.nextInt(CARD_TYPES.length)];
        return createCard(cardType, owner);
    }
}

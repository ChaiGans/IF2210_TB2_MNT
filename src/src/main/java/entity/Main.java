package entity;

class Main {
    public static void main(String[] args) {
        Player Player1 = new Player();
        Player Player2 = new Player();
        // AbstractProduct vegan_food = new Strawberry();
        // AbstractProduct meat_food = new BearMeat();
        AnimalCard animal_1 = new LandShark(Player1);
        PlantCard new_plant = new CornSeed(Player1);
        Player1.AddHand(animal_1);
        Player1.AddHand(new_plant);
        Player1.ShowHand();
        Player1.useCard(1);
        Player1.ShowHand();
    }
}

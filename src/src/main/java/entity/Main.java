package entity;

class Main {
    public static void main(String[] args) {
        Player new_player = new Player();
        AbstractProduct vegan_food = new Strawberry();
        AbstractProduct meat_food = new BearMeat();

        AnimalCard animal_1 = new LandShark(new_player);

        animal_1.eat(vegan_food);  // Expected output: "Punten bro bukan makanan lu, lu karnivor gabisa makan sayur"
        System.out.println(animal_1.getCurrentWeight());
        animal_1.eat(meat_food);   // LandShark eats meat without problem
        System.out.println(animal_1.getCurrentWeight());
        
        PlantCard new_plant = new CornSeed(new_player);
        System.out.println(new_plant.getCurrentAge());
        new_plant.addAge(1);
        System.out.println(new_plant.getCurrentAge());
        System.out.println(new_plant.getOwnerName());
    }
}

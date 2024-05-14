package entity;

import entity.*;

class Main {
    public static void main(String[] args) {
        AbstractProduct vegan_food = new Strawberry();
        AbstractProduct meat_food = new BearMeat();

        AbstractAnimal animal_1 = new LandShark();

        animal_1.eat(vegan_food);  // Expected output: "Punten bro bukan makanan lu, lu karnivor gabisa makan sayur"
        System.out.println(animal_1.getCurrentWeight());
        animal_1.eat(meat_food);   // LandShark eats meat without problem
        System.out.println(animal_1.getCurrentWeight());
    }
}

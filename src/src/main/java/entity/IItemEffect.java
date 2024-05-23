package entity;

public interface IItemEffect {
    void useOn(PlantCard plant, Grid grid, int col, int row, Player currentPlayer);
    void useOn(AnimalCard animal, Grid grid, int col, int row, Player currentPlayer);
    String getName();
}

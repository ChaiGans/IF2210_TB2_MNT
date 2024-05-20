package entity;
public class GameData {
    private static GameData instance;
    private Hands hands;
    private Grid gridData;

    private GameData() {
        hands = new Hands();
        gridData = new Grid(5, 4);
    }

    public static synchronized GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    public Hands getHands() {
        return hands;
    }

    public Grid getGridData() {
        return gridData;
    }
}

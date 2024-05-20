package entity;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int width;
    private int height;
    private List<List<Card>> matrix;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Card> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(null); 
            }
            this.matrix.add(row);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Card getCard(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException("Coordinates out of bounds");
        }
        return matrix.get(y).get(x);
    }

    public void setCard(int x, int y, Card card) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException("Coordinates out of bounds");
        }
        matrix.get(y).set(x, card);
    }

    public void removeCard(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            matrix.get(y).set(x, null);
        } else {
            throw new IndexOutOfBoundsException("Coordinates out of bounds");
        }
    }
}

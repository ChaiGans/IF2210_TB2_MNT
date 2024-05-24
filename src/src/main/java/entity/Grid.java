package entity;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int width;
    private int height;
    private List<List<Card>> matrix;

    public static int[] convertLocation(String location) {
        if (location == null || location.length() < 3) {
            throw new IllegalArgumentException("Invalid location format: " + location);
        }

        // Extract the row part ('A', 'B', etc.)
        char rowChar = location.charAt(0);
        // Extract the column part ('01', '02', etc.)
        String columnStr = location.substring(1);

        // Convert rowChar ('A' -> 0, 'B' -> 1, etc.)
        int rowIndex = rowChar - 'A';

        // Convert columnStr to a zero-based index
        int columnIndex = Integer.parseInt(columnStr) - 1;

        return new int[]{rowIndex, columnIndex};
    }

    public static String convertIndicesToLocation(int i, int j) {
        // Convert the row index to a letter
        char row = (char) ('A' + i);
        
        // Convert the column index to a two-digit number
        String column = String.format("%02d", j + 1);
        
        // Combine row and column to form the location string
        return row + column;
    }

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

    public void removeCardArea (List<List<Integer>> subgridPosition) {
        for (List<Integer> area : subgridPosition) {
            if (this.getCard(area.get(1), area.get(0)) != null && !this.getCard(area.get(1), area.get(0)).getActiveEffect().contains("Protect")) {
                this.removeCard(area.get(1), area.get(0));
            }
        }
    }

    public boolean isAreaTrap (List<List<Integer>> subgridPosition) {
        for (List<Integer> area : subgridPosition) {
            System.out.println("area: "  + area);
            Card currentCard = this.getCard(area.get(1), area.get(0));
            if (currentCard != null && currentCard.getActiveEffect().contains("Trap")) {
                return true;
            };
        }
        return false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int countCardInField() {
        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (this.matrix.get(i).get(j) != null) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public Card getCard(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            System.out.println("col: " + x);
            System.out.println("row: " + y);
            throw new IndexOutOfBoundsException("Coordinates out of bounds");
        }
        return matrix.get(y).get(x);
    }

    public void removeCard(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            matrix.get(y).set(x, null);
        } else {
            System.out.println("ini remove");
            throw new IndexOutOfBoundsException("Coordinates out of bounds");
        }
    }
    public ProductCard Panen(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            Card card = this.matrix.get(y).get(x);
            if (card instanceof AnimalCard){
                AnimalCard animalCard = (AnimalCard) card;
                ProductCard product = animalCard.harvest();
                matrix.get(y).set(x, null);
                return product;
            }
            else if (card instanceof PlantCard){
                PlantCard plantCard = (PlantCard) card;
                ProductCard product = plantCard.harvest();
                matrix.get(y).set(x, null);
                return product;
            }
            throw new IndexOutOfBoundsException("Not any of the type");
        } else {
            throw new IndexOutOfBoundsException("Coordinates out of bounds");
        }
    }

    public void setCard(int x, int y, Card card) {
        System.out.println("Koordinat:"+x+","+y);
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException("Coordinates out of bounds");
        }
        this.matrix.get(y).set(x, card);
    }

    public void nextDay(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (this.matrix.get(i).get(j) != null  && this.matrix.get(i).get(j) instanceof PlantCard) {
                    PlantCard card = (PlantCard) this.matrix.get(i).get(j);
                    card.addAge(1);
                }
            }
        } 
    }
    public void printInformation() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (this.matrix.get(i).get(j) != null) {
                    System.out.println(convertIndicesToLocation(i, j));
                    this.matrix.get(i).get(j).printInformation();
                }
            }
        }
    }
}


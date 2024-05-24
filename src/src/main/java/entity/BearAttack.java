package entity;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class BearAttack {
    public final int minDuration = 10; // 30 seconds
    public final int maxDuration = 15; // 60 seconds
    private final Random random = new Random();
    private boolean bearAttackHappening = false;
    private List<List<Integer>> subgridPosition; // Position of the subgrid being attacked

    public boolean isBearAttackHappening() {
        return this.bearAttackHappening;
    }

    public void setBearAttackHappening(boolean targetStatus) {
        this.bearAttackHappening = targetStatus;
    }

    public void setSubgridPosition(List<List<Integer>> newSubgrid) {
        this.subgridPosition = newSubgrid;
    }

    public List<List<Integer>> getTargetSubgrid() {
        return this.subgridPosition;
    }

    public Random getRandom() {
        return this.random;
    }

    public List<List<Integer>> determineSubgridPosition(Grid field) {
        int rows = field.getHeight();
        int cols = field.getWidth();
        int subgridArea = random.nextInt(1, 7);
        List<List<Integer>> positions = new ArrayList<>();

        switch (subgridArea) {
            case 1: // 1x1 subgrid
                positions.add(List.of(random.nextInt(rows), random.nextInt(cols)));
                break;
            case 2: // 1x2 or 2x1 subgrid
                int row2 = random.nextInt(rows);
                int col2 = random.nextInt(cols - 1);
                if (random.nextBoolean()) {
                    positions.add(List.of(row2, col2));
                    positions.add(List.of(row2, col2 + 1));
                } else {
                    positions.add(List.of(col2, row2));
                    positions.add(List.of(col2 + 1, row2));
                }
                break;
            case 3: // 1x3 or 3x1 subgrid
                int row3 = random.nextInt(rows);
                int col3 = random.nextInt(cols - 2);
                if (random.nextBoolean()) {
                    positions.add(List.of(row3, col3));
                    positions.add(List.of(row3, col3 + 1));
                    positions.add(List.of(row3, col3 + 2));
                } else {
                    positions.add(List.of(col3, row3));
                    positions.add(List.of(col3 + 1, row3));
                    positions.add(List.of(col3 + 2, row3));
                }
                break;
            case 4: // 2x2 subgrid
                int row4 = random.nextInt(rows - 1);
                int col4 = random.nextInt(cols - 1);
                positions.add(List.of(row4, col4));
                positions.add(List.of(row4, col4 + 1));
                positions.add(List.of(row4 + 1, col4));
                positions.add(List.of(row4 + 1, col4 + 1));
                break;
            case 5: // 1x5 or 5x1 subgrid
                if (random.nextBoolean()) {
                    int row5 = random.nextInt(rows);
                    for (int i = 0; i < 5; i++) {
                        positions.add(List.of(row5, i));
                    }
                } else {
                    int col5 = random.nextInt(cols);
                    for (int i = 0; i < 5; i++) {
                        positions.add(List.of(i, col5));
                    }
                }
                break;
            case 6: // 2x3 or 3x2 subgrid
                int row6 = random.nextInt(rows - 2);
                int col6 = random.nextInt(cols - 2);
                if (random.nextBoolean()) { // 2x3
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 3; j++) {
                            positions.add(List.of(row6 + i, col6 + j));
                        }
                    }
                } else { //3x2
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 2; j++) {
                            positions.add(List.of(row6 + i, col6 + j));
                        }
                    }
                }

                break;
        }
        return positions;
    }
}

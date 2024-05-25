package entity;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class BearAttack {
    public final int minDuration = 30; // 30 seconds
    public final int maxDuration = 60; // 60 seconds
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
                if (random.nextBoolean()) { // 1 x 2
                    int row2 = random.nextInt(0, rows);
                    int col2 = random.nextInt(0, cols - 1); // [0..3]
                    positions.add(List.of(row2, col2));
                    positions.add(List.of(row2, col2 + 1));
                } else {
                    int row2 = random.nextInt(0, rows - 1); // [0..2]
                    int col2 = random.nextInt(0, cols);
                    positions.add(List.of(row2, col2));
                    positions.add(List.of(row2 + 1, col2));
                }
                break;
            case 3: // 1x3 or 3x1 subgrid
                if (random.nextBoolean()) { // 1x3
                    int row3 = random.nextInt(0, rows);
                    int col3 = random.nextInt(0, cols - 2); // [0..2]
                    positions.add(List.of(row3, col3));
                    positions.add(List.of(row3, col3 + 1));
                    positions.add(List.of(row3, col3 + 2));
                } else { // 3 x 1
                    int row3 = random.nextInt(0, rows-2); // [0..1]
                    int col3 = random.nextInt(0, cols);
                    positions.add(List.of(row3, col3));
                    positions.add(List.of(row3+1, col3));
                    positions.add(List.of(row3+2, col3));
                }
                break;
            case 4: // 2x2 subgrid
                int row4 = random.nextInt(0,rows - 1); // [0..2]
                int col4 = random.nextInt(0,cols - 1); // [0..3]
                positions.add(List.of(row4, col4));
                positions.add(List.of(row4, col4 + 1));
                positions.add(List.of(row4 + 1, col4));
                positions.add(List.of(row4 + 1, col4 + 1));
                break;
            case 5: // 1x5 or 5x1 subgrid
                if (random.nextBoolean()) {
                    int row5 = random.nextInt(0, rows); // 1x5
                    for (int i = 0; i < 5; i++) {
                        positions.add(List.of(row5, i));
                    }
                } else {
                    int col5 = random.nextInt(0, cols); // 4x1
                    for (int i = 0; i < 4; i++) {
                        positions.add(List.of(i, col5));
                    }
                }
                break;
            case 6: // 2x3 or 3x2 subgrid batas aman row[0..3] col[0..4]
                if (random.nextBoolean()) { // 2x3
                    int row6 = random.nextInt(0,rows - 2); // row6[0..2]
                    int col6 = random.nextInt(0,cols - 3); // col6[0..2]
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 3; j++) {
                            positions.add(List.of(row6 + i, col6 + j));
                        }
                    }
                } else { //3x2
                    int row6 = random.nextInt(0,rows - 3); // row6[0..1]
                    int col6 = random.nextInt(0,cols - 2); // col6[0..3]
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

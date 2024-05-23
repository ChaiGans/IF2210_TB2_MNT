package org.example.src;

import entity.GameData;
import javafx.application.Platform;

public class UIUpdateService {
    private static UIUpdateService instance = new UIUpdateService();
    private ActiveHandsController handsController;
    private DrawsController drawsController;
    private GridController gridController;
    private UIUpdateService() {}

    public static UIUpdateService getInstance() {
        return instance;
    }

    public void setHandsController(ActiveHandsController controller) {
        this.handsController = controller;
    }

    public void setDrawsController(DrawsController controller) {
        this.drawsController = controller;
    }

    public void setGridsController(GridController controller) {
        this.gridController = controller;
    }

    public void updateHandsGrid() {
        System.out.println("Wanted to Save: "+PlayerManager.getInstance().getCurrentPlayer().getHands());
        // System.out.println("Tes hertz:");
        // PlayerManager.getInstance().getCurrentPlayer().ShowHand();
        if (handsController != null) {
            Platform.runLater(() -> {
                handsController.updateGrid(PlayerManager.getInstance().getCurrentPlayer().getHands());
            });
        }
        else{
            System.out.println("Hands is null");
        }
    }
    

    public void updateRealGrid() {
        System.out.println("updating grid: ");
        PlayerManager.getInstance().getCurrentPlayer().getField().printInformation();
        System.out.println("row ui" + PlayerManager.getInstance().getCurrentPlayer().getField().getHeight());
        System.out.println("col ui" + PlayerManager.getInstance().getCurrentPlayer().getField().getWidth());
        if (gridController != null) {
            Platform.runLater(() -> {
                gridController.toggleGridDisplay(false);
            });
        }
        else{
            System.out.println("grid is null");
        }
    }
    public void updateEnemyGrid() {
        if (gridController != null) {
            Platform.runLater(() -> {
                gridController.toggleGridDisplay(true);
            });
        }
        else{
            System.out.println("grid is null");
        }
    }
    
    public void updateDrawsGrid() {
        if (drawsController != null) {
            Platform.runLater(() -> {
                drawsController.updateCardGrid(PlayerManager.getInstance().getCurrentPlayer().draw4());
            });
        }
    }
}

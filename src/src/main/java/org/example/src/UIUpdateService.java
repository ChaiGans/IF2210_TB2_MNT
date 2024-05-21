package org.example.src;

import entity.GameData;
import javafx.application.Platform;

public class UIUpdateService {
    private static UIUpdateService instance = new UIUpdateService();
    private ActiveHandsController handsController;
    private DrawsController drawsController;
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
    public void updateDrawsGrid() {
        if (drawsController != null) {
            Platform.runLater(() -> {
                drawsController.updateCardGrid(PlayerManager.getInstance().getCurrentPlayer().draw4());
            });
        }
    }
}

package org.example.src;

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
        // System.out.println("Wanted to Save:"+PlayerManager.getInstance().getCurrentPlayer().Hand());
        if (handsController != null) {
            Platform.runLater(() -> {
                handsController.updateGrid(PlayerManager.getInstance().getCurrentPlayer().Hand());
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

package com.ExceptionHandled.InternalPacketsAndWrappers;

import com.ExceptionHandled.TicTacToeUI.BoardUI.GameBoardController;

public class RemoveGame {
    private GameBoardController controller;

    public RemoveGame(GameBoardController controller) {
        this.controller = controller;
    }

    public GameBoardController getController() {
        return controller;
    }
}

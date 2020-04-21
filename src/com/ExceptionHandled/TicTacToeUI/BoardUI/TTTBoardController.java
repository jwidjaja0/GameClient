package com.ExceptionHandled.TicTacToeUI.BoardUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import jfxtras.styles.jmetro.JMetroStyleClass;

import java.io.IOException;
import java.util.ArrayList;

public class TTTBoardController {
    @FXML
    public Button panel1;

    @FXML
    public Button panel2;

    @FXML
    public Button panel3;

    @FXML
    public Button panel4;

    @FXML
    public Button panel5;

    @FXML
    public Button panel6;

    @FXML
    public Button panel7;

    @FXML
    public Button panel8;

    @FXML
    public Button panel9;

    private ArrayList<Button> buttons;
    private String gameID;
    private String gameName;
    private String player;

    public void initialize() throws IOException {
        buttons = new ArrayList<>();
        buttons.add(panel1);
        buttons.add(panel2);
        buttons.add(panel3);
        buttons.add(panel4);
        buttons.add(panel5);
        buttons.add(panel6);
        buttons.add(panel7);
        buttons.add(panel8);
        buttons.add(panel9);

    }



}

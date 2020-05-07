package com.ExceptionHandled.TicTacToeUI.AI;

import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.GameMessages.Game.MoveMade;

public class AI {

    private static AI instance = new AI();
    final private String id = "a1234bcd";

    private AI(){
    }

    public void makeMove(String[][] board, String gameID){
        //Call Minimax
        //Send move
        MessageSender.getInstance().sendMessage("Game", id, getMove(board, gameID));
    }

    public static AI getInstance(){
        return instance;
    }

    private MoveMade getMove(String[][] board, String gameID){
        int bestMove = 100; //will be the fewest number of moves to win, or the largest number of moves to loose/draw
        int bestMoveX = -10; //board position of best move
        int bestMoveY = -5; //board position of best move
        AIBoard aiBoard = new AIBoard(board);

        ///iterate through all possible moves...
        for (int x = 0; x < aiBoard.getSideDim(); x++){
            for (int y = 0; y < aiBoard.getSideDim(); y++){
                if (aiBoard.getMoveAt(x, y).equals(" ")){
                    //test the benefits of this move
                    aiBoard.setMove(x, y, "o");
                    int temp = minimax(aiBoard, aiBoard.remainingMoves(), false, -100, 100);
                    //if its the best move so far, then save it
                    if (temp < bestMove){
                        bestMove = temp;
                        bestMoveX = x;
                        bestMoveY = y;
                    }
                    //then delete the move to test the other moves
                    aiBoard.unSetMove(x, y);
                }
            }
        }
        //return the best move
        return new MoveMade(gameID, "o", bestMoveX, bestMoveY);
    }

    public int minimax (AIBoard board, int depth, boolean maxPlayer, int alpha, int beta) {
        if (board.isWon("x")) return 10 + board.remainingMoves(); //X is max player
        if (board.isWon("o")) return -10 - board.remainingMoves(); //O is min player
        if (depth == 0) return 0; //tie)

        int eval;

        if (!maxPlayer) {
            for (int x = 0; x < board.getSideDim(); x++) {
                for (int y = 0; y < board.getSideDim(); y++) {
                    if (board.getMoveAt(x, y).equals(" ")) {
                        board.setMove(x,y,"x");
                        eval = minimax(board, depth - 1, true, alpha, beta);
                        board.unSetMove(x,y);
                        if (eval > alpha) alpha = eval;
                    }
                }
            }
            return alpha;
        }

        else {
            for (int x = 0; x < board.getSideDim(); x++) {
                for (int y = 0; y < board.getSideDim(); y++) {
                    if (board.getMoveAt(x, y).equals(" ")) {
                        board.setMove(x,y,"o");
                        eval = minimax(board, depth - 1, false, alpha, beta);
                        board.unSetMove(x,y);
                        if (eval < beta) beta = eval;
                    }
                }
            }
            return beta;
        }
    }
}

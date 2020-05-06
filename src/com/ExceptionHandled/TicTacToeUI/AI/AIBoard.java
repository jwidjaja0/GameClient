package com.ExceptionHandled.TicTacToeUI.AI;

/**
 * This class is only meant to be used by the AI. That is why all methods are package private.
 */
public class AIBoard {

    private String[][] board;

    AIBoard(String[][] board){
        this.board = new String[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.board[i][j] = board[i][j];
            }
        }
    }

    //places the player token in a specific spot on the board
    void setMove(int row, int col, String token){
        board[row][col] = token;
    }

    //checks if the player token has a winning set of moves
    boolean isWon(String token) {
        //check horizontals
        for(int i = 0; i < 3; i++){
            if(board[i][0].equals(token)
                    && board[i][1].equals(token)
                    && board[i][2].equals(token)) {
                return true;
            }
        }
        //check verticals
        for(int i = 0; i < 3; i++){
            if(board[0][i].equals(token)
                    && board[1][i].equals(token)
                    && board[2][i].equals(token)) {
                return true;
            }
        }
        //check one diagonal
        if(board[0][0].equals(token)
                && board[1][1].equals(token)
                && board[2][2].equals(token)){
            return true;
        }
        //check other diagonal
        if(board[0][2].equals(token)
                && board[1][1].equals(token)
                && board[2][0].equals(token)){
            return true;
        }
        //otherwise they didn't win
        return false;
    }

    //calculates how many moves are left in the game
    int remainingMoves(){
        int count = 9;
        for (int i = 0; i < board.length; ++i){
            for (int j = 0; j < board[i].length; ++j){
                if (!board[i][j].equals(" ")){
                    --count;
                }
            }
        }
        return count;
    }

    //resets the move at x,y to a blank
    void unSetMove(int row, int col){
        board[row][col] = " ";
    }

    int getSideDim(){
        return board.length;
    }

    String getMoveAt(int x, int y){
        return board[x][y];
    }
}

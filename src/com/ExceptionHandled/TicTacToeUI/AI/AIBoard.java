package com.ExceptionHandled.TicTacToeUI.AI;

public class AIBoard {
    private String[][] board;

    public AIBoard(String[][] board){
        this.board = new String[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.board[i][j] = board[i][j];
            }
        }
    }

    //game is over if either player won or the board is full
    private boolean isGameOver(){
        return isWon("x") || isWon("o") || isFull();
    }

    //places the player token in a specific spot on the board
    void setMove(int row, int col, String token){
        board[row][col] = token;
    }

    //checks if the player token has a winning set of moves
    public boolean isWon(String token) {
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
        if(board[0][2] == token
                && board[1][1] == token
                && board[2][0] == token){
            return true;
        }
        //otherwise they didn't win
        return false;
    }

    //returns true if neither player won but the board is full
    public boolean isDraw(){
        if (isFull() && !isWon("x") && !isWon("o"))
            return true;
        else
            return false;
    }

    //returns true of there are no available moves left
    public boolean isFull(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j].equals(" "))
                    return false;
            }
        }
        return true;
    }

}

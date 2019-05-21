package com.example.guy.gamebattleship.Logic;

enum Turn {
    PLAYER1,PLAYER2
}

public class Game {
private boolean turnSucceed;

    public Board getUserBoard() {
        return userBoard;
    }

    public Board getComputerBoard() {
        return computerBoard;
    }


    private Board userBoard ;
    private Board computerBoard ;

    private Turn mTurn;


    private ComputerPlayer mComputerPlayer;
    public int getTurn()
    {
        if(mTurn==Turn.PLAYER1)
        return 1;
        else
            return 0;
    }
    public Game(int boardSize) {
        this.userBoard = new Board(boardSize,"user" );
        this.computerBoard = new Board(boardSize,"computer");

        mTurn = Turn.PLAYER1;

        mComputerPlayer = new ComputerPlayer();
    }

    public void toggleTurn() {

        if(mTurn == Turn.PLAYER1)
            mTurn = Turn.PLAYER2;
        else
            mTurn = Turn.PLAYER1;
    }

    public void playTile(int position) {
        boolean playWon,playerPlayed = false;
            switch (mTurn) {

                case PLAYER1:
                default:
                    playerPlayed = userBoard.setTile(position);
                    break;
                case PLAYER2:
                    playerPlayed = computerBoard.setTile(position);
                    break;


            }

        if(playerPlayed){
                turnSucceed =true;
            if(mTurn.ordinal()==0){
                userBoard.checkSinking();
                playWon = userBoard.gameWon();
            }
            else{
                computerBoard.checkSinking();
                playWon = computerBoard.gameWon();
            }
            if(playWon == false) {
                toggleTurn();
            }
        }
        else
            { turnSucceed =false;
            }
    }

    public void rotation()
    {
        //computerBoard.ratation();
        userBoard.ratation();
        computerBoard.hiting();

    }
    public boolean gameWon(Board curBoard) {
        return curBoard.gameWon();
    }

    public void playComputer() {
        if(turnSucceed) {
            int positionToPlay = mComputerPlayer.playTurn(computerBoard);
            playTile(positionToPlay);
        }
    }

}
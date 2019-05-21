package com.example.guy.gamebattleship.Logic;


import java.util.Random;

public class ComputerPlayer {
private final int MILL_SEC_TIME=1000;



    public void think() {

        try {

            Thread.sleep(MILL_SEC_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    public int playTurn(Board board) {
        think();
        Random random = new Random();
        int positionToPlay = random.nextInt(board.getBoardSize());
        while (board.getTile(positionToPlay).getStatus() != Board.TileState.NONE && board.getTile(positionToPlay).getStatus() != Board.TileState.Ship) {

            positionToPlay = random.nextInt(board.getBoardSize());

        }
        return positionToPlay;
    }

}

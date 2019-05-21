package com.example.guy.gamebattleship.Logic;

public class Tile {

    private Board.TileState mStatus;

    public Board.TileState getStatus() {
        return mStatus;
    }

    public void setStatus(Board.TileState status) {
        this.mStatus = status;
    }


    public Tile() {
        mStatus = Board.TileState.NONE;
    }




}
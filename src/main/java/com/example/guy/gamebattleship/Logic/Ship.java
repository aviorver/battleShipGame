package com.example.guy.gamebattleship.Logic;

public class Ship {
    private int coordinates[];
    private int size;
    private boolean isSinking = false;

    public Ship(int[] coordinates, int size) {
        this.size = size;
        this.coordinates = coordinates;
    }
    public int getSize(){
        return size;
    }
    public void setIsSinking() {
        this.isSinking = true;
    }

    public boolean getIsSinking() {
        return isSinking;
    }

    public int[] getCoordinates() {
        return coordinates;
    }
}

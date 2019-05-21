package com.example.guy.gamebattleship.Logic;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private final int BOARD_SIZE_3x3 = 9;
    private final int BOARD_SIZE_4x4 = 16;

    private int BoardSize;
    private String boardType;
    private Ship[] Ships = new Ship[2];
    private Random rand = new Random();
    final static int[][] shipsThreeSize3X3 = new int[][]{
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8}

    };

    final static int[][] shipsTwoSize3X3 = new int[][]{
            {0, 1},
            {1, 2},
            {3, 4},
            {4, 5},
            {6, 7},
            {7, 8},
            {0, 3},
            {3, 6},
            {1, 4},
            {4, 7},
            {2, 5},
            {5, 8}

    };

    final static int[][] shipsThreeSize4X4 = new int[][]{
            {0, 1, 2},
            {1, 2, 3},
            {4, 5, 6},
            {5, 6, 7},
            {8, 9, 10},
            {9, 10, 11},
            {12, 13, 14},
            {13, 14, 15},
            {0, 4, 8},
            {4, 8, 12},
            {1, 5, 9},
            {5, 9, 13},
            {2, 6, 10},
            {6, 10, 14},
            {3, 7, 11},
            {7, 11, 15}
    };

    final static int[][] shipsFourSize5X5 = new int[][]{
            {0, 1, 2, 3},
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {6, 7, 8, 9},
            {10, 11, 12, 13},
            {11, 12, 13, 14},
            {15, 16, 17, 18},
            {16, 17, 18, 19},
            {20, 21, 22, 23},
            {21, 22, 23, 24},
            {0, 5, 10, 15},
            {5, 10, 15, 20},
            {1, 6, 11, 16},
            {6, 11, 16, 21},
            {2, 7, 12, 17},
            {7, 12, 17, 22},
            {3, 8, 13, 18},
            {8, 13, 18, 23},
            {4, 9, 14, 19},
            {9, 14, 19, 24}
    };

    public void ratation(){

        int randShip;
        int sizeOfShip;
        boolean isEqualPos ;
        int[][] shipList1=null;
        Random rand = new Random();
        //Ships[0].setIsSinking();
        for(int i=0; i<Ships.length;i++) {
            if (!Ships[i].getIsSinking()) {
                sizeOfShip = Ships[i].getSize();
                if (sizeOfShip == 3) {
                    if (BoardSize == BOARD_SIZE_3x3) {
                        shipList1 = shipsThreeSize3X3;
                    } else if (BoardSize == BOARD_SIZE_4x4) {
                        shipList1 = shipsThreeSize4X4;
                    }
                } else if (sizeOfShip == 2) {
                    shipList1 = shipsTwoSize3X3;
                } else {
                    shipList1 = shipsFourSize5X5;
                }
                Ship tempShip = null;
                do {
                    isEqualPos = false;
                    randShip = rand.nextInt((shipList1.length - 1));
                    tempShip = new Ship(shipList1[randShip], shipList1[randShip].length);
                    for (int k = 0; k < Ships[2 - 1 - i].getCoordinates().length; k++) {
                        for (int j = 0; j < tempShip.getCoordinates().length; j++) {
                            if (Ships[2 - 1 - i].getCoordinates()[k] == tempShip.getCoordinates()[j]) {
                                isEqualPos = true;
                                break;
                            }
                            if (isEqualPos)
                                break;
                        }
                        if (isEqualPos)
                            break;
                    }
                } while (isEqualPos);

                deleteShips(Ships[i]);
                Ships[i] = tempShip;
                deleteShips(Ships[i]);
              //  showShips(Ships[i]);

            }
        }
    }
    public void hiting() {
        Random rand = new Random();
        boolean isPossible=false;
        int randomNum,randomNum2;
        randomNum = rand.nextInt(1);
        do {


            randomNum2 = rand.nextInt(Ships[randomNum].getSize());
                 if (mTiles[Ships[randomNum].getCoordinates()[randomNum2]].getStatus() == TileState.Ship) {
                     mTiles[Ships[randomNum].getCoordinates()[randomNum2]].setStatus(TileState.HIT);
                     Log.i("HITTTTT", "HOTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTt");
                     isPossible = true;
                     checkSinking();
                 }
                 if (Ships[randomNum].getIsSinking()) {
                     randomNum = 2 - 1 - randomNum;
                 }
                 if(Ships[0].getIsSinking() && Ships[1].getIsSinking()){
                     isPossible=true;
                 }
        }while (!isPossible);
    }
    public void deleteShips(Ship ship) {
        for (int i = 0; i < ship.getCoordinates().length; i++)
            mTiles[ship.getCoordinates()[i]].setStatus(TileState.NONE);


    }

    public enum TileState {
        NONE, Miss,HIT,Sinking,Ship;

        @Override
        public String toString() {
            switch (this) {
                case NONE:
                default:
                    return "";
                case Miss:
                    return "Miss";
                case Sinking:
                    return "Sinking";
                case Ship:
                    return "Ship";
                case HIT:
                    return "Hit";

            }
        }
    }

    private Tile mTiles[];

    public Board(int sizeBoard, String boardType) {
        this.boardType = boardType;
        this.BoardSize = sizeBoard;
        this.mTiles = new Tile[BoardSize];
        for (int i = 0; i < BoardSize; i++) {
            mTiles[i] = new Tile();
        }
        boolean isEqualPos;
        int[][] shipList1, shipList2;

        if (sizeBoard == BOARD_SIZE_3x3) {
            shipList1 = shipsThreeSize3X3;
            shipList2 = shipsTwoSize3X3;
        } else if (sizeBoard == BOARD_SIZE_4x4) {
            shipList1 = shipsThreeSize4X4;
            shipList2 = shipsThreeSize4X4;
        } else {
            shipList1 = shipsFourSize5X5;
            shipList2 = shipsFourSize5X5;
        }


        int randShip1 = rand.nextInt((shipList1.length - 1));
        int randShip2;
        do {
            isEqualPos = false;
            randShip2 = rand.nextInt((shipList2.length - 1));

            for (int i = 0; i < shipList1[randShip1].length; i++) {
                for (int j = 0; j < shipList2[randShip2].length; j++) {
                    if (shipList1[randShip1][i] == shipList2[randShip2][j]) {
                        isEqualPos = true;
                        break;
                    }
                    if (isEqualPos)
                        break;
                }
            }
        } while (isEqualPos);

        Ships[0] = new Ship(shipList1[randShip1], shipList1[randShip1].length);
        Ships[1] = new Ship(shipList2[randShip2], shipList2[randShip2].length);
        if (boardType.equals("computer")) {
            showShips(Ships[0]);
            showShips(Ships[1]);

        }
    }

    public int getBoardSize() {
        return mTiles.length;
    }

    public Tile getTile(int position) {
        return mTiles[position];
    }


    public boolean setTile(int position) {
        if (mTiles[position].getStatus() == TileState.HIT || mTiles[position].getStatus() == TileState.Miss|| mTiles[position].getStatus() == TileState.Sinking)
            return false;

        for (int i = 0; i < Ships.length; i++) {
            for (int j = 0; j < Ships[i].getCoordinates().length; j++) {
                if (Ships[i].getCoordinates()[j] == position) {
                    mTiles[position].setStatus(TileState.HIT);
                    return true;
                }
            }
        }
        if (mTiles[position].getStatus() == TileState.NONE) {
            mTiles[position].setStatus(TileState.Miss);
            return true;
        }
        return false;
    }


    public void showShips(Ship ship) {
        for (int i = 0; i < ship.getCoordinates().length; i++)
            mTiles[ship.getCoordinates()[i]].setStatus(TileState.Ship);


    }

    public void checkSinking() {
        boolean check = true;

        for (int i = 0; i < Ships.length; i++) {
            check = true;
            for (int j = 0; j < Ships[i].getCoordinates().length; j++) {
                if (mTiles[Ships[i].getCoordinates()[j]].getStatus() != TileState.HIT) {
                    check = false;
                    break;
                }
            }
            if (check) {
                for (int j = 0; j < Ships[i].getCoordinates().length; j++) {
                    mTiles[Ships[i].getCoordinates()[j]].setStatus(TileState.Sinking);

                }
                Ships[i].setIsSinking();
            }


        }
    }
    public void hit()
    {

    }
    public boolean gameWon() {

        for (int i = 0; i < Ships.length; i++) {
            if (!Ships[i].getIsSinking()) {
                return false;
            }
        }
        return true;
    }
}

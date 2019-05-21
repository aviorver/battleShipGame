package com.example.guy.gamebattleship;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.guy.gamebattleship.Logic.Board;


public class TileAdapter extends BaseAdapter {


    private Context mContext;
    private Board mBoard;
    private int sizeOfBoard;

    public TileAdapter(Context context, Board board, int sizeOfBoard) {
        this.mBoard = board;
        this.mContext = context;
        this.sizeOfBoard = sizeOfBoard;

    }

    @Override
    public int getCount() {
        return mBoard.getBoardSize();
    }

    @Override
    public Object getItem(int position) {
        return mBoard.getTile(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TileView tileView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
          Log.e("Tile Adapter", "Not RECYCLED");

            tileView = new TileView(mContext);
            if (sizeOfBoard == 3)
                tileView.setLayoutParams(new GridView.LayoutParams(340, 200));
            else if (sizeOfBoard == 4)
                tileView.setLayoutParams(new GridView.LayoutParams(250, 160));
            else
                tileView.setLayoutParams(new GridView.LayoutParams(200, 120));

            tileView.setPadding(8, 8, 8, 8);
        } else {
            tileView = (TileView) convertView;
            //tileView = new TileView(mContext);
            Log.e("Tile Adapter", "RECYCLE YAY!!!");

        }

        tileView.text.setText(mBoard.getTile(position).getStatus().toString());
        tileView.changeBackground();

        return tileView;
    }
}
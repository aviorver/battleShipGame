package com.example.guy.gamebattleship;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.guy.gamebattleship.Logic.*;



public class TileView extends LinearLayout {
     TextView text;
    private AnimationDrawable draw;

    public TileView(Context context) {
        super(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(layoutParams);
        this.setOrientation(VERTICAL);
        text = new TextView(context);
    }

    public void changeBackground() {
        if (text.getText().toString().equals(Board.TileState.Miss.toString())){
            this.setBackgroundResource(R.drawable.rain);
             draw = (AnimationDrawable) getBackground();
            draw.setOneShot(true);
            draw.start();

        }

        else if (text.getText().toString().equals(Board.TileState.Ship.toString()))
            this.setBackgroundResource(R.drawable.ship);
        else if (text.getText().toString().equals(Board.TileState.HIT.toString())) {
            this.setBackgroundResource(R.drawable.animation);
             draw = (AnimationDrawable) getBackground();
            draw.setOneShot(true);
            draw.start();
        } else if (text.getText().toString().equals(Board.TileState.Sinking.toString())) {
            this.setBackgroundResource(R.drawable.skull);
        } else
            this.setBackgroundColor(0xFF353535);


    }
}
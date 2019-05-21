package com.example.guy.gamebattleship;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

@SuppressLint("ValidFragment")
public class Main_Fragment extends Fragment {
    private int oldLevel;
    public Main_Fragment(int oldLevel) {
        this.oldLevel=oldLevel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_one,container,false);
            if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }
        try {
            v = inflater.inflate(R.layout.fragment_one, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }
        switch (oldLevel) {
            case 3: {
                RadioButton radioButton = v.findViewById(R.id.EasyButton);
                radioButton.setChecked(true);
                break;
            }
            case 4: {
                RadioButton radioButton = v.findViewById(R.id.MediumButton);
                radioButton.setChecked(true);
                break;
            }
            case 5: {
                RadioButton radioButton =( v.findViewById(R.id.ProButton));
                radioButton.setChecked(true);
                break;
            }
        }
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        onDestroyView();
    }

}
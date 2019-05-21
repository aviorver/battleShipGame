package com.example.guy.gamebattleship;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Sec_Fragment extends Fragment {
        private DatabaseHelper myDb;
        private  ArrayList<String> scores;
        View v;
        public Sec_Fragment(DatabaseHelper myDb)
        {
            this.myDb=myDb;
        }
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 final Bundle savedInstanceState)
        {
            myDb = new DatabaseHelper(getContext());
             v = inflater.inflate(R.layout.fragment_two,container,false);
            RadioGroup LevelGroup = v.findViewById(R.id.RadioButtonDifficultyScore);

            LevelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int scoreToGet=0;
                    switch (group.getCheckedRadioButtonId()) {
                        case R.id.EasyButtonScore: {
                            scoreToGet = 1;
                            break;
                        }
                        case R.id.MediumButtonScore: {
                            scoreToGet = 2;
                            break;
                        }
                        case R.id.ProButtonScore: {
                            scoreToGet = 3;
                            break;
                        }
                    }
                    Cursor res =myDb.getAllData(scoreToGet);
                        scores = new ArrayList<>();
                        while (res.moveToNext())
                        {
                            scores.add(getText(R.string.Name) +" "+res.getString(1)+"\t  "+getText(R.string.Score)+" "+res.getString(0));
                        }
                        ListView lv = v.findViewById(R.id.scorelist);
                        CustomAdapter customAdapter = new CustomAdapter();
                        lv.setAdapter(customAdapter);}
            });
            RadioButton radioButton = v.findViewById(R.id.EasyButtonScore);
            radioButton.setChecked(true);

            return v;
        }
            class CustomAdapter extends BaseAdapter
            {

                @Override
                public int getCount() {
                    return scores.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    convertView=getLayoutInflater().inflate(R.layout.list_item,null);
                    ImageView imageView=convertView.findViewById(R.id.imageView4);
                    TextView textView = convertView.findViewById(R.id.textid);
                    if(position<3)
                    imageView.setImageResource(R.drawable.pirate);
                    else if(position>=3 && position<=5)
                        imageView.setImageResource(R.drawable.pirate2);
                    else
                        imageView.setImageResource(R.drawable.sadpirate);
                    textView.setText(scores.get(position));
                    return convertView;
                }
            }
}

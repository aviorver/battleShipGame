
//Avior Vered - 204565105
//Guy Moreh  - 311120448

package com.example.guy.gamebattleship;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.example.guy.gamebattleship.Logic.*;
import com.luolc.emojirain.EmojiRainLayout;
import android.content.Intent;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements ServiceCallbacks {
    private DatabaseHelper myDb;
    private Game mGame;
    private ProgressBar prg;
    private TextView userText;
    private TextView compText;
    private final int MIN_LEVEL=3;
    private AlertDialog ad;
    private final int BOARD_SIZE_3x3=3;
    private final int BOARD_SIZE_4x4=4;
    private final int BOARD_SIZE_5x5=5;
    private final int MILL_SEC=300;
    private EditText ed;
    private Sec_Fragment fr2;
    private String playerName;
    private int turn=0;
    private Main_Fragment fr1;
    private int sizeOfBoard=0;
    private int oldLevel;
    private boolean check=false;
    private final String SHARED_PREFS = "sharedPrefs";
    private final String LEVEL_PREFS = "level";
    private SharedPreferences sharedPreferences ;
    private SharedPreferences.Editor editor;
    private MyService myService;
    private GridView computerGridView;
    private  GridView userGridView;
    private ImageView imageView;
    private boolean turnDone=true;
    private  ServiceConnection serviceConnection;
    public void changeFrament(View view){
         fr2= new Sec_Fragment(myDb);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fr2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        check=true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int oldLevel=MIN_LEVEL;
        sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        oldLevel=sharedPreferences.getInt(LEVEL_PREFS,MIN_LEVEL);
        fr1 = new Main_Fragment(oldLevel);
        myDb = new DatabaseHelper(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,new Main_Fragment(oldLevel));
        fragmentTransaction.commit();
        setContentView(R.layout.start_screen);
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //
        builder.setTitle(R.string.PersonData);
        builder.setIcon(R.drawable.ship);
        builder.setMessage(R.string.EnterName);
        ed = new EditText(this);
        builder.setView(ed);
        builder.setPositiveButton(R.string.Submit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 playerName = ed.getText().toString();
                if(playerName.length()>1) {
                    insertData(playerName, turn, sizeOfBoard);
                    Toast.makeText(getApplicationContext(),playerName,Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),R.string.Empty,Toast.LENGTH_SHORT).show();
            }
        });
         ad = builder.create();
    }

    @Override
    protected void onPause(){
        super.onPause();
 //       stopPlay();
    }

    public void onDestroy(){
        super.onDestroy();
        stopPlay();
    }
    public  void stopPlay(){
    myService.setIsDestroy(true);
    }

    public  void callPlay(){
         myService = new MyService();
        Intent intent = new Intent(MainActivity.this , myService.getClass());
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    public void setVibrate(){
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (v != null) {
            v.vibrate(MILL_SEC);
        }
    }


    public void Restart(View view) {
        oldLevel=sharedPreferences.getInt(LEVEL_PREFS,MIN_LEVEL);
        fr1 = new Main_Fragment(oldLevel);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,new Main_Fragment(oldLevel));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setContentView(R.layout.start_screen);

    }
    public void StartGame(View view) {
        turn=0;
        sizeOfBoard = BOARD_SIZE_3x3;
        RadioGroup LevelGroup = findViewById(R.id.RadioButtonDifficulty);
        switch (LevelGroup.getCheckedRadioButtonId()) {
            case R.id.EasyButton: {
                sizeOfBoard = BOARD_SIZE_3x3;
                break;
            }
            case R.id.MediumButton: {
                sizeOfBoard = BOARD_SIZE_4x4;
                break;
            }
            case R.id.ProButton: {
                sizeOfBoard = BOARD_SIZE_5x5;
                break;
            }
        }
        editor.putInt(LEVEL_PREFS,sizeOfBoard).apply();
        editor.commit();
        serviceConnection = new ServiceConnection() {

            @Override
             public void onServiceConnected(ComponentName name, IBinder service) {
                 MyService.MyLocalService binder = (MyService.MyLocalService) service;
                 myService= binder.getService();
                myService.setCallbacks(MainActivity.this); // register
                myService.updageSensor();
             }

             @Override
             public void onServiceDisconnected(ComponentName name) {
                serviceConnection=null;
             }
         };
        setContentView(R.layout.game_screen);
        imageView = findViewById(R.id.alertImage);
        prg = findViewById(R.id.progressBar);
        prg.setVisibility(View.INVISIBLE);
        userText = findViewById(R.id.computerBoardText);
        userText.setTextColor(Color.RED);
        compText = findViewById(R.id.userBoardText);
        computerGridView = findViewById(R.id.GridViewComputer);
        userGridView = findViewById(R.id.GridViewUser);
        computerGridView.setNumColumns(sizeOfBoard);
        userGridView.setNumColumns(sizeOfBoard);
        mGame = new Game(sizeOfBoard * sizeOfBoard);
        callPlay();
        computerGridView.setAdapter(new TileAdapter(this, mGame.getComputerBoard(), sizeOfBoard));
        userGridView.setAdapter(new TileAdapter(this, mGame.getUserBoard(), sizeOfBoard));
        userGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (mGame.getTurn()==1) {
                    setVibrate();
                    mGame.playTile(position);
                    turn++;

                    ((TileAdapter) userGridView.getAdapter()).notifyDataSetChanged();
                    if (mGame.gameWon(mGame.getUserBoard())) {
                        ad.show();
                        setContentView(R.layout.win_screen);
                        stopPlay();
                        check = false;
                        EmojiRainLayout em = findViewById(R.id.winning);
                        em.addEmoji(R.drawable.emoji_1_3);
                        em.addEmoji(R.drawable.emoji_4_3);
                        em.addEmoji(R.drawable.emoji_5_3);
                        em.addEmoji(R.drawable.emoji_2_3);
                        em.stopDropping();
                        em.setDuration(7200);
                        em.setPer(10);
                        em.setDropFrequency(500);
                        em.setDropDuration(2400);
                        em.startDropping();
                        //TODO
                    }
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mGame.playComputer();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((TileAdapter) computerGridView.getAdapter()).notifyDataSetChanged();
                                    if (mGame.gameWon(mGame.getComputerBoard())) {
                                        setContentView(R.layout.lose_screen);
                                        myService.stopSelf();
                                        stopPlay();
                                    }
                                    prg.setVisibility(View.INVISIBLE);
                                    compText.setTextColor(Color.DKGRAY);
                                    userText.setTextColor(Color.RED);
                                }
                            });

                        }
                        //         });
                        //        }
                    });
                    userText.setTextColor(Color.DKGRAY);
                    compText.setTextColor(Color.RED);
                    prg.setVisibility(View.VISIBLE);
                    t.start();

                }
            }
            });


    }
    public Boolean insertData(String name,int Score,int BoardSize){
        return myDb.insertData(BoardSize,Score,name);
    }

    @Override
    public void Moving() {
        Toast.makeText(getApplicationContext(),R.string.Rotate, Toast.LENGTH_SHORT).show();
        userGridView.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);

        mGame.rotation();
        ((TileAdapter) userGridView.getAdapter()).notifyDataSetChanged();
        ((TileAdapter) computerGridView.getAdapter()).notifyDataSetChanged();
        if (mGame.gameWon(mGame.getComputerBoard())) {
            setContentView(R.layout.lose_screen);
            myService.stopSelf();
            stopPlay();
        }
    }

    @Override
    public void show() {
        imageView.setVisibility(View.INVISIBLE);
        userGridView.setVisibility(View.VISIBLE);
    }
}

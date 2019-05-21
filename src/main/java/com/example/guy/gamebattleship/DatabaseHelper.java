package com.example.guy.gamebattleship;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ScoreDB2.db";
    public static final String TABLE_NAME3= "score3_table";
    public static final String TABLE_NAME4 = "score4_table";
    public static final String TABLE_NAME5 = "score5_table";
    public static final String COL_1 = "Score";
    public static final String COL_2 = "Name";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME3 +" (SCORE INTEGER,NAME TEXT)");
        db.execSQL("create table " + TABLE_NAME4 +" (SCORE INTEGER,NAME TEXT)");
        db.execSQL("create table " + TABLE_NAME5 +" (SCORE INTEGER,NAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME5);
        onCreate(db);
    }
    public boolean insertData(int BoardSize,int Score,String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Score);
        contentValues.put(COL_2,name);
        long result;
        if(BoardSize== 3){
             result = db.insert(TABLE_NAME3, null, contentValues);
        Log.i("333333333333","33333333");}
        else if (BoardSize == 4 ){
            Log.i("444444","444444");
        result = db.insert(TABLE_NAME4, null, contentValues);}
        else {
            Log.i("5555555555","555555555555555555555");
            result = db.insert(TABLE_NAME5, null, contentValues);}
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData(int scoreToGet) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=null;
        switch (scoreToGet) {
            case 1: {
                res = db.rawQuery("select * from score3_table ORDER by Score limit 10 ", null);
                break;
            }
            case 2: {
                res = db.rawQuery("select * from score4_table ORDER by Score limit 10 ", null);
                break;
            }
            case 3: {
                res = db.rawQuery("select * from score5_table ORDER by Score limit 10 ", null);
                break;
            }
        }
        return res;
    }
    public void deleteData () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME4);
        db.execSQL("delete from "+ TABLE_NAME3);
        db.execSQL("delete from "+ TABLE_NAME5);

    }

}

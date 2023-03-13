package com.example.mangchimaster;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";
    public static final String MOVIE_TABLE_NAME = "movie";
    public static final String MOVIE_COLUMN_ID = "id";
    public static final String MOVIE_COLUMN_NAME = "name";
    public static final String MOVIE_COLUMN_GIGAN = "gigan";
    public static final String MOVIE_COLUMN_THNAME = "thname";
    public static final String MOVIE_COLUMN_LOCATION = "location";
    public static final String MOVIE_COLUMN_WEDO = "wedo";
    public static final String MOVIE_COLUMN_KYEONGDO = "kyeongdo";
    public static final String MOVIE_COLUMN_RATING = "rating";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table movie " +
                        "(id integer primary key,name text, gigan text, thname text, location text , wedo text, kyeongdo text, rating text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS movie");
        onCreate(db);
    }

    public boolean insertMovie(String name, String gigan, String thname, String location, String wedo, String kyeongdo, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("gigan", gigan);
        contentValues.put("thname", thname);
        contentValues.put("location", location);
        contentValues.put("wedo", wedo);
        contentValues.put("kyeongdo", kyeongdo);
        contentValues.put("rating", rating);

        db.insert("movie", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movie where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MOVIE_TABLE_NAME);
        return numRows;
    }

    public boolean updateMovie(int id, String name, String gigan, String thname, String location, String wedo, String kyeongdo, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("gigan", gigan);
        contentValues.put("thname", thname);
        contentValues.put("location", location);
        contentValues.put("wedo", wedo);
        contentValues.put("kyeongdo", kyeongdo);
        contentValues.put("rating", rating);
        db.update("movie", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteMovie(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("movie",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList getAllMovie() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movie", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(""+res.getString(res.getColumnIndex(MOVIE_COLUMN_ID))+" 번   |     "+
                    res.getString(res.getColumnIndex(MOVIE_COLUMN_NAME))+"   |     "+res.getString(res.getColumnIndex(MOVIE_COLUMN_RATING))
                    +" 개    |    "+res.getString(res.getColumnIndex(MOVIE_COLUMN_THNAME))
                    +"    |    "+res.getString(res.getColumnIndex(MOVIE_COLUMN_LOCATION)));
            res.moveToNext();
        }
        return array_list;
    }
}

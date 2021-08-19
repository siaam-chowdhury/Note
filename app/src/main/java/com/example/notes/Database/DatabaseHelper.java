package com.example.notes.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.notes.Models.HeadingAndDescriptionModel;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String NEW_NOTE_TABLE = "NEW_NOTE_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NOTE_HEADING = "COLUMN_NOTE_HEADING";
    public static final String COLUMN_NOTE_DES = "COLUMN_NOTE_DES";
    public static final String COLUMN_NOTE_TIME = "COLUMN_NOTE_TIME";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newNote.db";



    Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + NEW_NOTE_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NOTE_HEADING + " TEXT, " + COLUMN_NOTE_DES + " TEXT, " + COLUMN_NOTE_TIME + " TEXT )";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public List<HeadingAndDescriptionModel> getALl() {

        List<HeadingAndDescriptionModel> noteStoreModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + NEW_NOTE_TABLE;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String heading = cursor.getString(1);
                String description = cursor.getString(2);
                String time = cursor.getString(3);

                HeadingAndDescriptionModel hdm = new HeadingAndDescriptionModel(id, heading, description, time);
                noteStoreModelList.add(hdm);

            }while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return noteStoreModelList;
    }



    public boolean addNote(HeadingAndDescriptionModel headingAndDescriptionModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOTE_HEADING, headingAndDescriptionModel.getHeading());
        cv.put(COLUMN_NOTE_DES, headingAndDescriptionModel.getDescription());
        cv.put(COLUMN_NOTE_TIME, headingAndDescriptionModel.getTime());

        long insert = db.insert(NEW_NOTE_TABLE, null, cv);
        return insert != -1;
    }


    public boolean deleteNote(HeadingAndDescriptionModel headingAndDescriptionModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "DELETE FROM " + NEW_NOTE_TABLE + " WHERE " + COLUMN_ID + " = " + headingAndDescriptionModel.getId();

        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }









}

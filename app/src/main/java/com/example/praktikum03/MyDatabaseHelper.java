package com.example.praktikum03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "pendataanbuku.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "book";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_PUBLISHER = "book_publisher";
    private static final String COLUMN_TOTAL = "book_total";
    private static final String COLUMN_YEAR = "book_year";
    private static final String COLUMN_TYPE = "book_type";
    private static final String COLUMN_GENRE = "book_genre";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PUBLISHER + " TEXT, " +
                COLUMN_YEAR + " INTEGER, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_GENRE + " TEXT, " +
                COLUMN_TOTAL + " INTEGER);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    long addBook(String title, String author, int total, String publisher, String type, String genre, int year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_TOTAL, total);
        cv.put(COLUMN_PUBLISHER, publisher);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_GENRE, genre);
        cv.put(COLUMN_YEAR, year);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();

        }
        return result;
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor searchData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //String qry = "SELECT * FROM "+TABLE_NAME+" WHERE ID="+id;
        Integer in = Integer.parseInt(id);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE _id="+in,null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }
    void updateData(String row_id, String title, String author, String total ,String publisher, String type, String genre, int year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_TOTAL, total);
        cv.put(COLUMN_PUBLISHER, publisher);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_GENRE, genre);
        cv.put(COLUMN_YEAR, year);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}

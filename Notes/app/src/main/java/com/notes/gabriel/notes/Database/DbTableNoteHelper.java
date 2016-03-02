package com.notes.gabriel.notes.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by fefochico on 22/02/2016.
 */
public class DbTableNoteHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME= "table_notes";

    public static final String COLUMN_ID= "id";
    public static final String COLUMN_DATE= "date";
    public static final String COLUMN_TITLE= "title";
    public static final String COLUMN_NOTE= "note";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_DATE + " text not null, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_NOTE + " text not null);";

    private static final String DATABASE_NAME= "db_notes.db";
    private static final int DATABASE_VERSION=3;


    public DbTableNoteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(DbTableNoteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }
}

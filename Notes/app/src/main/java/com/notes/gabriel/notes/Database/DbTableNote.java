package com.notes.gabriel.notes.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.notes.gabriel.notes.ObjectNote;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by fefochico on 22/02/2016.
 */
public class DbTableNote {
    private static final String DATABASE_NAME ="DbTableNote";
    public static final String TABLE_NAME= "table_notes";

    String[] mAllColumns = {
            DbTableNoteHelper.COLUMN_ID,
            DbTableNoteHelper.COLUMN_DATE,
            DbTableNoteHelper.COLUMN_TITLE,
            DbTableNoteHelper.COLUMN_NOTE
    };

    private SQLiteDatabase mDatabase;
    private DbTableNoteHelper mDbHelper;

    public DbTableNote (Context context){
        mDbHelper = new DbTableNoteHelper(context);
    }

    public void openW() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void openR(){
        mDatabase = mDbHelper.getReadableDatabase();
    }

    public void close(){
        if(mDbHelper!=null){
            mDbHelper.close();
        }
        if(mDatabase !=null && mDatabase.isOpen()){
            mDatabase.close();
        }
    }

    public boolean isOpen(){
        if(mDatabase!=null){
            return mDatabase.isOpen();
        }else{
            return false;
        }

    }

    public long addNote(ObjectNote objectNote){
        ContentValues values = new ContentValues();
        values.put(DbTableNoteHelper.COLUMN_DATE, objectNote.date);
        values.put(DbTableNoteHelper.COLUMN_TITLE, objectNote.title);
        values.put(DbTableNoteHelper.COLUMN_NOTE, objectNote.note);

        if(mDatabase!=null) {
            return mDatabase.insert(DbTableNoteHelper.TABLE_NAME, null, values);
        }else{
            return -1;
        }
    }

    public boolean deleteNote(long id){
        return mDatabase.delete(TABLE_NAME, DbTableNoteHelper.COLUMN_ID + "=" + id ,null)> 0;
    }

    public ArrayList<ObjectNote> getAllNotes() {
        ArrayList<ObjectNote> list= new ArrayList<ObjectNote>();
        Cursor c=  mDatabase.query(mDbHelper.TABLE_NAME,mAllColumns,null,null,null,null,null);;

        if(c!=null){
            c.moveToFirst();
            while(!c.isAfterLast()){
                ObjectNote objectNote= new ObjectNote();
                objectNote.id= c.getLong(0);
                objectNote.date= c.getString(1);
                objectNote.title= c.getString(2);
                objectNote.note= c.getString(3);
                list.add(objectNote);
                c.moveToNext();
            }
        }

        close();
        return list;
    }

}

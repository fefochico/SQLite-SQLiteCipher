package com.notes.gabriel.notes.DatabaseCipher;

import android.content.ContentValues;
import android.content.Context;

import com.notes.gabriel.notes.ObjectNote;

import net.sqlcipher.Cursor;
import net.sqlcipher.DatabaseUtils;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by fefochico on 01/03/2016.
 */
public class DbTableNoteCipher {
    private static final String DATABASE_NAME ="DbTableNote_Cipher";
    public static final String TABLE_NAME= "table_notes_cipher";

    String[] mAllColumns = {
            DbTableNoteCipherHelper.COLUMN_ID,
            DbTableNoteCipherHelper.COLUMN_DATE,
            DbTableNoteCipherHelper.COLUMN_TITLE,
            DbTableNoteCipherHelper.COLUMN_NOTE
    };

    private SQLiteDatabase mDatabase;
    private DbTableNoteCipherHelper mDbHelper;
    private String password;

    public DbTableNoteCipher (Context context, String password){
        mDbHelper = new DbTableNoteCipherHelper(context);
        this.password= password;
    }

    public void openW() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase(password);
    }

    public void openR()throws SQLException {
        mDatabase = mDbHelper.getReadableDatabase(password);
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
        mDatabase = mDbHelper.getWritableDatabase(password);
        ContentValues values = new ContentValues();
        values.put(DbTableNoteCipherHelper.COLUMN_DATE, objectNote.date);
        values.put(DbTableNoteCipherHelper.COLUMN_TITLE, objectNote.title);
        values.put(DbTableNoteCipherHelper.COLUMN_NOTE, objectNote.note);

        if(mDatabase!=null) {
            return mDatabase.insert(DbTableNoteCipherHelper.TABLE_NAME, null, values);
        }else{
            return -1;
        }
    }

    public boolean deleteNote(long id){
        return mDatabase.delete(TABLE_NAME, DbTableNoteCipherHelper.COLUMN_ID + "=" + id ,null)> 0;
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

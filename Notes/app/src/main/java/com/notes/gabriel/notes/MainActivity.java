package com.notes.gabriel.notes;

import android.content.Context;
import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.notes.gabriel.notes.Database.DbTableNote;
import com.notes.gabriel.notes.DatabaseCipher.DbTableNoteCipher;
import net.sqlcipher.database.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private CustomAdapter customAdapter;
    //private DbTableNote dbTableNote;
    private DbTableNoteCipher dbTableNote;
    private ArrayList<ObjectNote> list;

    private void instantBD(int bd){
        if(bd==1){
            //dbTableNote= new DbTableNote(getApplicationContext());
        }else{
            SQLiteDatabase.loadLibs(getApplicationContext());
            dbTableNote= new DbTableNoteCipher(getApplicationContext(), "clave");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent intent= new Intent(this, AddNoteActivity.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent, 1);
            }
        });

        listView= (ListView)findViewById(R.id.lvList);
        customAdapter= new CustomAdapter(getApplicationContext());

        instantBD(2);

        dbTableNote.openR();
        list= dbTableNote.getAllNotes();
        dbTableNote.close();
        for(ObjectNote objectNote : list){
            customAdapter.add(objectNote);
        }
        final Context context= this;
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentAux= new Intent(context, ViewNoteActivity.class);
                intentAux.putExtra("title", list.get(position).title);
                intentAux.putExtra("note", list.get(position).note);
                startActivity(intentAux);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                customAdapter.delete(position);
                dbTableNote.openW();
                dbTableNote.deleteNote(list.get(position).id);
                dbTableNote.close();
                list.remove(position);
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK){
            String title= data.getStringExtra("title");
            String note= data.getStringExtra("note");
            String date= getDatePhone();

            ObjectNote objectNote= new ObjectNote();
            objectNote.date=date;
            objectNote.title=title;
            objectNote.note=note;
            addNote(objectNote);
        }
    }

    private void addNote(ObjectNote objectNote){
        dbTableNote.openW();
        dbTableNote.addNote(objectNote);
        dbTableNote.close();
        customAdapter.addNew(objectNote);
        list.add(objectNote);
    }

    private String getDatePhone(){
        Calendar cal= new GregorianCalendar();
        Date date= cal.getTime();
        SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
        String formatteDate= df.format(date);
        return formatteDate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.notes.gabriel.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by fefochico on 28/02/2016.
 */
public class ViewNoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_note_layout);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String title = extras.getString("title");
            String note = extras.getString("note");

            TextView tv=(TextView)findViewById(R.id.tvNoteView);
            tv.setText(note);

            ActionBar actionBar= this.getSupportActionBar();
            actionBar.setTitle(title);
        }
    }
}

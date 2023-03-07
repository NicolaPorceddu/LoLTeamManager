package com.example.bacci.lolteammanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotePreviewActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_preview);
        Intent intent = getIntent();
        /********** INIZIO CODICE TOOLBAR / DRAWER LAYOUT **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR / DRAWER LAYOUT **********/

        TextView title = (TextView) findViewById(R.id.title);
        TextView note = (TextView) findViewById(R.id.note);

        if (intent != null) {
            MyEventDay event = intent.getParcelableExtra(Calendario.EVENT);

            if(event.getNote() != null){
                title.setText(getFormattedDate(event.getCalendar().getTime()));
                note.setText(event.getNote());
            } else {
                title.setText(getFormattedDate(((EventDay)event).getCalendar().getTime()));
            }
        }
    }

    /********** INIZIO METODI TOOLBAR / DRAWER LAYOUT **********/
    private void setToolbar(Toolbar tb){
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    /********** FINE METODI TOOLBAR / DRAWER LAYOUT **********/

    public static String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }
}

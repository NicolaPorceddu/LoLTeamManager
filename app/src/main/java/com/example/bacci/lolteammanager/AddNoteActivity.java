package com.example.bacci.lolteammanager;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.applandeo.materialcalendarview.CalendarView;

import java.util.ArrayList;

public class AddNoteActivity extends AppCompatActivity {
    Toolbar toolbar;
    User player;
    ArrayList<MyEventDay> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Intent i = getIntent();
        player = UserFactory.getInstance().getUtenteByID(i.getExtras().getInt("currentUserID"));
        eventList = TeamFactory.getInstance().getTeamById(player.getIdSquadra()).getEventsList();

        final CalendarView datePicker = (CalendarView) findViewById(R.id.datePicker);
        Button button = (Button) findViewById(R.id.addNoteButton);
        final EditText noteEditText = (EditText) findViewById(R.id.noteEditText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                Intent returnIntent = new Intent();

                MyEventDay myEventDay = new MyEventDay(datePicker.getSelectedDate(),
                        R.drawable.ic_note, noteEditText.getText().toString());


                for(MyEventDay day : eventList){
                    if (day.getCalendar().equals(myEventDay.getCalendar())) {
                        eventList.remove(day);
                        flag = true;
                    }
                }
                sendNotification(myEventDay);

                eventList.add(myEventDay);
                returnIntent.putExtra(Calendario.RESULT, myEventDay);
                setResult(Activity.RESULT_OK, returnIntent);
                returnIntent.putExtra("FLAG", flag);
                returnIntent.putExtra("currentUserID", player.getIdPlayer());
                returnIntent.putExtra("notMePlayer", player.getIdPlayer());
                finish();
            }
        });
        /********** INIZIO CODICE TOOLBAR / DRAWER LAYOUT **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR / DRAWER LAYOUT **********/
    }

    public void sendNotification(MyEventDay day) {
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, "notify_001");

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(day.getNote());
        bigText.setBigContentTitle(NotePreviewActivity.getFormattedDate(day.getCalendar().getTime()));
        bigText.setSummaryText("Nuova nota aggiunta al calendario!");

        mBuilder.setSmallIcon(R.mipmap.ic_logo);
        mBuilder.setContentTitle(NotePreviewActivity.getFormattedDate(day.getCalendar().getTime()));
        mBuilder.setContentText(day.getNote());
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
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

        MenuItem imp = menu.findItem(R.id.settings);
        MenuItem add = menu.findItem(R.id.add_friend);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    /********** FINE METODI TOOLBAR / DRAWER LAYOUT **********/
}

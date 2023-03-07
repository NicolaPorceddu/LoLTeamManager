package com.example.bacci.lolteammanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.List;

public class Calendario extends AppCompatActivity {
    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private static final int ADD_NOTE = 44;

    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();
    Toolbar toolbar;
    User player;
    ArrayList<MyEventDay> eventList;

    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/
    LinearLayout homepage, msg, friends;
    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        /********** INIZIO CODICE TOOLBAR / DRAWER LAYOUT **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR / DRAWER LAYOUT **********/

        homepage = findViewById(R.id.bottomHomeProfile);
        msg = findViewById(R.id.bottomMsgProfile);
        friends = findViewById(R.id.bottomFriendsProfile);

        Intent i = getIntent();
        player = UserFactory.getInstance().getUtenteByID(i.getExtras().getInt("currentUserID"));
        eventList = TeamFactory.getInstance().getTeamById(player.getIdSquadra()).getEventsList();
        boolean flag = false;
        ArrayList<Team> teamList = TeamFactory.getTeamList();

        for(Team t : teamList)
            if(t.getCaptain().getIdPlayer() == player.getIdPlayer())
                flag = true;

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        for(MyEventDay day : eventList){
            try {
                mCalendarView.setDate(day.getCalendar());
            } catch (OutOfDateRangeException e) {
                e.printStackTrace();
            }
            mEventDays.add(day);
        }
        mCalendarView.setEvents(mEventDays);


        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        if(flag==false)
            floatingActionButton.hide();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                previewNote(eventDay);
            }
        });

        /* GESTIONE BOTTOM NAV */
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Calendario.this, Home.class);
                home.putExtra("currentUserID", player.getIdPlayer());
                startActivity(home);
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendsList = new Intent(Calendario.this, FriendsList.class);
                friendsList.putExtra("currentUserID", player.getIdPlayer());
                friendsList.putExtra("notMePlayer", player.getIdPlayer());
                startActivity(friendsList);
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent(Calendario.this, ListaChat.class);
                chat.putExtra("currentUserID", player.getIdPlayer());
                chat.putExtra("notMePlayer", player.getIdPlayer());
                startActivity(chat);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
            Intent i = getIntent();
            if(i.getExtras().getBoolean("FLAG")){
                mEventDays = new ArrayList<>();
                for(MyEventDay day : eventList){
                    try {
                        mCalendarView.setDate(day.getCalendar());
                    } catch (OutOfDateRangeException e) {
                        e.printStackTrace();
                    }
                    mEventDays.add(day);
                }
            }

            mEventDays = new ArrayList<>();
            for(MyEventDay day : eventList) {
                mEventDays.add(day);
                try {
                    mCalendarView.setDate(day.getCalendar());
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
            }

            mCalendarView.setEvents(mEventDays);
        }
    }

    private void addNote() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra("currentUserID", player.getIdPlayer());
        intent.putExtra("notMePlayer", player.getIdPlayer());
        startActivityForResult(intent, ADD_NOTE);
    }

    private void previewNote(EventDay eventDay) {
        Intent intent = new Intent(this, NotePreviewActivity.class);
        if(eventDay instanceof MyEventDay){
            intent.putExtra(EVENT, (MyEventDay) eventDay);
        }
        else{
            intent.putExtra(EVENT,  new MyEventDay(eventDay.getCalendar(),0,null));
        }
        startActivity(intent);
    }
}

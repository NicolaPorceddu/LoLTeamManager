package com.example.bacci.lolteammanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TeamMembers extends AppCompatActivity{
    /********** INIZIO DICHIRAZIONI TOOLBAR / DRAWER LAYOUT **********/
    Toolbar toolbar;
    /********** FINE DICHIRAZIONI TOOLBAR / DRAWER LAYOUT **********/

    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/
    LinearLayout homepage, msg;
    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/

    RecyclerView recycleMember;

    UserFactory uf = UserFactory.getInstance();
    TeamFactory tf = TeamFactory.getInstance();
    TextView teamInfo;
    Team t;
    User player, notMePlayer;
    static User p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_members);

        /********** INIZIO CODICE TOOLBAR**********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR **********/

        homepage = findViewById(R.id.bottomHomeTeam);
        msg = findViewById(R.id.bottomMsgTeam);

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        p = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        notMePlayer = uf.getUtenteByID(i.getExtras().getInt("notMePlayer"));
        t = tf.getTeamById(i.getExtras().getInt("teamID"));

        teamInfo = findViewById(R.id.teamInfo);

        String info = t.getNome() + "\nCapitano\n" + t.getCaptain().getInGameName();
        teamInfo.setText(info);

        ArrayList<User> memberList = t.getPlayers();

        LinearLayoutManager layoutMngr = new LinearLayoutManager(this);
        recycleMember = findViewById(R.id.recycleMember);
        recycleMember.setHasFixedSize(true);
        recycleMember.setLayoutManager(layoutMngr);

        Intent callerClass = new Intent(this, AdapterMembers.class);
        callerClass.putExtra("callerClass", 1);

        AdapterMembers adapter = new AdapterMembers(memberList, callerClass);
        recycleMember.setAdapter(adapter);

        /* GESTIONE BOTTOM NAV */
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(TeamMembers.this, Home.class);
                home.putExtra("currentUserID", player.getIdPlayer());
                startActivity(home);
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!player.equals(notMePlayer)){
                    Intent chat = new Intent(TeamMembers.this, Chat.class);
                    chat.putExtra("currentUserID", player.getIdPlayer());
                    chat.putExtra("notMePlayer", t.getCaptain().getIdPlayer());
                    startActivity(chat);
                } else {
                    Intent chat = new Intent(TeamMembers.this, ListaChat.class);
                    chat.putExtra("currentUserID", player.getIdPlayer());
                    chat.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                    startActivity(chat);
                }
            }
        });
    }

    /********** INIZIO METODI TOOLBAR **********/

    private void setToolbar(android.support.v7.widget.Toolbar tb) {
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
    /********** FINE METODI TOOLBAR **********/
}

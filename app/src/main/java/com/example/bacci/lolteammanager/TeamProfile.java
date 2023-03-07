package com.example.bacci.lolteammanager;

/********** INIZIO IMPORT TOOLBAR / DRAWER LAYOUT **********/
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
/********** FINE DICHIRAZIONI TOOLBAR / DRAWER LAYOUT **********/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TeamProfile extends AppCompatActivity {
    /********** INIZIO DICHIRAZIONI TOOLBAR **********/
    Toolbar toolbar;
    /********** FINE DICHIRAZIONI TOOLBAR **********/

    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/
    LinearLayout homepage, msg, memberList;
    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/

    int callerClass;

    UserFactory uf = UserFactory.getInstance();
    TeamFactory tf = TeamFactory.getInstance();
    LinearLayout members;
    User player, notMePlayer;
    Team t;
    TextView stats, teamInfo, infoChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_profile);

        /********** INIZIO CODICE TOOLBAR **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR **********/

        homepage = findViewById(R.id.bottomHomeTeam);
        msg = findViewById(R.id.bottomMsgTeam);
        memberList = findViewById(R.id.bottomMembersTeam);

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        notMePlayer = uf.getUtenteByID(i.getExtras().getInt("notMePlayer"));

        callerClass = i.getExtras().getInt("callerClass");

        members = findViewById(R.id.membersLayout);

        if(player.equals(notMePlayer)){
            setTeamInfo(members, player.getIdSquadra());
            setLanguages(player.getIdSquadra());
        }
        else{
            setTeamInfo(members, notMePlayer.getIdSquadra());
            setLanguages(notMePlayer.getIdSquadra());
        }

        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teamMembers = new Intent(TeamProfile.this, TeamMembers.class);
                teamMembers.putExtra("teamID", t.getIdTeam());
                teamMembers.putExtra("currentUserID", player.getIdPlayer());
                teamMembers.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                startActivity(teamMembers);
            }
        });

        /* GESTIONE BOTTOM NAV */
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(TeamProfile.this, Home.class);
                home.putExtra("currentUserID", player.getIdPlayer());
                startActivity(home);
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!player.equals(notMePlayer)){
                    Intent chat = new Intent(TeamProfile.this, Chat.class);
                    chat.putExtra("currentUserID", player.getIdPlayer());
                    chat.putExtra("notMePlayer", t.getCaptain().getIdPlayer());
                    startActivity(chat);
                } else {
                    Intent chat = new Intent(TeamProfile.this, ListaChat.class);
                    chat.putExtra("currentUserID", player.getIdPlayer());
                    chat.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                    startActivity(chat);
                }
            }
        });

        memberList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent members = new Intent(TeamProfile.this, TeamMembers.class);
                members.putExtra("currentUserID", player.getIdPlayer());
                members.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                members.putExtra("teamID", player.getIdSquadra());
                startActivity(members);
            }
        });
    }

    /********** INIZIO METODI TOOLBAR **********/

    private void setToolbar(Toolbar tb){
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_team, menu);

        MenuItem imp = menu.findItem(R.id.settings);
        MenuItem impMembers = menu.findItem(R.id.settings_members);
        MenuItem ann = menu.findItem(R.id.announce);
        if(t.getCaptain().equals(player)){
            imp.setVisible(true);
            impMembers.setVisible(true);
        }

        if(t.getCaptain().equals(player) && t.getnPlayers() < 5)
            ann.setVisible(true);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            switch(callerClass){
                case 0:
                case 5:
                case 6: System.out.println(callerClass);
                    Intent i = new Intent(TeamProfile.this, Home.class);
                    i.putExtra("currentUserID", player.getIdPlayer());
                    startActivity(i);
                    break;
                default: finish(); break;
            }
        }

        if (item.getItemId() ==  R.id.settings){
            Intent edit = new Intent(TeamProfile.this, EditTeam.class);
            edit.putExtra("sizeLingue", t.getLingue().size());
            edit.putExtra("sizeVoiceChat", t.getVoiceChat().size());
            edit.putExtra("currentUserID", player.getIdPlayer());
            edit.putExtra("notMePlayer", player.getIdPlayer());
            startActivity(edit);
        }

        if(item.getItemId() == R.id.settings_members){
            Intent edit = new Intent(TeamProfile.this, EditMembers.class);
            edit.putExtra("currentUserID", player.getIdPlayer());
            edit.putExtra("notMePlayer", player.getIdPlayer());
            startActivity(edit);
        }

        if(item.getItemId() == R.id.announce){
            Intent ann = new Intent(TeamProfile.this, InserimentoAnnuncio.class);
            ann.putExtra("currentUserID", player.getIdPlayer());
            ann.putExtra("notMePlayer", player.getIdPlayer());
            startActivity(ann);
        }

        return super.onOptionsItemSelected(item);
    }

    /********** FINE METODI TOOLBAR **********/

    /********** INIZIO METODI RIEMPIMENTO TEAM INFO **********/

    private LinearLayout setTeamInfo(LinearLayout members, int id){
        teamInfo = findViewById(R.id.teamInfo);
        t = tf.getTeamById(id);
        String info = t.getNome() + "\nCapitano\n" + t.getCaptain().getInGameName();
        teamInfo.setText(info);

        ArrayList<User> memberList = t.getPlayers();

        members.setClickable(true);
        members.removeAllViews();

        for(int j = 0; j < memberList.size(); j++){
            View view = getLayoutInflater().from(this).inflate(R.layout.textview_team_members, members, false);
            LinearLayout layoutMyMsg = findViewById(R.id.layoutMyMsg);
            TextView m = view.findViewById(R.id.member);
            m.setText(memberList.get(j).getRuoli().get(0) + ": " + memberList.get(j).getInGameName());
            members.addView(view);
        }

        stats = findViewById(R.id.stats);
        String statistiche = "Status: ";
        if(memberList.size()<5) statistiche = statistiche + "incompleta\n";
        else statistiche = statistiche +  "completa\n";

        if(!t.getIsNew()){
            Random win = new Random();
            Random lose = new Random();
            statistiche = statistiche + "Win: " + (win.nextInt(100)) + "\nLose: " + (lose.nextInt(100));
        } else {
            statistiche = statistiche + "Win: 0\nLose: 0";
        }

        statistiche = statistiche + "\nRank minimo: " + t.getRankMinMax().get(0) + "\nRank massimo: " + t.getRankMinMax().get(1);

        stats.setText(statistiche);

        return members;
    }

    private void setLanguages(int id){
        infoChat = findViewById(R.id.infoChat);
        t = tf.getTeamById(id);

        ArrayList<String> lingue = t.getLingue();
        ArrayList<String> voiceChat = t.getVoiceChat();

        String str = "Lingue: ";

        for(int j = 0; j < lingue.size(); j++){
            if (j == lingue.size()-1) str = str + lingue.get(j);
            else str = str + lingue.get(j) + ", ";
        }

        str = str + "\nVoiceChat: ";

        for(int j = 0; j < voiceChat.size(); j++){
            if (j == voiceChat.size()-1) str = str + voiceChat.get(j);
            else str = str + voiceChat.get(j) + ", ";
        }

        infoChat.setText(str);

    }

    /********** FINE METODI RIEMPIMENTO TEAM INFO **********/

    @Override
    public void onBackPressed() {
        switch(callerClass){
            case 0:
            case 5:
            case 6: System.out.println(callerClass);
                Intent i = new Intent(TeamProfile.this, Home.class);
                i.putExtra("currentUserID", player.getIdPlayer());
                startActivity(i);
                break;
            default: finish(); break;
        }
    }
}


package com.example.bacci.lolteammanager;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendsList extends AppCompatActivity {
    /********** INIZIO DICHIRAZIONI TOOLBAR / DRAWER LAYOUT **********/
    Toolbar toolbar;
    /********** FINE DICHIRAZIONI TOOLBAR / DRAWER LAYOUT **********/

    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/
    LinearLayout homepage, msg;
    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/

    RecyclerView recycleMember;
    LinearLayout errore;

    UserFactory uf = UserFactory.getInstance();
    TextView playerInfo;
    User player, notMePlayer;
    static User p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        /********** INIZIO CODICE TOOLBAR**********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR **********/

        homepage = findViewById(R.id.bottomHomeProfile);
        msg = findViewById(R.id.bottomMsgProfile);

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        p = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        notMePlayer = uf.getUtenteByID(i.getExtras().getInt("notMePlayer"));
        playerInfo = findViewById(R.id.playerInfo);

        errore = findViewById(R.id.errore);

        setPlayerInfo(player, notMePlayer);

        /* GESTIONE BOTTOM NAV */
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(FriendsList.this, Home.class);
                home.putExtra("currentUserID", player.getIdPlayer());
                startActivity(home);
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!player.equals(notMePlayer)) {
                    Intent chat = new Intent(FriendsList.this, Chat.class);
                    chat.putExtra("currentUserID", player.getIdPlayer());
                    chat.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                    startActivity(chat);
                } else {
                    Intent chat = new Intent(FriendsList.this, ListaChat.class);
                    chat.putExtra("currentUserID", player.getIdPlayer());
                    chat.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                    startActivity(chat);
                }
            }
        });
    }

    /********** INIZIO METODI TOOLBAR **********/
    private void setToolbar(android.support.v7.widget.Toolbar tb){
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    /********** FINE METODI TOOLBAR **********/

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void setPlayerInfo(final User player, final User notMePlayer){
        User p;

        if(player.equals(notMePlayer)) p = player;
        else p = notMePlayer;

        String userInfo = p.getInGameName() + "\nLv. " + p.getLivello() + " - " + p.getRank().toString() +
                " " + p.getDivisione() + "\n";
        ArrayList<Double> kdaList = p.getKDA();

        for(int j = 0; j < kdaList.size(); j++){
            if (j == kdaList.size()-1) userInfo = userInfo + kdaList.get(j).toString();
            else userInfo = userInfo + kdaList.get(j).toString() + " / ";
        }

        userInfo = userInfo + "\nEtà: " + p.getEta();

        playerInfo = findViewById(R.id.playerInfo);
        playerInfo.setText(userInfo);

        ArrayList<User> friendsList = p.getFriendList();

        if(friendsList.size() != 0) {
            LinearLayoutManager layoutMngr = new LinearLayoutManager(this);
            recycleMember = findViewById(R.id.recycleMember);
            recycleMember.setHasFixedSize(true);
            recycleMember.setLayoutManager(layoutMngr);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycleMember.getContext(),
                    layoutMngr.getOrientation());
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(recycleMember.getContext(), R.drawable.linea_separator));
            recycleMember.addItemDecoration(dividerItemDecoration);

            Intent callerClass = new Intent(this, AdapterMembers.class);
            callerClass.putExtra("callerClass", 2);

            AdapterMembers adapter = new AdapterMembers(friendsList, callerClass);
            recycleMember.setAdapter(adapter);
        } else errore.setVisibility(View.VISIBLE);
    }
}


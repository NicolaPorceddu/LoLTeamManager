package com.example.bacci.lolteammanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaChat extends AppCompatActivity {
    /********** INIZIO DICHIRAZIONI TOOLBAR **********/
    Toolbar toolbar;
    /********** FINE DICHIRAZIONI TOOLBAR **********/

    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/
    LinearLayout homepage, friends;
    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/

    LinearLayout layout, errore;
    UserFactory uf = UserFactory.getInstance();
    User player, notMePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_chat);

        /********** INIZIO CODICE TOOLBAR **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR **********/

        homepage = findViewById(R.id.bottomHomeProfile);
        friends = findViewById(R.id.bottomFriendsProfile);

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        notMePlayer = uf.getUtenteByID(i.getExtras().getInt("notMePlayer"));

        layout = findViewById(R.id.messageLayout);
        errore = findViewById(R.id.layoutError);

        ArrayList<User> users = player.getFriendList();

        if(users.size() != 0) {
            for (int j = 0; j < users.size(); j++) {
                User u = users.get(j);
                View l = getLayoutInflater().from(this).inflate(R.layout.textview_chat_list, layout, false);
                TextView name = l.findViewById(R.id.memberName);
                name.setText(users.get(j).getInGameName());
                TextView msg = l.findViewById(R.id.msg);
                msg.setText("Hey!");

                name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 10);
                l.setLayoutParams(params);

                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent chat = new Intent(ListaChat.this, Chat.class);
                        chat.putExtra("currentUserID", player.getIdPlayer());
                        chat.putExtra("notMePlayer", u.getIdPlayer());
                        startActivity(chat);
                    }
                });

                msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent chat = new Intent(ListaChat.this, Chat.class);
                        chat.putExtra("currentUserID", player.getIdPlayer());
                        chat.putExtra("notMePlayer", u.getIdPlayer());
                        startActivity(chat);
                    }
                });

                layout.addView(l);
            }
        } else errore.setVisibility(View.VISIBLE);

        /* GESTIONE BOTTOM NAV */
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(ListaChat.this, Home.class);
                home.putExtra("currentUserID", player.getIdPlayer());
                startActivity(home);
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendsList = new Intent(ListaChat.this, FriendsList.class);
                friendsList.putExtra("currentUserID", player.getIdPlayer());
                friendsList.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                startActivity(friendsList);
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

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    /********** FINE METODI TOOLBAR **********/
}

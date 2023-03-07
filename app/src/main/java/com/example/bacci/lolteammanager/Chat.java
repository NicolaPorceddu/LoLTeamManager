package com.example.bacci.lolteammanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Chat extends AppCompatActivity {
    /********** INIZIO DICHIRAZIONI TOOLBAR **********/
    Toolbar toolbar;
    /********** FINE DICHIRAZIONI TOOLBAR **********/
    Context context = this;
    UserFactory uf = UserFactory.getInstance();
    User player, notMePlayer;
    LinearLayout layout;
    EditText msg;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        /********** INIZIO CODICE TOOLBAR **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR **********/

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        notMePlayer = uf.getUtenteByID(i.getExtras().getInt("notMePlayer"));

        toolbar.setTitle(notMePlayer.getInGameName());
        toolbar.setTitleTextColor(Color.WHITE);

        layout = findViewById(R.id.layoutMsg);
        msg = findViewById(R.id.msg);
        send = findViewById(R.id.send);

        if(player.getMyMsg().size() == 0){
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(msg.getText().length() == 0 || msg.getText().toString() == null)) {
                        View view = getLayoutInflater().from(context).inflate(R.layout.textview_chat, layout, false);
                        TextView message = view.findViewById(R.id.myMsg);
                        message.setText(msg.getText().toString());
                        player.getMyMsg().add(new Message(notMePlayer.getInGameName(), msg.getText().toString()));
                        layout.addView(view);
                        msg.getText().clear();
                    }
                }
            });
        } else {
            for(int j = 0; j < player.getMyMsg().size(); j++){
                if(player.getMyMsg().get(j).getUsername().equals(notMePlayer.getInGameName())){
                    View v = getLayoutInflater().from(this).inflate(R.layout.textview_chat, layout, false);
                    TextView message = v.findViewById(R.id.myMsg);
                    message.setText(player.getMyMsg().get(j).getMsg());

                    layout.addView(v);

                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!(msg.getText().length() == 0 || msg.getText().toString() == null)) {
                                View view = getLayoutInflater().from(context).inflate(R.layout.textview_chat, layout, false);
                                TextView message = view.findViewById(R.id.myMsg);
                                message.setText(msg.getText().toString());
                                layout.addView(view);
                                msg.getText().clear();
                            }
                        }
                    });
                } else {
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!(msg.getText().length() == 0 || msg.getText().toString() == null)) {
                                View view = getLayoutInflater().from(context).inflate(R.layout.textview_chat, layout, false);
                                TextView message = view.findViewById(R.id.myMsg);
                                message.setText(msg.getText().toString());
                                player.getMyMsg().add(new Message(notMePlayer.getInGameName(), msg.getText().toString()));
                                layout.addView(view);
                                msg.getText().clear();
                            }
                        }
                    });
                }
            }
        }
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

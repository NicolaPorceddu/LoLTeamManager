package com.example.bacci.lolteammanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EditTeam extends AppCompatActivity {
    /********** INIZIO DICHIRAZIONI TOOLBAR **********/
    Toolbar toolbar;
    /********** FINE DICHIRAZIONI TOOLBAR **********/
    LinearLayout lingueLayout, voiceChatLayout;
    UserFactory uf = UserFactory.getInstance();
    TeamFactory tf = TeamFactory.getInstance();
    TextView teamInfo, addLingua, addVoiceChat;
    int currentSizeLingue, currentSizeVoiceChat;
    Team t;
    User player, notMePlayer;
    EditText nome;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);
        /********** INIZIO CODICE TOOLBAR **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR **********/
        final Context context = this;

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        notMePlayer = uf.getUtenteByID(i.getExtras().getInt("notMePlayer"));
        currentSizeLingue = i.getExtras().getInt("sizeLingue");
        currentSizeVoiceChat = i.getExtras().getInt("sizeVoiceChat");

        t = tf.getTeamById(player.getIdSquadra());

        nome = findViewById(R.id.teamName);
        lingueLayout = findViewById(R.id.lingueLayout);
        addLingua = findViewById(R.id.aggiungiLingua);
        voiceChatLayout = findViewById(R.id.voiceChatLayout);
        addVoiceChat = findViewById(R.id.aggiungiVoiceChat);
        save = findViewById(R.id.salva);

        setTeamInfo(t);

        ArrayList<String> lingue = t.getLingue();
        ArrayList<String> voiceChat = t.getVoiceChat();

        lingueLayout.removeAllViews();
        voiceChatLayout.removeAllViews();

        for(int j = 0; j < lingue.size(); j++){
            final int index = j;
            View l = getLayoutInflater().from(this).inflate(R.layout.layout_edit, lingueLayout, false);
            TextView lingua = l.findViewById(R.id.textViewEdit);
            ImageView delete = l.findViewById(R.id.delete);
            delete.setClickable(true);
            lingua.setText(lingue.get(j));
            lingueLayout.addView(l);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditTeam.this);
                    builder.setTitle("Sei sicuro di voler cancellare " + lingue.get(index) + "?")
                            .setPositiveButton("Sì", (dialog, id) -> {
                                lingue.remove(index);
                                Intent editTeam = new Intent(EditTeam.this, EditTeam.class);
                                editTeam.putExtra("currentUserID", player.getIdPlayer());
                                editTeam.putExtra("notMePlayer", player.getIdPlayer());
                                editTeam.putExtra("sizeLingue", currentSizeLingue);
                                editTeam.putExtra("sizeVoiceChat", currentSizeVoiceChat);
                                startActivity(editTeam);
                            })
                            .setNegativeButton("No", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

        addLingua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View view = li.inflate(R.layout.message_box_layout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setView(view);

                final EditText input = view.findViewById(R.id.editInput);

                builder.setTitle("Lingua");
                builder.setPositiveButton("Aggiungi",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                lingue.add(input.getText().toString());
                                Intent editTeam = new Intent(EditTeam.this, EditTeam.class);
                                editTeam.putExtra("currentUserID", player.getIdPlayer());
                                editTeam.putExtra("notMePlayer", player.getIdPlayer());
                                editTeam.putExtra("sizeLingue", currentSizeLingue);
                                editTeam.putExtra("sizeVoiceChat", currentSizeVoiceChat);
                                startActivity(editTeam);
                            }
                        })
                        .setNegativeButton("Annulla", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        for(int k = 0; k < voiceChat.size(); k++){
            final int index = k;
            View v = getLayoutInflater().from(this).inflate(R.layout.layout_edit, voiceChatLayout, false);
            TextView voiceC = v.findViewById(R.id.textViewEdit);
            ImageView delete = v.findViewById(R.id.delete);
            voiceC.setText(voiceChat.get(k));
            voiceChatLayout.addView(v);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditTeam.this);
                    builder.setTitle("Sei sicuro di voler cancellare " + voiceChat.get(index) + "?")
                            .setPositiveButton("Sì", (dialog, id) -> {
                                voiceChat.remove(index);
                                Intent editTeam = new Intent(EditTeam.this, EditTeam.class);
                                editTeam.putExtra("currentUserID", player.getIdPlayer());
                                editTeam.putExtra("notMePlayer", player.getIdPlayer());
                                editTeam.putExtra("sizeLingue", currentSizeLingue);
                                editTeam.putExtra("sizeVoiceChat", currentSizeVoiceChat);
                                startActivity(editTeam);
                            })
                            .setNegativeButton("No", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

        addVoiceChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View view = li.inflate(R.layout.message_box_layout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setView(view);

                final EditText input = view.findViewById(R.id.editInput);

                builder.setTitle("Voice Chat");
                builder.setPositiveButton("Aggiungi",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                voiceChat.add(input.getText().toString());
                                Intent editTeam = new Intent(EditTeam.this, EditTeam.class);
                                editTeam.putExtra("currentUserID", player.getIdPlayer());
                                editTeam.putExtra("notMePlayer", player.getIdPlayer());
                                editTeam.putExtra("sizeLingue", currentSizeLingue);
                                editTeam.putExtra("sizeVoiceChat", currentSizeVoiceChat);
                                startActivity(editTeam);
                            }
                        })
                        .setNegativeButton("Annulla", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(nome.getText().toString().equals(""))) t.setNome(nome.getText().toString());
                Intent teamProfile = new Intent(EditTeam.this, TeamProfile.class);
                teamProfile.putExtra("currentUserID", player.getIdPlayer());
                teamProfile.putExtra("notMePlayer", player.getIdPlayer());
                teamProfile.putExtra("callerClass", 0);
                startActivity(teamProfile);
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

    /********** INIZIO METODI RIEMPIMENTO TEAM INFO **********/

    private void setTeamInfo(Team t){
        teamInfo = findViewById(R.id.teamInfo);

        String info = t.getNome() + "\nCapitano\n" + t.getCaptain().getInGameName();
        teamInfo.setText(info);
    }

    /********** FINE METODI RIEMPIMENTO TEAM INFO **********/

    @Override
    public void onBackPressed() {
        Intent home = new Intent(EditTeam.this, Home.class);
        home.putExtra("currentUserID", player.getIdPlayer());
        startActivity(home);
    }
}

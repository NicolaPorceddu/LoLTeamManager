package com.example.bacci.lolteammanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;

public class EditMembers extends AppCompatActivity {
    /********** INIZIO DICHIRAZIONI TOOLBAR **********/
    Toolbar toolbar;
    /********** FINE DICHIRAZIONI TOOLBAR **********/
    LinearLayout membersLayout;
    UserFactory uf = UserFactory.getInstance();
    TeamFactory tf = TeamFactory.getInstance();
    User player, notMePlayer, p;
    Team t;
    TextView add;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_members);

        /********** INIZIO CODICE TOOLBAR **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR **********/
        final Context context = this;

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        notMePlayer = uf.getUtenteByID(i.getExtras().getInt("notMePlayer"));

        add = findViewById(R.id.aggiungiMembro);
        membersLayout = findViewById(R.id.membersLayout);
        save = findViewById(R.id.salva);

        t = tf.getTeamById(player.getIdSquadra());

        ArrayList<User> members = uf.getPlayersByIdTeam(t.getIdTeam());

        for(int j = 0; j < members.size(); j++){
            final int index = j;
            View l = getLayoutInflater().from(this).inflate(R.layout.layout_edit, membersLayout, false);
            TextView textViewEdit = l.findViewById(R.id.textViewEdit);
            textViewEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 10);
            textViewEdit.setLayoutParams(params);

            ImageView delete = l.findViewById(R.id.delete);
            delete.setClickable(true);
            textViewEdit.setText(members.get(j).getInGameName());
            membersLayout.addView(l);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(player.equals(t.getCaptain()) && player.equals(t.getPlayers().get(index))){
                        Toast toast = Toast.makeText(getApplicationContext(), "ERRORE! Non puoi eliminare il capitano.", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditMembers.this);
                        builder.setTitle("Sei sicuro di voler cancellare " + members.get(index).getInGameName() + "?")
                                .setPositiveButton("Sì", (dialog, id) -> {
                                    int n = t.getnPlayers();
                                    t.getPlayers().remove(index);
                                    t.setnPlayers(n-1);
                                    p = uf.getUtenteByID(members.get(index).getIdPlayer());
                                    p.setIdSquadra(0);
                                    p.setSquadra(false);

                                    Team.Rank rMin = t.getRankMinMax().get(0), rMax = t.getRankMinMax().get(1);
                                    int min = AdapterTeams.assegnaIntRank(t.getPlayers().get(0).getRank()),
                                            max = AdapterTeams.assegnaIntRank(t.getPlayers().get(0).getRank());

                                    for(User pl : t.getPlayers()){
                                        if(AdapterTeams.assegnaIntRank(pl.getRank()) <= min){
                                            min = AdapterTeams.assegnaIntRank(pl.getRank());
                                            rMin = pl.getRank();
                                        }

                                        if(AdapterTeams.assegnaIntRank(pl.getRank()) >= max){
                                            max = AdapterTeams.assegnaIntRank(pl.getRank());
                                            rMax = pl.getRank();
                                        }
                                    }

                                    t.setRankMinMax(new ArrayList<>(Arrays.asList(rMin, rMax)));

                                    Intent editMembers = new Intent(EditMembers.this, EditMembers.class);
                                    editMembers.putExtra("currentUserID", player.getIdPlayer());
                                    editMembers.putExtra("notMePlayer", player.getIdPlayer());
                                    startActivity(editMembers);
                                })
                                .setNegativeButton("No", null);

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t.getnPlayers() == 5){
                    Toast toast = Toast.makeText(getApplicationContext(), "ERRORE! La squadra non può avere più di 5 membri.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    LayoutInflater li = LayoutInflater.from(context);
                    View view = li.inflate(R.layout.message_box_layout, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setView(view);

                    final EditText input = view.findViewById(R.id.editInput);

                    builder.setTitle("Nuovo membro");
                    builder.setPositiveButton("Aggiungi",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    if(uf.getUtenteByInGameName(input.getText().toString()) != null) {
                                        p = uf.getUtenteByInGameName(input.getText().toString());
                                        p.setSquadra(true);
                                        p.setIdSquadra(t.getIdTeam());
                                        t.setnPlayers(t.getnPlayers()+1);
                                        t.getPlayers().add(p);
                                        members.add(p);

                                        Team.Rank rMin, rMax;
                                        int maxRank = AdapterTeams.assegnaIntRank(t.getRankMinMax().get(1));
                                        int minRank = AdapterTeams.assegnaIntRank(t.getRankMinMax().get(0));
                                        int playerRank = AdapterTeams.assegnaIntRank(p.getRank());

                                        if(minRank > playerRank){
                                            rMin = p.getRank();
                                        }  else rMin = t.getRankMinMax().get(0);

                                        if(maxRank < playerRank){
                                            rMax = p.getRank();
                                        }  else rMax = t.getRankMinMax().get(1);

                                        t.setRankMinMax(new ArrayList<>(Arrays.asList(rMin, rMax)));

                                        Intent editTeam = new Intent(EditMembers.this, EditMembers.class);
                                        editTeam.putExtra("currentUserID", player.getIdPlayer());
                                        editTeam.putExtra("notMePlayer", player.getIdPlayer());
                                        startActivity(editTeam);
                                    } else {
                                        input.setError("Utente non trovato");
                                    }
                                }
                            })
                            .setNegativeButton("Annulla", null);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teamMembers = new Intent(EditMembers.this, TeamProfile.class);
                teamMembers.putExtra("currentUserID", player.getIdPlayer());
                teamMembers.putExtra("notMePlayer", player.getIdPlayer());
                teamMembers.putExtra("callerClass", 0);
                startActivity(teamMembers);
            }
        });

    }

    /********** INIZIO METODI TOOLBAR **********/

    private void setToolbar(Toolbar tb){
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

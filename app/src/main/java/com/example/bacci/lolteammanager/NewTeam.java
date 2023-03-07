package com.example.bacci.lolteammanager;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public class NewTeam extends AppCompatActivity {
    /********** INIZIO DICHIRAZIONI TOOLBAR **********/
    Toolbar toolbar;
    /********** FINE DICHIRAZIONI TOOLBAR **********/
    UserFactory uf = UserFactory.getInstance();
    TeamFactory tf = TeamFactory.getInstance();
    User player, notMePlayer;
    Team t = new Team();
    Button create;
    EditText name, language;
    CheckBox voice1, voice2, voice3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);

        /********** INIZIO CODICE TOOLBAR **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR **********/

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        notMePlayer = uf.getUtenteByID(i.getExtras().getInt("notMePlayer"));

        create = findViewById(R.id.crea);
        name = findViewById(R.id.nome);
        language = findViewById(R.id.lingua);
        voice1 = findViewById(R.id.cb1);
        voice2 = findViewById(R.id.cb2);
        voice3 = findViewById(R.id.cb3);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    applyInfo();
                }
            }
        });
    }


    /********** INIZIO METODI TOOLBAR **********/

    private void setToolbar(Toolbar tb){
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    /********** FINE METODI TOOLBAR **********/

    private boolean checkInput(){
        if((name.getText() == null || name.getText().length() == 0) &&
                (language.getText() == null || language.getText().length() == 0)){
            language.setError("ERRORE! Inserire la lingua.");
            name.setError("ERRORE! Inserire il nome.");
        } else if(!(voice1.isChecked()) && !(voice2.isChecked()) && !(voice3.isChecked())){
            voice1.setError("Inserire almeno una voice chat.");
        } else if(name.getText() == null || name.getText().length() == 0){
            name.setError("ERRORE! Inserire il nome.");
        } else if(language.getText() == null || language.getText().length() == 0){
            language.setError("ERRORE! Inserire la lingua.");
        } else return true;

        return false;
    }

    private void applyInfo(){
        t.setNome(name.getText().toString());
        t.setCaptain(player);
        player.setSquadra(true);
        player.setIdSquadra(t.getIdTeam());

        //SETTARE SETPLAYERS CLASSE TEAM

        t.getLingue().add(language.getText().toString());

        if(voice1.isChecked())
            t.getVoiceChat().add(voice1.getText().toString());

        if(voice2.isChecked())
            t.getVoiceChat().add(voice2.getText().toString());

        if(voice3.isChecked())
            t.getVoiceChat().add(voice3.getText().toString());

        t.addPlayer(player);
        player.setIdSquadra(t.getIdTeam());
        player.setSquadra(true);

        t.setRankMinMax(new ArrayList<Team.Rank>(Arrays.asList(player.getRank(), player.getRank())));

        tf.getTeamList().add(t);

        AlertDialog.Builder builder = new AlertDialog.Builder(NewTeam.this);
        builder.setTitle("Team creato correttamente!")
                .setPositiveButton("Ok", (dialog, id) -> {
                    Intent team = new Intent(NewTeam.this, TeamProfile.class);
                    team.putExtra("currentUserID", player.getIdPlayer());
                    team.putExtra("notMePlayer", player.getIdPlayer());
                    team.putExtra("callerClass", 6);
                    startActivity(team);
                });

        AlertDialog dialog = builder.create();
        dialog.show();
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
}

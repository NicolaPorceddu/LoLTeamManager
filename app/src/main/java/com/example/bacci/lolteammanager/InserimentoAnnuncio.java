package com.example.bacci.lolteammanager;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class InserimentoAnnuncio extends AppCompatActivity {
    /********** INIZIO DICHIRAZIONI TOOLBAR **********/
    Toolbar toolbar;
    /********** FINE DICHIRAZIONI TOOLBAR **********/
    UserFactory uf = UserFactory.getInstance();
    TeamFactory tf = TeamFactory.getInstance();
    AnnuncioFactory af = AnnuncioFactory.getInstance();
    Annuncio a = new Annuncio();
    User player, notMePlayer;
    Team t;
    Button insert;
    EditText rankMin, rankMax, age;
    CheckBox role1, role2, role3, role4, role5;
    TextView errore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserimento_annuncio);

        /********** INIZIO CODICE TOOLBAR **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR **********/

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        notMePlayer = uf.getUtenteByID(i.getExtras().getInt("notMePlayer"));
        t = tf.getTeamById(player.getIdSquadra());

        insert = findViewById(R.id.inserisci);
        rankMax = findViewById(R.id.rankmax);
        rankMin = findViewById(R.id.rankmin);
        age = findViewById(R.id.age);
        role1 = findViewById(R.id.cb1);
        role2 = findViewById(R.id.cb2);
        role3 = findViewById(R.id.cb3);
        role4 = findViewById(R.id.cb4);
        role5 = findViewById(R.id.cb5);

        errore = findViewById(R.id.errore);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()) manageNewAnnounce();
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

    private void manageNewAnnounce(){
        errore.setVisibility(View.GONE);

        a.setTeamName(t.getNome());
        a.setRankMinimo(rankMin.getText().toString());
        a.setRankMassimo(rankMax.getText().toString());
        a.setRuolo(new ArrayList<>());

        if(role1.isChecked())
            a.getRuolo().add(role1.getText().toString());

        if(role2.isChecked())
            a.getRuolo().add(role2.getText().toString());

        if(role3.isChecked())
            a.getRuolo().add(role3.getText().toString());

        if(role4.isChecked())
            a.getRuolo().add(role4.getText().toString());

        if(role5.isChecked())
            a.getRuolo().add(role5.getText().toString());

        if(a.getRuolo().size() == 5 || (a.getRuolo().size() > 5 - t.getnPlayers())){
            errore.setVisibility(View.VISIBLE);
            errore.setText("ERRORE! Non puoi inserire più di " + (5 - t.getnPlayers()) +" ruoli.\n" +
                    "Annuncio non inserito.");
        } else {
            af.getAnnList().add(a);

            AlertDialog.Builder builder = new AlertDialog.Builder(InserimentoAnnuncio.this);
            builder.setTitle("Annuncio inserito correttamente!")
                    .setPositiveButton("Ok", (dialog, id) -> {
                        Intent team = new Intent(InserimentoAnnuncio.this, TeamProfile.class);
                        team.putExtra("currentUserID", player.getIdPlayer());
                        team.putExtra("notMePlayer", player.getIdPlayer());
                        team.putExtra("callerClass", 5);
                        startActivity(team);
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private boolean checkInput(){
        if((rankMin.getText() == null || rankMin.getText().length() == 0) &&
                (rankMax.getText() == null || rankMax.getText().length() == 0) &&
                (age.getText() == null || age.getText().length() == 0)){
            rankMax.setError("ERRORE! Inserire rank massimo.");
            rankMin.setError("ERRORE! Inserire rank minimo.");
            age.setError("ERRORE! Inserire l'età.");
            errore.setVisibility(View.VISIBLE);
            errore.setText("ERRORE! Inserire almeno un ruolo.");
        } else if(!(role1.isChecked()) && !(role2.isChecked()) && !(role3.isChecked())
                && !(role4.isChecked()) && !(role5.isChecked())){
            errore.setVisibility(View.VISIBLE);
            errore.setText("ERRORE! Inserire almeno un ruolo.");
        } else if(rankMin.getText() == null || rankMin.getText().length() == 0){
            rankMin.setError("ERRORE! Inserire rank minimo.");
        } else if(rankMax.getText() == null || rankMax.getText().length() == 0){
            rankMax.setError("ERRORE! Inserire rank massimo.");
        } else if(age.getText() == null || age.getText().length() == 0){
            age.setError("ERRORE! Inserire l'età.");
        } else return true;

        return false;
    }
}

package com.example.bacci.lolteammanager;

import android.content.Intent;
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
import java.util.Arrays;

public class Registrazione extends AppCompatActivity{
    /********** INIZIO DICHIRAZIONI TOOLBAR / DRAWER LAYOUT **********/
    Toolbar toolbar;
    /********** FINE DICHIRAZIONI TOOLBAR / DRAWER LAYOUT **********/

    UserFactory uf = UserFactory.getInstance();
    User u = new User();
    Button reg;
    EditText username, ingamename, password, mail, age, rank, rankD, livello, k, d, a;
    CheckBox role1, role2, role3, role4, role5;
    TextView errore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        /********** INIZIO CODICE TOOLBAR / DRAWER LAYOUT **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR / DRAWER LAYOUT **********/

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        username = findViewById(R.id.user);
        password = findViewById(R.id.psw);
        ingamename = findViewById(R.id.ingamename);
        mail = findViewById(R.id.email);
        age = findViewById(R.id.age);
        rank = findViewById(R.id.rank);
        rankD = findViewById(R.id.divisione);
        livello = findViewById(R.id.livello);
        k = findViewById(R.id.k);
        d = findViewById(R.id.d);
        a = findViewById(R.id.a);

        role1 = findViewById(R.id.cb1);
        role2 = findViewById(R.id.cb2);
        role3 = findViewById(R.id.cb3);
        role4 = findViewById(R.id.cb4);
        role5 = findViewById(R.id.cb5);

        errore = findViewById(R.id.errore);

        reg = findViewById(R.id.registrati);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    u = applyInfo(u, username, password, ingamename, mail, age, rank, rankD, livello, k, d, a,
                                  role1, role2, role3, role4, role5);
                    uf.getUserList().add(u);

                    Intent profilePlayer = new Intent(Registrazione.this, Home.class);
                    profilePlayer.putExtra("currentUserID", u.getIdPlayer());
                    startActivity(profilePlayer);
                }
            }
        });
    }

    private boolean checkInput(){
        if((username.getText() == null || username.getText().length() == 0) &&
                (password.getText() == null || password.getText().length() == 0) &&
                (ingamename.getText() == null || ingamename.getText().length() == 0) &&
                (mail.getText() == null || mail.getText().length() == 0) &&
                (age.getText() == null || age.getText().length() == 0) &&
                (rank.getText() == null || rank.getText().length() == 0) &&
                (rankD.getText() == null || rankD.getText().length() == 0) &&
                (livello.getText() == null || livello.getText().length() == 0) &&
                (k.getText() == null || k.getText().length() == 0) &&
                (d.getText() == null || d.getText().length() == 0) &&
                (a.getText() == null || a.getText().length() == 0) &&
                (!(role1.isChecked()) && (!(role2.isChecked())) &&
                (!(role3.isChecked())) && (!(role4.isChecked())) &&
                (!(role5.isChecked())))){
            username.setError("ERRORE! Inserire lo username.");
            password.setError("ERRORE! Inserire la password.");
            ingamename.setError("ERRORE! Inserire il nome di gioco.");
            mail.setError("ERRORE! Inserire l'e-mail.");
            age.setError("ERRORE! Inserire l'età.");
            rank.setError("ERRORE! Inserire il rank.");
            rankD.setError("ERRORE! Inserire la divisione.");
            livello.setError("ERRORE! Inserire il livello.");
            k.setError("ERRORE! Inserire KDA.");
            errore.setVisibility(View.VISIBLE);
        } else if(username.getText() == null || username.getText().length() == 0) {
            username.setError("ERRORE! Inserire lo username.");
        } else if(password.getText() == null || password.getText().length() == 0){
            password.setError("ERRORE! Inserire la password.");
        } else if(ingamename.getText() == null || ingamename.getText().length() == 0){
            ingamename.setError("ERRORE! Inserire il nome di gioco.");
        } else if(mail.getText() == null || mail.getText().length() == 0){
            mail.setError("ERRORE! Inserire l'e-mail.");
        } else if(age.getText() == null || age.getText().length() == 0){
            age.setError("ERRORE! Inserire l'età.");
        } else if(rank.getText() == null || rank.getText().length() == 0){
            rank.setError("ERRORE! Inserire il rank.");
        } else if(rankD.getText() == null || rankD.getText().length() == 0){
            rankD.setError("ERRORE! Inserire la divisione.");
        } else if(livello.getText() == null || livello.getText().length() == 0){
            rankD.setError("ERRORE! Inserire il livello.");
        } else if(!(role1.isChecked()) && (!(role2.isChecked())) && (!(role3.isChecked())) &&
                (!(role4.isChecked())) && (!(role5.isChecked()))){
            errore.setVisibility(View.VISIBLE);
        } else if(k.getText() == null || k.getText().length() == 0 || d.getText() == null ||
                d.getText().length() == 0 || a.getText() == null || a.getText().length() == 0){
            k.setError("ERRORE! Inserire KDA.");
        } else return true;

        return false;
    }

    private User applyInfo(User utente, EditText u, EditText p, EditText ing, EditText m, EditText a,
                           EditText r, EditText rd, EditText liv, EditText kill, EditText death, EditText assist,
                           CheckBox r1, CheckBox r2, CheckBox r3, CheckBox r4, CheckBox r5){

        errore.setVisibility(View.GONE);
        utente.setUsername(u.getText().toString());
        utente.setPassword(p.getText().toString());
        utente.setInGameName(ing.getText().toString());
        utente.setEmail(m.getText().toString());
        utente.setEta(Integer.parseInt(a.getText().toString()));
        utente.setRank(valueRank(rank.getText().toString()));
        utente.setDivisione(Integer.parseInt(rd.getText().toString()));
        utente.setLivello(Integer.parseInt(liv.getText().toString()));
        utente.setKDA(new ArrayList<Double>(Arrays.asList(Double.parseDouble(kill.getText().toString()),
                Double.parseDouble(death.getText().toString()), Double.parseDouble(assist.getText().toString()))));
        utente.setRuoli(new ArrayList<String>());
        utente.setSquadra(false);

        if(r1.isChecked()) {
            utente.getRuoli().add(r1.getText().toString());
        }

        if(r2.isChecked()) {
            utente.getRuoli().add(r2.getText().toString());
        }

        if(r3.isChecked()) {
            utente.getRuoli().add(r3.getText().toString());
        }

        if(r4.isChecked()) {
            utente.getRuoli().add(r4.getText().toString());
        }

        if(r5.isChecked()) {
            utente.getRuoli().add(r5.getText().toString());
        }

        return utente;
    }

    private Team.Rank valueRank(String str){
        String s = Character.toUpperCase(str.charAt(0)) + str.substring(1);

        switch(str){
            case "Unranked": return Team.Rank.Unranked;
            case "Iron": return Team.Rank.Iron;
            case "Bronze": return Team.Rank.Bronze;
            case "Silver": return Team.Rank.Silver;
            case "Gold": return Team.Rank.Gold;
            case "Platinum": return Team.Rank.Platinum;
            case "Diamond": return Team.Rank.Diamond;
            case "Master": return Team.Rank.Master;
            case "Grandmaster": return Team.Rank.Grandmaster;
            case "Challenger": return Team.Rank.Challenger;
        }

        return null;
    }

    /********** INIZIO METODI TOOLBAR / DRAWER LAYOUT **********/
    private void setToolbar(Toolbar tb){
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
    /********** FINE METODI TOOLBAR / DRAWER LAYOUT **********/
}

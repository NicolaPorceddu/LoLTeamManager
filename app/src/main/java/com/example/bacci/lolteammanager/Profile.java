package com.example.bacci.lolteammanager;

/********** INIZIO IMPORT TOOLBAR / DRAWER LAYOUT **********/
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
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

public class Profile extends AppCompatActivity {
    /********** INIZIO DICHIRAZIONI TOOLBAR / DRAWER LAYOUT **********/
    Toolbar toolbar;
    /********** FINE DICHIRAZIONI TOOLBAR / DRAWER LAYOUT **********/

    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/
    LinearLayout homepage, msg, friends;
    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/

    UserFactory uf = UserFactory.getInstance();
    TeamFactory tf = TeamFactory.getInstance();
    Champions c = new Champions();
    Game g = new Game();
    User player, notMePlayer;
    Team t;
    TextView teamInfo, playerInfo, topChamp, game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /********** INIZIO CODICE TOOLBAR / DRAWER LAYOUT **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR / DRAWER LAYOUT **********/

        homepage = findViewById(R.id.bottomHomeProfile);
        msg = findViewById(R.id.bottomMsgProfile);
        friends = findViewById(R.id.bottomFriendsProfile);

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        notMePlayer = uf.getUtenteByID(i.getExtras().getInt("notMePlayer"));

        //t = tf.getTeamById(player.getIdSquadra());

        /* RIEMPIMENTO PROFILO */
        setPlayerInfo(player, notMePlayer);

        /* GESTIONE BOTTOM NAV */
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Profile.this, Home.class);
                home.putExtra("currentUserID", player.getIdPlayer());
                startActivity(home);
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendsList = new Intent(Profile.this, FriendsList.class);
                friendsList.putExtra("currentUserID", player.getIdPlayer());
                friendsList.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                startActivity(friendsList);
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!player.equals(notMePlayer)) {
                    Intent chat = new Intent(Profile.this, Chat.class);
                    chat.putExtra("currentUserID", player.getIdPlayer());
                    chat.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                    startActivity(chat);
                } else {
                    Intent chat = new Intent(Profile.this, ListaChat.class);
                    chat.putExtra("currentUserID", player.getIdPlayer());
                    chat.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                    startActivity(chat);
                }
            }
        });
    }

    /********** INIZIO METODI TOOLBAR / DRAWER LAYOUT **********/
    private void setToolbar(Toolbar tb){
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        t = tf.getTeamById(player.getIdSquadra());

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);

        MenuItem create = menu.findItem(R.id.createTeam);
        MenuItem add = menu.findItem(R.id.add_friend);
        MenuItem add2team = menu.findItem(R.id.add_team);

        if(notMePlayer.equals(player)){
            if(!player.getSquadra()) create.setVisible(true);
        } else {
            boolean ok = player.getFriendList().contains(notMePlayer);
            if(ok == false) add.setVisible(true);

            if (notMePlayer.getSquadra() == false && player.getSquadra() == true && t.getnPlayers() < 5)
                add2team.setVisible(true);
        }

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        t = tf.getTeamById(player.getIdSquadra());
        if (item.getItemId() == android.R.id.home)
            finish();

        if(item.getItemId() == R.id.createTeam) {
            Intent newTeam = new Intent(Profile.this, NewTeam.class);
            newTeam.putExtra("currentUserID", player.getIdPlayer());
            newTeam.putExtra("notMePlayer", player.getIdPlayer());
            startActivity(newTeam);
        }

        if(item.getItemId() == R.id.add_friend){
            System.out.println("I'M HERE 2");
            player.getFriendList().add(notMePlayer);

            AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
            builder.setTitle("Amico inserito correttamente!")
                    .setPositiveButton("Ok", (dialog, id) -> {
                        Intent profile = new Intent(Profile.this, Profile.class);
                        profile.putExtra("currentUserID", player.getIdPlayer());
                        profile.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                        startActivity(profile);
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        if(item.getItemId() == R.id.add_team){
            t.setnPlayers(t.getnPlayers()+1);
            t.getPlayers().add(notMePlayer);

            notMePlayer.setIdSquadra(t.getIdTeam());
            notMePlayer.setSquadra(true);

            AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
            builder.setTitle("Player aggiunto al team!")
                    .setPositiveButton("Ok", (dialog, id) -> {
                        Team.Rank rMin, rMax;
                        int maxRank = AdapterTeams.assegnaIntRank(t.getRankMinMax().get(1));
                        int minRank = AdapterTeams.assegnaIntRank(t.getRankMinMax().get(0));
                        int playerRank = AdapterTeams.assegnaIntRank(notMePlayer.getRank());

                        if(minRank > playerRank){
                            rMin = notMePlayer.getRank();
                        }  else rMin = t.getRankMinMax().get(0);

                        if(maxRank < playerRank){
                            rMax = notMePlayer.getRank();
                        }  else rMax = t.getRankMinMax().get(1);

                        t.setRankMinMax(new ArrayList<>(Arrays.asList(rMin, rMax)));
                        Intent team = new Intent(Profile.this, TeamProfile.class);
                        team.putExtra("currentUserID", player.getIdPlayer());
                        team.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                        team.putExtra("callerClass", 0);
                        startActivity(team);
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
    /********** FINE METODI TOOLBAR / DRAWER LAYOUT **********/

    /********** INIZIO METODI RIEMPIMENTO PROFILO **********/
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

        teamInfo = findViewById(R.id.teamInfo);

        if(p.getSquadra()){
            t = tf.getTeamById(p.getIdSquadra());
            String info = "Nome: " + t.getNome() + "\nRuolo: " + stampaRuoli(p);
            teamInfo.setText(info);

            teamInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent teamProfile = new Intent(Profile.this, TeamProfile.class);
                    teamProfile.putExtra("currentUserID", player.getIdPlayer());
                    teamProfile.putExtra("notMePlayer", notMePlayer.getIdPlayer());
                    startActivity(teamProfile);
                }
            });
        } else teamInfo.setText("Non appartiene ad alcuna squadra.");

        topChamp = findViewById(R.id.topChampions);
        String role1 = p.getRuoli().get(0).toString();
        String role2 = p.getRuoli().get(1).toString();
        String champ = "- " + role1 + ": " + c.getRandomChamp(role1) + "\n- " + role2 + ": " + c.getRandomChamp(role2);
        topChamp.setText(champ);

        game = findViewById(R.id.ultimoGame);
        String gameInfo = "Vinta - " + g.getRandomGame() + "\nRuolo: " + role1 + "\nKDA: ";
        for(int j = 0; j < kdaList.size(); j++){
            if (j == kdaList.size()-1) gameInfo = gameInfo + kdaList.get(j).toString();
            else gameInfo = gameInfo + kdaList.get(j).toString() + " / ";
        }
        game.setText(gameInfo);
    }
    /********** FINE METODI RIEMPIMENTO PROFILO **********/
    private String stampaRuoli(User user) {
        String risultato="";
        int cont =0;
        if (user.getRuoli()!= null) {
            for (String s : user.getRuoli()) {
                cont++;
                if (cont == user.getRuoli().size())
                    risultato = risultato + s;
                else
                    risultato = risultato + s + ", ";
            }
        }
        return risultato;
    }
}
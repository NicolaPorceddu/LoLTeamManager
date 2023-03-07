package com.example.bacci.lolteammanager;

/********** INIZIO IMPORT TOOLBAR / DRAWER LAYOUT **********/
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
/********** FINE DICHIRAZIONI TOOLBAR / DRAWER LAYOUT **********/

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity{
    UserFactory uf = UserFactory.getInstance();
    User player;
    TextView username, levRank, kda, team, searchP, searchT, calendario;

    /********** INIZIO DICHIRAZIONI TOOLBAR / DRAWER LAYOUT **********/
    DrawerLayout dl;
    Toolbar toolbar;
    NavigationView menu;
    /********** FINE DICHIRAZIONI TOOLBAR / DRAWER LAYOUT **********/

    int callerClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /********** INIZIO CODICE TOOLBAR / DRAWER LAYOUT **********/
        toolbar = findViewById(R.id.toolbar);
        dl = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.nav_view);
        setToolbar(toolbar);
        dlListener(dl);
        manageNavigationMenu(menu);
        /********** FINE CODICE TOOLBAR / DRAWER LAYOUT **********/

        username = findViewById(R.id.username);
        searchP = findViewById(R.id.searchP);
        searchT = findViewById(R.id.searchT);
        calendario = findViewById(R.id.calendario);
        team = findViewById(R.id.yourTeam);
        levRank = findViewById(R.id.levelRank);
        kda = findViewById(R.id.kda);

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        callerClass = i.getExtras().getInt("callerClass");
        ArrayList<Double> kdaList = player.getKDA();
        String str = "";

        View header =  menu.getHeaderView(0);
        TextView nav_user = header.findViewById(R.id.profileName);
        nav_user.setText(player.getInGameName());

        username.setText(player.getInGameName());
        levRank.setText("Lv. " + player.getLivello() + " - " + player.getRank().toString() + " " +  player.getDivisione());

        for(int j = 0; j < kdaList.size(); j++){
            if (j == kdaList.size()-1) str = str + kdaList.get(j).toString();
            else str = str + kdaList.get(j).toString() + " / ";
        }

        kda.setText(str);

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(Home.this, Profile.class);
                profile.putExtra("currentUserID", player.getIdPlayer());
                profile.putExtra("notMePlayer", player.getIdPlayer());
                startActivity(profile);
            }
        });

        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.getSquadra()){
                    Intent teamProfile = new Intent(Home.this, TeamProfile.class);
                    teamProfile.putExtra("currentUserID", player.getIdPlayer());
                    teamProfile.putExtra("notMePlayer", player.getIdPlayer());
                    teamProfile.putExtra("callerClass", 0);
                    startActivity(teamProfile);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Non fai parte di alcun team\n" +
                            "Per crearne uno nuovo accedi al tuo profilo", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        searchP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchP = new Intent(Home.this, Search_player.class);
                searchP.putExtra("currentUserID", player.getIdPlayer());
                searchP.putExtra("notMePlayer", player.getIdPlayer());
                startActivity(searchP);
            }
        });

        searchT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchP = new Intent(Home.this, Search_Team.class);
                searchP.putExtra("currentUserID", player.getIdPlayer());
                searchP.putExtra("notMePlayer", player.getIdPlayer());
                startActivity(searchP);
            }
        });

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.getSquadra()) {
                    Intent calendar = new Intent(Home.this, Calendario.class);
                    calendar.putExtra("currentUserID", player.getIdPlayer());
                    calendar.putExtra("notMePlayer", player.getIdPlayer());
                    startActivity(calendar);
                }  else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Non c'è alcun calendario da visualizzare.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    /********** INIZIO METODI TOOLBAR / DRAWER LAYOUT **********/
    private void dlListener(DrawerLayout drawer){
        drawer.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

    }

    private void setToolbar(Toolbar tb){
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void manageNavigationMenu(NavigationView menu){
        menu.bringToFront();
        menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                switch(menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent home = new Intent(Home.this, Home.class);
                        home.putExtra("currentUserID", player.getIdPlayer());
                        startActivity(home);
                        break;
                    case R.id.nav_profile:
                        Intent profile = new Intent(Home.this, Profile.class);
                        profile.putExtra("currentUserID", player.getIdPlayer());
                        profile.putExtra("notMePlayer", player.getIdPlayer());
                        startActivity(profile);
                        break;
                    case R.id.nav_team:
                        if(player.getSquadra()){
                            Intent teamProfile = new Intent(Home.this, TeamProfile.class);
                            teamProfile.putExtra("currentUserID", player.getIdPlayer());
                            teamProfile.putExtra("notMePlayer", player.getIdPlayer());
                            teamProfile.putExtra("callerClass", 0);
                            startActivity(teamProfile);
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Non fai parte di alcun team\n" +
                                    "Per crearne uno nuovo accedi al tuo profilo", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        break;
                    case R.id.nav_chat:
                        Intent chat = new Intent(Home.this, ListaChat.class);
                        chat.putExtra("currentUserID", player.getIdPlayer());
                        chat.putExtra("notMePlayer", player.getIdPlayer());
                        startActivity(chat);
                        break;
                    case R.id.nav_lista_amici:
                        Intent friends = new Intent(Home.this, FriendsList.class);
                        friends.putExtra("currentUserID", player.getIdPlayer());
                        friends.putExtra("notMePlayer", player.getIdPlayer());
                        startActivity(friends);
                        break;
                    case R.id.nav_calendario:
                        if(player.getSquadra()) {
                            Intent calendar = new Intent(Home.this, Calendario.class);
                            calendar.putExtra("currentUserID", player.getIdPlayer());
                            calendar.putExtra("notMePlayer", player.getIdPlayer());
                            startActivity(calendar);
                        }  else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Non c'è alcun calendario da visualizzare.", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        break;
                    case R.id.nav_ricerca_player:
                        Intent searchP = new Intent(Home.this, Search_player.class);
                        searchP.putExtra("currentUserID", player.getIdPlayer());
                        searchP.putExtra("notMePlayer", player.getIdPlayer());
                        startActivity(searchP);
                        break;
                    case R.id.nav_ricerca_team:
                        Intent searchT = new Intent(Home.this, Search_Team.class);
                        searchT.putExtra("currentUserID", player.getIdPlayer());
                        searchT.putExtra("notMePlayer", player.getIdPlayer());
                        startActivity(searchT);
                        break;
                    case R.id.nav_logout:
                        Intent logout = new Intent(Home.this, Login.class);
                        startActivity(logout);
                        break;
                    default:
                        return true;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dl.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /********** FINE METODI TOOLBAR / DRAWER LAYOUT **********/

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Home.this, Home.class);
        i.putExtra("currentUserID", player.getIdPlayer());
        i.putExtra("notMePlayer", player.getIdPlayer());
        startActivity(i);
    }
}
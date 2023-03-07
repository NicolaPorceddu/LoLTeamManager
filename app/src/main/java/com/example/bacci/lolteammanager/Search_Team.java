package com.example.bacci.lolteammanager;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Search_Team extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    /********** INIZIO DICHIRAZIONI TOOLBAR **********/
    Toolbar toolbar;
    /********** FINE DICHIRAZIONI TOOLBAR **********/

    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/
    LinearLayout homepage, msg, friends;
    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/

    TeamFactory tf = TeamFactory.getInstance();
    ArrayList <Team> teamList = tf.getTeamList();
    static ArrayList<Team> lista = new ArrayList<>();
    SearchView search;
    UserFactory uf = UserFactory.getInstance();
    User player;
    User notMePlayer;
    RecyclerView recycleTeam;
    LinearLayout iconFilters;
    static User p;
    String rankSelected;
    Boolean teamIncomplete = false;
    CheckBox lingua1, lingua2, lingua3, lingua4, lingua5, lingua6;
    CheckBox voice1, voice2, voice3;
    Button ricercaBottone;
    LinearLayout filterLayout;
    TextView risultati;
    RadioGroup radioGroup;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__team);

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        notMePlayer = uf.getUtenteByID(i.getExtras().getInt("notMePlayer"));
        p = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));

        /********** INIZIO CODICE TOOLBAR **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR **********/

        homepage = findViewById(R.id.bottomHomeProfile);
        msg = findViewById(R.id.bottomMsgProfile);
        friends = findViewById(R.id.bottomFriendsProfile);

        search = findViewById(R.id.search);
        iconFilters = findViewById(R.id.icon_filters);
        filterLayout = findViewById(R.id.filterLayout);
        risultati = findViewById(R.id.risultati);
        radioGroup = findViewById(R.id.radioGroup);

        /********** INIZIO CODICE SPINNERS **********/
        spinner = (Spinner) findViewById(R.id.spinnerRank);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,
                R.array.rankForSpinners, R.layout.spinner_item);
       // adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(this);
        /********** FINE CODICE SPINNERS **********/

        lista.clear();
        for(Team t : teamList)
            lista.add(t);

        LinearLayoutManager layoutMngr = new LinearLayoutManager(this);
        recycleTeam = findViewById(R.id.recycleTeam);
        recycleTeam.setHasFixedSize(true);
        recycleTeam.setLayoutManager(layoutMngr);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycleTeam.getContext(),
                layoutMngr.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(recycleTeam.getContext(), R.drawable.linea_separator));
        recycleTeam.addItemDecoration(dividerItemDecoration);

        lingua1 = findViewById(R.id.lingua1);
        lingua2 = findViewById(R.id.lingua2);
        lingua3 = findViewById(R.id.lingua3);
        lingua4 = findViewById(R.id.lingua4);
        lingua5 = findViewById(R.id.lingua5);
        lingua6 = findViewById(R.id.lingua6);

        voice1 = findViewById(R.id.voice1);
        voice2 = findViewById(R.id.voice2);
        voice3 = findViewById(R.id.voice3);

        ricercaBottone = findViewById(R.id.ricercaBottone);

        Intent callerClass = new Intent(this, TeamMembers.class);
        callerClass.putExtra("callerClass", 4);

        final AdapterTeams adapter = new AdapterTeams(lista, callerClass);
        recycleTeam.setAdapter(adapter);

         ricercaBottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                risultati.setVisibility(View.GONE);
                adapter.filterMode(rankSelected, getLingue(), teamIncomplete, getVoice());
                if(adapter.getListaAdapter().size()==0)
                    risultati.setVisibility(View.VISIBLE);
                filterLayout.setVisibility(View.GONE);
            }
        });

        iconFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svuotaFiltri();
                filterLayout.setVisibility(View.VISIBLE);
            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                risultati.setVisibility(View.GONE);
                adapter.filter(newText);
                if(adapter.getListaAdapter().size()==0)
                    risultati.setVisibility(View.VISIBLE);
                return false;
            }
        });

        /* GESTIONE BOTTOM NAV */
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Search_Team.this, Home.class);
                home.putExtra("currentUserID", player.getIdPlayer());
                startActivity(home);
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendsList = new Intent(Search_Team.this, FriendsList.class);
                friendsList.putExtra("currentUserID", player.getIdPlayer());
                friendsList.putExtra("notMePlayer", player.getIdPlayer());
                startActivity(friendsList);
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent(Search_Team.this, ListaChat.class);
                chat.putExtra("currentUserID", player.getIdPlayer());
                chat.putExtra("notMePlayer", player.getIdPlayer());
                startActivity(chat);
            }
        });
    }

    /********** INIZIO METODI TOOLBAR **********/
    private void setToolbar(Toolbar tb){
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(filterLayout.getVisibility() == View.GONE) {
            if (item.getItemId() == android.R.id.home)
                finish();
        } else{
            if(item.getItemId() == android.R.id.home){
                filterLayout.setVisibility(View.GONE);
            }
        }


        return super.onOptionsItemSelected(item);
    }

    /********** FINE METODI TOOLBAR / DRAWER LAYOUT **********/

    private ArrayList<String> getLingue(){
        ArrayList<String> listToReturn = new ArrayList<>();
        if (lingua1.isChecked()) {
            listToReturn.add("Italiano");
        }
        if (lingua2.isChecked()) {
            listToReturn.add("Inglese");
        }
        if (lingua3.isChecked()) {
            listToReturn.add("Francese");
        }
        if (lingua4.isChecked()) {
            listToReturn.add("Tedesco");
        }
        if (lingua5.isChecked()) {
            listToReturn.add("Russo");
        }
        if (lingua6.isChecked()) {
            listToReturn.add("Cinese");
        }
        return listToReturn;
    }

    private ArrayList<String> getVoice(){
        ArrayList<String> listToReturn = new ArrayList<>();
        if (voice1.isChecked()) {
            listToReturn.add("Discord");
        }
        if (voice2.isChecked()) {
            listToReturn.add("LoL VoiceChat");
        }
        if (voice3.isChecked()) {
            listToReturn.add("TeamSpeak");
        }
        return listToReturn;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (pos) {
            case 0: rankSelected = "Tutti"; break;
            case 1: rankSelected = "Unranked"; break;
            case 2: rankSelected = "Iron"; break;
            case 3: rankSelected = "Bronze"; break;
            case 4: rankSelected = "Silver"; break;
            case 5: rankSelected = "Gold"; break;
            case 6: rankSelected = "Platinum"; break;
            case 7: rankSelected = "Diamond"; break;
            case 8: rankSelected = "Master"; break;
            case 9: rankSelected = "Grandmaster"; break;
            case 10: rankSelected = "Challenger"; break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        rankSelected = "Tutti";
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio1:
                if (checked)
                    teamIncomplete = true;
                break;
            case R.id.radio2:
                if (checked)
                    teamIncomplete = false;
                break;
        }
    }

    private void svuotaFiltri() {
        spinner.setSelection(0);
        
        if(lingua1.isChecked())
            lingua1.toggle();
        if(lingua2.isChecked())
            lingua2.toggle();
        if(lingua3.isChecked())
            lingua3.toggle();
        if(lingua4.isChecked())
            lingua4.toggle();
        if(lingua5.isChecked())
            lingua5.toggle();
        if(lingua6.isChecked())
            lingua6.toggle();

        if(voice1.isChecked())
            voice1.toggle();
        if(voice2.isChecked())
            voice2.toggle();
        if(voice3.isChecked())
            voice3.toggle();

        radioGroup.clearCheck();

    }



}

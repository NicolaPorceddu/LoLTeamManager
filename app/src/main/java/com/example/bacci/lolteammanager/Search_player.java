package com.example.bacci.lolteammanager;


import android.app.SearchManager;
import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.support.v7.widget.SearchView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class Search_player extends AppCompatActivity {
    /********** INIZIO DICHIRAZIONI TOOLBAR **********/
    Toolbar toolbar;
    BottomNavigationView botMenu;
    /********** FINE DICHIRAZIONI TOOLBAR **********/

    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/
    LinearLayout homepage, msg, friends;
    /********** INIZIO DICHIARAZIONI BOTTOM NAV **********/

    UserFactory uf = UserFactory.getInstance();
    ArrayList <User> playerList = uf.getUserList();
    static ArrayList<User> lista2 = new ArrayList<>();
    static User p;
    User player;
    User notMePlayer;
    RecyclerView recycleMember;
    LinearLayout iconFilters;
    boolean filtersActive =false;
    CheckBox rank2, rank3, rank4, rank5, rank6, rank7, rank8, rank9, rank10;
    CheckBox ruolo1, ruolo2, ruolo3, ruolo4, ruolo5;
    CheckBox livello1, livello2, livello3, livello4, livello5, livello6;
    CheckBox eta1, eta2, eta3, eta4, eta5, eta6;
    Boolean hasNoTeam = false;
    Button ricercaBottone;
    LinearLayout filterLayout;
    TextView risultati;
    RadioGroup radioGroup;

    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);

        Intent i = getIntent();
        player = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));
        notMePlayer = uf.getUtenteByID(i.getExtras().getInt("notMePlayer"));
        p = uf.getUtenteByID(i.getExtras().getInt("currentUserID"));

        /********** INIZIO CODICE TOOLBAR **********/
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        /********** FINE CODICE TOOLBAR **********/

        search = findViewById(R.id.search);
        iconFilters = findViewById(R.id.icon_filters);
        filterLayout = findViewById(R.id.filterLayout);
        risultati = findViewById(R.id.risultati);
        radioGroup = findViewById(R.id.radioGroup);

        homepage = findViewById(R.id.bottomHomeProfile);
        msg = findViewById(R.id.bottomMsgProfile);
        friends = findViewById(R.id.bottomFriendsProfile);

        lista2.clear();
        for(User u : playerList)
            lista2.add(u);

        LinearLayoutManager layoutMngr = new LinearLayoutManager(this);
        recycleMember = findViewById(R.id.recycleMember);
        recycleMember.setHasFixedSize(true);
        recycleMember.setLayoutManager(layoutMngr);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycleMember.getContext(),
                layoutMngr.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(recycleMember.getContext(), R.drawable.linea_separator));
        recycleMember.addItemDecoration(dividerItemDecoration);

        rank2 = findViewById(R.id.rank2);
        rank3 = findViewById(R.id.rank3);
        rank4 = findViewById(R.id.rank4);
        rank5 = findViewById(R.id.rank5);
        rank6 = findViewById(R.id.rank6);
        rank7 = findViewById(R.id.rank7);
        rank8 = findViewById(R.id.rank8);
        rank9 = findViewById(R.id.rank9);
        rank10 = findViewById(R.id.rank10);

        ruolo1 = findViewById(R.id.ruolo1);
        ruolo2 = findViewById(R.id.ruolo2);
        ruolo3 = findViewById(R.id.ruolo3);
        ruolo4 = findViewById(R.id.ruolo4);
        ruolo5 = findViewById(R.id.ruolo5);

        livello1 = findViewById(R.id.livello1);
        livello2 = findViewById(R.id.livello2);
        livello3 = findViewById(R.id.livello3);
        livello4 = findViewById(R.id.livello4);
        livello5 = findViewById(R.id.livello5);
        livello6 = findViewById(R.id.livello6);

        eta1 = findViewById(R.id.eta1);
        eta2 = findViewById(R.id.eta2);
        eta3 = findViewById(R.id.eta3);
        eta4 = findViewById(R.id.eta4);
        eta5 = findViewById(R.id.eta5);
        eta6 = findViewById(R.id.eta6);

        ricercaBottone = findViewById(R.id.ricercaBottone);

        Intent callerClass = new Intent(this, AdapterMembers.class);
        callerClass.putExtra("callerClass", 3);

        final AdapterMembers adapter = new AdapterMembers(lista2, callerClass);
        recycleMember.setAdapter(adapter);

        ricercaBottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                risultati.setVisibility(View.GONE);
                adapter.filterMode(getRank(), getRuoli(), getLivello(), getEta(), hasNoTeam);
                if(adapter.getListaAdapter().size()==0)
                    risultati.setVisibility(View.VISIBLE);
                filterLayout.setVisibility(View.GONE);
            }
        });

        iconFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svuotaFiltri();
                filtersActive = true;
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
                Intent home = new Intent(Search_player.this, Home.class);
                home.putExtra("currentUserID", player.getIdPlayer());
                startActivity(home);
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendsList = new Intent(Search_player.this, FriendsList.class);
                friendsList.putExtra("currentUserID", player.getIdPlayer());
                friendsList.putExtra("notMePlayer", player.getIdPlayer());
                startActivity(friendsList);
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent(Search_player.this, ListaChat.class);
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



    private ArrayList<Team.Rank> getRank() {
        ArrayList<Team.Rank> listToReturn = new ArrayList<>();
        if (rank2.isChecked()) {
            listToReturn.add(Team.Rank.Iron);
        }
        if (rank3.isChecked()) {
            listToReturn.add(Team.Rank.Bronze);
        }
        if (rank4.isChecked()) {
            listToReturn.add(Team.Rank.Silver);
        }
        if (rank5.isChecked()) {
            listToReturn.add(Team.Rank.Gold);
        }
        if (rank6.isChecked()) {
            listToReturn.add(Team.Rank.Platinum);
        }
        if (rank7.isChecked()) {
            listToReturn.add(Team.Rank.Diamond);
        }
        if (rank8.isChecked()) {
            listToReturn.add(Team.Rank.Master);
        }
        if (rank9.isChecked()) {
            listToReturn.add(Team.Rank.Grandmaster);
        }
        if (rank10.isChecked()) {
            listToReturn.add(Team.Rank.Challenger);
        }
        return listToReturn;
    }

    private ArrayList<String> getRuoli(){
        ArrayList<String> listToReturn = new ArrayList<>();

        if (ruolo1.isChecked()) {
            listToReturn.add("Toplaner");
        }
        if (ruolo2.isChecked()) {
            listToReturn.add("Jungler");
        }
        if (ruolo3.isChecked()) {
            listToReturn.add("Midlaner");
        }
        if (ruolo4.isChecked()) {
            listToReturn.add("Botlaner");
        }
        if (ruolo5.isChecked()) {
            listToReturn.add("Support");
        }
        return listToReturn;
    }

    private ArrayList<String> getLivello() {
        ArrayList<String> listToReturn = new ArrayList<>();
        if (livello1.isChecked()) {
            listToReturn.add("less30");
        }
        if (livello2.isChecked()) {
            listToReturn.add("30_59");
        }
        if (livello3.isChecked()) {
            listToReturn.add("60_89");
        }
        if (livello4.isChecked()) {
            listToReturn.add("90_119");
        }
        if (livello5.isChecked()) {
            listToReturn.add("120_149");
        }
        if (livello6.isChecked()) {
            listToReturn.add("over150");
        }
        return listToReturn;
    }

    private ArrayList<String> getEta() {
        ArrayList<String> listToReturn = new ArrayList<>();
        if (eta1.isChecked()) {
            listToReturn.add("less18");
        }
        if (eta2.isChecked()) {
            listToReturn.add("18_24");
        }
        if (eta3.isChecked()) {
            listToReturn.add("25_29");
        }
        if (eta4.isChecked()) {
            listToReturn.add("30_34");
        }
        if (eta5.isChecked()) {
            listToReturn.add("35_39");
        }
        if (eta6.isChecked()) {
            listToReturn.add("over40");
        }
        return listToReturn;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio1:
                if (checked)
                    hasNoTeam = true;
                break;
            case R.id.radio2:
                if (checked)
                    hasNoTeam = false;
                break;
        }
    }

    private void svuotaFiltri() {
        hasNoTeam = false;
        if(rank3.isChecked())
            rank3.toggle();
        if(rank4.isChecked())
            rank4.toggle();
        if(rank5.isChecked())
            rank5.toggle();
        if(rank6.isChecked())
            rank6.toggle();
        if(rank7.isChecked())
            rank7.toggle();
        if(rank8.isChecked())
            rank8.toggle();
        if(rank9.isChecked())
            rank9.toggle();
        if(rank10.isChecked())
            rank10.toggle();
        if(rank2.isChecked())
            rank2.toggle();

        if(ruolo1.isChecked())
            ruolo1.toggle();
        if(ruolo2.isChecked())
            ruolo2.toggle();
        if(ruolo3.isChecked())
            ruolo3.toggle();
        if(ruolo4.isChecked())
            ruolo4.toggle();
        if(ruolo5.isChecked())
            ruolo5.toggle();

        if(livello1.isChecked())
            livello1.toggle();
        if(livello2.isChecked())
            livello2.toggle();
        if(livello3.isChecked())
            livello3.toggle();
        if(livello4.isChecked())
            livello4.toggle();
        if(livello5.isChecked())
            livello5.toggle();
        if(livello6.isChecked())
            livello6.toggle();

        if(eta1.isChecked())
            eta1.toggle();
        if(eta2.isChecked())
            eta2.toggle();
        if(eta3.isChecked())
            eta3.toggle();
        if(eta4.isChecked())
            eta4.toggle();
        if(eta5.isChecked())
            eta5.toggle();
        if(eta6.isChecked())
            eta6.toggle();
        radioGroup.clearCheck();

    }

}


package com.example.bacci.lolteammanager;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class AdapterTeams extends RecyclerView.Adapter<AdapterTeams.TeamViewHolder> {
    TeamFactory tf = TeamFactory.getInstance();
    Intent classe;
    ArrayList<Team> teamList = new ArrayList<>();
    ArrayList<Team> searchList = tf.getTeamList();

    AdapterTeams(ArrayList<Team> teamList, Intent classe) {

        this.teamList = teamList;
        this.classe = classe;
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name, member;

        TeamViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardViewMember);
            name = itemView.findViewById(R.id.memberName);
            member = itemView.findViewById(R.id.member);
        }
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.textview_member_list, viewGroup, false);
        TeamViewHolder pvh = new TeamViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder teamViewHolder, int j) {
        User notMePlayer = Search_Team.p;
        teamViewHolder.name.setText(teamList.get(j).getNome());
        AnnuncioFactory af = AnnuncioFactory.getInstance();
        ArrayList<Annuncio> annuncioList = new ArrayList<>();

        String str;
        Annuncio annTeam = af.getAnnByTeamName(teamList.get(j).getNome());
        if (annTeam!= null) {
            int cont =0;
            str = "\nRuolo cercato: ";
            for (String s : annTeam.getRuolo()) {
                cont++;
                str = str + s;
                if (annTeam.getRuolo().size()!=cont)
                    str= str + ", ";
            }
            str = str + "\nRank minimo richiesto: " + annTeam.getRankMinimo();
            str = str + "\nRank massimo richiesto: " + annTeam.getRankMassimo();
        }
        else {
            str = "\nLa squadra non è in cerca di giocatori.";
        }

        teamViewHolder.member.setText(str);

        teamViewHolder.member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(v.getContext(), TeamProfile.class);

                /*int k = classe.getExtras().getInt("callerClass");

                switch(k){
                    case 1: profile.putExtra("currentUserID", TeamMembers.p.getIdPlayer()); break;
                    // case 2: profile.putExtra("currentUserID", FriendsList.p.getIdPlayer()); break;
                    case 3: profile.putExtra("currentUserID", Search_player.p.getIdPlayer()); break;
                    case 4: profile.putExtra("currentUserID", Search_Team.p.getIdPlayer());break;
                    default: break;
                }*/

                profile.putExtra("currentUserID", Search_Team.p.getIdPlayer());
                profile.putExtra("notMePlayer", teamList.get(j).getCaptain().getIdPlayer());
                profile.putExtra("callerClass", 0);

                v.getContext().startActivity(profile);
            }
        });

    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase();
        Search_Team.lista.clear();
        if (charText.length() == 0) {
            Search_Team.lista.addAll(searchList);
        } else {
            for (Team t : searchList) {
                if (t.getNome().toLowerCase().contains(charText)) {
                    Search_Team.lista.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static int assegnaIntRank(Team.Rank r) {
        int num=0;
        switch (r) {
            case Unranked: num = 0; break;
            case Iron: num = 1; break;
            case Bronze: num = 2; break;
            case Silver: num = 3; break;
            case Gold: num = 4; break;
            case Platinum: num = 5; break;
            case Diamond: num = 6; break;
            case Master: num = 7; break;
            case Grandmaster: num = 8; break;
            case Challenger: num = 9; break;
        }
        return num;
    }
    public void filterMode(String rankSelected, ArrayList<String> lingue, Boolean teamIncomplete,
                           ArrayList<String> voice) {
        Search_Team.lista.clear();
        ArrayList<Team> filteredList = new ArrayList<>();
        ArrayList<Team> okList = new ArrayList<>();
        AnnuncioFactory af =  AnnuncioFactory.getInstance();
        int rank = stringToInt(rankSelected);
        Boolean flag = false;

        if(rank==0) {
            filteredList.addAll(searchList);
        }
        else {
            int rMinTeam;
            int rMaxTeam;
            for (Team t : searchList) {
                if(af.getAnnByTeamName(t.getNome())!= null) {
                    rMinTeam = stringToInt(af.getAnnByTeamName(t.getNome()).getRankMinimo());
                    rMaxTeam = stringToInt(af.getAnnByTeamName(t.getNome()).getRankMassimo());
                    if(rank >= rMinTeam && rank <= rMaxTeam) {
                        filteredList.add(t);
                    }
                }
            }
        }
        okList.addAll(filteredList);

        if (lingue.size()!=0) {
            for (Team t : filteredList) {
                for (String l : lingue) {
                    for (String lTeam : t.getLingue()) {
                        if(lTeam.toLowerCase().equals(l.toLowerCase()))
                            flag = true;
                    }
                }
                if (!flag)
                    okList.remove(t);
                flag = false;
            }
        }
        filteredList.clear();
        filteredList.addAll(okList);

        if (voice.size()!=0) {
            for (Team t : filteredList) {
                for (String v : voice) {
                    for (String vTeam : t.getVoiceChat()) {
                        if(vTeam.toLowerCase().equals(v.toLowerCase()))
                            flag = true;
                    }
                }
                if (!flag)
                    okList.remove(t);
                flag = false;
            }
        }
        filteredList.clear();
        filteredList.addAll(okList);
        if (teamIncomplete) {
            for (Team t : filteredList) {
                if (t.getnPlayers()==5) {
                    okList.remove(t);
                }
            }
        }
        Search_Team.lista.addAll(okList);
        notifyDataSetChanged();
    }

    private int stringToInt(String s) {
        int t = 0;
        switch (s) {
            case "Tutti": t=0; break;
            case "Unranked": t = 1; break;
            case "Iron": t = 2; break;
            case "Bronze": t = 3; break;
            case "Silver": t = 4; break;
            case "Gold": t = 5; break;
            case "Platinum": t = 6; break;
            case "Diamond": t = 7; break;
            case "Master": t = 8; break;
            case "Grandmaster": t = 9; break;
            case "Challenger": t = 10; break;

        }
        return t;
    }

    public ArrayList<Team> getListaAdapter() {
        return Search_Team.lista;
    }
}


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

public class AdapterMembers extends RecyclerView.Adapter<AdapterMembers.MemberViewHolder>{
    UserFactory uf = UserFactory.getInstance();
    Intent classe;
    ArrayList<User> memberList = new ArrayList<>();
    ArrayList<User> searchList = uf.getUserList();

    AdapterMembers(ArrayList<User> memberList, Intent classe){
        this.memberList = memberList;
        this.classe = classe;
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name, member;

        MemberViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardViewMember);
            name = itemView.findViewById(R.id.memberName);
            member = itemView.findViewById(R.id.member);
        }
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.textview_member_list, viewGroup, false);
        MemberViewHolder pvh = new MemberViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder memberViewHolder, int j) {
        User notMePlayer = memberList.get(j);
        memberViewHolder.name.setText(memberList.get(j).getInGameName());

        ArrayList<Double> kdaList = memberList.get(j).getKDA();
        String str = "\nRuolo: " + stampaRuoli(memberList.get(j))
                + "\nRank: " + memberList.get(j).getRank() + " " + memberList.get(j).getDivisione()
                + "\nLivello: " + memberList.get(j).getLivello() + "\nKDA: ";

        for(int k = 0; k < kdaList.size(); k++){
            if (k == kdaList.size()-1) str = str + kdaList.get(k).toString();
            else str = str + kdaList.get(k).toString() + " / ";
        }

        memberViewHolder.member.setText(str);

        memberViewHolder.member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(v.getContext(), Profile.class);

                int k = classe.getExtras().getInt("callerClass");

                switch(k){
                    case 1: profile.putExtra("currentUserID", TeamMembers.p.getIdPlayer()); break;
                    case 2: profile.putExtra("currentUserID", FriendsList.p.getIdPlayer()); break;
                    case 3: profile.putExtra("currentUserID", Search_player.p.getIdPlayer()); break;
                    case 4: profile.putExtra("currentUserID", Search_Team.p.getIdPlayer());break;
                    default: break;
                }

                profile.putExtra("notMePlayer", notMePlayer.getIdPlayer());

                v.getContext().startActivity(profile);
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase();
        Search_player.lista2.clear();
        if (charText.length() == 0) {
            Search_player.lista2.addAll(searchList);
        } else {
            for (User u : searchList) {
                System.out.println("OK");
                if (u.getInGameName().toLowerCase().contains(charText)) {
                    Search_player.lista2.add(u);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterMode(ArrayList<Team.Rank> rankList, ArrayList<String> rolesList, ArrayList<String> level_list,
                           ArrayList<String> ageList, Boolean hasNoTeam) {
        Search_player.lista2.clear();
        ArrayList<User> listFiltered = new ArrayList<>();
        ArrayList<User> okList = new ArrayList<>();
        if (rankList.size()==0 && rolesList.size()==0 && level_list.size()==0 && ageList.size()==0 && hasNoTeam == false) {
            Search_player.lista2.addAll(searchList);
        }
        else {
            boolean ok = false, flag = false;

            listFiltered.addAll(searchList);

            if(rankList.size()>0){
                for (User u : searchList) {
                    for (Team.Rank tr : rankList) {
                        if (u.getRank() == tr) {
                            flag=true;
                        }
                    }
                    if(flag==false){
                        listFiltered.remove(u);
                    }
                    flag=false;
                }
            }

            okList.addAll(listFiltered);

            //size=2

            if(rolesList.size()>0) {
                for (User u : listFiltered) {
                    for (int i = 0; (i < rolesList.size()) || flag == false; i++) {
                        ArrayList<String> ruoliU = u.getRuoli();
                        for (String ru : ruoliU) {
                            if (rolesList.get(i).toLowerCase().equals(ru.toLowerCase())) {
                                ok = true;
                                flag = true;
                            }
                        }

                        if ((rolesList.size() == i+1) && flag == false)
                            flag = true;
                    }

                    if (ok == false) okList.remove(u);
                    ok = false;
                    flag = false;
                }
            }

            listFiltered.clear();
            listFiltered.addAll(okList);

            okList.clear();
            okList.addAll(listFiltered);

            if(level_list.size()>0) {
                for (User u : listFiltered) {
                    for (int i = 0; (i < level_list.size()) || flag == false; i++) {
                        switch (level_list.get(i)) {
                            case "less30":
                                if (u.getLivello() < 30) {
                                    ok = true;
                                    flag = true;
                                }
                                break;
                            case "30_59":
                                if (u.getLivello() >= 30 && u.getLivello() <= 59) {
                                    ok = true;
                                    flag = true;
                                }
                                break;
                            case "60_89":
                                if (u.getLivello() >= 60 && u.getLivello() <= 89) {
                                    ok = true;
                                    flag = true;
                                }
                                break;
                            case "90_119":
                                if (u.getLivello() >= 90 && u.getLivello() <= 119) {
                                    ok = true;
                                    flag = true;
                                }
                                break;
                            case "120_149":
                                if (u.getLivello() >= 120 && u.getLivello() <= 149) {
                                    ok = true;
                                    flag = true;
                                }
                                break;
                            case "over150":
                                if (u.getLivello() >= 150) {
                                    ok = true;
                                    flag = true;
                                }
                                break;
                        }
                        if ((level_list.size() == i+1) && flag == false)
                            flag = true;
                    }

                    if (ok == false) okList.remove(u);
                    ok = false;
                    flag = false;
                }
            }

            listFiltered.clear();
            listFiltered.addAll(okList);

            okList.clear();
            okList.addAll(listFiltered);

            System.out.println("size:"+ ageList.size());

            if(ageList.size()>0) {
                for (User u : listFiltered) {
                    for (int i = 0; (i < ageList.size()) || flag == false; i++) {
                        switch (ageList.get(i)) {
                            case "less18":
                                if (u.getEta() < 18) {
                                    ok = true;
                                    flag = true;
                                }
                                break;
                            case "18_24":
                                if (u.getEta() >= 18 && u.getEta() <= 24) {
                                    ok = true;
                                    flag = true;
                                }
                                break;
                            case "25_29":
                                if (u.getEta() >= 25 && u.getEta() <= 29) {
                                    ok = true;
                                    flag = true;
                                }
                                break;
                            case "30_34":
                                if (u.getEta() >= 30 && u.getEta() <= 34) {
                                    ok = true;
                                    flag = true;
                                }
                                break;
                            case "35_39":
                                if (u.getEta() >= 35 && u.getEta() <= 39) {
                                    ok = true;
                                    flag = true;
                                }
                                break;
                            case "over40":
                                if (u.getEta() >= 40) {
                                    ok = true;
                                    flag = true;
                                }
                                break;

                        }
                        if ((ageList.size() == i+1) && flag == false)
                            flag = true;
                    }

                    if (ok == false) okList.remove(u);
                    ok = false;
                    flag = false;
                }
            }


            listFiltered.clear();
            listFiltered.addAll(okList);

            okList.clear();
            okList.addAll(listFiltered);

            if(hasNoTeam){
                for (User u : listFiltered) {
                    if (u.getSquadra() == hasNoTeam)
                        okList.remove(u);
                }
            }
            Search_player.lista2.addAll(okList);
        }
        notifyDataSetChanged();
    }

    public ArrayList<User> getListaAdapter() {
        return Search_player.lista2;
    }

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
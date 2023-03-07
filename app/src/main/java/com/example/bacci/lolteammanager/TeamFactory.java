package com.example.bacci.lolteammanager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class TeamFactory{
    private static TeamFactory instance;
    private static ArrayList<Team> teamList = new ArrayList<>();
    UserFactory uf = UserFactory.getInstance();

    private TeamFactory(){
        Team team1 = new Team();
        Team team2 = new Team();

        team1.setNome("IUMmy Team");
        team1.addPlayer(uf.getUtenteByID(1));
        team1.addPlayer(uf.getUtenteByID(2));
        team1.addPlayer(uf.getUtenteByID(3));
        team1.addPlayer(uf.getUtenteByID(4));
        team1.addPlayer(uf.getUtenteByID(5));
        team1.setCaptain(uf.getUtenteByID(1));
        team1.setRankMinMax(new ArrayList<Team.Rank>(Arrays.asList(Team.Rank.Unranked, Team.Rank.Silver)));
        team1.setLingue(new ArrayList<String>(Arrays.asList("Italiano", "Inglese")));
        team1.setVoiceChat(new ArrayList<String>(Arrays.asList("Discord", "Team Speak", "LoL VoiceChat")));
        team1.setIsNew(false);

        team2.setNome("Reformed Team");
        team2.addPlayer(uf.getUtenteByID(6));
        team2.addPlayer(uf.getUtenteByID(7));
        team2.addPlayer(uf.getUtenteByID(8));
        team2.addPlayer(uf.getUtenteByID(9));
        team2.setCaptain(uf.getUtenteByID(6));
        team2.setRankMinMax(new ArrayList<Team.Rank>(Arrays.asList(Team.Rank.Silver, Team.Rank.Gold)));
        team2.setLingue(new ArrayList<String>(Arrays.asList("Italiano")));
        team2.setVoiceChat(new ArrayList<String>(Arrays.asList("Discord", "Team Speak")));
        team2.setIsNew(false);

        teamList.add(team1);
        teamList.add(team2);
    }

    public static TeamFactory getInstance(){
        if (instance == null)
            instance = new TeamFactory();
        return instance;
    }

    public static ArrayList<Team> getTeamList() {
        return teamList;
    }

    public Team getTeamById(int id){
        for(Team t : teamList)
            if (t.getIdTeam() == id) return t;

        return null;
    }

    public ArrayList<Team> getCompleteTeams() {
        ArrayList<Team> listToReturn = new ArrayList<>();
        for (Team t : teamList) {
            if (t.getnPlayers()==5)
                listToReturn.add(t);
        }
        return listToReturn;
    }

}

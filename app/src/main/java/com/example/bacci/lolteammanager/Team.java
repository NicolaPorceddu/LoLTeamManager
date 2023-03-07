package com.example.bacci.lolteammanager;

import java.util.ArrayList;

public class Team{
    public enum Rank {
        Unranked,
        Iron,
        Bronze,
        Silver,
        Gold,
        Platinum,
        Diamond,
        Master,
        Grandmaster,
        Challenger
    }

    private LingueVoiceChat l = new LingueVoiceChat();
    private static int ID=0;
    private int idTeam;
    private User captain;
    private String nome;
    private int nPlayers;
    private boolean isNew;
    private ArrayList<User> players;
    private ArrayList<Rank> rankMinMax;
    private ArrayList<String> lingue;
    private ArrayList<String> voiceChat;
    private ArrayList<MyEventDay> eventsList;

    public Team(){
        ID++;
        this.setIdTeam(ID);
        players = new ArrayList<>();
        nPlayers = players.size();
        rankMinMax = new ArrayList<>();
        lingue = new ArrayList<>();
        voiceChat = new ArrayList<>();
        this.setIsNew(true);
        eventsList = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getnPlayers() {
        return nPlayers;
    }

    public void setnPlayers(int nPlayers){this.nPlayers = nPlayers;}

    public ArrayList<User> getPlayers() {
        return players;
    }

    public void addPlayer(User player){
        this.players.add(player);
        nPlayers = players.size();
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public User getCaptain() {
        return captain;
    }

    public void setCaptain(User captain) {
        this.captain = captain;
    }

    public ArrayList<Rank> getRankMinMax() {
        return rankMinMax;
    }

    public void setRankMinMax(ArrayList<Rank> rankMinMax) {
        this.rankMinMax = rankMinMax;
    }

    public ArrayList<String> getLingue(){
        return lingue;
    }

    public void setLingue(ArrayList<String> lingue) {
        this.lingue = lingue;
    }

    public ArrayList<String> getVoiceChat(){
        return voiceChat;
    }

    public void setVoiceChat(ArrayList<String> voiceChat) {
        this.voiceChat = voiceChat;
    }

    public boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(boolean aNew) {
        isNew = aNew;
    }

    public void addEvent(MyEventDay event){ this.eventsList.add(event); }

    public ArrayList<MyEventDay> getEventsList(){ return eventsList; }
}

package com.example.bacci.lolteammanager;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static int ID=0;
    private int idPlayer;
    private String username;
    private String inGameName;
    private String email;
    private String password;
    private Team.Rank rank;
    private ArrayList<String> ruoli;
    private boolean squadra;
    private int livello;
    private int idSquadra;
    private int eta;
    private int divisione;
    private ArrayList<User> friendList;
    private ArrayList<Double> KDA;
    private ArrayList<Message> myMsg;

    public User(){
        ID++;
        this.setIdPlayer(ID);
        ruoli = new ArrayList<>();
        friendList = new ArrayList<>();
        KDA = new ArrayList<>();
        setMyMsg(new ArrayList<>());
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInGameName() {
        return inGameName;
    }

    public void setInGameName(String inGameName) {
        this.inGameName = inGameName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getRuoli() {
        return ruoli;
    }

    public void setRuoli(ArrayList<String> ruoli) {
        this.ruoli = ruoli;
    }

    public boolean getSquadra() {
        return squadra;
    }

    public void setSquadra(boolean squadra) {
        this.squadra = squadra;
    }

    public int getIdSquadra() {
        return idSquadra;
    }

    public void setIdSquadra(int idSquadra) {
        this.idSquadra = idSquadra;
    }

    public ArrayList<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<User> friendList) {
        this.friendList = friendList;
    }

    public ArrayList<Double> getKDA() {
        return KDA;
    }

    public void setKDA(ArrayList<Double> KDA) {
        this.KDA = KDA;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Team.Rank getRank() {
        return rank;
    }

    public void setRank(Team.Rank rank) {
        this.rank = rank;
    }

    public int getDivisione() {
        return divisione;
    }

    public void setDivisione(int divisione) {
        this.divisione = divisione;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public ArrayList<Message> getMyMsg() {
        return myMsg;
    }

    public void setMyMsg(ArrayList<Message> myMsg) {
        this.myMsg = myMsg;
    }
}

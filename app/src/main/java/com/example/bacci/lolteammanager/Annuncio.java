package com.example.bacci.lolteammanager;

import java.util.ArrayList;

public class Annuncio {
    private String teamName;
    private ArrayList<String> ruoli;
    private String rankMinimo;
    private  String rankMassimo;

    Annuncio(){

    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<String> getRuolo() {
        return ruoli;
    }

    public void setRuolo(ArrayList<String> ruoli) {
        this.ruoli = ruoli;
    }

    public String getRankMinimo() {
        return rankMinimo;
    }

    public void setRankMinimo(String rankMinimo) {
        this.rankMinimo = rankMinimo;
    }

    public String getRankMassimo() {
        return rankMassimo;
    }

    public void setRankMassimo(String rankMassimo) {
        this.rankMassimo = rankMassimo;
    }
}

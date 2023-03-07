package com.example.bacci.lolteammanager;

import java.util.ArrayList;
import java.util.Arrays;

public class AnnuncioFactory {
    private static AnnuncioFactory instance;
    private static ArrayList<Annuncio> annList = new ArrayList<>();

    public static AnnuncioFactory getInstance(){
        if (instance == null)
            instance = new AnnuncioFactory();
        return instance;
    }

    private AnnuncioFactory(){
        Annuncio ann1 = new Annuncio();
        Annuncio ann2 = new Annuncio();
        Annuncio ann3 = new Annuncio();

        ann1.setTeamName("Reformed Team");
        ann1.setRuolo(new ArrayList<>(Arrays.asList("Support")));
        ann1.setRankMinimo("Bronze");
        ann1.setRankMassimo("Gold");
        annList.add(ann1);

        ann2.setTeamName("Goat Team");
        ann2.setRuolo(new ArrayList<>(Arrays.asList("ADC", "Midlaner", "Jungler")));
        ann2.setRankMinimo("Bronze");
        ann2.setRankMassimo("Silver");
        annList.add(ann2);

        ann3.setTeamName("I fiji de Mazzinga");
        ann3.setRuolo(new ArrayList<>(Arrays.asList("Jungler")));
        ann3.setRankMinimo("Unranked");
        ann3.setRankMassimo("Iron");
        annList.add(ann3);
    }

    public static ArrayList<Annuncio> getAnnList() {
        return annList;
    }

    public static ArrayList<Annuncio> getAnnListByTeam(String name){
        ArrayList<Annuncio> a = new ArrayList<>();

        for(Annuncio ann : annList)
            if(ann.getTeamName().equals(name)) a.add(ann);

        return a;
    }

    public Annuncio getAnnByTeamName(String teamName) {
        for (Annuncio a : annList) {
            if (a.getTeamName().toLowerCase().equals(teamName.toLowerCase()))
                return a;
        }
        return null;
    }

}

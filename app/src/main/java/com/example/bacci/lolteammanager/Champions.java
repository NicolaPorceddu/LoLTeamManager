package com.example.bacci.lolteammanager;

import java.util.Random;

public class Champions {
    private int dim = 6;
    private String[] topChampions = new String[]{"Darius", "Irelia", "Yasuo", "Quinn", "Teemo", "Gnar"};
    private String[] jungleChampions = new String[]{"Evelynn", "Hecarim", "Lee Sin", "Shyvana", "Warwick", "Master Yi"};
    private String[] midChampions = new String[]{"Ahri", "Ekko", "Katarina", "Talon", "Zed", "Azir"};
    private String[] botChampions = new String[]{"Miss Fortune", "Ashe", "Corki", "Jinx", "Sivir", "Vayne"};
    private String[] supportChampions = new String[]{"Braum", "Janna", "Lulu", "Morgana", "Pyke", "Taric"};

    Champions (){

    }

    public String getRandomChamp(String role){
        Random j = new Random();
        switch(role){
            case "Toplaner": return topChampions[j.nextInt(dim)];
            case "Jungler": return jungleChampions[j.nextInt(dim)];
            case "Midlaner": return midChampions[j.nextInt(dim)];
            case "ADC": return botChampions[j.nextInt(dim)];
            case "Support": return supportChampions[j.nextInt(dim)];
            default: break;
        }

        return "";
    }
}

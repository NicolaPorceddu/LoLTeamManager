package com.example.bacci.lolteammanager;

import java.util.Random;

public class Game {
    private int dim = 5;
    private String[] gamesType = new String[]{"Normal5v5", "Normal3v3", "Ranked SoloDuo",
                                                "Ranked Flex", "Ranked Flex 3v3"};

    Game(){

    }

    public String getRandomGame(){
        Random j = new Random();
        return gamesType[j.nextInt(dim)];
    }
}

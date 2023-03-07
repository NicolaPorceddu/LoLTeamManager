package com.example.bacci.lolteammanager;

import java.util.Random;

public class LingueVoiceChat {
    private int dim = 6;
    private int dim2 = 3;
    private String[] lingue = new String[]{"Italiano", "Inglese", "Francese", "Tedesco", "Russo", "Cinese"};
    private String[] voiceChat = new String[]{"Discord", "LoL VoiceChat", "TeamSpeak"};

    LingueVoiceChat (){

    }

    public String getRandomLingua(){
        Random j = new Random();

        return lingue[j.nextInt(dim)];
    }

    public String getRandomVoiceChat(){
        Random j = new Random();

        return voiceChat[j.nextInt(dim2)];
    }
}

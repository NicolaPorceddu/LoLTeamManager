package com.example.bacci.lolteammanager;

import java.util.ArrayList;

public class EventFactory {
    private static EventFactory instance;
    private static ArrayList<MyEventDay> eventsList = new ArrayList<>();

    private EventFactory(){
    }

    public static EventFactory getInstance(){
        if (instance == null)
            instance = new EventFactory();
        return instance;
    }

    public ArrayList<MyEventDay> getList(){
        return eventsList;
    }
}

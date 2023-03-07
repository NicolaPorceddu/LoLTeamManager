package com.example.bacci.lolteammanager;

public class Message {
    private String username;
    private String msg;

    Message(String username, String msg){
        this.setUsername(username);
        this.setMsg(msg);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

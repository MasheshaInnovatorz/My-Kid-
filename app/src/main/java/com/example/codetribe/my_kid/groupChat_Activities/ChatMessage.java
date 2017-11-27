package com.example.codetribe.my_kid.groupChat_Activities;

import java.util.Date;

/**
 * Created by mudau on 11/9/2017.
 */
public class ChatMessage {

    String message;
    String name;
    long time;
    Boolean btn;
    String userId;

    public ChatMessage() {
    }

    public ChatMessage(String message, String name, long time, String userId) {
        this.message = message;
        this.name = name;
        this.userId = userId;

        this.time = new Date().getTime();

    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setIsmne(Boolean v) {
        btn = v;
    }

    public Boolean isMine() {

        return btn;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
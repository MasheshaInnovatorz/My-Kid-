package com.example.codetribe.my_kid;

import java.util.Date;

/**
 * Created by mudau on 9/15/2017.
 */

public class Chat {

    String message;
    String name;
    long time;
    //String time;

    public Chat(String message, String user, long time) {
        this.message = message;
        this.name = user;
        this.time = new Date().getTime();
    }

    Chat() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}

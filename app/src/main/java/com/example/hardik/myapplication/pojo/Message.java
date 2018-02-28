package com.example.hardik.myapplication.pojo;

/**
 * Created by Hardik on 1/3/2018.
 */

public class Message {

    public Message(){

    }

    String message;
    long time;
    boolean seen;
    String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Message(String message, long time, boolean seen, String from) {
        this.message = message;
        this.time = time;
        this.seen = seen;
        this.from=from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}



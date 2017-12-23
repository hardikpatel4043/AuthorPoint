package com.example.hardik.myapplication.recycle_home;

/**
 * Created by Hardik on 12/7/2017.
 */

public class Event {
    private String name;
    private String des;
    public Event() {

    }

    public Event(String name,String des) {
        this.name = name;
        this.des = des;

    }
    public String getName(){
        return name;
    }
    public String getDes() {
        return des;
    }


}
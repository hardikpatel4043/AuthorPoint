package com.example.hardik.myapplication.POJO;

/**
 * Created by Hardik on 12/7/2017.
 */

public class Event {
    private String name;
    private String place,time,data,description;

    public Event() {
        //Empty constructor
    }

    public Event(String name, String place, String time, String data, String description) {
        this.name = name;
        this.place = place;
        this.time = time;
        this.data = data;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
package com.example.hardik.myapplication.recycle_home;

/**
 * Created by Hardik on 12/7/2017.
 */

public class Author {
    private String name;
    private int image;
    public Author() {
    }

    public Author(String title,int image) {
        this.name = title;
        this.image = image;

    }
    public String getName(){
        return name;
    }
    public int getImage() {
        return image;
    }


}


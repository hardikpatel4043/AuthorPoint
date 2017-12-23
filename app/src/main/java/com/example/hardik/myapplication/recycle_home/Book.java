package com.example.hardik.myapplication.recycle_home;

/**
 * Created by Hardik on 12/7/2017.
 */

public class Book {
    private String title;
    private int image;
    public Book() {

    }

    public Book(String title,int image) {
        this.title = title;
        this.image = image;

    }
    public String getTitle(){
        return title;
    }
    public int getImage() {
        return image;
    }


}

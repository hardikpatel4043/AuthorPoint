package com.example.hardik.myapplication.recycle_home;

/**
 * Created by Hardik on 12/20/2017.
 */

public class AuthorData {
    String imageUrl;
    String name;

    public AuthorData(){

    }
    public AuthorData(String name,String imageUrl){
        this.name=name;
        this.imageUrl=imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}

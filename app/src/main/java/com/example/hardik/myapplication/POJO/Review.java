package com.example.hardik.myapplication.POJO;

/**
 * Created by Hardik on 12/30/2017.
 */

public class Review {

    public Review(String review) {
        this.review = review;
    }

    private String review;

    public  Review(){

    }
    public String getReview ()
    {
        return review;
    }

    public void setReview (String msg)
    {
        this.review = msg;
    }


}

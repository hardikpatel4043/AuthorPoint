package com.example.hardik.myapplication.POJO;

/**
 * Created by Hardik on 12/25/2017.
 */
public class Reader {
    private String buyBook;

    private String email;

    private String name;

    private String mobileNumber;

    private Follow follow=null;

    public Reader(){

    }

    public Reader(String email,String name,String mobileNumber,String buyBook,Follow follow){

        this.buyBook=buyBook;
        this.email=email;
        this.name=name;
        this.mobileNumber=mobileNumber;
        this.follow=follow;

    }
    public String getBuyBook() {
        return buyBook;
    }

    public void setBuyBook(String buyBook) {
        this.buyBook = buyBook;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Follow getFollow() {
        return follow;
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }
}
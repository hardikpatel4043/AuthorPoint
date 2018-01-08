package com.example.hardik.myapplication.POJO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hardik on 12/30/2017.
 */

public class Review implements Parcelable {

    private String review;

    public Review(String review) {
        this.review = review;
    }

    public  Review(){

    }

    protected Review(Parcel in) {
        review = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(review);
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

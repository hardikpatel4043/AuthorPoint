package com.example.hardik.myapplication.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hardik on 12/27/2017.
 */
    public class Author implements Parcelable{

        public Author(){

        }

    protected Author(Parcel in) {
        phone = in.readString();
        follower = in.readString();
        online = in.readString();
        buyBook = in.readString();
        email = in.readString();
        name = in.readString();
        image = in.readString();
        ownBook = in.readString();
        type=in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(phone);
        parcel.writeString(follower);
        parcel.writeString(online);
        parcel.writeString(buyBook);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeString(ownBook);
        parcel.writeString(type);
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    public String isOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Author(String phone, String follower, String buyBook, String email, String name, String image, String ownBook, String online, String type) {
        this.phone = phone;
        this.follower = follower;
        this.buyBook = buyBook;
        this.email = email;
        this.name = name;
        this.image = image;
        this.ownBook = ownBook;
        this.online=online;
        this.type=type;
        }

        private String type;

        private String phone;

        private String follower;

        private String online;

        private String buyBook;

        private String email;

        private String name;

        private String image;

        private String ownBook;

        public String getPhone ()
        {
            return phone;
        }

        public void setPhone (String phone)
        {
            this.phone = phone;
        }

        public String getFollower ()
        {
            return follower;
        }

        public void setFollower (String follower)
        {
            this.follower = follower;
        }

        public String getBuyBook ()
        {
            return buyBook;
        }

        public void setBuyBook (String buyBook)
        {
            this.buyBook = buyBook;
        }

        public String getEmail ()
        {
            return email;
        }

        public void setEmail (String email)
        {
            this.email = email;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getImage ()
        {
            return image;
        }

        public void setImage (String image)
        {
            this.image = image;
        }

        public String getOwnBook ()
        {
            return ownBook;
        }

        public void setOwnBook (String ownBook)
        {
            this.ownBook = ownBook;
        }

       @Override

       public int describeContents() {
        return 0;
    }


}


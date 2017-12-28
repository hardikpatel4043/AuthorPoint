package com.example.hardik.myapplication.POJO;

/**
 * Created by Hardik on 12/27/2017.
 */
    public class AuthorRegister {
        private String phone;

        private String follower;

        public AuthorRegister(){

        }

       public AuthorRegister(String phone, String follower, String buyBook, String email, String name, String image, String ownBook) {
        this.phone = phone;
        this.follower = follower;
        this.buyBook = buyBook;
        this.email = email;
        this.name = name;
        this.image = image;
        this.ownBook = ownBook;
        }

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
    }


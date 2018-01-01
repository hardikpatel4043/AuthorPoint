package com.example.hardik.myapplication.POJO;

/**
 * Created by Hardik on 12/30/2017.
 */

public class Book {

        public Book(){

        }

    public Book(String time, int price, String name, String bookId, String language, String book_type, Review review, String authorId,String image) {
        this.time = time;
        this.price = price;
        this.name = name;
        this.bookId = bookId;
        this.language = language;
        this.book_type = book_type;
        this.review = review;
        this.authorId = authorId;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

         private String time;

         private int price;

        private String name;

        private String bookId;

        private String language;

        private String book_type;

        private Review review;

        private String authorId;

        public String getTime ()
        {
            return time;
        }

        public void setTime (String time)
        {
            this.time = time;
        }

        public int getPrice ()
        {
            return price;
        }

        public void setPrice (int price)
        {
            this.price = price;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getBookId ()
        {
            return bookId;
        }

        public void setBookId (String bookId)
        {
            this.bookId = bookId;
        }

        public String getLanguage ()
        {
            return language;
        }

        public void setLanguage (String language)
        {
            this.language = language;
        }

        public String getBook_type ()
        {
            return book_type;
        }

        public void setBook_type (String book_type)
        {
            this.book_type = book_type;
        }

        public Review getReview ()
        {
            return review;
        }

        public void setReview (Review review)
        {
            this.review = review;
        }

        public String getAuthorId ()
        {
            return authorId;
        }

        public void setAuthorId (String authorId)
        {
            this.authorId = authorId;
        }

    }




package com.example.hardik.myapplication.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hardik on 12/30/2017.
 */

public class Book implements Parcelable {

    public Book(){

    }

    public Book(String image, String time, int price, String name, String bookId, String language, String book_type, Review review, String authorId, String publication, String description, String isbn, int rating) {
        this.image = image;
        this.time = time;
        this.price = price;
        this.name = name;
        this.bookId = bookId;
        this.language = language;
        this.book_type = book_type;
        this.review = review;
        this.authorId = authorId;
        this.publication = publication;
        this.description = description;
        Isbn = isbn;
        this.rating = rating;
    }

    protected Book(Parcel in) {
        name = in.readString();
        image = in.readString();
        time = in.readString();
        price = in.readInt();
        bookId = in.readString();
        language = in.readString();
        book_type = in.readString();
        authorId = in.readString();
        review=in.readParcelable(Review.class.getClassLoader());
        publication=in.readString();
        description=in.readString();
        Isbn=in.readString();
        rating=in.readInt();
        pdfLink=in.readString();
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(this.name);
        parcel.writeString(this.image);
        parcel.writeString(this.time);
        parcel.writeInt(this.price);
        parcel.writeString(this.bookId);
        parcel.writeString(this.language);
        parcel.writeString(this.book_type);
        parcel.writeString(this.authorId);
        parcel.writeParcelable(this.review,i);
        parcel.writeString(this.publication);
        parcel.writeString(this.description);
        parcel.writeString(this.Isbn);
        parcel.writeInt(this.rating);
        parcel.writeString(this.pdfLink);

    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
     };

        private String image;

        private String time;

        private int price;

        private String name;

        private String bookId;

        private String language;

        private String book_type;

        private Review review;

        private String pdfLink;

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return Isbn;
    }

    public void setIsbn(String isbn) {
        Isbn = isbn;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    private String authorId;

        private String publication;

        private String description;

        private String Isbn;

        private int rating;

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
        public String getImage() {
            return image;
         }

        public void setImage(String image) {
            this.image = image;
         }

        public String getPdfLink() {
            return pdfLink;
        }

        public void setPdfLink(String pdfLink) {
            this.pdfLink = pdfLink;
        }

    }






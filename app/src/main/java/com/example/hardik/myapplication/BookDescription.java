package com.example.hardik.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.example.hardik.myapplication.POJO.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDescription extends AppCompatActivity {

    TextView author,title,price,bookType,bookLanguage,publication,isbn;
    RatingBar ratingBar;
    DatabaseReference mRef;
    ImageView bookImage;
    ReadMoreTextView descriptionReadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_description);

        mRef=FirebaseDatabase.getInstance().getReference("author");

        Book book = getIntent().getParcelableExtra("AuthorId");

        Button readButton=findViewById(R.id.book_description_read);
        title=findViewById(R.id.book_description_name);
        author=findViewById(R.id.book_description_author);
        bookImage=findViewById(R.id.book_description_image);
        price=findViewById(R.id.book_description_price);
        bookType=findViewById(R.id.book_description_type);
        bookLanguage=findViewById(R.id.book_description_language);
        isbn=findViewById(R.id.book_description_isbn);
        publication=findViewById(R.id.book_descrpiton_publication);
        ratingBar=findViewById(R.id.book_description_rating);
        descriptionReadMore=findViewById(R.id.readmore);

        title.setText(book.getName());
        Glide.with(getApplicationContext()).load(book.getImage()).into(bookImage);
        price.setText("Rs "+book.getPrice());
        bookType.setText("Book Type : "+book.getBook_type());
        bookLanguage.setText("Book Language : "+book.getLanguage());
        isbn.setText("ISBN No: "+book.getIsbn());
        publication.setText("Publication : "+book.getPublication());
        ratingBar.setRating(book.getRating());
        descriptionReadMore.setText(book.getDescription());

        mRef.child(book.getAuthorId()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String authorName=dataSnapshot.getValue().toString();
                author.setText(authorName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookDescription.this,BookPdfRead.class));
            }
        });

    }
}

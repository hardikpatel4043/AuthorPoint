package com.example.hardik.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hardik.myapplication.POJO.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDescription extends AppCompatActivity {

    TextView author,title;
    DatabaseReference mRef;
    ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_description);

        mRef=FirebaseDatabase.getInstance().getReference("author");

        Book book = getIntent().getParcelableExtra("AuthorId");

        title=findViewById(R.id.book_description_name);
        author=findViewById(R.id.book_description_author);
        bookImage=findViewById(R.id.book_description_image);


        title.setText(book.getName());
        Glide.with(getApplicationContext()).load(book.getImage()).into(bookImage);
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

        Button readButton=findViewById(R.id.book_description_pdf);

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookDescription.this,BookPdfRead.class));
            }
        });

    }
}

package com.example.hardik.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hardik.myapplication.POJO.Book;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookDescription extends AppCompatActivity {

    TextView author,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_description);

        Book book = getIntent().getParcelableExtra("AuthorId");

        title=findViewById(R.id.book_description_name);
        author=findViewById(R.id.book_description_author);

        title.setText(book.getName());
        author.setText(book.getAuthorId());






        Button readButton=findViewById(R.id.book_description_pdf);

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookDescription.this,BookPdfRead.class));
            }
        });

    }
}

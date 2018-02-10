package com.example.hardik.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class BookPdfRead extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_pdf_read);

        PDFView pdfView=findViewById(R.id.pdfView);

        String url= "https://firebasestorage.googleapis.com/v0/b/myapplication-4fcd2.appspot.com/o/BookPdf%2FJava%20IO%20(O'Reilly).pdf?alt=media&token=151479d0-73a5-4c6b-8cb8-c93222932e36";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent newIntent = Intent.createChooser(intent, "Open File");
        try {
            startActivity(newIntent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }

    }
}

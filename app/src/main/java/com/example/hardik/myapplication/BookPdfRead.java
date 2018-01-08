package com.example.hardik.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.InputStream;

public class BookPdfRead extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_pdf_read);

        PDFView pdfView=findViewById(R.id.pdfView);


        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"resume_latest.pdf");
        Uri path = Uri.fromFile(file);
        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenintent.setDataAndType(path, "application/pdf");

        try {
            startActivity(pdfOpenintent);
        }
        catch (Exception e) {
            Log.e("file","file not found");
        }


//        try{
//            InputStream stream = getAssets().open("");
//            pdfView.fromStream(stream);
//        }catch(Exception e){
//            Log.e("file","file not found");
//        }



    }
}

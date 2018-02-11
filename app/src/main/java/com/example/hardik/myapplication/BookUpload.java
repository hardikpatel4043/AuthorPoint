package com.example.hardik.myapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.w3c.dom.Text;

import java.net.URL;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookUpload extends Fragment {

    private ImageView uploadPdf,uploadImage;
    private TextInputLayout bookImage,bookPdf;

    private final int PICK_IMAGE_REQUEST=10;
    private final int PICK_PDF_REQUEST=11;
    private Uri filePath;
    private Uri pdfpath;

    public BookUpload() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.book_upload, container, false);

        uploadImage=rootView.findViewById(R.id.upload_image_button);
        uploadPdf=rootView.findViewById(R.id.upload_pdf_button);
        bookImage=rootView.findViewById(R.id.book_upload_image);
        bookPdf=rootView.findViewById(R.id.book_upload_select_pdf);



        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();

                }
        });

        uploadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooosePDF();
            }
        });
        return rootView;
    }

    public void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void chooosePDF(){

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                bookImage.getEditText().setText(filePath.toString());
               Log.e(">>>",""+filePath);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if(requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK
                 && data !=null && data.getData() !=null)
        {
            pdfpath = data.getData();

            try {
                bookPdf.getEditText().setText(pdfpath.toString());
                Log.e(">>>>>", "" + pdfpath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

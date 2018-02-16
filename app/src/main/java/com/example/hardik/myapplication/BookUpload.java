package com.example.hardik.myapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hardik.myapplication.POJO.Book;
import com.example.hardik.myapplication.POJO.CheckNetwork;
import com.example.hardik.myapplication.POJO.Review;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookUpload extends Fragment {

    private ImageView uploadPdf,uploadImage;
    private TextInputLayout bookImage,bookPdf,name,type,language,publication,price,description,ISBN;
    private Button submit;

    private final int PICK_IMAGE_REQUEST=10;
    private final int PICK_PDF_REQUEST=11;
    private Uri filePath;
    private Uri pdfpath;

    //Firebase
    DatabaseReference mBookRef;
    StorageReference mRootStorage;
    FirebaseUser mCurrentUser;
    String getBookUrl;

    public BookUpload() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.book_upload, container, false);

        getActivity().setTitle("Upload Book");
        mBookRef= FirebaseDatabase.getInstance().getReference().child("book");
        mRootStorage= FirebaseStorage.getInstance().getReference();
        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();

        uploadImage=rootView.findViewById(R.id.upload_image_button);
        uploadPdf=rootView.findViewById(R.id.upload_pdf_button);
        bookImage=rootView.findViewById(R.id.book_upload_image);
        bookPdf=rootView.findViewById(R.id.book_upload_select_pdf);
        name=rootView.findViewById(R.id.book_upload_name);
        type=rootView.findViewById(R.id.book_upload_type);
        language=rootView.findViewById(R.id.book_upload_language);
        publication=rootView.findViewById(R.id.book_upload_publication);
        price=rootView.findViewById(R.id.book_upload_price);
        description=rootView.findViewById(R.id.book_upload_description);
        ISBN=rootView.findViewById(R.id.book_upload_ISBN);

        submit=rootView.findViewById(R.id.book_upload_submit);

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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check Internet Connection
                if(!CheckNetwork.isInternetAvailable(getActivity())){
                    Toast.makeText(getActivity(), "Network is not Available ",Toast.LENGTH_LONG).show();
                }
                String id=mBookRef.push().getKey();
                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                Review fakeReview=new Review("nice book");

                String getPrice=price.getEditText().getText().toString();
                int priceConverted=Integer.parseInt(getPrice);

//                Book bookdataObject=new Book(getBookUrl,timeStamp,priceConverted,name.getEditText().getText().toString(),"bookid",
//                        language.getEditText().getText().toString(),
//                        type.getEditText().getText().toString(),
//                        fakeReview,
//                        mCurrentUser.getUid(),
//                        publication.getEditText().getText().toString(),
//                        description.getEditText().getText().toString(),
//                        ISBN.getEditText().getText().toString(),
//                        4
//                        );
                Map bookData=new HashMap();
                bookData.put("name",name.getEditText().getText().toString());
                bookData.put("book_type",type.getEditText().getText().toString());
                bookData.put("language",language.getEditText().getText().toString());
                bookData.put("description",description.getEditText().getText().toString());
                bookData.put("Isbn",ISBN.getEditText().getText().toString());
                bookData.put("price",priceConverted);
                bookData.put("publication",publication.getEditText().getText().toString());
                bookData.put("authorId",mCurrentUser.getUid());
                bookData.put("bookId","book11");
                bookData.put("image",getBookUrl);
                bookData.put("rating",4);
                bookData.put("time",timeStamp);
                bookData.put("review",fakeReview);

                mBookRef.child(id).setValue(bookData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(),"Uploaded",Toast.LENGTH_SHORT).show();
                    }
                });

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
                mRootStorage.child("BookImages").child(name.getEditText().getText().toString()).putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        getBookUrl=task.getResult().getDownloadUrl().toString();

                    }
                });
                bookImage.getEditText().setText(filePath.toString());

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

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

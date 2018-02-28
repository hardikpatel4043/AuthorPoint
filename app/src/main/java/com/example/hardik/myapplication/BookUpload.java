package com.example.hardik.myapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hardik.myapplication.pojo.CheckNetwork;
import com.example.hardik.myapplication.pojo.Review;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    private ProgressBar progressBar;

    private final int PICK_IMAGE_REQUEST=10;
    private final int PICK_PDF_REQUEST=11;
    private Uri filePath;
    private Uri pdfpath;

    //Firebase
    DatabaseReference mBookRef;
    StorageReference mRootStorage;
    FirebaseUser mCurrentUser;
    String getBookUrl;
    String getPdfUrl;

    public BookUpload() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView=inflater.inflate(R.layout.book_upload, container, false);

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

        progressBar=rootView.findViewById(R.id.progressBar_book_upload);
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

                progressBar.setVisibility(View.VISIBLE);
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                //Check Internet Connection
                if(!CheckNetwork.isInternetAvailable(getActivity())){
                    Toast.makeText(getActivity(), "Network is not Available ",Toast.LENGTH_LONG).show();
                }

                final String id=mBookRef.push().getKey();
                final String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                final Review fakeReview=new Review("nice book");

                String getPrice=price.getEditText().getText().toString();
                final int priceConverted=Integer.parseInt(getPrice);

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

            mRootStorage.child("BookPdf").child(name.getEditText().getText().toString()).putFile(pdfpath)
                     .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> pdfUploadTask) {

                           if(pdfUploadTask.isSuccessful()){
                              getPdfUrl=pdfUploadTask.getResult().getDownloadUrl().toString();

                              //---------------------Image upload to server---------------------------------------
                              mRootStorage.child("BookImages").child(name.getEditText().getText().toString()).putFile(filePath)
                                       .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadImageTask) {

                                               if(uploadImageTask.isSuccessful()){

                                               getBookUrl=uploadImageTask.getResult().getDownloadUrl().toString();

                                                   //----------------------upload book detail to server-------------------------------
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
                                                   bookData.put("pdfLink",getPdfUrl);

                                                   mBookRef.child(id).setValue(bookData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           if(task.isSuccessful()){
                                                               Toast.makeText(getActivity(),"Book Succesfully Uploaded",Toast.LENGTH_SHORT).show();
                                                               progressBar.setVisibility(View.INVISIBLE);
                                                               getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                                           }else{
                                                               Toast.makeText(getActivity(),"Book Upload Error",Toast.LENGTH_SHORT).show();
                                                               progressBar.setVisibility(View.INVISIBLE);
                                                               getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                           }

                                                       }
                                                   });

                                               }

                                           }
                                       });//End of Image upload to server part


                           }else{
                               Toast.makeText(getActivity(), "Error in Uploading Book", Toast.LENGTH_SHORT).show();
                               progressBar.setVisibility(View.INVISIBLE);
                               getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            }//End of pdf upload if else method
                           }
                        });

                
            }
        });//End of setOnClickListener method
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


        //--------------------------Image select option-------------------------
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {

            filePath = data.getData();
            try {

                bookImage.getEditText().setText(filePath.toString());

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        //--------------------------------Pdf select option-----------------------------
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

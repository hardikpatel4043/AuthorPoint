package com.example.hardik.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.example.hardik.myapplication.pojo.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class BookDescription extends AppCompatActivity {

    TextView author,title,price,bookType,bookLanguage,publication,isbn;
    RatingBar ratingBar;
    DatabaseReference mRef;
    ImageView bookImage;
    ReadMoreTextView descriptionReadMore;
    Button buyBook;
    int amountPay;
    StorageReference storageReference;

    //Paypal account
    private static final int PAYPAL_REQUEST_CODE = 7171;
    public static final String PAYPAL_ID = "Ac0qxcNSovWEkKh8rFztCVkDjai7lFMLAPiJy7wrEY2lR9_nks0UrP9oPXSL9P1_KxlAwBpc3t_mJbyB";
    private static PayPalConfiguration config = new PayPalConfiguration().
            environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PAYPAL_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_description);

        mRef=FirebaseDatabase.getInstance().getReference("author");
        storageReference= FirebaseStorage.getInstance().getReference().child("BookPdf");

        final Book book = getIntent().getParcelableExtra("AuthorId");

        Button readButton=findViewById(R.id.book_description_read);
        buyBook=findViewById(R.id.book_description_buy);
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

        amountPay=book.getPrice();
        price.setText("Rs "+ amountPay);
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

                String bookUrl=storageReference.getDownloadUrl().toString();
                String url= book.getPdfLink();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Intent newIntent = Intent.createChooser(intent, "Open File");

                try {
                     startActivity(newIntent);

                } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here, or something
                }

                //startActivity(new Intent(BookDescription.this,BookPdfRead.class));
            }
        });

        buyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPayment();

            }
        });

    }//End of onCreate() method


    private void processPayment() {

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amountPay)),"USD","Pay to AuthorPoint",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(BookDescription.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PAYPAL_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    try{
                        String paymentdetails = confirmation.toJSONObject().toString(4);
//                        startActivity(new Intent(BookDescription.this, PaymentDetails.class)
//                                .putExtra("paymentDetails",paymentdetails).putExtra("amount",amount).putExtra("activityId",actId));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else if(resultCode == android.app.Activity.RESULT_CANCELED){
                    Toast.makeText(BookDescription.this,">>>>",Toast.LENGTH_LONG).show();
                }

            }else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

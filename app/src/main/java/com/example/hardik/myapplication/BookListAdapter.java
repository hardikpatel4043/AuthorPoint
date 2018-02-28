package com.example.hardik.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.hardik.myapplication.pojo.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Hardik on 12/31/2017.
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {

   private Context context;
   private List<Book> mBookList;
   private DatabaseReference mAuthorDatabase;
   String getAuthorName;

    public BookListAdapter(Context context,List<Book> mBookList){
        this.context=context;
        this.mBookList=mBookList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list, parent, false);

        return new BookListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.bookName.setText(mBookList.get(position).getName());
        holder.bookPrice.setText("Rs "+String.valueOf(mBookList.get(position).getPrice()));

        String authorId=mBookList.get(position).getAuthorId();
        mAuthorDatabase= FirebaseDatabase.getInstance().getReference("author").child(authorId);


        mAuthorDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAuthorName=dataSnapshot.child("name").getValue().toString();
                holder.authorName.setText("by "+getAuthorName);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(mBookList.get(position).getImage().equals("default")){

        }else {
            Glide.with(context).load(mBookList.get(position).getImage()).into(holder.bookImage);
        }
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView bookImage;
        TextView bookName,bookPrice,authorName;

        public MyViewHolder(View itemView) {
            super(itemView);

            bookImage=itemView.findViewById(R.id.book_list_image);
            bookName=itemView.findViewById(R.id.book_list_name);
            bookPrice=itemView.findViewById(R.id.book_list_price);
            authorName=itemView.findViewById(R.id.book_list_author_name);
        }
    }
}

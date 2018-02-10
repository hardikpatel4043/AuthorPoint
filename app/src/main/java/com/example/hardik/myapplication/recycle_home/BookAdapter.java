package com.example.hardik.myapplication.recycle_home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hardik.myapplication.POJO.Book;
import com.example.hardik.myapplication.R;

import java.util.List;

/**
 * Created by Hardik on 12/7/2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {


  private List<Book> bookList;
  private Context context;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText(bookList.get(position).getName());

        Glide.with(context).load(bookList.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public BookAdapter(Context context,List<Book> bookList )
    {
        this.context=context;
        this.bookList=bookList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.title);
            image=(ImageView) itemView.findViewById(R.id.book_image);
        }
    }//end of Myviewholder
}

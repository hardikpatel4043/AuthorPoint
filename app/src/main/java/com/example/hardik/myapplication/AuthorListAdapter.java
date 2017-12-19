package com.example.hardik.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hardik.myapplication.recycle_home.Author;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Hardik on 12/11/2017.
 */

public class AuthorListAdapter extends RecyclerView.Adapter<AuthorListAdapter.MyViewHolder> {

    private List<Bitmap> authorsList;
    public Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.fragment_author__list_name);
            thumbnail = (ImageView) view.findViewById(R.id.fragment_author__list_image);

        }
    }//End of MyViewHolder class

    public AuthorListAdapter(List<Bitmap> moviesList)
    {
        this.authorsList = moviesList;
    }

    //display different items in the data set
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_author_list_vertical_view, parent, false);

        return new MyViewHolder(itemView);
    }

    //display data at specified location
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

      //  holder.name.setText(authorsList.get(position).getName());
        Glide.with(getApplicationContext()).load(authorsList.get(position)).into(holder.thumbnail);
     //   holder.thumbnail.setImageBitmap(authorsList.get(position));
    }

    @Override
    public int getItemCount() {
        return authorsList.size();
    }
}


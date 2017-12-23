package com.example.hardik.myapplication;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.hardik.myapplication.recycle_home.AuthorData;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * Created by Hardik on 12/11/2017.
 */

public class AuthorListAdapter extends RecyclerView.Adapter<AuthorListAdapter.MyViewHolder> {

    private List<AuthorData> authorList;
    private Context context;


    @Override
    public AuthorListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_author_list_vertical_view, parent, false);

        return new AuthorListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AuthorListAdapter.MyViewHolder holder, int position) {

        Log.e("imageUrl",""+authorList.get(position).getImageUrl());
        holder.name.setText(authorList.get(position).getName());
    //    holder.image.setImageResource(authorList.get(position).getImageUrl());
      Glide.with(context).load(authorList.get(position).getImageUrl()).into(holder.image);


    }


    @Override
    public int getItemCount() {
        return authorList.size();
    }

    public AuthorListAdapter(Context context, List<AuthorData> bookList )
    {
        this.context=context;
        this.authorList=bookList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.fragment_author__list_name);
            image=(ImageView) itemView.findViewById(R.id.fragment_author__list_image);
        }
    }//end of Myviewholder
}
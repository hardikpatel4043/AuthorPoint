package com.example.hardik.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hardik.myapplication.POJO.AuthorRegister;

import java.util.List;
/**
 * Created by Hardik on 12/11/2017.
 */

public class AuthorListAdapter extends RecyclerView.Adapter<AuthorListAdapter.MyViewHolder> {

    private List<AuthorRegister> authorList;
    private Context context;

    @Override
    public AuthorListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_author_list_vertical_view, parent, false);

        return new AuthorListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AuthorListAdapter.MyViewHolder holder, int position) {


        holder.name.setText(authorList.get(position).getName());
        if(authorList.get(position).getImage().equals("default")){
            Glide.with(context).load(R.drawable.default_avatar).apply(RequestOptions.circleCropTransform()).into(holder.image);
        }else{
            Glide.with(context).load(authorList.get(position).getImage()).apply(RequestOptions.circleCropTransform()).into(holder.image);
        }

    }


    @Override
    public int getItemCount() {
        return authorList.size();
    }

    public AuthorListAdapter(Context context, List<AuthorRegister> bookList )
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
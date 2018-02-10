package com.example.hardik.myapplication.recycle_home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hardik.myapplication.POJO.Author;
import com.example.hardik.myapplication.R;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Hardik on 12/7/2017.
 */
public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.MyViewHolder> {

    private List<Author> authorList;
    private Context context;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.author, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

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

    public AuthorAdapter(Context context, List<Author> bookList )
    {
        this.context=context;
        this.authorList=bookList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.name);
            image=(ImageView) itemView.findViewById(R.id.author_image);
        }
    }//end of Myviewholder
}


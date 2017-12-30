package com.example.hardik.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hardik.myapplication.POJO.AuthorRegister;

import java.util.List;

/**
 * Created by Hardik on 12/30/2017.
 */

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.MyViewHolder> {

    private Context context;
    List<AuthorRegister> mFriedList;
    @Override
    public FriendRequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_request_tab, parent, false);

        return new FriendRequestAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FriendRequestAdapter.MyViewHolder holder, int position) {

        holder.name.setText(mFriedList.get(position).getName());
        if(mFriedList.get(position).getImage().equals("default")){
            Glide.with(context).load(R.drawable.default_avatar).apply(RequestOptions.circleCropTransform()).into(holder.profile);
        }else{
            Glide.with(context).load(mFriedList.get(position).getImage()).apply(RequestOptions.circleCropTransform()).into(holder.profile);
        }

    }

    @Override
    public int getItemCount() {
        return mFriedList.size();
    }

    public FriendRequestAdapter(Context context,List<AuthorRegister> mFriedList){
        this.context=context;
        this.mFriedList=mFriedList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profile;
        Button accept,decline;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);

            profile=itemView.findViewById(R.id.friend_request_tab_image);
            accept=itemView.findViewById(R.id.accept_button);
            decline=itemView.findViewById(R.id.decline_button);
            name=itemView.findViewById(R.id.friend_request_tab_name);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    accept.setVisibility(View.INVISIBLE);
                }
            });
        }


    }
}

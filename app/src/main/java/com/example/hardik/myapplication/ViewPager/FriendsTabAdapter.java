package com.example.hardik.myapplication.ViewPager;
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
import com.example.hardik.myapplication.R;

import java.util.List;

/**
 * Created by Hardik on 12/30/2017.
 */

public class FriendsTabAdapter extends RecyclerView.Adapter<FriendsTabAdapter.MyViewHolder> {

    Context context;
    List<AuthorRegister> mFriendsList;
    List<String>  mFriendIdList;

    public FriendsTabAdapter(){

    }

    public FriendsTabAdapter(Context context, List<AuthorRegister> mFriendsList, List<String>  mFriendIdList){
        this.context=context;
        this.mFriendIdList=mFriendIdList;
        this.mFriendsList=mFriendsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friends_tab, parent, false);

        return new FriendsTabAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.name.setText(mFriendsList.get(position).getName());
        if(mFriendsList.get(position).getImage().equals("default")){
            Glide.with(context).load(R.drawable.default_avatar).apply(RequestOptions.circleCropTransform()).into(holder.image);
        }else{
            Glide.with(context).load(mFriendsList.get(position).getImage()).apply(RequestOptions.circleCropTransform()).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return mFriendsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView  name;
        public MyViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.friends_tab_profile_image);
            name=itemView.findViewById(R.id.friends_tab_name);
        }
    }
}
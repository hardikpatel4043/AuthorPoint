package com.example.hardik.myapplication.ViewPager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hardik.myapplication.POJO.Author;
import com.example.hardik.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Hardik on 12/30/2017.
 */

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.MyViewHolder> {

    private Context context;
    List<Author> mFriedList;
    private DatabaseReference mFriendRequestDatabase;
    private DatabaseReference mFriendDatabase;
    private FirebaseUser mCurrentUser;
    List<String> viewId;
    int pos;

    @Override
    public FriendRequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_request_tab, parent, false);

        return new FriendRequestAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FriendRequestAdapter.MyViewHolder holder, int position) {

        pos=position;
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

    public FriendRequestAdapter(Context context, List<Author> mFriedList, List<String> viewId){
        this.context=context;
        this.mFriedList=mFriedList;
        this.viewId=viewId;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profile;
        Button accept,decline;
        TextView name;
        String viewUserId;

        public MyViewHolder(View itemView) {
            super(itemView);

            mFriendRequestDatabase= FirebaseDatabase.getInstance().getReference("friends_req");
            mFriendDatabase=FirebaseDatabase.getInstance().getReference("friends");
            mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
            viewUserId=viewId.get(pos);

            profile=itemView.findViewById(R.id.friend_request_tab_image);
            accept=itemView.findViewById(R.id.accept_button);
            decline=itemView.findViewById(R.id.decline_button);
            name=itemView.findViewById(R.id.friend_request_tab_name);




            //-----------------ACCEPT FRIENDS REQUEST-------------
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String currentDate=new SimpleDateFormat("yyyy/MM/dd-HH.mm.ss").format(new Date());
                    mFriendDatabase.child(mCurrentUser.getUid()).child(viewUserId).setValue(currentDate)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mFriendDatabase.child(viewUserId).child(mCurrentUser.getUid()).setValue(currentDate)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            mFriendRequestDatabase.child(mCurrentUser.getUid()).child(viewUserId).removeValue()
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {

                                                                            mFriendRequestDatabase.child(viewUserId).child(mCurrentUser.getUid()).removeValue()
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if(task.isSuccessful()){

                                                                                                accept.setEnabled(false);

                                                                                            }

                                                                                        }
                                                                                    });
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                    accept.setVisibility(View.INVISIBLE);
                }
            });

            //---------------DECLINE FRIENDS REQUEST--------------
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mFriendRequestDatabase.child(mCurrentUser.getUid()).child(viewUserId).removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mFriendRequestDatabase.child(viewUserId).child(mCurrentUser.getUid()).removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    decline.setEnabled(false);

                                                }
                                            });
                                }
                            });
                }
            });

            //------------

        }//End Of MyViewHolder constructor

    }
}

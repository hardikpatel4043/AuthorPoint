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
import com.example.hardik.myapplication.POJO.Author;
import com.example.hardik.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

/**
 * Created by Hardik on 1/3/2018.
 */

public class FriendChatsTabAdapter extends RecyclerView.Adapter<FriendChatsTabAdapter.MyViewHolder> {

    Context context;
    List<Author> mFriendsList;
    List<String> mFriendIdList;
    FirebaseUser mCurrent= FirebaseAuth.getInstance().getCurrentUser();
    String mCurrent_user_id=mCurrent.getUid();
    DatabaseReference mMessageDatabase = FirebaseDatabase.getInstance().getReference().child("messages").child(mCurrent_user_id);

    public FriendChatsTabAdapter() {

    }

    public FriendChatsTabAdapter(Context context, List<Author> mFriendsList, List<String> mFriendIdList) {
        this.context = context;
        this.mFriendIdList = mFriendIdList;
        this.mFriendsList = mFriendsList;
    }

    @Override
    public FriendChatsTabAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friends_chats_tab, parent, false);

        return new FriendChatsTabAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FriendChatsTabAdapter.MyViewHolder holder, int position) {


        String online_status = mFriendsList.get(position).isOnline();
        if (online_status.equals("true")) {
            holder.online_image.setVisibility(View.VISIBLE);
        }

        holder.name.setText(mFriendsList.get(position).getName());
        if (mFriendsList.get(position).getImage().equals("default")) {
            Glide.with(context).load(R.drawable.default_avatar).apply(RequestOptions.circleCropTransform()).into(holder.image);
        } else {
            Glide.with(context).load(mFriendsList.get(position).getImage()).apply(RequestOptions.circleCropTransform()).into(holder.image);
        }


        final String list_user_id = mFriendIdList.get(position);

        Query lastMessageQuery = mMessageDatabase.child(list_user_id).limitToLast(1);

        lastMessageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String data = dataSnapshot.child("message").getValue().toString();
                if(data!=null){
                    holder.lastMessage.setText(data);
                }else{
                    holder.lastMessage.setText("");

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return mFriendsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image, online_image;
        TextView lastMessage;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.friend_chat_tab_profile_image);
            name = itemView.findViewById(R.id.friend_chat_tab_name);
            online_image = itemView.findViewById(R.id.friend_chat_tab_online_image);
            lastMessage=itemView.findViewById(R.id.friends_chats_last_message);
        }
    }//End of MyViewHolder class

}//End of Adapter Class



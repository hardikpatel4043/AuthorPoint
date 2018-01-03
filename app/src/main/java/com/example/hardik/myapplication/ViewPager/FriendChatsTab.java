package com.example.hardik.myapplication.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hardik.myapplication.AuthorDisplayProfile;
import com.example.hardik.myapplication.ItemClick.RecyclerItemClickListener;
import com.example.hardik.myapplication.POJO.AuthorRegister;
import com.example.hardik.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendChatsTab extends Fragment {

    private List<AuthorRegister> mFriendsDetailList;
    private List<String> mFriendIdList;
    private  String currentUserId;

    RecyclerView recyclerView;
    DatabaseReference mChatDatabase;
    DatabaseReference mFriendDetailDatabase;
    FirebaseUser mCurrentUser;

    FriendChatsTabAdapter mAdapter;

    public FriendChatsTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.friends_chats_tab_recyclerview,container,false);

        mFriendsDetailList=new ArrayList<>();
        mFriendIdList=new ArrayList<>();

        recyclerView=rootView.findViewById(R.id.friends_chat_recycleview);

        mChatDatabase= FirebaseDatabase.getInstance().getReference("chat");
        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        mFriendDetailDatabase=FirebaseDatabase.getInstance().getReference().child("author");

        mAdapter = new FriendChatsTabAdapter(getActivity(),mFriendsDetailList,mFriendIdList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);

        currentUserId=mCurrentUser.getUid().toString();

        mChatDatabase.child(currentUserId).orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mFriendIdList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String friendId=snapshot.getKey();
                    String valueId=snapshot.getValue().toString();

                    if(!valueId.isEmpty()){
                        mFriendIdList.add(friendId);
                    }
                }

                mFriendDetailDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        mFriendsDetailList.clear();
                        for(int i=0;i<mFriendIdList.size();i++){
                            String getId=mFriendIdList.get(i);

                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                String id=snapshot.getKey().toString();

                                if(id.equals(getId)){
                                    AuthorRegister authorData = snapshot.getValue(AuthorRegister.class);
                                    mFriendsDetailList.add(authorData);
                                }
                            }
                        }
                        recyclerView.setAdapter(mAdapter);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent inDetail=new Intent(getActivity(),ChatActivity.class);
                inDetail.putExtra("AuthorId",mFriendIdList.get(position));
                startActivity(inDetail);
            }
        }));

        return rootView;
    }

}

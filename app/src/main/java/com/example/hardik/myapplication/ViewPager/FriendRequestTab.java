package com.example.hardik.myapplication.ViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hardik.myapplication.POJO.Author;
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

public class FriendRequestTab extends Fragment {

    private DatabaseReference mFriendReqDatabase;
    private DatabaseReference mAuthorDetail;
    private FirebaseUser currentUser;
    private DatabaseReference mReaderDatabase;

    public List<String> friendReqList;
    private List<Author> mAuthorFriendList;

    private RecyclerView recyclerView;
    private FriendRequestAdapter mAdapter;



    public FriendRequestTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.friend_request_recyclerview, container, false);
        friendReqList=new ArrayList<>();
        mAuthorFriendList=new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recycler_friend_req);

        mAdapter = new FriendRequestAdapter(getActivity(),mAuthorFriendList,friendReqList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);

        mAuthorDetail=FirebaseDatabase.getInstance().getReference("author");
        mFriendReqDatabase= FirebaseDatabase.getInstance().getReference().child("friends_req");
        mReaderDatabase=FirebaseDatabase.getInstance().getReference().child("reader");

        currentUser= FirebaseAuth.getInstance().getCurrentUser();

        String currentUserId=currentUser.getUid().toString();

        mFriendReqDatabase.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendReqList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String req = snapshot.getKey();
                    String f = dataSnapshot.child(req).child("request_type").getValue().toString();

                    if (f.equals("received")) {
                        friendReqList.add(req);
                    }
                }//End of for loop



                mAuthorDetail.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        mAuthorFriendList.clear();
                        for(int i=0;i<friendReqList.size();i++){
                            String getId=friendReqList.get(i);

                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                String id=snapshot.getKey().toString();

                                if(id.equals(getId)){
                                    Author authorData = snapshot.getValue(Author.class);
                                    mAuthorFriendList.add(authorData);
                                }
                            }
                        }
                        recyclerView.setAdapter(mAdapter);
                    }//End of onDataChange method

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });//End of mAuthorDetail

            }//End of onDataChange
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });//End of mFriendReqDatabase


        return rootView;
    }//End of onCreateView() method

}//End of Class

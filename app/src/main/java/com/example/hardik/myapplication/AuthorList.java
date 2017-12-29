package com.example.hardik.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hardik.myapplication.ItemClick.RecyclerItemClickListener;
import com.example.hardik.myapplication.POJO.AuthorRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * this class is main class for authorList in vertical view
 */
public class AuthorList extends android.support.v4.app.Fragment {


    private List<AuthorRegister> authorList = new ArrayList<>();
    private List<String> authorId=new ArrayList<>();
    private RecyclerView recyclerView;
    private AuthorListAdapter mAdapter;
    private DatabaseReference mref;

    private FirebaseUser mCurrentUser;



    public AuthorList() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.author_list_vertical, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_author_list);

        mAdapter = new AuthorListAdapter(getActivity(),authorList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);

        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        prepareAuthorData();

        return rootView;
    }

    void prepareAuthorData(){
        mref=FirebaseDatabase.getInstance().getReference("author");
        mref.keepSynced(true);
        final String current_user_id=mCurrentUser.getUid().toString();

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                authorList.clear();
                authorId.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    String key=snapshot.getRef().getKey().toString();

//                    if(current_user_id.equals(key)){
//                        continue;
//                    }

                    authorId.add(key);

                    AuthorRegister authorData = snapshot.getValue(AuthorRegister.class);
                    authorList.add(authorData);
                }//End of for Loop
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent inDetail=new Intent(getActivity(),AuthorDisplayProfile.class);
                inDetail.putExtra("AuthorId",authorId.get(position));
                startActivity(inDetail);
            }
        }));
    }
}

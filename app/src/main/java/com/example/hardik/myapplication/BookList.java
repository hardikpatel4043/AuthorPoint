package com.example.hardik.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hardik.myapplication.ItemClick.RecyclerItemClickListener;
import com.example.hardik.myapplication.pojo.Book;
import com.example.hardik.myapplication.pojo.CheckNetwork;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookList extends Fragment {


    private RecyclerView recyclerView;
    private BookListAdapter mBookListAdapter;
    private List<Book> mBookList;
    private DatabaseReference mBookDatabase;
    public BookList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView=inflater.inflate(R.layout.book_list_recyclerview, container, false);
        mBookList=new ArrayList<>();

        // Set title bar
        getActivity().setTitle("BookList");

        //Check internet connection
        if(!CheckNetwork.isInternetAvailable(getActivity())){
            Toast.makeText(getActivity(), "Network is not Available ",Toast.LENGTH_SHORT).show();
        }

        recyclerView=rootView.findViewById(R.id.book_list_recyclerview_tag);
        mBookListAdapter=new BookListAdapter(getActivity(),mBookList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);

        prepareBookData();


        //-----------------------------recyclerview item click listener-----------------------------
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent inDetail=new Intent(getActivity(),BookDescription.class);
                        inDetail.putExtra("AuthorId", mBookList.get(position));
                        Log.e("send",""+ mBookList.get(position));
                        startActivity(inDetail);
                    }
                }));
        //-----------------------------------------------------------------------------------------

        return rootView;

    }//End of onCreateView() method

    //--------------------------------prepareBookData-------------------------------------
    private void prepareBookData() {

        mBookDatabase= FirebaseDatabase.getInstance().getReference("book");

        mBookDatabase.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            mBookList.clear();
            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                Book getBookData=snapshot.getValue(Book.class);
                mBookList.add(getBookData);

            }
            recyclerView.setAdapter(mBookListAdapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

    }//End of prepareBookData() method

    //----------------------------------------------------------------------------------

}//End of BookList class

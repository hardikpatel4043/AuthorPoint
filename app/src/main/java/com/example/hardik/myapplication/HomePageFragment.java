package com.example.hardik.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hardik.myapplication.ItemClick.RecyclerItemClickListener;
import com.example.hardik.myapplication.pojo.Author;
import com.example.hardik.myapplication.pojo.Book;
import com.example.hardik.myapplication.recycle_home.AuthorAdapter;
import com.example.hardik.myapplication.recycle_home.BookAdapter;
import com.example.hardik.myapplication.pojo.Event;
import com.example.hardik.myapplication.recycle_home.EventAdapter;
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
public class HomePageFragment extends android.support.v4.app.Fragment {

    public HomePageFragment() {
        // Required empty public constructor

    }

    private List<Book> bookList = new ArrayList<>();
    private List<Author> authorList = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private List<String> authorIdList=new ArrayList<>();
    private List<String> eventIdList=new ArrayList<>();

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;

    private BookAdapter bAdapter;
    private AuthorAdapter aAdapter;
    private EventAdapter eAdapter;

    private DatabaseReference mRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.home_page_fragment, container, false);

        mRoot= FirebaseDatabase.getInstance().getReference();

        getActivity().setTitle("Dashboard");
        recyclerView1 =  (RecyclerView) rootView.findViewById(R.id.recycler_book);
        bAdapter = new BookAdapter(getActivity(),bookList);
        recyclerView1.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(mLayoutManager);
        recyclerView1.setAdapter(bAdapter);
        prepareBookData();

        recyclerView1.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                recyclerView1, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent inDetail=new Intent(getActivity(),BookDescription.class);
                inDetail.putExtra("AuthorId",bookList.get(position));
                startActivity(inDetail);
            }
        }));

        //second recycler
        recyclerView2 =  (RecyclerView) rootView.findViewById(R.id.recycler_author);
        aAdapter = new AuthorAdapter(getActivity(),authorList);
        recyclerView2.setHasFixedSize(true);
        RecyclerView.LayoutManager aLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(aLayoutManager);
        recyclerView2.setAdapter(aAdapter);


        recyclerView2.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                recyclerView2, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent inDetail=new Intent(getActivity(),AuthorDisplayProfile.class);
                inDetail.putExtra("AuthorId",authorIdList.get(position));
                startActivity(inDetail);
            }
        }));

        //  third recycler
        recyclerView3 = (RecyclerView) rootView.findViewById(R.id.recycler_event);
        eAdapter = new EventAdapter(getActivity(),eventList);
        recyclerView3.setHasFixedSize(true);
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        //   RecyclerView.LayoutManager eLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false);
        recyclerView3.setLayoutManager(eLayoutManager);
        recyclerView3.setAdapter(eAdapter);

        recyclerView3.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                recyclerView3, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent inDetail=new Intent(getActivity(),EventDescription.class);
                inDetail.putExtra("Event",eventIdList.get(position));
                startActivity(inDetail);
            }
        }));

        prepareAuthorData();

        return rootView;
    }
    private void prepareBookData(){


    }

    private void prepareAuthorData(){

        mRoot.child("author").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                authorList.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    Author authorData=snapshot.getValue(Author.class);

                    if(authorData.getType().equals("author")){
                        authorList.add(authorData);
                        authorIdList.add(snapshot.getKey());
                    }

                }
                aAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ////////////////////////////////////////////

        mRoot.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventList.clear();
                eventIdList.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    eventIdList.add(snapshot.getKey());
                    Event eventData=snapshot.getValue(Event.class);
                    eventList.add(eventData);

                }

                eAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /////////////////////////////////////////


        mRoot.child("book").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Book bookData=snapshot.getValue(Book.class);
                    bookList.add(bookData);
                }
             bAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}

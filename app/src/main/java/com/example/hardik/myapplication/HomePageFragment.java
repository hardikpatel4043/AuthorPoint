package com.example.hardik.myapplication;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hardik.myapplication.recycle_home.Author;
import com.example.hardik.myapplication.recycle_home.AuthorAdapter;
import com.example.hardik.myapplication.recycle_home.Book;
import com.example.hardik.myapplication.recycle_home.BookAdapter;
import com.example.hardik.myapplication.recycle_home.Event;
import com.example.hardik.myapplication.recycle_home.EventAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends android.support.v4.app.Fragment {

    public HomePageFragment() {
        // Required empty public constructor

    }

    public Context context;
    private List<Book> bookList = new ArrayList<>();
    private List<Author> authorList = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;

    private BookAdapter bAdapter;
    private AuthorAdapter aAdapter;
    private EventAdapter eAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.home_page_fragment, container, false);



        recyclerView1 =  (RecyclerView) rootView.findViewById(R.id.recycler_book);
        bAdapter = new BookAdapter(context,bookList);
        recyclerView1.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(mLayoutManager);
        recyclerView1.setAdapter(bAdapter);
        prepareBookData();

        //second recycler
        recyclerView2 =  (RecyclerView) rootView.findViewById(R.id.recycler_author);
        aAdapter = new AuthorAdapter(context,authorList);
        recyclerView2.setHasFixedSize(true);
        RecyclerView.LayoutManager aLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(aLayoutManager);
        recyclerView2.setAdapter(aAdapter);
        prepareAuthorData();

        //third recycler
        recyclerView3 = (RecyclerView) rootView.findViewById(R.id.recycler_event);
        eAdapter = new EventAdapter(context,eventList);
        recyclerView3.setHasFixedSize(true);
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        //   RecyclerView.LayoutManager eLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false);
        recyclerView3.setLayoutManager(eLayoutManager);
        recyclerView3.setAdapter(eAdapter);
        prepareEventData();

        return rootView;
    }
    private void prepareBookData(){

        int[] drawableArray = {R.drawable.youcanwin, R.drawable.halfgirl};
        String[] nameArray = {"You Can Win", "Half Girlfriend"};
        Book a=new Book(nameArray[0],drawableArray[0]);
        bookList.add(a);

        Book b=new Book(nameArray[1],drawableArray[1]);
        bookList.add(b);

        Book c=new Book(nameArray[0],drawableArray[0]);
        bookList.add(c);

        bAdapter.notifyDataSetChanged();
    }

    private void prepareAuthorData(){

        int[] drawableArray = {R.drawable.youcanwin, R.drawable.halfgirl};
        String[] nameArray = {"You Can Win", "Half Girlfriend"};

        Author a=new Author(nameArray[0],drawableArray[0]);
        authorList.add(a);

        Author b=new Author(nameArray[1],drawableArray[1]);
        authorList.add(b);

        Author c=new Author(nameArray[0],drawableArray[0]);
        authorList.add(c);

        Author d=new Author(nameArray[1],drawableArray[1]);
        authorList.add(d);

        bAdapter.notifyDataSetChanged();
    }

    private void prepareEventData(){

        String[] desArray = {"new","old"};
        String[] nameArray = {"You Can Win", "Half Girlfriend"};

        Event a=new Event(nameArray[0],desArray[0]);
        eventList.add(a);

        Event b=new Event(nameArray[1],desArray[1]);
        eventList.add(b);

        Event c=new Event(nameArray[0],desArray[0]);
        eventList.add(c);

        Event d=new Event(nameArray[1],desArray[1]);
        eventList.add(d);
        bAdapter.notifyDataSetChanged();
    }

}

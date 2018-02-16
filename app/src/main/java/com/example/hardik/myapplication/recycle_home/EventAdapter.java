package com.example.hardik.myapplication.recycle_home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hardik.myapplication.POJO.Event;
import com.example.hardik.myapplication.R;

import java.util.List;

/**
 * Created by Hardik on 12/7/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {


    private List<Event> eventList;
    private Context context;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.name.setText(eventList.get(position).getName());
        holder.place.setText(eventList.get(position).getPlace());
        holder.time.setText(eventList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public EventAdapter(Context context,List<Event> bookList )
    {
        this.context=context;
        this.eventList=bookList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name,place,time;
        public MyViewHolder(View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.recycler_event_name);
            place=itemView.findViewById(R.id.recycler_event_place);
            time=itemView.findViewById(R.id.recycler_event_time);
        }
    }//end of Myviewholder
}


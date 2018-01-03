package com.example.hardik.myapplication.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hardik.myapplication.POJO.Message;
import com.example.hardik.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


/**
 * Created by Hardik on 1/3/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    List<Message> messageList;
    Context context;
    public MessageAdapter(){

    }

    public MessageAdapter(List<Message> messageList,Context context){
        this.messageList=messageList;
        this.context=context;
    }


    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext())
               .inflate(R.layout.message_single_layout,parent,false);

        return new MessageHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {

        String current_user_id=mAuth.getCurrentUser().getUid();

        String get_user_id=messageList.get(position).getFrom().toString();

        if(current_user_id.equals(get_user_id)){

        holder.message.setBackgroundColor(Color.WHITE);
        holder.message.setTextColor(Color.BLACK);
        holder.message.setText(messageList.get(position).getMessage());
//        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
//        Params.setMarginStart(500);
//        holder.message.setLayoutParams(Params);
        }else{

        holder.message.setBackgroundResource(R.drawable.message_background);
        holder.message.setTextColor(Color.WHITE);
        holder.message.setText(messageList.get(position).getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        TextView message;

        public MessageHolder(View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.single_message_text);
        }
    }
}

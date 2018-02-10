package com.example.hardik.myapplication.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.hardik.myapplication.AuthorListAdapter;
import com.example.hardik.myapplication.POJO.Message;
import com.example.hardik.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;

    DatabaseReference mRootRef;

    FirebaseUser mCurrentUser;

    ImageView sendButton;
    EditText messageGet;

    RecyclerView chatRecyclerview;
    String mChatUserId;

    List<Message> messageList;
    MessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        mRootRef= FirebaseDatabase.getInstance().getReference();
        toolbar=findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mChatUserId=getIntent().getStringExtra("AuthorId");

        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();

        sendButton=findViewById(R.id.chat_send_button);
        messageGet=findViewById(R.id.message);

        //Adapter code
        chatRecyclerview=findViewById(R.id.chat_recyclerview);
        messageList=new ArrayList<>();
        mAdapter = new MessageAdapter(messageList,getApplicationContext());
        chatRecyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        chatRecyclerview.setLayoutManager(mLayoutManager);
        chatRecyclerview.setAdapter(mAdapter);
        loadMessages();

        
        //---------------------------------------------------------------------------
        mRootRef.child("author").child(mChatUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String chat_user_name=dataSnapshot.child("name").getValue().toString();
                getSupportActionBar().setTitle(chat_user_name);

                String online_status=dataSnapshot.child("online").getValue().toString();

                if(online_status.equals("true")){
                    getSupportActionBar().setSubtitle("online");
                }else{
                    getSupportActionBar().setSubtitle(online_status);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });


        //-----------------------------------------------------------------------------------------

        mRootRef.child("chat").child(mCurrentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.hasChild(mChatUserId)){

                    Map chatAddMap=new HashMap();
                    chatAddMap.put("seen",false);
                    chatAddMap.put("timestamp", ServerValue.TIMESTAMP);

                    Map chatUserMap=new HashMap();

                    chatUserMap.put("chat/"+ mChatUserId +"/"+ mCurrentUser.getUid(),chatAddMap);
                    chatUserMap.put("chat/"+ mCurrentUser.getUid() +"/"+ mChatUserId,chatAddMap);

                    mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {


                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //----------------------------------------------------------------------------------------


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendMessage();

            }
        });


        //------------------------------------------------------------------

    }//End of onCreate method
//--------------------------------------------------------------------------
    private void loadMessages() {


        mRootRef.child("messages").child(mCurrentUser.getUid()).child(mChatUserId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Message getMessage=dataSnapshot.getValue(Message.class);
                messageList.add(getMessage);
                mAdapter.notifyDataSetChanged();
                chatRecyclerview.scrollToPosition(messageList.size()-1);

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

    }//End of loadMessage method
//--------------------------------------------------
    private void sendMessage() {

        String message = messageGet.getText().toString().trim();

        DatabaseReference message_push=mRootRef.child("messages").child(mCurrentUser.getUid()).child(mChatUserId).push();
        String push_key=message_push.getKey();

        if(!TextUtils.isEmpty(message)){

            Map messageMap=new HashMap();
            messageMap.put("message",message);
            messageMap.put("seen",false);
            messageMap.put("time",ServerValue.TIMESTAMP);
            messageMap.put("from",mCurrentUser.getUid());

            Map messageUserMap=new HashMap();
            messageUserMap.put("messages/"+mCurrentUser.getUid()+"/"+mChatUserId+"/"+push_key,messageMap);
            messageUserMap.put("messages/"+mChatUserId  + "/" + mCurrentUser.getUid()+"/"+push_key,messageMap);

            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    messageGet.setText("");
                }
            });


        }

    }
}//End of ChatActivity class

package com.example.hardik.myapplication;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.hardik.myapplication.POJO.Reader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class EnterDataTemp extends AppCompatActivity {

    TextInputLayout layout1,layout2;
    Button submit;
    DatabaseReference database;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data_temp);

        database=FirebaseDatabase.getInstance().getReference("reader");
        user= FirebaseAuth.getInstance().getCurrentUser();

        layout1=findViewById(R.id.textInputLayout1);
        submit=findViewById(R.id.tem_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   String id=user.getUid();

                String pushId=database.push().getKey();
                String parameter =layout1.getEditText().getText().toString();
                Map<String,Object> updataData=new HashMap<>();
                //I need to get follower id from user and enter here
                updataData.put("followerId","true");
                database.child(id).child("follow").child(pushId).setValue(updataData);


                //Retrieve buy book
             /*   database.child(id).child("buyBook").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String email=dataSnapshot.child(email).getValue().toString();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            HashMap<String, HashMap<String, String>> value = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                            Map<String, String> temp = value.get(snapshot.getKey());
                            String name = temp.get("buyBook");
                            Log.e("name", "" + name);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
*/
            }
        });

    }
}

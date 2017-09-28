package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {

    String userKey;
    //public static final String ARTIST_ID = "artistid";
    private TextView add_Kids;

    //initialization for kids
    ListView listUsers;
    List<UserProfile> user;

    //database
    DatabaseReference usersRetriveRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listUsers  = (ListView)findViewById(R.id.listUser);

        Intent intent = getIntent();
        //String id = intent.getStringExtra(Teachers.ARTIST_ID);
        userKey =  intent.getStringExtra("User_KEY");

        user = new ArrayList<>();


        //kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids").child(userKey);

        usersRetriveRef = FirebaseDatabase.getInstance().getReference("Users");


        listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserProfile users =  user.get(i);

                Intent intent = new Intent(getApplicationContext(),Chat.class);
                intent.putExtra("users_kids", users.getKeyUser());


                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();



        usersRetriveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.clear();
                for (DataSnapshot kidssnapshot : dataSnapshot.getChildren()){


                        UserProfile kidInf = kidssnapshot.getValue(UserProfile.class);
                        user.add(kidInf);



                }
                UserArray trackListAdapter = new UserArray(Admin.this,user);
                listUsers.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Teachers extends AppCompatActivity {

    String userKey;
    //public static final String ARTIST_ID = "artistid";
    private TextView add_Kids;

    //initialization for kids
    ListView listViewKids;
    List<Kids> kid;

    //database
    DatabaseReference kidsRetriveRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        listViewKids  = (ListView)findViewById(R.id.listViewkids);

        Intent intent = getIntent();
        //String id = intent.getStringExtra(Teachers.ARTIST_ID);
        userKey =  intent.getStringExtra("User_KEY");

        kid = new ArrayList<>();


        kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids").child(userKey);

        add_Kids = (TextView)findViewById(R.id.text_Add_Kids);

        listViewKids.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Kids kido =  kid.get(i);

                Intent intent = new Intent(getApplicationContext(),Chat.class);
                intent.putExtra("kid_id", kido.getId());


                startActivity(intent);
            }
        });

        add_Kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Teachers.this,Kid.class);
                intent.putExtra("User_KEY",userKey);
                startActivity(intent);
                Toast.makeText(Teachers.this,userKey, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        kidsRetriveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kid.clear();
                for (DataSnapshot tracksnapshot : dataSnapshot.getChildren()){
                    Kids kidInf = tracksnapshot.getValue(Kids.class);
                    kid.add(kidInf);
                }
                KidsArray trackListAdapter = new KidsArray(Teachers.this,kid);
                listViewKids.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}

package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Teachers_activity extends AppCompatActivity {

    String userKey;
    //public static final String ARTIST_ID = "artistid";
    private TextView add_Kids;

    //initialization for kids
    ListView listViewKids;
    List<Kids> kid;

    FirebaseAuth fireAuth;
    //database
    DatabaseReference kidsRetriveRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        listViewKids  = (ListView)findViewById(R.id.listViewkids);
        fireAuth =  FirebaseAuth.getInstance();



        kid = new ArrayList<>();


        //kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids").child(userKey);

       // kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids");

        kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Users");



        add_Kids = (TextView)findViewById(R.id.text_Add_Kids);

        listViewKids.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Kids kido =  kid.get(i);


                Intent intent = new Intent(getApplicationContext(),Uploud_kids_memo.class);
                intent.putExtra("kid_id", kido.getId());
                intent.putExtra("User_KEY",userKey);


                startActivity(intent);
            }
        });

        add_Kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        /*kidsRetriveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kid.clear();
                for (DataSnapshot kidssnapshot : dataSnapshot.getChildren()){

                    if(kidssnapshot.child("userKey").getValue().toString().equals(fireAuth.getCurrentUser().getUid())) {
                        Kids kidInf = kidssnapshot.getValue(Kids.class);
                        kid.add(kidInf);
                    }else{

                    }
                }
                KidsArray trackListAdapter = new KidsArray(Teachers_activity.this,kid);
                listViewKids.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }


}

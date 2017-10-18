package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeachersActivity extends AppCompatActivity {

    String userKey;
    //public static final String ARTIST_ID = "artistid";
    private TextView add_Kids;

    //initialization for kids
    ListView listViewKids;
    List<Kids> kid;

    FirebaseAuth fireAuth;
    //database
    DatabaseReference kidsRetriveRef,creachRef,orgNameReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        listViewKids  = (ListView)findViewById(R.id.listViewkids);
        fireAuth =  FirebaseAuth.getInstance();

        Intent intent = getIntent();
        //String id = intent.getStringExtra(TeachersActivity.ARTIST_ID);
        userKey =  intent.getStringExtra("User_KEY");



        kid = new ArrayList<>();


        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        orgNameReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("teacherClassroom");
        //kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids").child(userKey);

        //kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids");
        creachRef = FirebaseDatabase.getInstance().getReference().child("Creche");

        kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids");

        orgNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              final  String org_name = dataSnapshot.getValue(String.class);


                kidsRetriveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kid.clear();

                        for (DataSnapshot kidssnapshot : dataSnapshot.getChildren()){


                            if(kidssnapshot.child("kidsGrade").getValue().toString().equals(org_name)) {

                                Kids kidInf = kidssnapshot.getValue(Kids.class);
                                kid.add(kidInf);
                            }else{

                            }
                        }
                        KidsArray trackListAdapter = new KidsArray(TeachersActivity.this,kid);
                        listViewKids.setAdapter(trackListAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        add_Kids = (TextView)findViewById(R.id.text_Add_Kids);

        listViewKids.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Kids kido =  kid.get(i);


                Intent intent = new Intent(getApplicationContext(),UploadKidsMemo.class);
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


    }


}

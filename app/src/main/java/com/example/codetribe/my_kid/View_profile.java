package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class View_profile extends AppCompatActivity {


    private DatabaseReference databaseReference;
    TextView name,surname;
    String iduser;

    String nameString,surnameString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        //initialize
        name = (TextView) findViewById(R.id.user_profile_name);
        surname= (TextView)findViewById(R.id.user_profile_status);


        Intent intentId =getIntent();
        iduser = intentId.getStringExtra("parent_user");

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");






        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Infor(dataSnapshot,iduser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ImageView editprofile=(ImageView) findViewById(R.id.editprofile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(View_profile.this,profile.class) ;
                startActivity(intent);
            }

        });



/*
        databaseReference = FirebaseDatabase.getInstance().getReference().child("courses").child("Business");
        name = (TextView) findViewById(R.id.user_profile_name);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CourseDetails courseDetails = dataSnapshot.getValue(CourseDetails.class);
                code = courseDetails.getCourseCode();
                name = courseDetails.getCourseName();

                name.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */

    }


    private void Infor(DataSnapshot dataSnapshot, String userId){

        Iterator iterator = dataSnapshot.getChildren().iterator();

        while(iterator.hasNext()) {
            DataSnapshot dataUser = (DataSnapshot) iterator.next();

            if (dataUser.child("userKey").getValue().toString().equals(userId))
            {

                name.setText(dataUser.child("userName").getValue().toString());
                surname.setText(dataUser.child("userSurname").getValue().toString());



            }


        }

    }


}
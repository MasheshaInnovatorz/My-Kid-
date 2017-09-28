package com.example.codetribe.my_kid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class View_profile extends AppCompatActivity {

    private Uri imgUri;
    private DatabaseReference databaseReference;
    TextView name,surname,gender,phonenumber,address,email,editprofile;
    String iduser;
    ImageView profilecover;
    String idLoged;
    String nameString,surnameString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        //initialize
        name = (TextView) findViewById(R.id.user_profile_name);
        surname= (TextView)findViewById(R.id.user_profile_status);
        gender = (TextView) findViewById(R.id.gender_view);
        phonenumber= (TextView)findViewById(R.id.phone_view);
        address= (TextView)findViewById(R.id.address_view);
        email= (TextView)findViewById(R.id.email_view);
        profilecover=(ImageView) findViewById(R.id.header_cover_image);


        //profile edit
        editprofile=(TextView)findViewById(R.id.editprofile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(View_profile.this,profile.class);
                startActivity(i);
            }
        });


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


        TextView editprofile=(TextView) findViewById(R.id.editprofile);
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

                name.setText("Name : " + dataUser.child("userName").getValue().toString());
                surname.setText("Surname : " + dataUser.child("userSurname").getValue().toString());
                gender.setText("  male :"+ dataUser.child("userGender").getValue().toString());
                phonenumber.setText("  phone number :"+ dataUser.child("userContact").getValue().toString());
                address.setText("  Lives in :"+ dataUser.child("userAddress").getValue().toString());
                email.setText("  Email :"+ dataUser.child("emailUser").getValue().toString());


        //     profilecover.setImageDrawable(dataSnapshot.child("fdsdfs").getRef());

          //Lives in

            }


        }

    }


}
package com.example.codetribe.my_kid.kids_Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.codetribe.my_kid.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AdminKidsInformatin extends AppCompatActivity {

    private DatabaseReference kidsDataProf, userDataRef, kidsppic,parentDatabase;
    private FirebaseUser fireAuthorization;
    private TextView editProfile, allergies,
            dietRequirements,
            doctorsRecomendations,
            kidHeight,
            bodyWeight;

    private ImageView kidsImage;

    private String idsKid;


    private String id_Key;

    private TextView kidsUser, parentName, kidsGender, phonenumber, parentEmail, city, homeAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_kids_informatin);


        Intent intent = getIntent();
        idsKid = intent.getStringExtra("nwana");

        //Initialization
        fireAuthorization = FirebaseAuth.getInstance().getCurrentUser();

        kidsDataProf = FirebaseDatabase.getInstance().getReference("Kids").child(idsKid);

        userDataRef = FirebaseDatabase.getInstance().getReference("Users");

        parentDatabase = FirebaseDatabase.getInstance().getReference("Users");

        kidsppic = FirebaseDatabase.getInstance().getReference().child("Kids");


        editProfile = (TextView) findViewById(R.id.editprofile);
        kidsUser = (TextView) findViewById(R.id.kids_profile_name);
        parentName = (TextView) findViewById(R.id.parentName);
        kidsGender = (TextView) findViewById(R.id.kids_gender_view);
        phonenumber = (TextView) findViewById(R.id.kids_phone_view);
        parentEmail = (TextView) findViewById(R.id.kids_email_view);
        city = (TextView) findViewById(R.id.kids_city_view);
        homeAddress = (TextView) findViewById(R.id.kids_address_view);

        //extra information
        allergies = (TextView) findViewById(R.id.kids_Allergies_view);
        dietRequirements = (TextView) findViewById(R.id.kids_diet_Requirements_view);
        doctorsRecomendations = (TextView) findViewById(R.id.kids_doctorsRequirements_view);
        kidHeight = (TextView) findViewById(R.id.kids_height_view);
        bodyWeight = (TextView) findViewById(R.id.kids_BodyWeight_view);

        //profilePic
        kidsImage = (ImageView) findViewById(R.id.kid_header_cover_image);

        Toast.makeText(this, fireAuthorization.getUid(), Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();

        //extra kid info
/*
        kidsDataProf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //kid profile

                //extra kid info
                if (dataSnapshot.child("allergies").getValue().toString() != " ")
                    allergies.setText(dataSnapshot.child("allergies").getValue().toString());

                if (dataSnapshot.child("dietRequirements").getValue().toString() != " ")
                    dietRequirements.setText(dataSnapshot.child("dietRequirements").getValue().toString());

                if (dataSnapshot.child("doctorsRecomendations").getValue().toString() != " ")
                    doctorsRecomendations.setText(dataSnapshot.child("doctorsRecomendations").getValue().toString());

                if (dataSnapshot.child("kidHeight").getValue().toString() != " ")
                    kidHeight.setText(dataSnapshot.child("kidHeight").getValue().toString());

                if (dataSnapshot.child("bodyWeight").getValue().toString() != " ")
                    bodyWeight.setText(dataSnapshot.child("bodyWeight").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

*/



                kidsDataProf.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot kidSnapshot) {

                        userDataRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot userSnapshot) {

                         for (DataSnapshot parentInfor : userSnapshot.getChildren()) {


                             if (kidSnapshot.child("parentid").getValue().toString().equals(parentInfor.child("userIdNumber").getValue().toString())) {

                                     if (parentInfor.child("orgName").getValue().toString().equals(kidSnapshot.child("orgName").getValue().toString())) {

                                         //parent name
                                         parentName.setText(parentInfor.child("userSurname").getValue().toString() + " " + parentInfor.child("userName").getValue().toString());
                                         //kids names
                                         kidsUser.setText(kidSnapshot.child("surname").getValue().toString() + " " + kidSnapshot.child("name").getValue().toString());


                                         id_Key = kidSnapshot.child("id").getValue().toString();


                                         kidsGender.setText("Gender :" + kidSnapshot.child("gender").getValue().toString());
                                         phonenumber.setText("Parent Contact :" + parentInfor.child("userContact").getValue().toString());
                                         parentEmail.setText("Parent Email :" + parentInfor.child("emailUser").getValue().toString());
                                         id_Key = kidSnapshot.child("id").getValue().toString();
                                         city.setText("Class :" + kidSnapshot.child("kidsGrade").getValue().toString());
                                         homeAddress.setText("Address :" + kidSnapshot.child("address").getValue().toString());

                                         Glide.with(getApplicationContext()).load(kidSnapshot.child("profilePic").getValue().toString()).centerCrop().into(kidsImage);

                                         parentName.setText(parentInfor.child("userName").getValue().toString() + " " + parentInfor.child("userName").getValue().toString());
                                         phonenumber.setText("Parent Contact :" + parentInfor.child("userContact").getValue().toString());
                                         parentEmail.setText("Parent Email :" + parentInfor.child("emailUser").getValue().toString());


                                         //}


                                         //if (kidSnapshot.child("id").getValue().toString().equals(idsKid)) {

                                         //kids names
                                         kidsUser.setText(kidSnapshot.child("surname").getValue().toString() + " " + kidSnapshot.child("name").getValue().toString());
                                         kidsGender.setText("Gender :" + kidSnapshot.child("gender").getValue().toString());
                                         id_Key = kidSnapshot.child("id").getValue().toString();
                                         city.setText("Class :" + kidSnapshot.child("kidsGrade").getValue().toString());
                                         homeAddress.setText("Address :" + kidSnapshot.child("address").getValue().toString());
                                         Glide.with(getApplicationContext()).load(kidSnapshot.child("profilePic").getValue().toString()).centerCrop().into(kidsImage);
                                     }
                                 }

                             }


                           // }
                       // }

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

    }
}

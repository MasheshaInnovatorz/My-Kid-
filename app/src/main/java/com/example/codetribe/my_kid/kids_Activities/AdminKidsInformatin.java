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

    private DatabaseReference kidsDataProf, userDataRef,kidsppic;
    private FirebaseUser fireAuthorization;
    private TextView editProfile;
    private ImageView kidsImage;



    private String id_Key;

    private TextView kidsUser, parentName, kidsGender, phonenumber, parentEmail, city, homeAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_kids_informatin);

        //Initialization
        fireAuthorization = FirebaseAuth.getInstance().getCurrentUser();

        kidsDataProf = FirebaseDatabase.getInstance().getReference("Kids");

        userDataRef = FirebaseDatabase.getInstance().getReference("Users").child(fireAuthorization.getUid());

        kidsppic = FirebaseDatabase.getInstance().getReference().child("Kids");



        editProfile = (TextView)findViewById(R.id.editprofile);
        kidsUser = (TextView) findViewById(R.id.kids_profile_name);
        parentName = (TextView) findViewById(R.id.parentName);
        kidsGender = (TextView) findViewById(R.id.kids_gender_view);
        phonenumber = (TextView) findViewById(R.id.kids_phone_view);
        parentEmail = (TextView) findViewById(R.id.kids_email_view);
        city = (TextView) findViewById(R.id.kids_city_view);
        homeAddress = (TextView) findViewById(R.id.kids_address_view);

        //profilePic
        kidsImage =(ImageView)findViewById(R.id.kid_header_cover_image);

        Toast.makeText(this, fireAuthorization.getUid(), Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();


        userDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot userSnapshot) {


                kidsDataProf.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot kidSnapshot : dataSnapshot.getChildren()) {


                            if (userSnapshot.child("userIdNumber").getValue().toString().equals(kidSnapshot.child("parentid").getValue().toString())) {

                                if (userSnapshot.child("orgName").getValue().toString().equals(kidSnapshot.child("orgName").getValue().toString())) {

                                    //parent name
                                    parentName.setText(userSnapshot.child("userName").getValue().toString() + " " + userSnapshot.child("userName").getValue().toString());
                                    //kids names
                                    kidsUser.setText(kidSnapshot.child("surname").getValue().toString() + " " + kidSnapshot.child("name").getValue().toString());



                                    id_Key = kidSnapshot.child("id").getValue().toString();


                                    kidsGender.setText("Gender :" + kidSnapshot.child("gender").getValue().toString());
                                    phonenumber.setText("Parent Contact :"+ userSnapshot.child("userContact").getValue().toString());
                                    parentEmail.setText("Parent Email :"+ userSnapshot.child("emailUser").getValue().toString());
                                    id_Key = kidSnapshot.child("id").getValue().toString();
                                    city.setText("Class :" +kidSnapshot.child("kidsGrade").getValue().toString());
                                    homeAddress.setText("Address :" +kidSnapshot.child("address").getValue().toString());

                                    Glide.with(getApplicationContext()).load(kidSnapshot.child("profilePic").getValue().toString()).centerCrop().into(kidsImage);

                                }
                            }else if(userSnapshot.child("role").getValue().toString().equals("teacher")){
                                Intent intent = getIntent();
                                String idsKid= intent.getStringExtra("nwana");

                                if(kidSnapshot.child("id").getValue().toString().equals(idsKid)){


                                    kidsGender.setText("Gender :" + kidSnapshot.child("gender").getValue().toString());
                                    phonenumber.setText("Parent Contact :"+ userSnapshot.child("userContact").getValue().toString());
                                    parentEmail.setText("Parent Email :"+ userSnapshot.child("emailUser").getValue().toString());
                                    id_Key = kidSnapshot.child("id").getValue().toString();
                                    city.setText("Class :" +kidSnapshot.child("kidsGrade").getValue().toString());
                                    homeAddress.setText("Address :" +kidSnapshot.child("address").getValue().toString());

                                    Glide.with(getApplicationContext()).load(kidSnapshot.child("profilePic").getValue().toString()).centerCrop().into(kidsImage);
                                }
                            }
                        }

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

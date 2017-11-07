package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KidsViewProfile extends AppCompatActivity {

    DatabaseReference kidsDataProf, userDataRef;
    FirebaseUser fireAuthorization;
    ImageView editProfile;

    String id_Key;

    TextView kidsUser, parentName, kidsGender, phonenumber, parentEmail, city, homeAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids_view_profile);

        //Initialization
        fireAuthorization = FirebaseAuth.getInstance().getCurrentUser();

        kidsDataProf = FirebaseDatabase.getInstance().getReference("Kids");

        userDataRef = FirebaseDatabase.getInstance().getReference("Users").child(fireAuthorization.getUid());

        kidsUser = (TextView) findViewById(R.id.kids_profile_name);
        parentName = (TextView) findViewById(R.id.parentName);
        kidsGender = (TextView) findViewById(R.id.kids_gender_view);
        phonenumber = (TextView) findViewById(R.id.kids_phone_view);
        parentEmail = (TextView) findViewById(R.id.kids_email_view);
        city = (TextView) findViewById(R.id.kids_city_view);
        homeAddress = (TextView) findViewById(R.id.kids_address_view);
        editProfile = (ImageView) findViewById(R.id.editKids_profile);

        Toast.makeText(this, fireAuthorization.getUid(), Toast.LENGTH_SHORT).show();


<<<<<<< HEAD
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplication(),EditKidsProfile.class);
                    intent.putExtra("kidId",id_Key);

                startActivity(intent);
            }
        });


=======
>>>>>>> 6ff658d52501eeeaafa17900e11436cd728d15e9
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


                                    parentName.setText(userSnapshot.child("userName").getValue().toString() + " " + userSnapshot.child("userName").getValue().toString());
                                    phonenumber.setText(userSnapshot.child("userContact").getValue().toString());
                                    parentEmail.setText(userSnapshot.child("emailUser").getValue().toString());

                                    id_Key = kidSnapshot.child("id").getValue().toString();

                                    kidsUser.setText(kidSnapshot.child("surname").getValue().toString() + " " + kidSnapshot.child("name").getValue().toString());
                                    kidsGender.setText(kidSnapshot.child("gender").getValue().toString());
                                    city.setText(kidSnapshot.child("kidsGrade").getValue().toString());
                                    homeAddress.setText(kidSnapshot.child("address").getValue().toString());

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

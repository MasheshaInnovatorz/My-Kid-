package com.example.codetribe.my_kid.kids_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.databinding.ActivityAdminKidsInformatinBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminKidsInformatin extends AppCompatActivity {

    private DatabaseReference kidsDataProf, userDataRef, kidsppic, parentDatabase;
    private FirebaseUser fireAuthorization;

    private ImageView kidsImage;

    private String idsKid;
    private String id_Key;

    private ActivityAdminKidsInformatinBinding activityAdminKidsInformatinBinding;

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

    }


    @Override
    protected void onStart() {
        super.onStart();


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
                                    activityAdminKidsInformatinBinding.parentName.setText(parentInfor.child("userSurname").getValue().toString() + " " + parentInfor.child("userName").getValue().toString());
                                    //kids names
                                    activityAdminKidsInformatinBinding.kidsProfileName.setText(kidSnapshot.child("surname").getValue().toString() + " " + kidSnapshot.child("name").getValue().toString());
                                    activityAdminKidsInformatinBinding.kidsGenderView.setText("Gender : " + kidSnapshot.child("gender").getValue().toString());
                                    activityAdminKidsInformatinBinding.kidsPhoneView.setText("Parent Contact : " + parentInfor.child("userContact").getValue().toString());
                                    activityAdminKidsInformatinBinding.kidsEmailView.setText("Parent Email : " + parentInfor.child("emailUser").getValue().toString());
                                    activityAdminKidsInformatinBinding.kidsCityView.setText("Class : " + kidSnapshot.child("kidsGrade").getValue().toString());
                                    activityAdminKidsInformatinBinding.kidsAddressView.setText("Address : " + kidSnapshot.child("address").getValue().toString());

                                    id_Key = kidSnapshot.child("id").getValue().toString();

                                    //kid added infor
                                    activityAdminKidsInformatinBinding.kidsAllergiesView.setText("Allergies : " + kidSnapshot.child("allergies").getValue().toString());
                                    activityAdminKidsInformatinBinding.kidsDietRequirementsView.setText("Diet Requirements : " + kidSnapshot.child("dietRequirements").getValue().toString());
                                    activityAdminKidsInformatinBinding.kidsDietRequirementsView.setText("Doctors Recomendations :" + kidSnapshot.child("doctorsRecomendations").getValue().toString());
                                    activityAdminKidsInformatinBinding.kidsHeightView.setText("Kid Height : " + kidSnapshot.child("kidHeight").getValue().toString());
                                    activityAdminKidsInformatinBinding.kidsBodyWeightView.setText("Kid Weight : " + kidSnapshot.child("bodyWeight").getValue().toString());

                                    //kid image
                                    Glide.with(getApplicationContext()).load(kidSnapshot.child("profilePic").getValue().toString()).centerCrop().into(activityAdminKidsInformatinBinding.kidHeaderCoverImage);
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

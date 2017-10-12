package com.example.codetribe.my_kid;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditKidsProfile extends AppCompatActivity {

    private TextInputLayout h_allergies,h_dietRequirements,h_doctorsRecomendations,h_kidHeight,h_bodyWeight;

 public EditText allergies,
            dietRequirements,
            doctorsRecomendations,
            kidHeight,kidStringparentidS,
            bodyWeight;
       TextView upDateKid_infors;


    String genderString,keyUser;
    String  s_allergies,
            s_dietRequirements,
            s_doctorsRecomendations,
            s_kidHeight,kidStringparentid,
            s_bodyWeight;

    //database
    DatabaseReference databaseKids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kids_profile);



        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Adding extra kid information");



        allergies = (EditText) findViewById(R.id.edit_Allergies);
        dietRequirements = (EditText) findViewById(R.id.diet_Requirements);
        doctorsRecomendations = (EditText) findViewById(R.id.doctorsRecomendations);
        kidHeight = (EditText) findViewById(R.id.height);
        bodyWeight = (EditText) findViewById(R.id.edit_BodyWeight);
        bodyWeight = (EditText) findViewById(R.id.edit_BodyWeight);
        kidStringparentidS = (EditText) findViewById(R.id.editParentId);
        upDateKid_infors = (TextView) findViewById(R.id.upDateKid_infor);


        Intent intent = getIntent();
        //String id = intent.getStringExtra(TeachersActivity.ARTIST_ID);
        keyUser =  intent.getStringExtra("User_KEY");

        //database
        databaseKids = FirebaseDatabase.getInstance().getReference().child("Kids");

        upDateKid_infors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveKids();


            }
        });


    }
    private void saveKids(){

        s_allergies = allergies.getText().toString().trim();
        s_dietRequirements = dietRequirements .getText().toString().trim();
        s_doctorsRecomendations= doctorsRecomendations.getText().toString().trim();
        s_kidHeight = kidHeight.getText().toString().trim();
        s_bodyWeight= bodyWeight.getText().toString().trim();
        kidStringparentid = kidStringparentidS.getText().toString();


        if(!TextUtils.isEmpty(kidStringparentid)){


            String id = databaseKids.push().getKey();


            Kids kids = new Kids(keyUser, s_allergies,s_dietRequirements ,s_doctorsRecomendations,s_kidHeight,s_bodyWeight,kidStringparentid,id);

            databaseKids.child(id).setValue(kids);

            Toast.makeText(this, "Kid information added", Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(this, "Track name should not be empty", Toast.LENGTH_SHORT).show();
        }
    }
}

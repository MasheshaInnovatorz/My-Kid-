package com.example.codetribe.my_kid.groupChat_Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.account_Activities.ViewProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditKidsProfile extends AppCompatActivity {

    private TextInputLayout h_allergies, h_dietRequirements, h_doctorsRecomendations, h_kidHeight, h_bodyWeight;

    private AwesomeValidation awesomeValidation;

    public EditText allergies,
            dietRequirements,
            doctorsRecomendations,
            kidHeight,
            bodyWeight,
            kid_name,
            kid_surname,
            kid_address;


    private ProgressDialog progressDialog;
    String genderString, keyUser;
    String s_allergies,
            s_dietRequirements,
            s_doctorsRecomendations,
            s_kidHeight,
            s_bodyWeight,
            s_namekid,
            s_surnamekid,
            s_addresskid;


    //database
    DatabaseReference databaseKids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kids_profile);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Kid Profile");

        //validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        progressDialog = new ProgressDialog(this);

        //extra information
        allergies = (EditText) findViewById(R.id.edit_Allergies);
        dietRequirements = (EditText) findViewById(R.id.diet_Requirements);
        doctorsRecomendations = (EditText) findViewById(R.id.doctorsRecomendations);
        kidHeight = (EditText) findViewById(R.id.height);
        bodyWeight = (EditText) findViewById(R.id.edit_BodyWeight);

        //kids profile
        kid_name = (EditText) findViewById(R.id.kid_name);
        kid_surname = (EditText) findViewById(R.id.kid_surname);
        kid_address = (EditText) findViewById(R.id.kid_address);


        Intent intent = getIntent();
        keyUser = intent.getStringExtra("kidId");

        //database
        databaseKids = FirebaseDatabase.getInstance().getReference().child("Kids").child(keyUser);

        databaseKids.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //kid profile
                kid_name.setText(dataSnapshot.child("name").getValue().toString());
                kid_surname.setText(dataSnapshot.child("surname").getValue().toString());
                kid_address.setText(dataSnapshot.child("address").getValue().toString());

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

        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.kid_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.kid_surname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.surnameerror);
        awesomeValidation.addValidation(this, R.id.kid_address, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.address);

        awesomeValidation.addValidation(this, R.id.edit_Allergies, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.allergies_error);
        awesomeValidation.addValidation(this, R.id.diet_Requirements, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.dietRequirements_error);
        awesomeValidation.addValidation(this, R.id.doctorsRecomendations, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.doctorsRecomendations_error);
        // awesomeValidation.addValidation(this, R.id.edit_BodyWeight, "^[0-9]", R.string.bodyWeight_error);
        //    awesomeValidation.addValidation(this, R.id.height, "^[0-9]", R.string.kidHeight_error);

    }

    private void saveKids() {
        if (awesomeValidation.validate()) {
            s_namekid = kid_name.getText().toString().trim();
            s_surnamekid = kid_surname.getText().toString().trim();
            s_addresskid = kid_address.getText().toString().trim();

            s_allergies = allergies.getText().toString().trim();
            s_dietRequirements = dietRequirements.getText().toString().trim();
            s_doctorsRecomendations = doctorsRecomendations.getText().toString().trim();
            s_kidHeight = kidHeight.getText().toString().trim();
            s_bodyWeight = bodyWeight.getText().toString().trim();

            databaseKids.child("name").setValue(s_namekid);
            databaseKids.child("surname").setValue(s_surnamekid);
            databaseKids.child("address").setValue(s_addresskid);

            databaseKids.child("allergies").setValue(s_allergies);
            databaseKids.child("bodyWeight").setValue(s_bodyWeight);
            databaseKids.child("kidHeight").setValue(s_kidHeight);
            databaseKids.child("dietRequirements").setValue(s_dietRequirements);
            databaseKids.child("doctorsRecomendations").setValue(s_doctorsRecomendations);

            databaseKids.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(EditKidsProfile.this, "User Failed to Update Kids Information", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            Toast.makeText(EditKidsProfile.this, "Kid Information Uptdated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditKidsProfile.this, ViewProfile.class);
            startActivity(intent);
        } else

        {
            Toast.makeText(EditKidsProfile.this, "User Failed to Update Kids Information", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.profile_save) {
            saveKids();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
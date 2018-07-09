package com.example.codetribe.my_kid.kids_Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.example.codetribe.my_kid.databinding.ActivityEditKidsProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditKidsProfile extends AppCompatActivity {

    private AwesomeValidation awesomeValidation;
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
    //editBinding
    private ActivityEditKidsProfileBinding editKidsProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editKidsProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_kids_profile);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Kid Profile");

        //validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        keyUser = intent.getStringExtra("kidId");

        //database
        databaseKids = FirebaseDatabase.getInstance().getReference().child("Kids").child(keyUser);

        databaseKids.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //kid profile
                editKidsProfileBinding.kidName.setText(dataSnapshot.child("name").getValue().toString());
                editKidsProfileBinding.kidSurname.setText(dataSnapshot.child("surname").getValue().toString());
                editKidsProfileBinding.kidAddress.setText(dataSnapshot.child("address").getValue().toString());

                //extra kid info
                if (dataSnapshot.child("allergies").getValue().toString() != " ")
                    editKidsProfileBinding.editAllergies.setText(dataSnapshot.child("allergies").getValue().toString());

                if (dataSnapshot.child("dietRequirements").getValue().toString() != " ")
                    editKidsProfileBinding.dietRequirements.setText(dataSnapshot.child("dietRequirements").getValue().toString());

                if (dataSnapshot.child("doctorsRecomendations").getValue().toString() != " ")
                    editKidsProfileBinding.doctorsRecomendations.setText(dataSnapshot.child("doctorsRecomendations").getValue().toString());

                if (dataSnapshot.child("kidHeight").getValue().toString() != " ")
                    editKidsProfileBinding.height.setText(dataSnapshot.child("kidHeight").getValue().toString());

                if (dataSnapshot.child("bodyWeight").getValue().toString() != " ")
                    editKidsProfileBinding.editBodyWeight.setText(dataSnapshot.child("bodyWeight").getValue().toString());

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

    }


    private void saveKids() {
        if (awesomeValidation.validate()) {
            s_namekid = editKidsProfileBinding.kidName.getText().toString().trim();
            s_surnamekid = editKidsProfileBinding.kidSurname.getText().toString().trim();
            s_addresskid = editKidsProfileBinding.kidAddress.getText().toString().trim();

            s_allergies = editKidsProfileBinding.editAllergies.getText().toString().trim();
            s_dietRequirements = editKidsProfileBinding.dietRequirements.getText().toString().trim();
            s_doctorsRecomendations = editKidsProfileBinding.doctorsRecomendations.getText().toString().trim();
            s_kidHeight = editKidsProfileBinding.height.getText().toString().trim();
            s_bodyWeight = editKidsProfileBinding.editBodyWeight.getText().toString().trim();

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
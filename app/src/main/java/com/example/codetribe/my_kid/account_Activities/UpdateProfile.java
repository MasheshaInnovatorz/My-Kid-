package com.example.codetribe.my_kid.account_Activities;

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
import com.example.codetribe.my_kid.databinding.ActivityUpdateProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {


    private AwesomeValidation awesomeValidation;

    //firebase user
    private FirebaseUser user;

    //Firebase
    private DatabaseReference databaseReference, mdatabaseReference;

    //variables declaration
    String keyUser;
    String userNameString, inputSurnameString, inputCityString, inputAddressString, userContactString;

    //progress bar
    private ProgressDialog progressDialog;
    private ActivityUpdateProfileBinding updateProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_update_profile);
        updateProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_update_profile);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile Update");

        //validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //progress bar initialization
        progressDialog = new ProgressDialog(this);

        //getting intent
        Intent intent = getIntent();
        keyUser = intent.getStringExtra("User_KEY");

        //initializing user Key in String from database
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //firebase getting instances
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


        //updating data to database for a parent registered
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Infor(dataSnapshot,user.getUid());

                updateProfileBinding.updateFullname.setText(dataSnapshot.child("userName").getValue().toString());
                updateProfileBinding.updateSurname.setText(dataSnapshot.child("userSurname").getValue().toString());
                updateProfileBinding.updatePhone.setText(dataSnapshot.child("userContact").getValue().toString());
                updateProfileBinding.updateAddress.setText(dataSnapshot.child("userAddress").getValue().toString());
                updateProfileBinding.updateCity.setText(dataSnapshot.child("userCity").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.update_fullname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.update_Surname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.surnameerror);
        awesomeValidation.addValidation(this, R.id.update_address, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.address);
        awesomeValidation.addValidation(this, R.id.update_city, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.city);
        awesomeValidation.addValidation(this, R.id.update_phone, "^[+]?[0-9]{10,13}$", R.string.contacterror);




    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        //saving data information to database
        //noinspection SimplifiableIfStatement
        if (id == R.id.profile_save) {
            if (awesomeValidation.validate()) {

                progressDialog.setMessage("Wait Updating Your Profile");
                progressDialog.show();

                userNameString = updateProfileBinding.updateFullname.getText().toString().trim();
                userContactString = updateProfileBinding.updatePhone.getText().toString().trim();
                inputSurnameString = updateProfileBinding.updateSurname.getText().toString().trim();
                inputAddressString = updateProfileBinding.updateAddress.getText().toString().trim();
                inputCityString = updateProfileBinding.updateCity.getText().toString().trim();


                UserProfile update = new UserProfile();
                update.setKeyUser(keyUser);
                update.setUserName(userNameString);
                update.setUserSurname(inputSurnameString);
                update.setUserContact(userContactString);
                update.setUserAddress(inputAddressString);
                update.setUserCity(inputCityString);

                databaseReference.child("userName").setValue(update.getUserName());
                databaseReference.child("userSurname").setValue(update.getUserSurname());
                databaseReference.child("userContact").setValue(update.getUserContact());
                databaseReference.child("userAddress").setValue(update.getUserAddress());
                databaseReference.child("userCity").setValue(update.getUserCity());

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(UpdateProfile.this, "Information you are trying to upload doesnt exist", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Toast.makeText(UpdateProfile.this, "User Profile Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateProfile.this, ViewProfile.class);
                startActivity(intent);
            } else {
                Toast.makeText(UpdateProfile.this, "User Failed to Update Profile", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

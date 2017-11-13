package com.example.codetribe.my_kid;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {


    private AwesomeValidation awesomeValidation;

    private EditText
            Name,
            Surname,
            Address,
            City,
            phoneNumber;

    FirebaseUser user;

    //validdation
    private TextInputLayout
            inputLayoutName,
            inputLayoutSurname,
            inputLayoutCity,
            inputLayoutAddress,
            inputLayoutNumber;

    //Firebase
    private DatabaseReference databaseReference, mdatabaseReference;

    String keyUser;
    String userNameString, inputSurnameString, inputCityString, inputAddressString, userContactString;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile Update");



        //validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        progressDialog = new ProgressDialog(this);
        //getting intent

        Intent intent = getIntent();
        keyUser = intent.getStringExtra("User_KEY");

        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        //database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Infor(dataSnapshot,user.getUid());

                Name.setText(dataSnapshot.child("userName").getValue().toString());
                Surname.setText(dataSnapshot.child("userSurname").getValue().toString());
                phoneNumber.setText(dataSnapshot.child("userContact").getValue().toString());
                Address.setText(dataSnapshot.child("userAddress").getValue().toString());
                City.setText(dataSnapshot.child("userCity").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Edit lText
        Name = (EditText) findViewById(R.id.update_fullname);
        Surname = (EditText) findViewById(R.id.update_Surname);
        Address = (EditText) findViewById(R.id.update_address);
        City = (EditText) findViewById(R.id.update_city);
        phoneNumber = (EditText) findViewById(R.id.update_phone);


        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.update_fullname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.update_Surname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.surnameerror);
        awesomeValidation.addValidation(this, R.id.update_address, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.address);
        awesomeValidation.addValidation(this, R.id.update_city, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.city);
        awesomeValidation.addValidation(this, R.id.update_phone, "^[+]?[0-9]{10,13}$", R.string.contacterror);

        //TextLayout
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_update_fullname);
        inputLayoutSurname = (TextInputLayout) findViewById(R.id.input_update_Surname);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_update_address);
        inputLayoutCity = (TextInputLayout) findViewById(R.id.input_update_city);
        inputLayoutNumber = (TextInputLayout) findViewById(R.id.input_update_phoneNo);



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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile_save) {
            if (awesomeValidation.validate()) {

                progressDialog.setMessage("Wait Updating Your Profile");
                progressDialog.show();

                userNameString = Name.getText().toString().trim();
                userContactString = phoneNumber.getText().toString().trim();
                inputSurnameString = Surname.getText().toString().trim();
                inputAddressString = Address.getText().toString().trim();
                inputCityString = City.getText().toString().trim();


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
                            Toast.makeText(UpdateProfile.this, "Infor you are trying to upload doesnt exist", Toast.LENGTH_SHORT).show();
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

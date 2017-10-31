package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.codetribe.my_kid.R.id.gender;

public class CreateParentProfile extends AppCompatActivity {


    private AwesomeValidation awesomeValidation;
    TextView signUpButton;
    private TextView editprofile;

    private EditText inputName,
            inputSurname,
            inputIdnumber,
            inputAddress,
            inputCity,
            inputCellphoneNumber;

    FirebaseUser user;
    //validdation
    private TextInputLayout
            inputLayoutName,
            inputLayoutSurname, inputLayoutAddress,
            inputLayoutCity,
            inputLayoutIdNumber,
            inputLayoutNumber;

    //private Button signUpButton;
    RadioGroup radGender;

    //Firebase
    private DatabaseReference databaseReference;

    String keyUser;
    String userNameString, inputSurnameString, inputCityString, inputAddressString, inputIdnumberString, userContactString, genderString;


    private RadioButton rdGenders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createparentprofile);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register Profile Update");


        //validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //getting intent

        Intent intent = getIntent();
        keyUser = intent.getStringExtra("User_KEY");


        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/CreateParentProfile.java
=======
        //getting UserKey
        // String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

>>>>>>> 7049439f0af4f0a7a5090f6ff105ba8d86b09ee5:app/src/main/java/com/example/codetribe/my_kid/ProfileUpdate.java

        //database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


        // signupLink = (TextView) findViewById(R.id.back_to_signup);
        signUpButton = (TextView) findViewById(R.id.btnRegister);

        //Edit lText
        inputName = (EditText) findViewById(R.id.reg_fullname);
        inputSurname = (EditText) findViewById(R.id.reg_Surname);
        inputAddress = (EditText) findViewById(R.id.reg_address);
        inputCity = (EditText) findViewById(R.id.reg_city);
        inputCellphoneNumber = (EditText) findViewById(R.id.reg_phone);
        radGender = (RadioGroup) findViewById(gender);
        inputIdnumber = (EditText) findViewById(R.id.reg_idnumber);

        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.reg_fullname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.reg_Surname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.surnameerror);
        awesomeValidation.addValidation(this, R.id.reg_address, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.address);
        awesomeValidation.addValidation(this, R.id.reg_city, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.city);
        awesomeValidation.addValidation(this, R.id.reg_phone, "^[+]?[0-9]{10,13}$", R.string.contacterror);
        awesomeValidation.addValidation(this, R.id.reg_idnumber, "^[0-9]{13}$", R.string.err_msg_idnumber);


        //TextLayout
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_reg_fullname);
        inputLayoutSurname = (TextInputLayout) findViewById(R.id.input_reg_Surname);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_reg_address);
        inputLayoutCity = (TextInputLayout) findViewById(R.id.input_reg_city);
        inputLayoutIdNumber = (TextInputLayout) findViewById(R.id.input_reg_idNumber);
        inputLayoutNumber = (TextInputLayout) findViewById(R.id.input_reg_phoneNo);


        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    userNameString = inputName.getText().toString().trim();
                    userContactString = inputCellphoneNumber.getText().toString().trim();
                    inputSurnameString = inputSurname.getText().toString().trim();
                    inputIdnumberString = inputIdnumber.getText().toString().trim();
                    inputAddressString = inputAddress.getText().toString().trim();
                    inputCityString = inputCity.getText().toString().trim();
                    int selectedId = radGender.getCheckedRadioButtonId();
                    rdGenders = (RadioButton) findViewById(selectedId);
                    genderString = rdGenders.getText().toString();


                    if (!TextUtils.isEmpty(inputIdnumberString)) {


                        String isVerified = "verified";
                        UserProfile profile = new UserProfile(keyUser, userNameString, inputSurnameString, inputIdnumberString, inputAddressString, inputCityString, userContactString, genderString, isVerified);
                        databaseReference.child("userName").setValue(profile.getUserName());
                        databaseReference.child("userSurname").setValue(profile.getUserSurname());
                        databaseReference.child("userIdNumber").setValue(profile.getUserIdNumber());
                        databaseReference.child("userContact").setValue(profile.getUserContact());
                        databaseReference.child("userAddress").setValue(profile.getUserAddress());
                        databaseReference.child("userCity").setValue(profile.getUserCity());
                        databaseReference.child("userGender").setValue(profile.getUserGender());
                        databaseReference.child("isVerified").setValue(profile.getIsVerified());

<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/CreateParentProfile.java
                        if (!TextUtils.isEmpty(inputIdnumberString)) {


                            String isVerified = "verified";
                            UserProfile profile = new UserProfile(keyUser, userNameString, inputSurnameString, inputIdnumberString, inputAddressString, inputCityString, userContactString, genderString, isVerified);
                            databaseReference.child("userName").setValue(profile.getUserName());
                            databaseReference.child("userSurname").setValue(profile.getUserSurname());
                            databaseReference.child("userIdNumber").setValue(profile.getUserIdNumber());
                            databaseReference.child("userContact").setValue(profile.getUserContact());
                            databaseReference.child("userAddress").setValue(profile.getUserAddress());
                            databaseReference.child("userCity").setValue(profile.getUserCity());
                            databaseReference.child("userGender").setValue(profile.getUserGender());
                            databaseReference.child("isVerified").setValue(profile.getIsVerified());


                            Toast.makeText(CreateParentProfile.this, "User Profile Updated", Toast.LENGTH_SHORT).show();
                            // startActivity(new Intent(CreateParentProfile.this, ParentActivity.class));


                        } else {
                            Toast.makeText(CreateParentProfile.this, "User Failed to Update Profile", Toast.LENGTH_SHORT).show();
                        }
=======
>>>>>>> 7049439f0af4f0a7a5090f6ff105ba8d86b09ee5:app/src/main/java/com/example/codetribe/my_kid/ProfileUpdate.java

                        Toast.makeText(ProfileUpdate.this, "User Profile Updated", Toast.LENGTH_SHORT).show();
                        // startActivity(new Intent(ProfileUpdate.this, ParentActivity.class));


                    } else {
                        Toast.makeText(ProfileUpdate.this, "User Failed to Update Profile", Toast.LENGTH_SHORT).show();
                    }


                }

            }

        });


    }

}



















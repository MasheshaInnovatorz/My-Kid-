package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.bumptech.glide.Glide;

=======
>>>>>>> 48acdef426884f6e4e790c7441e026b450efd7af
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.codetribe.my_kid.R.id.gender;

public class ProfileUpdate extends AppCompatActivity {


<<<<<<< HEAD
=======

    private AwesomeValidation awesomeValidation;
>>>>>>> 48acdef426884f6e4e790c7441e026b450efd7af

    private AwesomeValidation awesomeValidation;
      TextView signUpButton;
<<<<<<< HEAD
=======

>>>>>>> 48acdef426884f6e4e790c7441e026b450efd7af
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
        setContentView(R.layout.activity_profile_edit);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Profile Update");



        //validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        
        //getting intent

        Intent intent = getIntent();
        keyUser = intent.getStringExtra("User_KEY");

<<<<<<< HEAD
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //getting UserKey
       // String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
=======

        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
>>>>>>> 48acdef426884f6e4e790c7441e026b450efd7af

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


<<<<<<< HEAD
=======




>>>>>>> 48acdef426884f6e4e790c7441e026b450efd7af
        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputSurname.addTextChangedListener(new MyTextWatcher(inputSurname));
        inputAddress.addTextChangedListener(new MyTextWatcher(inputAddress));
        inputCity.addTextChangedListener(new MyTextWatcher(inputCity));
        inputCellphoneNumber.addTextChangedListener(new MyTextWatcher(inputCellphoneNumber));
<<<<<<< HEAD
=======
        inputIdnumber.addTextChangedListener(new MyTextWatcher(inputIdnumber));
>>>>>>> 48acdef426884f6e4e790c7441e026b450efd7af
        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                validateUpdate();
            }
        });
    }



    private void validateUpdate() {

        Boolean boom = true;
        if (!validateName()) {
            boom = false;
            return;
        }

        if (!validateSurname()) {
            boom = false;
            return;
        }

        if (!validateAddress()) {
            boom = false;
            return;
        }

        if (!validateCity()) {
            boom = false;
            return;
        }

        if (!validatePhone()) {
            boom = false;
            return;
        }

        if (!validateIdNo()) {
            boom = false;
            return;
        }


        if (boom == true) {
<<<<<<< HEAD


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
=======


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
>>>>>>> 48acdef426884f6e4e790c7441e026b450efd7af

                            //databaseReference.setValue(ProfileUpdate);

<<<<<<< HEAD
                            Toast.makeText(ProfileUpdate.this, "User Profile Updated", Toast.LENGTH_SHORT).show();
                            // startActivity(new Intent(ProfileUpdate.this, ParentActivity.class));
=======
                        if (!TextUtils.isEmpty(inputIdnumberString)) {
>>>>>>> 48acdef426884f6e4e790c7441e026b450efd7af

                        } else {
                            Toast.makeText(ProfileUpdate.this, "User Failed to Update Profile", Toast.LENGTH_SHORT).show();
                        }

<<<<<<< HEAD

                    }
=======
                            String isVerified = "verified";

>>>>>>> 48acdef426884f6e4e790c7441e026b450efd7af

                            UserProfile profile = new UserProfile(keyUser, userNameString, inputSurnameString, inputIdnumberString, inputAddressString, inputCityString, userContactString, genderString, isVerified);

<<<<<<< HEAD
                }
=======
                            databaseReference.child("userName").setValue(profile.getUserName());
                            databaseReference.child("userSurname").setValue(profile.getUserSurname());
                            databaseReference.child("userIdNumber").setValue(profile.getUserIdNumber());
                            databaseReference.child("userContact").setValue(profile.getUserContact());
                            databaseReference.child("userAddress").setValue(profile.getUserAddress());
                            databaseReference.child("userCity").setValue(profile.getUserCity());
                            databaseReference.child("userGender").setValue(profile.getUserGender());
                            databaseReference.child("isVerified").setValue(profile.getIsVerified());
>>>>>>> 48acdef426884f6e4e790c7441e026b450efd7af

                            //databaseReference.setValue(ProfileUpdate);

<<<<<<< HEAD
            });


        }}
=======
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
>>>>>>> 48acdef426884f6e4e790c7441e026b450efd7af





    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty() && inputName.getText().length() < 6) {
            inputLayoutName.setError("Enter the number");
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateSurname() {
        if (inputSurname.getText().toString().trim().isEmpty() && inputSurname.getText().length() < 6) {
            inputLayoutSurname.setError("Enter Your Surname");
            requestFocus(inputSurname);
            return false;
        } else {
            inputLayoutSurname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAddress() {
        if (inputAddress.getText().toString().trim().isEmpty() && inputAddress.getText().length() < 6) {
            inputLayoutAddress.setError("Enter Your Your Address");
            requestFocus(inputAddress);
            return false;
        } else {
            inputLayoutAddress.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCity() {
        if (inputCity.getText().toString().trim().isEmpty() && inputCity.getText().length() < 6) {
            inputLayoutCity.setError("Enter Your Your Address");
            requestFocus(inputCity);
            return false;
        } else {
            inputLayoutCity.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {
        if (inputCellphoneNumber.getText().toString().trim().isEmpty() && inputCellphoneNumber.getText().length() < 6) {
            inputLayoutNumber.setError("Enter Your Your Full Number phone");
            requestFocus(inputCellphoneNumber);
            return false;
        } else {
            inputLayoutNumber.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateIdNo() {
        if (inputIdnumber.getText().toString().trim().isEmpty() && inputIdnumber.getText().length() < 6) {
            inputLayoutIdNumber.setError("Enter Your Your Full Id Number phone");
            requestFocus(inputIdnumber);
            return false;
        } else {
            inputLayoutIdNumber.setErrorEnabled(false);
        }

        return true;
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {


            switch (view.getId()) {
                case R.id.reg_fullname:
                    validateName();
                    break;
                case R.id.reg_Surname:
                    validateSurname();
                    break;
                case R.id.reg_address:
                    validateAddress();
                    break;
                case R.id.reg_city:
                    validateCity();
                    break;
                case R.id.reg_phone:
                    validatePhone();
                    break;
                case R.id.reg_idnumber:
                    validateIdNo();
                    break;
            }


        }

    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }





    }


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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.codetribe.my_kid.R.id.gender;

public class Profile_Update extends AppCompatActivity {


      TextView signUpButton;
    private TextView editprofile;
    private EditText inputName, inputSurname, inputEmail, inputIdnumber, inputAddress, inputCity, inputCellphoneNumber,reg_idnumberhint;
    private TextInputLayout inputLayoutName, inputLayoutSurname, inputLayoutEmail, inputLayoutPassword, inputLayoutIdnumber;
    //private Button signUpButton;
    RadioGroup radGender;

    //Firebase
    private DatabaseReference databaseReference;

    String keyUser;
    String userNameString,inputSurnameString,inputCityString,inputAddressString,inputIdnumberString,userContactString,genderString;


private RadioButton rdGenders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Profile_Update");



        //database
        Intent intent = getIntent();
        keyUser =  intent.getStringExtra("User_KEY");



        //database
     databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(keyUser);

        //buttons
       // signupLink = (TextView) findViewById(R.id.back_to_signup);
        signUpButton = (TextView) findViewById(R.id.btnRegister);

        //Edit lessons


        inputName = (EditText) findViewById(R.id.reg_fullname);
        inputSurname = (EditText) findViewById(R.id.reg_Surname);
        //inputEmail = (EditText) findViewById(R.id.reg_email);
        //inputPassword = (EditText) findViewById(R.id.reg_password);

       // inputIdnumber = (EditText) findViewById(R.id.reg_idParents);
        //inputIdnumber = (EditText) findViewById(R.id.reg_idParents);

        inputAddress = (EditText) findViewById(R.id.reg_address);
        inputCity = (EditText) findViewById(R.id.reg_city);
        inputCellphoneNumber = (EditText) findViewById(R.id.reg_phone);
        radGender = (RadioGroup)findViewById(gender);
        inputIdnumber= (EditText) findViewById(R.id.reg_idnumber);






        //TextLayout

        inputLayoutName = (TextInputLayout)findViewById(R.id.input_reg_fullname);
        inputLayoutSurname = (TextInputLayout)findViewById(R.id.input_reg_Surname);

       // inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
       // inputLayoutPassword = (TextInputLayout)findViewById(R.id.input_layout_password);
       //inputLayoutIdnumber = (TextInputLayout)findViewById(R.id.input_reg_idParents);

      //  inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
     //   inputLayoutPassword = (TextInputLayout)findViewById(R.id.input_layout_password);
      //  inputLayoutIdnumber = (TextInputLayout)findViewById(R.id.input_reg_idParents);


        //watcher
        //inputName.addTextChangedListener(new MyInputWatcher(inputName));
        //inputSurname.addTextChangedListener(new MyInputWatcher(inputSurname));
        //inputEmail.addTextChangedListener(new MyInputWatcher(inputEmail));
        //inputPassword.addTextChangedListener(new MyInputWatcher(inputPassword));
        //inputIdnumber.addTextChangedListener(new MyInputWatcher(inputIdnumber));
       signUpButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //submitForm();

                saveProfile();
              /*  userNameString = inputName.getText().toString().trim();
           userContactString = inputCellphoneNumber.getText().toString().trim();
                inputSurnameString = inputSurname.getText().toString().trim();
                inputIdnumberString = inputIdnumber.getText().toString().trim();
                inputAddressString = inputAddress.getText().toString().trim();
                inputCityString = inputCity.getText().toString().trim();
                int selectedId= radGender.getCheckedRadioButtonId();
                rdGenders =(RadioButton)findViewById(selectedId);
                genderString  = rdGenders.getText().toString();



                    databaseReference.child("userName").setValue(userNameString);
                    databaseReference.child("userSurname").setValue(inputSurnameString);
                    databaseReference.child("userIdNumber").setValue(inputIdnumberString);
                    databaseReference.child("userContact").setValue(userContactString);
                    databaseReference.child("userAddress").setValue(inputAddressString);
                    databaseReference.child("userCity").setValue(inputCityString);
                    databaseReference.child("userGender").setValue(genderString);
                    databaseReference.child("isVerified").setValue("verified");

                    Toast.makeText(Profile_Update.this, "User Profile_Update Added", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Profile_Update.this, Welcome_activity.class));*/

            }
        });

    }

    private void saveProfile(){

       // submitForm();

        userNameString = inputName.getText().toString().trim();
        userContactString = inputCellphoneNumber.getText().toString().trim();
        inputSurnameString = inputSurname.getText().toString().trim();
        inputIdnumberString = inputIdnumber.getText().toString().trim();
        inputAddressString = inputAddress.getText().toString().trim();
        inputCityString = inputCity.getText().toString().trim();
        int selectedId= radGender.getCheckedRadioButtonId();
        rdGenders =(RadioButton)findViewById(selectedId);
        genderString  = rdGenders.getText().toString();





        //databaseKids.child(id).setValue(kids);

            if(!TextUtils.isEmpty(inputIdnumberString)){


                String isVerified = "verified";


                UserProfile profile = new UserProfile(keyUser, userNameString, inputSurnameString, inputIdnumberString, inputAddressString, inputCityString,userContactString,genderString,isVerified);



        databaseReference.child("userName").setValue(profile.getUserName());
        databaseReference.child("userSurname").setValue(profile.getUserSurname());
        databaseReference.child("userIdNumber").setValue(profile.getUserIdNumber());
        databaseReference.child("userContact").setValue(profile.getUserContact());
        databaseReference.child("userAddress").setValue(profile.getUserAddress());
        databaseReference.child("userCity").setValue(profile.getUserCity());
        databaseReference.child("userGender").setValue(profile.getUserGender());
        databaseReference.child("isVerified").setValue(profile.getIsVerified());



             //databaseReference.setValue(Profile_Update);

                Toast.makeText(Profile_Update.this, "User Profile_Update Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Profile_Update.this, Welcome_activity.class));

            }else{
                Toast.makeText(Profile_Update.this, "User Failed to add Profile_Update", Toast.LENGTH_SHORT).show();
            }
    }

    private void submitForm() {
        if (!validateFullName()) {
            return;
        }
        if (!validateSurname()) {
            return;
        }
        /*if (!validateIdNumber()) {
            return;
        }*/

       /* if (!validateEmail()) {
            return;
        }*/

       /* if (!validatePassword()) {
            return;
        }*/

       // Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateFullName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateSurname() {
        if (inputSurname.getText().toString().trim().isEmpty()) {
            inputLayoutSurname.setError(getString(R.string.err_msg_surname));
            requestFocus(inputSurname);
            return false;
        } else {
            inputLayoutSurname.setErrorEnabled(false);
        }
        return true;
    }

    /*private boolean validateIdNumber() {
        if (inputIdnumber.getText().toString().trim().isEmpty()) {
            inputLayoutIdnumber.setError(getString(R.string.err_msg_idnumber));
            requestFocus(inputIdnumber);
            return false;
        } else {
            inputLayoutIdnumber.setErrorEnabled(false);
        }
        return true;
    }*/

   /* private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }*/

    /*private boolean validateEmail() {
       String email = inputEmail.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;*/
    /*    if (inputEmail.getText().toString().trim().isEmpty()) {
            inputLayoutSurname.setError(getString(R.string.err_msg_surname));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }*/

/*
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }*/

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



    private class MyInputWatcher implements TextWatcher {
        private View view;

        private MyInputWatcher(View view) {
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
                case R.id.input_reg_fullname:
                    validateFullName();
                    break;
                case R.id.input_reg_Surname:
                    validateSurname();
                    break;

            //    case R.id.input_reg_idParents:
                   // validateIdNumber();
                   // break;

                //case R.id.input_reg_idParents:
                   // validateIdNumber();
                  //  break;

               /* case R.id.input_reg_email:
                    validateEmail();
                    break;*/
               /* case R.id.input_reg_password:
                    validatePassword();
                    break;*/

            }

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

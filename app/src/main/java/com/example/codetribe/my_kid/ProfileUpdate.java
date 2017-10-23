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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.codetribe.my_kid.R.id.gender;

public class ProfileUpdate extends AppCompatActivity {


      TextView signUpButton;
    private TextView editprofile;

    private EditText inputName,
                     inputSurname,
                     inputIdnumber,
                     inputAddress,
                     inputCity,
                     inputCellphoneNumber;


    //validdation
    private TextInputLayout
            inputLayoutName,
            inputLayoutSurname
           ,inputLayoutAddress,
            inputLayoutCity,
            inputLayoutIdNumber,
            inputLayoutNumber;

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
        getSupportActionBar().setTitle("Update Profile Update");



        //database
        Intent intent = getIntent();
        keyUser =  intent.getStringExtra("User_KEY");


String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //database
     databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //buttons
       // signupLink = (TextView) findViewById(R.id.back_to_signup);
        signUpButton = (TextView) findViewById(R.id.btnRegister);

        //Edit lText
        inputName = (EditText) findViewById(R.id.reg_fullname);
        inputSurname = (EditText) findViewById(R.id.reg_Surname);
        inputAddress = (EditText) findViewById(R.id.reg_address);
        inputCity = (EditText) findViewById(R.id.reg_city);
        inputCellphoneNumber = (EditText) findViewById(R.id.reg_phone);
        radGender = (RadioGroup)findViewById(gender);
        inputIdnumber= (EditText) findViewById(R.id.reg_idnumber);

        //TextLayout
        inputLayoutName = (TextInputLayout)findViewById(R.id.input_reg_fullname);
        inputLayoutSurname = (TextInputLayout)findViewById(R.id.input_reg_Surname);
        inputLayoutAddress = (TextInputLayout)findViewById(R.id.input_reg_address);
        inputLayoutCity = (TextInputLayout) findViewById(R.id.input_reg_city);
        inputLayoutIdNumber = (TextInputLayout)findViewById(R.id.input_reg_idNumber);
        inputLayoutNumber = (TextInputLayout) findViewById(R.id.input_reg_phoneNo);


        //watcher
        inputName.addTextChangedListener(new MyInputWatcher(inputName));
        inputSurname.addTextChangedListener(new MyInputWatcher(inputSurname));
        inputAddress.addTextChangedListener(new MyInputWatcher(inputAddress));
        inputCity.addTextChangedListener(new MyInputWatcher(inputCity));
        inputIdnumber.addTextChangedListener(new MyInputWatcher(inputIdnumber));
        inputCellphoneNumber.addTextChangedListener(new MyInputWatcher(inputCellphoneNumber));

       signUpButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //submitForm();

                saveProfile();


            }
        });

    }

    private void saveProfile(){

        submitForm();

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



             //databaseReference.setValue(ProfileUpdate);

                Toast.makeText(ProfileUpdate.this, "User Profile Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProfileUpdate.this, ParentActivity.class));

            }else{
                Toast.makeText(ProfileUpdate.this, "User Failed to Update Profile", Toast.LENGTH_SHORT).show();
            }
    }

    private void submitForm() {
        if (!validateFullName()) {
            return;
        }
        if (!validateSurname()) {
            return;
        }
        if (!validateIdNumber()) {
            return;
        }



        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateFullName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            if (inputName.getText().toString().length() <=6) {

                inputLayoutName.setError("Your name is too short atleast >6 letters will do");
                return false;
            }else{
                inputLayoutName.setErrorEnabled(false);
            }
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

    private boolean validateIdNumber() {
        if (inputIdnumber.getText().toString().trim().isEmpty()) {
            if(inputIdnumber.getText().toString().length() <13 || inputIdnumber.getText().toString().length() >13){
                inputLayoutIdNumber.setError("Identity Number Should be 13 digits");
            }
            inputLayoutIdNumber.setError(getString(R.string.err_msg_idnumber));
            requestFocus(inputIdnumber);
            return false;
        } else {
            inputLayoutIdNumber.setErrorEnabled(false);
        }
        return true;
    }


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

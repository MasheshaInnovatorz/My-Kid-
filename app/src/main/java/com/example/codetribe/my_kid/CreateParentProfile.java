package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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
        getSupportActionBar().setTitle(" Parent Profile Update");


        //validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //getting intent
        Intent intent = getIntent();
        keyUser = intent.getStringExtra("User_KEY");


        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

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


    }

    /*
    //back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);

    }

*/
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

            int selectedId = radGender.getCheckedRadioButtonId();

            if (awesomeValidation.validate()) {
                if (selectedId != -1) {
                    userNameString = inputName.getText().toString().trim();
                    userContactString = inputCellphoneNumber.getText().toString().trim();
                    inputSurnameString = inputSurname.getText().toString().trim();
                    inputIdnumberString = inputIdnumber.getText().toString().trim();
                    inputAddressString = inputAddress.getText().toString().trim();
                    inputCityString = inputCity.getText().toString().trim();

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



                        startActivity(new Intent(CreateParentProfile.this, ParentTabbedActivity.class));

                        Toast.makeText(CreateParentProfile.this, "User Profile Updated", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(CreateParentProfile.this, "User Failed to Update Profile", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateParentProfile.this, "Make sure you select gender before you continue", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CreateParentProfile.this, "Make sure you fix all the error shown in your input space", Toast.LENGTH_LONG).show();
            }


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

















package com.example.codetribe.my_kid.parent_Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.account_Activities.UserProfile;
import com.example.codetribe.my_kid.databinding.CreateParentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.codetribe.my_kid.R.id.gender;

public class CreateParentProfile extends AppCompatActivity {

    private AwesomeValidation awesomeValidation;

    //city and province
    private ArrayAdapter<String> adapter;
    private int positions;
    private String crechCity,  userCityString,userprovinceString;
    private String province = "";


    //Firebase
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private String keyUser;
    private String userNameString, inputSurnameString, inputCityString, inputAddressString, inputIdnumberString, userContactString, genderString;

    private RadioButton rdGenders;

    private CreateParentProfileBinding createParentProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createParentProfileBinding = DataBindingUtil.setContentView(this,R.layout.create_parent_profile);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Parent Profile Update");

        //validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //getting intent
        Intent intent = getIntent();
        keyUser = intent.getStringExtra("User_KEY");

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        user = FirebaseAuth.getInstance().getCurrentUser();
        //database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.reg_fullname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.reg_Surname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.surnameerror);
        awesomeValidation.addValidation(this, R.id.reg_address, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.address);
        awesomeValidation.addValidation(this, R.id.reg_city, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.city);
        awesomeValidation.addValidation(this, R.id.reg_phone, "^[+]?[0-9]{10,13}$", R.string.contacterror);
        awesomeValidation.addValidation(this, R.id.reg_idnumber, "^[0-9]{13}$", R.string.err_msg_idnumber);


        //provinces
        ArrayAdapter<String> provincesadapter = new ArrayAdapter<String>(CreateParentProfile.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_Province));
        provincesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        createParentProfileBinding.parentProvincesSpinner.setAdapter(provincesadapter);

        //cities
        createParentProfileBinding.parentProvincesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

                positions = position;

                switch (getResources().getStringArray(R.array.city_Province)[positions]) {
                    case "Limpopo":

                        adapter = new ArrayAdapter<String>(CreateParentProfile.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_limpopo));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        createParentProfileBinding.parentCitySpinner.setAdapter(adapter);

                        break;
                    case "Gauteng":

                        adapter = new ArrayAdapter<String>(CreateParentProfile.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_gauteng));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        createParentProfileBinding.parentCitySpinner.setAdapter(adapter);
                        break;
                    case "Western Cape":

                        adapter = new ArrayAdapter<String>(CreateParentProfile.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_western_cape));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        createParentProfileBinding.parentCitySpinner.setAdapter(adapter);
                        break;
                    case "Northern Cape":

                        adapter = new ArrayAdapter<String>(CreateParentProfile.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_Northern_Cape));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        createParentProfileBinding.parentCitySpinner.setAdapter(adapter);
                        break;
                    case "Eastern Cape":

                        adapter = new ArrayAdapter<String>(CreateParentProfile.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_eastern_Cape));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        createParentProfileBinding.parentCitySpinner.setAdapter(adapter);
                        break;
                    case "Free State":
                        adapter = new ArrayAdapter<String>(CreateParentProfile.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_free_state));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        createParentProfileBinding.parentCitySpinner.setAdapter(adapter);
                        break;

                    case "KwaZulu-Natal":
                        adapter = new ArrayAdapter<String>(CreateParentProfile.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_Kwazulu_Natal));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        createParentProfileBinding.parentCitySpinner.setAdapter(adapter);
                        break;
                    default:
                        Toast.makeText(CreateParentProfile.this, "PLease select one of the provinces", Toast.LENGTH_SHORT).show();
                        break;
                }

                province  = getResources().getStringArray(R.array.city_Province)[position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

                Toast.makeText(CreateParentProfile.this, "Please Select City", Toast.LENGTH_SHORT).show();
            }

        });

        //cities
        createParentProfileBinding.parentCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg1, View arg2, int position1, long id1) {

                // TODO Auto-generated method stub
                crechCity = getResources().getStringArray(R.array.city_list)[position1];

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

                Toast.makeText(CreateParentProfile.this, "Please Select City", Toast.LENGTH_SHORT).show();
            }

        });

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
        //noinspection SimplifiableIfStatement
        if (id == R.id.profile_save) {

            int selectedId = createParentProfileBinding.gender.getCheckedRadioButtonId();

            if (awesomeValidation.validate()) {
                if (selectedId != -1) {
                    userNameString = createParentProfileBinding.regFullname.getText().toString().trim();
                    userContactString = createParentProfileBinding.regPhone.getText().toString().trim();
                    inputSurnameString = createParentProfileBinding.regSurname.getText().toString().trim();
                    inputIdnumberString = createParentProfileBinding.regIdnumber.getText().toString().trim();
                    inputAddressString = createParentProfileBinding.regAddress.getText().toString().trim();
                    userCityString = crechCity.trim();
                    userprovinceString= province.trim();

                    rdGenders = (RadioButton) findViewById(selectedId);
                    genderString = rdGenders.getText().toString();


                    if (!TextUtils.isEmpty(inputIdnumberString)) {

                        String isVerified = "verified";
                        UserProfile profile = new UserProfile(keyUser, userNameString, inputSurnameString, inputIdnumberString, inputAddressString, userContactString, genderString,userCityString,userprovinceString,isVerified);
                        databaseReference.child("userName").setValue(profile.getUserName());
                        databaseReference.child("userSurname").setValue(profile.getUserSurname());
                        databaseReference.child("userIdNumber").setValue(profile.getUserIdNumber());
                        databaseReference.child("userContact").setValue(profile.getUserContact());
                        databaseReference.child("userAddress").setValue(profile.getUserAddress());
                        databaseReference.child("userProvince").setValue(profile.getuserprovince());
                        databaseReference.child("userCity").setValue(profile.getUserCity());
                        databaseReference.child("userGender").setValue(profile.getUserGender());
                        databaseReference.child("isVerified").setValue(profile.getIsVerified());
                        databaseReference.child("userProfilePic").setValue(" ");


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

















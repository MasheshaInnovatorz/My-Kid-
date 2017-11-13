package com.example.codetribe.my_kid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.example.codetribe.my_kid.R.id.orgCity;
import static com.example.codetribe.my_kid.R.id.teacherpassword;


public class SignUpOrganisationActivity extends AppCompatActivity {

    FirebaseAuth.AuthStateListener mAuthListener;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPrefEditor;
    DatabaseReference mOrganizationRef;
    private TextView signup;
    private EditText orgaEmail,

    orgPassword,
            crechName,
            crechAddress,
            crechCity,
            crechPhoneNo,
            adminName,
            adminSurname,
            adminIdNo;
    private RadioButton radGender;


    private ProgressDialog progressDialog;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;
    private RadioButton genderMale, genderFemale;

    private RadioGroup gender;
    //firebase Authentification
    private FirebaseAuth orgAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_organisation);

        getSupportActionBar().setTitle("Register Organisation");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        progressDialog = new ProgressDialog(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);//shared
        signup = (TextView) findViewById(R.id.btnRegisterCreche);
        orgAuth = FirebaseAuth.getInstance();
        mOrganizationRef = FirebaseDatabase.getInstance().getReference().child("Creche");


        orgaEmail = (EditText) findViewById(R.id.orgEmail);
        orgPassword = (EditText) findViewById(R.id.orgPassword);
        crechName = (EditText) findViewById(R.id.orgName);
        crechAddress = (EditText) findViewById(R.id.orgStrName);
        crechCity = (EditText) findViewById(orgCity);
        crechPhoneNo = (EditText) findViewById(R.id.orgTelNumber);
        adminName = (EditText) findViewById(R.id.orgAdminName);
        adminSurname = (EditText) findViewById(R.id.orgAdminSurname);
        adminIdNo = (EditText) findViewById(R.id.orgAdminIDNumber);
        gender = (RadioGroup) findViewById(R.id.AdminGender);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.orgEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this,R.id. orgPassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.orgName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.orgStrName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.orgCity, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.city);
        awesomeValidation.addValidation(this, R.id.orgTelNumber, "^[+]?[0-9]{10,13}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.orgAdminName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.orgAdminSurname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.surnameerror);
        awesomeValidation.addValidation(this, R.id.orgAdminIDNumber, "^^[0-9]{13}$", R.string.iderror);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = orgaEmail.getText().toString().trim();
                final String password = orgPassword.getText().toString().trim();

                /*
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                */

                sharedPrefEditor = sharedPreferences.edit();
                sharedPrefEditor.putString("email", email);
                sharedPrefEditor.apply();

                final String crechNameOrg, crechAddressOrg, crechCityOrg, crechPhoneNoOrg, adminNameOrg, adminSurnameOrg, adminIdNoOrg, adminGender;
                crechNameOrg = crechName.getText().toString().trim();
                crechAddressOrg = crechAddress.getText().toString().trim();
                crechCityOrg = crechCity.getText().toString().trim();
                crechPhoneNoOrg = crechPhoneNo.getText().toString().trim();
                adminNameOrg = adminName.getText().toString().trim();
                adminSurnameOrg = adminSurname.getText().toString().trim();
                adminIdNoOrg = adminIdNo.getText().toString().trim();

                int selectedId = gender.getCheckedRadioButtonId();
                radGender = (RadioButton) findViewById(selectedId);
                adminGender = radGender.getText().toString().trim();



                if (awesomeValidation.validate()) {

                    progressDialog.setMessage("Wait While Creating Organisation ");
                    progressDialog.show();
            //    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    orgAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {
                                // String user_id = task.getResult().getUser().getUid();
                                String adminRole = "admin";
                                String key = mOrganizationRef.push().getKey();

                                DatabaseReference mChildDatabase = mOrganizationRef;
                                DatabaseReference mAdminRef = FirebaseDatabase.getInstance().getReference().child("Users");
                                String userI = task.getResult().getUser().getUid();


                                OrganizationRegister orgReg = new OrganizationRegister(key, crechNameOrg, crechAddressOrg, crechCityOrg, email, crechPhoneNoOrg, password, key);

                                CrecheOnwer_Class adminReg = new CrecheOnwer_Class(userI, adminNameOrg, adminSurnameOrg, adminIdNoOrg, adminGender, adminRole, email, crechNameOrg, crechPhoneNoOrg,crechCityOrg);


                                //Map
                                Map<String, Object> postingOrg = orgReg.toMap();
                                Map<String, Object> organizationUpdate = new HashMap<>();

                                organizationUpdate.put(key, postingOrg);


                                Map<String, Object> postingAdmin = adminReg.toMap();
                                Map<String, Object> adminUpdate = new HashMap<>();

                                adminUpdate.put(userI, postingAdmin);


                                //Updating Messages
                                mAdminRef.updateChildren(adminUpdate);

                                mChildDatabase.updateChildren(organizationUpdate);

                                //Toast.makeText(SignUpOrganisationActivity.this, key, Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignUpOrganisationActivity.this, "Organization Sccessfully Created", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), AdminTabbedActivity.class));


                            } else {
                                Toast.makeText(SignUpOrganisationActivity.this, "Organizational Failed to SignUp", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });


                    orgAuth.signOut();
               // }


                } else {
                    Toast.makeText(SignUpOrganisationActivity.this, "Please Fix all the edit text", Toast.LENGTH_LONG).show();
                }
                //---



            }
        });

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

package com.example.codetribe.my_kid.organization_Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.util.Patterns;

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
import com.example.codetribe.my_kid.admin_Activities.AdminTabbedActivity;
import com.example.codetribe.my_kid.databinding.ActivitySignUpOrganisationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class SignUpOrganisationActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPrefEditor;
    private DatabaseReference mOrganizationRef, orgValidationRef, databasekidclass;
    private boolean valName;
    private int positions;
    private String province = "";

    private String name;

    private ArrayAdapter<String> adapter;

    private RadioButton radGender;

    private ProgressDialog progressDialog;

    private String crechCity = "";

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    //firebase Authentification
    private FirebaseAuth orgAuth;
    //user
    private FirebaseUser user;

    private ActivitySignUpOrganisationBinding organizationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        organizationBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_organisation);

        getSupportActionBar().setTitle("Register Organisation");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        orgAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        mOrganizationRef = FirebaseDatabase.getInstance().getReference().child("Creche");
        orgValidationRef = FirebaseDatabase.getInstance().getReference().child("Creche");

        //database
        databasekidclass = FirebaseDatabase.getInstance().getReference().child("kidclass");


        ArrayAdapter<String> provincesadapter = new ArrayAdapter<String>(SignUpOrganisationActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_Province));
        provincesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        organizationBinding.orgProvinces.setAdapter(provincesadapter);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.orgEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.orgPassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
        // awesomeValidation.addValidation(this,R.id. orgPassword, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\\\d])(?=.*[~`!@#\\\\$%\\\\^&\\\\*\\\\(\\\\)\\\\-_\\\\+=\\\\{\\\\}\\\\[\\\\]\\\\|\\\\;:\\\"<>,./\\\\?]).{8,}", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.orgName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.orgStrName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        //awesomeValidation.addValidation(this, R.id.orgCity, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.city);
        awesomeValidation.addValidation(this, R.id.orgTelNumber, "^[+]?[0-9]{10,13}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.orgAdminName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.orgAdminSurname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.surnameerror);
        awesomeValidation.addValidation(this, R.id.orgAdminIDNumber, "^^[0-9]{13}$", R.string.iderror);
        // awesomeValidation.addValidation(this, R.id.orgPostalCode, "^[+]?[0-3]{10,13}$", R.string.postalCode);
        //  awesomeValidation.addValidation(this, R.id.orgRegNumber, "^[+]?[0-9]{10,13}$", R.string.regNo);

        //province

        organizationBinding.orgProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {


                positions = position;

                switch (getResources().getStringArray(R.array.city_Province)[positions]) {
                    case "Limpopo":

                        adapter = new ArrayAdapter<String>(SignUpOrganisationActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_limpopo));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        organizationBinding.orgCitySpinner.setAdapter(adapter);

                        break;
                    case "Gauteng":

                        adapter = new ArrayAdapter<String>(SignUpOrganisationActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_gauteng));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        organizationBinding.orgCitySpinner.setAdapter(adapter);
                        break;
                    case "Western Cape":

                        adapter = new ArrayAdapter<String>(SignUpOrganisationActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_western_cape));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        organizationBinding.orgCitySpinner.setAdapter(adapter);
                        break;
                    case "Northern Cape":

                        adapter = new ArrayAdapter<String>(SignUpOrganisationActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_Northern_Cape));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        organizationBinding.orgCitySpinner.setAdapter(adapter);
                        break;
                    case "Eastern Cape":

                        adapter = new ArrayAdapter<String>(SignUpOrganisationActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_eastern_Cape));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        organizationBinding.orgCitySpinner.setAdapter(adapter);
                        break;
                    case "Free State":
                        adapter = new ArrayAdapter<String>(SignUpOrganisationActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_free_state));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        organizationBinding.orgCitySpinner.setAdapter(adapter);
                        break;

                    case "KwaZulu-Natal":
                        adapter = new ArrayAdapter<String>(SignUpOrganisationActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_Kwazulu_Natal));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        organizationBinding.orgCitySpinner.setAdapter(adapter);
                        break;
                    default:
                        Toast.makeText(SignUpOrganisationActivity.this, "PLease select one of the provinces", Toast.LENGTH_SHORT).show();
                        break;
                }

                province = getResources().getStringArray(R.array.city_Province)[position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

                Toast.makeText(SignUpOrganisationActivity.this, "Please Select Province", Toast.LENGTH_SHORT).show();
            }

        });

        //cities
        organizationBinding.orgCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg1, View arg2, int position1, long id1) {

                // TODO Auto-generated method stub

                crechCity = getResources().getStringArray(R.array.city_list)[position1];

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });

        organizationBinding.btnRegisterCreche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = organizationBinding.orgEmail.getText().toString().trim();
                final String password = organizationBinding.orgPassword.getText().toString().trim();

                final String creshes, crechNameOrg, provinceorg, crechAddressOrg, cityInfor, crechPhoneNoOrg, adminNameOrg, adminSurnameOrg, adminIdNoOrg, crechRefNumberOrg, crechPostalCodeOrg, adminGender;
                crechNameOrg = organizationBinding.orgName.getText().toString().trim();
                crechAddressOrg = organizationBinding.orgStrName.getText().toString().trim();

                //spinner

                cityInfor = crechCity.trim();
                provinceorg = province.trim();
                crechPhoneNoOrg = organizationBinding.orgTelNumber.getText().toString().trim();
                adminNameOrg = organizationBinding.orgAdminName.getText().toString().trim();
                adminSurnameOrg = organizationBinding.orgAdminSurname.getText().toString().trim();
                adminIdNoOrg = organizationBinding.orgAdminIDNumber.getText().toString().trim();

                crechPostalCodeOrg = organizationBinding.orgPostalCode.getText().toString().trim();

                int selectedId = organizationBinding.AdminGender.getCheckedRadioButtonId();

                if (password.isEmpty() || password.length() < 6) {
                    Toast.makeText(SignUpOrganisationActivity.this, "Password cannot be less than 6 characters!", Toast.LENGTH_SHORT).show();
                } else {

                    if (awesomeValidation.validate()) {
                        if (!organizationBinding.orgProvinces.getSelectedItem().toString().trim().equals("Select Province")) {

                            if (selectedId != -1) {
                                radGender = (RadioButton) findViewById(selectedId);
                                adminGender = radGender.getText().toString().trim();


                                progressDialog.setMessage("Wait While Creating Organisation ");
                                progressDialog.show();
                                //    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                                //organization validation

                                if (!orgNameValidation(crechNameOrg)) {
                                    orgAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()) {
                                                // String user_id = task.getResult().getUser().getUid();
                                                String adminRole = "admin";


                                                DatabaseReference mChildDatabase = mOrganizationRef;
                                                DatabaseReference mAdminRef = FirebaseDatabase.getInstance().getReference().child("Users");
                                                String userI = task.getResult().getUser().getUid();

                                                String key = mChildDatabase.push().getKey();


                                                OrganizationRegister orgReg = new OrganizationRegister(key, crechNameOrg, crechAddressOrg, cityInfor, provinceorg, email, crechPhoneNoOrg, password, crechPostalCodeOrg, userI);

                                                CrecheOnwer_Class adminReg = new CrecheOnwer_Class(userI, adminNameOrg, adminSurnameOrg, adminIdNoOrg, adminGender, adminRole, email, crechNameOrg, crechPhoneNoOrg, cityInfor, key);

                                                Map<String, Object> postingOrg = orgReg.toMap();
                                                Map<String, Object> organizationUpdate = new HashMap<>();
                                                organizationUpdate.put(key, postingOrg);

                                                Map<String, Object> postingAdmin = adminReg.toMap();
                                                Map<String, Object> adminUpdate = new HashMap<>();
                                                adminUpdate.put(userI, postingAdmin);


                                                //    databasekidclass.child(user.getUid()).child(databasekidclass.push().getKey()).child("className").setValue("Select Class");

                                                //Updating Messages
                                                mAdminRef.updateChildren(adminUpdate);

                                                mChildDatabase.updateChildren(organizationUpdate);

                                                //Toast.makeText(SignUpOrganisationActivity.this, key, Toast.LENGTH_SHORT).show();
                                                Toast.makeText(SignUpOrganisationActivity.this, "Organization Successfully Created", Toast.LENGTH_SHORT).show();

                                                startActivity(new Intent(getApplicationContext(), AdminTabbedActivity.class));

                                            } else {


                                                Toast.makeText(SignUpOrganisationActivity.this, "Organizational Failed to SignUp", Toast.LENGTH_SHORT).show();

                                            }
                                            progressDialog.dismiss();
                                        }
                                    });

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(SignUpOrganisationActivity.this, "Name Already Exist", Toast.LENGTH_SHORT).show();
                                }
                                orgAuth.signOut();
                                // }

                            } else {
                                Toast.makeText(SignUpOrganisationActivity.this, "Make sure you select gender before you continue", Toast.LENGTH_SHORT).show();
                            }

                        } else {


                            Toast.makeText(SignUpOrganisationActivity.this, "Please Select City", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpOrganisationActivity.this, "Make sure you fix all the error shown in your input space", Toast.LENGTH_LONG).show();
                    }
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

    public boolean orgNameValidation(final String nameOfOrg) {

        orgValidationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildren().equals("")) {
                    for (DataSnapshot orgSnapShot : dataSnapshot.getChildren()) {

                        name = orgSnapShot.child("orgName").getValue().toString();
                    }
                    if (name == nameOfOrg) {
                        valName = true;
                    } else {
                        valName = false;
                    }
                } else {
                    valName = false;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        return valName;
    }
}

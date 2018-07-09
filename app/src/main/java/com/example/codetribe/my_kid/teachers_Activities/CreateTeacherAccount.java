package com.example.codetribe.my_kid.teachers_Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
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
import com.example.codetribe.my_kid.databinding.ActivityCreateTeacherAccountBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateTeacherAccount extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPrefEditor;
    private String userNameString, userSurnameString, usercontactString, userprovinceString,
            userclassroomString, useridnumberString, usergenderString, useremailString, userpasswordString, userAddressString, userCityString;
    private RadioGroup gender;
    private String keyTeacher;
    private TextView createteacher;
    private String role = "teacher";
    private FirebaseAuth auth;

    //spinner
    private ArrayAdapter<String> dataAdapter;
    private List<String> list;

    private String userId;
    //city and province
    private ArrayAdapter<String> adapter;

    private String orgId, teacherclass;
    private int positions;
    private String crechCity;
    private String province = "";
    private Spinner spinnerCity, spinnerProvinces, classroom;

    private ProgressDialog progressDialog;
    //Firebase
    private DatabaseReference teacherReference, mDatabaseRef, mCrecheRef, orgNameReference, currentUserRef, kidclassdata;
    private RadioButton gnrteacher;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    private ActivityCreateTeacherAccountBinding activityTeacherBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activityTeacherBinding = DataBindingUtil.setContentView(this,R.layout.activity_create_teacher_account);


        getSupportActionBar().setTitle("Add Teacher");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //class
        classroom = (Spinner) findViewById(R.id.teacherclass);
        list = new ArrayList<String>();
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        progressDialog = new ProgressDialog(this);

        //database
        kidclassdata = FirebaseDatabase.getInstance().getReference("kidclass").child(userId);

        currentUserRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String shared_email = sharedPreferences.getString("email", "");
        activityTeacherBinding.teacheremail.setText(shared_email);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.teacherpassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.teachername, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.teachersurname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.surnameerror);
        awesomeValidation.addValidation(this, R.id.teacheremail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.teacherAddress, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.address);
        //  awesomeValidation.addValidation(this, R.id.teacherCity, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.city);
        awesomeValidation.addValidation(this, R.id.teachercontact, "^[+]?[0-9]{10,13}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.teacherid, "^^[0-9]{13}$", R.string.iderror);
        // awesomeValidation.addValidation(this, R.id.teacherclass, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}[0-9]$", R.string.classerror);

        //classs
        activityTeacherBinding.teacherclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                teacherclass = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //provinces
        ArrayAdapter<String> provincesadapter = new ArrayAdapter<String>(CreateTeacherAccount.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_Province));
        provincesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityTeacherBinding.teacherProvincesSpinner.setAdapter(provincesadapter);

        //cities
        activityTeacherBinding.teacherProvincesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {


                positions = position;

                switch (getResources().getStringArray(R.array.city_Province)[positions]) {
                    case "Limpopo":

                        adapter = new ArrayAdapter<String>(CreateTeacherAccount.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_limpopo));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        activityTeacherBinding.teacherCitySpinner.setAdapter(adapter);

                        break;
                    case "Gauteng":

                        adapter = new ArrayAdapter<String>(CreateTeacherAccount.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_gauteng));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        activityTeacherBinding.teacherCitySpinner.setAdapter(adapter);
                        break;
                    case "Western Cape":

                        adapter = new ArrayAdapter<String>(CreateTeacherAccount.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_western_cape));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        activityTeacherBinding.teacherCitySpinner.setAdapter(adapter);
                        break;
                    case "Northern Cape":

                        adapter = new ArrayAdapter<String>(CreateTeacherAccount.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_Northern_Cape));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        activityTeacherBinding.teacherCitySpinner.setAdapter(adapter);
                        break;
                    case "Eastern Cape":

                        adapter = new ArrayAdapter<String>(CreateTeacherAccount.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_eastern_Cape));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        activityTeacherBinding.teacherCitySpinner.setAdapter(adapter);
                        break;
                    case "Free State":
                        adapter = new ArrayAdapter<String>(CreateTeacherAccount.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_free_state));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        activityTeacherBinding.teacherCitySpinner.setAdapter(adapter);
                        break;

                    case "KwaZulu-Natal":
                        adapter = new ArrayAdapter<String>(CreateTeacherAccount.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_Kwazulu_Natal));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        activityTeacherBinding.teacherCitySpinner.setAdapter(adapter);
                        break;
                    default:
                        Toast.makeText(CreateTeacherAccount.this, "PLease select one of the provinces", Toast.LENGTH_SHORT).show();
                        break;
                }

                province = getResources().getStringArray(R.array.city_Province)[position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });


        //cities
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        //database
        Intent intent = getIntent();
        keyTeacher = intent.getStringExtra("User_KEY");


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //database
        keyTeacher = auth.getCurrentUser().getUid();

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        orgNameReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);


        activityTeacherBinding.CreateTeacherAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orgNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String org_name = dataSnapshot.child("orgName").getValue().toString();
                        orgId = dataSnapshot.child("userOrgId").getValue().toString();

                        if (awesomeValidation.validate()) {
                            if (!activityTeacherBinding.teacherclass.getSelectedItem().toString().trim().equals("Select Class")) {
                                progressDialog.setMessage("Wait While Adding Teacher");
                                progressDialog.show();
                                saveParent(org_name);
                                progressDialog.dismiss();

                                //  Toast.makeText(CreateTeacherAccount.this, "Teacher added", Toast.LENGTH_LONG).show();
                            } else

                            {
                                Toast.makeText(CreateTeacherAccount.this, "please select a class or add class ", Toast.LENGTH_SHORT).show();

                            }
                        } else {

                            Toast.makeText(CreateTeacherAccount.this, "Make sure you fix all the error shown in your input space", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    public void saveParent(String orgName) {

        //adminId
        String AdminId = auth.getCurrentUser().getUid();
        final String adminEmail = auth.getCurrentUser().getEmail();

        final String orgNames = orgName;
        teacherReference = FirebaseDatabase.getInstance().getReference("Users");

        teacherReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String email = activityTeacherBinding.teacheremail.getText().toString().trim();
                final String password = activityTeacherBinding.teacherpassword.getText().toString().trim();
                userNameString = activityTeacherBinding.teachername.getText().toString().trim();
                userSurnameString = activityTeacherBinding.teachersurname.getText().toString().trim();
                usercontactString = activityTeacherBinding.teachercontact.getText().toString().trim();
                useridnumberString = activityTeacherBinding.teacherid.getText().toString().trim();
                userAddressString = activityTeacherBinding.teacherAddress.getText().toString().trim();

                userclassroomString = teacherclass.trim();
                userCityString = crechCity.trim();
                userprovinceString = province.trim();


                int selectedId = activityTeacherBinding.teachergenders.getCheckedRadioButtonId();

                if (password.isEmpty() || password.length() < 6) {
                    Toast.makeText(CreateTeacherAccount.this, "Password cannot be less than 6 characters!", Toast.LENGTH_SHORT).show();
                } else {

                    if (selectedId != -1) {

                        gnrteacher = (RadioButton) findViewById(selectedId);
                        usergenderString = gnrteacher.getText().toString().trim();

                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(CreateTeacherAccount.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

                                            //Storing Information
                                            TeacherClassAcc teacher = new TeacherClassAcc(userNameString, userSurnameString, usercontactString, userclassroomString, useridnumberString, usergenderString, task.getResult().getUser().getUid().toString().trim(), task.getResult().getUser().getEmail().toString().trim(), password, role, "verified", orgNames, userAddressString, userprovinceString, userCityString, orgId);
                                            Toast.makeText(CreateTeacherAccount.this, orgId, Toast.LENGTH_SHORT).show();
                                            mDatabaseRef.child(task.getResult().getUser().getUid().toString().trim()).setValue(teacher);

                                        }

                                        if (!task.isSuccessful()) {
                                            Toast.makeText(CreateTeacherAccount.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                        } else {
                                            // startActivity(new Intent(Create_Teacher_Account.this, LoginActivity.class));
                                            Toast.makeText(CreateTeacherAccount.this, "Teacher Registration Successfull", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }

                                    }

                                });

                    } else {
                        Toast.makeText(CreateTeacherAccount.this, "Make sure you select gender before you continue", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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


    @Override
    protected void onStart() {
        super.onStart();
        list.add("Select Class");
        progressDialog.setMessage("Wait While searching for class list..");
        // progressDialog.show();

        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot currentSnap) {


                kidclassdata.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot outerSnap) {


                        for (DataSnapshot classSnapshot : outerSnap.getChildren()) {

                            //  if (!classSnapshot.child("className").getValue().toString().equals("")) {

                            list.add(classSnapshot.child("className").getValue().toString());

                            //  Toast.makeText(CreateTeacherAccount.this, classSnapshot.child("className").getValue().toString(), Toast.LENGTH_SHORT).show();

                            dataAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
                            //simple_spinner_dropdown_item


                            progressDialog.dismiss();
                            //   } else {
                            //      Toast.makeText(KidActivity.this, "There is no Class", Toast.LENGTH_SHORT).show();
                            //   }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dataAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        activityTeacherBinding.teacherclass.setAdapter(dataAdapter);
    }
}




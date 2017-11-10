package com.example.codetribe.my_kid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.codetribe.my_kid.R.id.teacherpassword;

public class CreateTeacherAccount extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPrefEditor;
    TextInputLayout inputLayoutName, inputLayoutsurname, inputLayoutcontact, inputLayoutclassroom, inputLayoutidnumber, inputLayoutemail, inputLayoutpassword;
    String userNameString, userSurnameString, usercontactString, userclassroomString, useridnumberString, usergenderString, useremailString, userpasswordString, userAddressString, userCityString;
    private EditText name, surname, contact, classroom, idnumber, useremail, userpassword, userAddress, userCity;
    RadioGroup gender;
    String keyTeacher;
    TextView createteacher;
    String role = "teacher";
    private FirebaseAuth auth;


    private ProgressDialog progressDialog;
    //Firebase
    private DatabaseReference teacherReference, mDatabaseRef, mCrecheRef, orgNameReference;
    private RadioButton gnrteacher;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__teacher__account);


        getSupportActionBar().setTitle("Add Teacher");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        inputLayoutName = (TextInputLayout) findViewById(R.id.inputteacherfullname);
        inputLayoutsurname = (TextInputLayout) findViewById(R.id.inputteacherSurname);
        inputLayoutcontact = (TextInputLayout) findViewById(R.id.inputteacherconatct);
        inputLayoutclassroom = (TextInputLayout) findViewById(R.id.inputteacherclass);
        inputLayoutidnumber = (TextInputLayout) findViewById(R.id.inputteacheridnumber);
        inputLayoutemail = (TextInputLayout) findViewById(R.id.inputteacheremail);
        inputLayoutpassword = (TextInputLayout) findViewById(R.id.inputteacherPassword);

        name = (EditText) findViewById(R.id.teachername);
        surname = (EditText) findViewById(R.id.teachersurname);
        contact = (EditText) findViewById(R.id.teachercontact);
        classroom = (EditText) findViewById(R.id.teacherclass);
        idnumber = (EditText) findViewById(R.id.teacherid);
        useremail = (EditText) findViewById(R.id.teacheremail);
        userpassword = (EditText) findViewById(teacherpassword);
        gender = (RadioGroup) findViewById(R.id.teachergenders);
        userAddress = (EditText) findViewById(R.id.teacherAddress);
        userCity = (EditText) findViewById(R.id.teacherCity);

        progressDialog = new ProgressDialog(this);
        createteacher = (TextView) findViewById(R.id.Create_Teacher_Account);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String shared_email = sharedPreferences.getString("email", "");
        useremail.setText(shared_email);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.teachername, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.teachersurname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.surnameerror);
        awesomeValidation.addValidation(this, R.id.teacheremail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.teacherAddress, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.address);
        awesomeValidation.addValidation(this, R.id.teacherCity, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.city);
        //  awesomeValidation.addValidation(this, teacherpassword, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\\\d])(?=.*[~`!@#\\\\$%\\\\^&\\\\*\\\\(\\\\)\\\\-_\\\\+=\\\\{\\\\}\\\\[\\\\]\\\\|\\\\;:\\\"<>,./\\\\?]).{8,}", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.teachercontact, "^[+]?[0-9]{10,13}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.teacherid, "^^[0-9]{13}$", R.string.iderror);
        awesomeValidation.addValidation(this, R.id.teacherclass, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}[0-9]$", R.string.classerror);


        //database
        Intent intent = getIntent();
        keyTeacher = intent.getStringExtra("User_KEY");


        // useremail = (EditText) findViewById(R.id.teacheremail);
        //  userpassword = (EditText) findViewById(R.id.teacherpassword);
        //createteacher = (TextView) findViewById(R.id.Create_Teacher_Account);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //database
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
        keyTeacher = auth.getCurrentUser().getUid();
        teacherReference = FirebaseDatabase.getInstance().getReference("Users");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        orgNameReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("orgName");


        createteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orgNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String org_name = dataSnapshot.getValue(String.class);
                        if (awesomeValidation.validate()) {


                            saveParent(org_name);
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

        teacherReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String email = useremail.getText().toString().trim();
                final String password = userpassword.getText().toString().trim();
                userNameString = name.getText().toString().trim();
                userSurnameString = surname.getText().toString().trim();
                usercontactString = contact.getText().toString().trim();
                userclassroomString = classroom.getText().toString().trim();
                useridnumberString = idnumber.getText().toString().trim();
                userAddressString = userAddress.getText().toString().trim();
                userCityString = userCity.getText().toString().trim();


                int selectedId = gender.getCheckedRadioButtonId();


                if (selectedId != -1) {

                    progressDialog.setMessage("Wait While Adding Teacher");
                    progressDialog.show();

                    gnrteacher = (RadioButton) findViewById(selectedId);
                    usergenderString = gnrteacher.getText().toString().trim();

                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(CreateTeacherAccount.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    if (task.isSuccessful()) {
                                        //String user_id = task.getResult().getUser().getUid();
                                        //firebase teacher database table
                                        DatabaseReference mChildDatabase = mDatabaseRef.child("Users");


                                        //Storing Information
                                        TeacherClassAcc teacher = new TeacherClassAcc(userNameString, userSurnameString, usercontactString, userclassroomString, useridnumberString, usergenderString, task.getResult().getUser().getUid().toString().trim(), task.getResult().getUser().getEmail().toString().trim(), password, role, "verified", orgNames, userAddressString, userCityString);

                                        mDatabaseRef.child(task.getResult().getUser().getUid().toString().trim()).setValue(teacher);

                                        Toast.makeText(CreateTeacherAccount.this, "Teacher Registration Successfull" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(getApplication(), AdminTabbedActivity.class));


                                    }

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(CreateTeacherAccount.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();

                                    } else {
                                        // startActivity(new Intent(Create_Teacher_Account.this, LoginActivity.class));

                                        finish();
                                    }
                                    progressDialog.dismiss();
                                }
                            });

                } else {
                    Toast.makeText(CreateTeacherAccount.this, "Make sure you select gender before you continue", Toast.LENGTH_SHORT).show();
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
}




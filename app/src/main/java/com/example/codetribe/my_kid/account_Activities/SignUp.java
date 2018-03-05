package com.example.codetribe.my_kid.account_Activities;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.codetribe.my_kid.R;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SignUp extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPrefEditor;

    private EditText inputEmail, inputPassword, kidsIdno;
    private TextView mainNav, btnSignUp;


    private ArrayAdapter<String> dataAdapter;

    private AwesomeValidation awesomeValidation;

    private ProgressDialog progressDialog;

    //spinner
    private Spinner orgNameList;

    //declaration
    private List<String> list;
    private TextInputLayout input_email1;
    private String orgIdKey;
    private String[] spinnerArray;
    private String strName;

    //firebase Declaration
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabaseRef, mUserCheckData, crecheDataRef, KidDataRef, searchCreacheRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign Up");
        // getSupportActionBar().setSubtitle("Parent");


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mUserCheckData = FirebaseDatabase.getInstance().getReference().child("Users");
        crecheDataRef = FirebaseDatabase.getInstance().getReference("Creche");
        KidDataRef = FirebaseDatabase.getInstance().getReference("Kids");
        searchCreacheRef = FirebaseDatabase.getInstance().getReference("Creche");

        //shared Preference
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);//shared

        //validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //progress bar
        progressDialog = new ProgressDialog(this);

        //Variable initialiaation
        btnSignUp = (TextView) findViewById(R.id.sinup);
        inputEmail = (EditText) findViewById(R.id.sign_up_email);
        inputPassword = (EditText) findViewById(R.id.signupPassword);
        kidsIdno = (EditText) findViewById(R.id.KidIdNumber);
        orgNameList = (Spinner) findViewById(R.id.orgname);
        input_email1 = (TextInputLayout) findViewById(R.id.input_email);
        input_email1 = (TextInputLayout) findViewById(R.id.input_email);

        //arrayList
        list = new ArrayList<String>();


        //AdapterArray
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);


        //owesome Validation
        awesomeValidation.addValidation(this, R.id.signupPassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.sign_up_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.KidIdNumber, "^^[0-9]{13}$", R.string.iderror);

        //Spinner for getting selected item
        orgNameList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strName = (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Authorization listener
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    final String emailForVer = user.getEmail();
                    mDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            checkUserValidation(dataSnapshot, emailForVer);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                        }
                    });

                    //startActivity(new Intent(LoginActivity.this, Welcome.class));

                } else {

                }

            }
        };

        //button for signup
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();


                final String userEmailString, userPassString;

                userEmailString = inputEmail.getText().toString().trim();
                userPassString = inputPassword.getText().toString().trim();

                sharedPrefEditor = sharedPreferences.edit();
                sharedPrefEditor.putString("email", userEmailString);
                sharedPrefEditor.apply();

                if (password.isEmpty() || password.length() < 6) {
                    Toast.makeText(SignUp.this, "Password cannot be less than 6 characters!", Toast.LENGTH_SHORT).show();
                } else {
                    //
                    if (awesomeValidation.validate()) {
                        if (!orgNameList.getSelectedItem().toString().trim().equals("Select Creshe")) {

                            searchCreacheRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot searchSnapshot) {
                                    for (final DataSnapshot searchDataSnapshot : searchSnapshot.getChildren()) {
                                        if (searchDataSnapshot.child("orgName").getValue().toString().equals(orgNameList.getSelectedItem().toString())) {

                                            KidDataRef.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot kidsSnapshot : dataSnapshot.getChildren()) {

                                                        progressDialog.setMessage("Wait While Creating an Parent Account");
                                                        progressDialog.show();

                                                        if (kidsSnapshot.child("orgName").getValue().toString().equals(strName) && kidsSnapshot.child("idNumber").getValue().toString().equals(kidsIdno.getText().toString())) {


                                                            auth.createUserWithEmailAndPassword(userEmailString, userPassString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                    if (task.isSuccessful()) {
                                                                        String user_id = task.getResult().getUser().getUid();
                                                                        DatabaseReference mChildDatabase = mDatabaseRef.child("Users").child(user_id);

                                                                        mChildDatabase.child("isVerified").setValue("unverified");
                                                                        mChildDatabase.child("orgName").setValue(strName);
                                                                        mChildDatabase.child("userKey").setValue(user_id);
                                                                        mChildDatabase.child("userAddress").setValue("");
                                                                        mChildDatabase.child("userCity").setValue("");
                                                                        mChildDatabase.child("userContact").setValue("");
                                                                        mChildDatabase.child("userGender").setValue("");
                                                                        mChildDatabase.child("userIdNumber").setValue("");
                                                                        mChildDatabase.child("role").setValue("parent");
                                                                        mChildDatabase.child("emailUser").setValue(userEmailString);
                                                                        mChildDatabase.child("passWordUser").setValue(userPassString);
                                                                        mChildDatabase.child("userName").setValue("");
                                                                        mChildDatabase.child("userOrgId").setValue(searchDataSnapshot.child("orguid").getValue().toString());
                                                                        mChildDatabase.child("userSurname").setValue("");


                                                                        Toast.makeText(SignUp.this, "Parent Account Created Successfully", Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(SignUp.this, LoginActivity.class));


                                                                    } else {
                                                                        Toast.makeText(SignUp.this, "Parent Fialed to Creating an Account", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                }
                                                            });

                                                            progressDialog.dismiss();
                                                        } else {

                                                            Toast.makeText(SignUp.this, "You dont have a kids on this Creche,Please contact an Admin Creshe", Toast.LENGTH_SHORT).show();

                                                        }

                                                    }


                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        } else {

                            Toast.makeText(SignUp.this, "Please Select a Creshe that your kid registerd at", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });
    }

    //methods for cheking up the validation
    public void checkUserValidation(DataSnapshot dataSnapshot, String emailForVer) {
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()) {
            DataSnapshot dataUser = (DataSnapshot) iterator.next();

            if (dataUser.child("emailUser").getValue().toString().equals(emailForVer)) {
                if (dataUser.child("isVerified").getValue().toString().equals("unverified")) {
                    Intent intent = new Intent(SignUp.this, LoginActivity.class);
                    intent.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                    startActivity(intent);
                } else {
                    //      startActivity(new Intent(SignUp.this, ParentActivity.class));
                }
            }
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setMessage("Wait While searching for creches list..");
        progressDialog.show();

        //creche database for listing all creche that exist
        crecheDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot checheSnapshot : dataSnapshot.getChildren()) {

                    if (!checheSnapshot.child("orgName").getValue().toString().equals(" ")) {

                        list.add(checheSnapshot.child("orgName").getValue().toString());

                        dataAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
                        //simple_spinner_dropdown_item

                        orgNameList.setAdapter(dataAdapter);
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(SignUp.this, "There is no Creche Registered", Toast.LENGTH_SHORT).show();
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
}


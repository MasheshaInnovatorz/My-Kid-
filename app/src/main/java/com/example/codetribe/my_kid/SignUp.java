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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPrefEditor;
    private EditText inputEmail, inputPassword,kidsIdno;
    //spinner
    private ArrayAdapter<String> dataAdapter;
    // private Button
    TextView mainNav, btnSignUp;
    private Spinner orgNameList;
    private List<String> list;
    private ProgressDialog progressDialog;
    private TextInputLayout input_email1;
    //firebase Authentification
    private FirebaseAuth auth;
    private AwesomeValidation awesomeValidation;
    private String strName;
    FirebaseAuth.AuthStateListener mAuthListener;

    String[] spinnerArray;

    DatabaseReference mDatabaseRef, mUserCheckData, crecheDataRef,KidDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //  getSupportActionBar().setTitle("SignUp");

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mUserCheckData = FirebaseDatabase.getInstance().getReference().child("Users");
        KidDataRef = FirebaseDatabase.getInstance().getReference("Kids");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);//shared


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        progressDialog = new ProgressDialog(this);

        btnSignUp = (TextView) findViewById(R.id.sinup);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.signupPassword);
        kidsIdno = (EditText)findViewById(R.id.KidIdNumber);

        orgNameList = (Spinner) findViewById(R.id.orgname);
        // mainNav = (TextView)findViewById(R.id.login);

        awesomeValidation.addValidation(this, R.id.signupPassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.KidIdNumber, "^^[0-9]{13}$", R.string.iderror);

        orgNameList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strName = (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        list  = new ArrayList<String>();



        input_email1= (TextInputLayout)findViewById(R.id.input_email);

        dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        list = new ArrayList<String>();


        input_email1 = (TextInputLayout) findViewById(R.id.input_email);


        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);


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


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();


                progressDialog.setMessage("Wait While Creating an Account");
                progressDialog.show();

                final String userEmailString, userPassString;

                userEmailString = inputEmail.getText().toString().trim();
                userPassString = inputPassword.getText().toString().trim();

                sharedPrefEditor = sharedPreferences.edit();
                sharedPrefEditor.putString("email", userEmailString);
                sharedPrefEditor.apply();

                if (awesomeValidation.validate()) {

                    KidDataRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot kidsSnapshot : dataSnapshot.getChildren()) {


                                if (kidsSnapshot.child("orgName").getValue().toString().equals(strName) && kidsSnapshot.child("idNumber").getValue().toString().equals(kidsIdno.getText().toString())) {

                                    auth.createUserWithEmailAndPassword(userEmailString, userPassString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                String user_id = task.getResult().getUser().getUid();
                                                DatabaseReference mChildDatabase = mDatabaseRef.child("Users").child(user_id);

                                                // String key_user = mChildDatabase.getKey();

                                                mChildDatabase.child("isVerified").setValue("unverified");
                                                mChildDatabase.child("orgnName").setValue(strName);
                                                mChildDatabase.child("userKey").setValue(user_id);
                                                mChildDatabase.child("role").setValue("parent");
                                                mChildDatabase.child("emailUser").setValue(userEmailString);
                                                mChildDatabase.child("passWordUser").setValue(userPassString);
                                                Toast.makeText(SignUp.this, "User Account Created", Toast.LENGTH_SHORT).show();
                                                auth.signOut();
                                                startActivity(new Intent(SignUp.this, WelcomeActivity.class));


                                            } else {
                                                Toast.makeText(SignUp.this, "User Fialed to Creating an Account", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });


                                } else {

                                    Toast.makeText(SignUp.this, "You dont have a kids on this Creche,Please contact an AdminTabbedActivity", Toast.LENGTH_SHORT).show();
                                }

                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                progressDialog.dismiss();


            }
        });
    }


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

    public void searchForCreacheName() {

    }

    @Override
    protected void onStart() {
        super.onStart();

        //  Toast.makeText(this, "Ellow Chivhedzelele", Toast.LENGTH_SHORT).show();

        crecheDataRef = FirebaseDatabase.getInstance().getReference("Creche");

        crecheDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot checheSnapshot : dataSnapshot.getChildren()) {

                    if (!checheSnapshot.child("orgName").getValue().toString().equals(" ")) {
                        list.add(checheSnapshot.child("orgName").getValue().toString());


                        dataAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
                        //simple_spinner_dropdown_item
                        orgNameList.setAdapter(dataAdapter);




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
}


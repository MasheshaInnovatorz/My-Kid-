package com.example.codetribe.my_kid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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


    private EditText inputEmail, inputPassword;
    private ProgressDialog progressDialog;
    //spinner
   private  ArrayAdapter<String> dataAdapter;
    // private Button
    TextView mainNav,btnSignUp;
    private Spinner orgNameList;
    private List<String> list;
    private ProgressDialog progressDialog;

    private TextInputLayout input_email1;
    //firebase Authentification
    private FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference mDatabaseRef,mUserCheckData,crecheDataRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



      //  getSupportActionBar().setTitle("SignUp");

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabaseRef  = FirebaseDatabase.getInstance().getReference();
        mUserCheckData = FirebaseDatabase.getInstance().getReference().child("Users");

        progressDialog = new ProgressDialog(this);

        btnSignUp = (TextView) findViewById(R.id.sinup);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.signupPassword);

         orgNameList = (Spinner) findViewById(R.id.orgname);
       // mainNav = (TextView)findViewById(R.id.login);

        list  = new ArrayList<String>();

        input_email1= (TextInputLayout)findViewById(R.id.input_email);
        /*mainNav.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUp.this, LoginActivity.class));
                finish();
            }
        });*/

        dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);






        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){

                    final String emailForVer = user.getEmail();
                    mDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            checkUserValidation(dataSnapshot,emailForVer);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //startActivity(new Intent(LoginActivity.this, Welcome.class));

                }else{

                }

            }
        };


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Wait While Creating an Account");
                progressDialog.show();

                final String userEmailString, userPassString;

                userEmailString = inputEmail.getText().toString().trim();
                userPassString = inputPassword.getText().toString().trim();



                if(!TextUtils.isEmpty(userEmailString) && !TextUtils.isEmpty(userPassString))
                {
                    auth.createUserWithEmailAndPassword(userEmailString,userPassString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                String user_id = task.getResult().getUser().getUid();
                                DatabaseReference mChildDatabase = mDatabaseRef.child("Users").child(user_id);

                               // String key_user = mChildDatabase.getKey();

                                mChildDatabase.child("isVerified").setValue("unverified");
                                mChildDatabase.child("userKey").setValue(user_id);
                                mChildDatabase.child("role").setValue("parent");
                                mChildDatabase.child("emailUser").setValue(userEmailString);
                                mChildDatabase.child("passWordUser").setValue(userPassString);
                                Toast.makeText(SignUp.this, "User Account Created", Toast.LENGTH_SHORT).show();
                                auth.signOut();
                                startActivity(new Intent(SignUp.this,WelcomeActivity.class));

                            }else{
                                Toast.makeText(SignUp.this, "Parent Fialed to Register", Toast.LENGTH_SHORT).show();


                            }
                        }
                    });
                }
                progressDialog.dismiss();



            }
        });
            }

    public void checkUserValidation(DataSnapshot dataSnapshot, String emailForVer){
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while(iterator.hasNext())
        {
            DataSnapshot dataUser  = (DataSnapshot) iterator.next();

            if(dataUser.child("emailUser").getValue().toString().equals(emailForVer)) {
                if (dataUser.child("isVerified").getValue().toString().equals("unverified")) {
                    Intent intent = new Intent(SignUp.this, LoginActivity.class);
                    intent.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                    startActivity(intent);
                } else {
                    startActivity(new Intent(SignUp.this, ParentActivity.class));
                }
            }
        }

    }

    public void searchForCreacheName(){

    }

    @Override
    protected void onStart() {
        super.onStart();

      //  Toast.makeText(this, "Ellow Chivhedzelele", Toast.LENGTH_SHORT).show();

        crecheDataRef = FirebaseDatabase.getInstance().getReference("Creche");

        crecheDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot checheSnapshot : dataSnapshot.getChildren()){

                    if (!checheSnapshot.child("orgName").getValue().toString().equals(" ")) {
                        list.add(checheSnapshot.child("orgName").getValue().toString());


                        dataAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);

                        //simple_spinner_dropdown_item
                        orgNameList.setAdapter(dataAdapter);
                    }else{
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


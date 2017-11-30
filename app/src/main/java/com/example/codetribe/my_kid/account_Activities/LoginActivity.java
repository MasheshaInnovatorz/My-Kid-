package com.example.codetribe.my_kid.account_Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.admin_Activities.AdminTabbedActivity;
import com.example.codetribe.my_kid.parent_Activities.CreateParentProfile;
import com.example.codetribe.my_kid.parent_Activities.ParentTabbedActivity;
import com.example.codetribe.my_kid.teachers_Activities.TeacherTabbedActivity;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView login_Button,loginsignup_;
    private ImageView backtowelcome, icon;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPrefEditor;

    //declaring buttons and textiew
    private FirebaseUser mFirebaseUser;
    private EditText editEmail, editPassword;

    //firebase Authentification
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabaseRef;

    private AwesomeValidation awesomeValidation;

    private ProgressDialog progressDialog;
    private TextView forgot;
    public static final String username = "usenameID";
    public static final String usersurname = "userSurname";


    private TextInputLayout passwordLayout;
    private TextInputLayout emailLayout;
    private String uid = "some-uid";
    private TextInputLayout input_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String shared_email = sharedPreferences.getString("email", "");

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        loginsignup_=(TextView) findViewById(R.id.loginsignup);
        editEmail = (EditText) findViewById(R.id.login_email);
        editPassword = (EditText) findViewById(R.id.login_password);
        forgot = (TextView) findViewById(R.id.forget_password);
        login_Button = (TextView) findViewById(R.id.login);

        input_email = (TextInputLayout) findViewById(R.id.input_reg_fullname);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Log In");


        // awesomeValidation.addValidation(this, R.id.password, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\\\d])(?=.*[~`!@#\\\\$%\\\\^&\\\\*\\\\(\\\\)\\\\-_\\\\+=\\\\{\\\\}\\\\[\\\\]\\\\|\\\\;:\\\"<>,./\\\\?]).{8,}", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.login_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.login_password, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
        //firebase

        mFirebaseAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);

        editEmail.setText(shared_email);

        loginsignup_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginActivity.this, SignUp.class);
                startActivity(it);

            }
        });

        //database
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //final String keyId = firebaseAuth.getCurrentUser().getEmail();
                if (user != null) {

                    //final String emailForVer = user.getEmail();
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

                    final String emailForVer = user.getEmail().toString();
                    mDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.i("Ygritte", dataSnapshot.toString());
                            checkUserValidation(dataSnapshot, emailForVer);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } else {


                }
            }
        };

        //setOnclick
        login_Button.setOnClickListener(this);
        //signup.setOnClickListener(this);
        forgot.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.setMessage("Wait While Logging In");
        progressDialog.show();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
        progressDialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onClick(View view) {
        if (view == login_Button) {

            if (awesomeValidation.validate()) {
                userLogin();
            }


        }

        if (view == forgot) {
            finish();
            // startActivity(new Intent(this, UploadKidsMemo.class));
            Intent i = new Intent(LoginActivity.this, ResetPassword.class);
            startActivity(i);

        }
    }

    private void checkUserValidation(DataSnapshot dataUser, String emailForVer) {

        //Iterator iterator = dataSnapshot.getChildren().iterator();

        //while (iterator.hasNext()) {
          //  DataSnapshot dataUser = (DataSnapshot) iterator.next();

            if (dataUser.child("isVerified").getValue().toString().equals("unverified")) {
                Intent intentUser = new Intent(LoginActivity.this, CreateParentProfile.class);

                //if (dataUser.child("emailUser").getValue().toString().trim().equals(emailForVer)) {

                    //if (dataUser.child("isVerified").getValue().toString().equals("unverified")) {
                        //Intent intentUser = new Intent(LoginActivity.this, CreateParentProfile.class);
                        intentUser.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                        Toast.makeText(this, dataUser.child("userKey").getValue().toString(), Toast.LENGTH_SHORT).show();
                        startActivity(intentUser);

                   // }
                //}

            } else {
                if (dataUser.child("role").getValue().toString().equals("teacher")) {
                    Intent intent = new Intent(LoginActivity.this, TeacherTabbedActivity.class);
                    intent.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                    startActivity(intent);
                    Toast.makeText(this, "Welcome To TeachersActivity Page", Toast.LENGTH_SHORT).show();


                } else if (dataUser.child("role").getValue().toString().equals("parent")) {
                    //  Intent intent = new Intent(LoginActivity.this, ParentActivity.class);
                    //Intent intent = new Intent(LoginActivity.this, ParentActivity.class);
                    Intent intent = new Intent(LoginActivity.this, ParentTabbedActivity.class);
                    intent.putExtra("parent_id", dataUser.child("userIdNumber").getValue().toString());
                    //intent.putExtra("kid_id", dataUser.child("id").getValue().toString());
                    startActivity(intent);
                    Toast.makeText(this, "Welcome To Parents Page", Toast.LENGTH_SHORT).show();

                } else if (dataUser.child("role").getValue().toString().equals("admin")) {

                    // Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    Intent intent = new Intent(LoginActivity.this, AdminTabbedActivity.class);

                    // intent.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                    intent.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                    startActivity(intent);
                    Toast.makeText(this, "Welcome To AdminActivity Page", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(this, dataUser.child("role").getValue().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, "Please Contact A heard Master To Assign you A Role", Toast.LENGTH_SHORT).show();
                }

            }

        //}
    }

    private void userLogin() {
        final String userEmailString, userPasswordString;

        userEmailString = editEmail.getText().toString().trim();
        userPasswordString = editPassword.getText().toString().trim();

        progressDialog.setMessage("Wait While Logging In");
        progressDialog.show();


        if (!TextUtils.isEmpty(userEmailString) && !TextUtils.isEmpty(userPasswordString)) {

            mFirebaseAuth.signInWithEmailAndPassword(userEmailString, userPasswordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                       // Toast.makeText(LoginActivity.this, "Parent Account Created", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Either your Email nor Password is Worng or not Registered", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });
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

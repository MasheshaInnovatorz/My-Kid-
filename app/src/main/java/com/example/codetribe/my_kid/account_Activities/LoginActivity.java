package com.example.codetribe.my_kid.account_Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.admin_Activities.AdminTabbedActivity;
import com.example.codetribe.my_kid.organization_Activities.SignUpOrganisationActivity;
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

    private ImageView backtowelcome, icon;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPrefEditor;

    //firebase Authentification
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabaseRef;

    //validation method
    private AwesomeValidation awesomeValidation;

    //progress bar
    private ProgressDialog progressDialog;

    //declaration of variables
    private TextView forgot;
    public static final String username = "usenameID";
    public static final String usersurname = "userSurname";
    private EditText editEmail, editPassword;
    private TextInputLayout passwordLayout;
    private TextInputLayout emailLayout;
    private String uid = "some-uid";
    private TextInputLayout input_email;
    private TextView login_Button, loginsignup_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //shared preferences for remembering an email entered
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String shared_email = sharedPreferences.getString("email", "");

        //Validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //variables initialization
        loginsignup_ = (TextView) findViewById(R.id.loginsignup);
        editEmail = (EditText) findViewById(R.id.login_email);
        editPassword = (EditText) findViewById(R.id.login_password);
        forgot = (TextView) findViewById(R.id.forget_password);
        login_Button = (TextView) findViewById(R.id.login);
        input_email = (TextInputLayout) findViewById(R.id.input_reg_fullname);


        //Renaming of title bar
        //   getSupportActionBar().setDisplayShowHomeEnabled(true);
        //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Log In");


        //validation of email and password
        awesomeValidation.addValidation(this, R.id.login_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.login_password, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);

        //firebase initialization
        mFirebaseAuth = FirebaseAuth.getInstance();

        //progress bar
        progressDialog = new ProgressDialog(this);

        editEmail.setText(shared_email);

        //user registration
        loginsignup_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialogue_spinner, null);
                mBuilder.setTitle("Select an option to create Account?");

                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_spinner_item
                        , getResources().getStringArray(R.array.signUp_Options));

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose Sign-Up Option")) {
                    //dialogInterface.dismiss();
                }
                final String text = mSpinner.getSelectedItem().toString();

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose Sign-Up Option")) {
                            dialogInterface.dismiss();
                        }
                        final String text = mSpinner.getSelectedItem().toString();

                        switch (text) {
                            case "Parent":
                                Intent it = new Intent(LoginActivity.this, SignUp.class);
                                startActivity(it);

                                break;
                            case "Organization":
                                Intent i = new Intent(LoginActivity.this, SignUpOrganisationActivity.class);
                                startActivity(i);
                                break;
                            default:
                                Toast.makeText(LoginActivity.this, "Choose an option to sign-Up", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


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
        if (haveNetworkConnection() == false) {
            Toast.makeText(this, "Please Confirm if You bundle is ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "You  are connected to internet", Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Wait While Logging In");
        progressDialog.show();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
        progressDialog.dismiss();
    }

    //check if there is internet connnection or not
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
                if (ni.isConnected())
                    haveConnectedWifi = true;
            }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
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


        switch (dataUser.child("role").getValue().toString()) {
            case "teacher":
                Intent intent = new Intent(LoginActivity.this, TeacherTabbedActivity.class);
                intent.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                startActivity(intent);
                Toast.makeText(this, "Welcome To TeachersActivity Page", Toast.LENGTH_SHORT).show();

                break;
            case "parent":

                if (dataUser.child("isVerified").getValue().toString().equals("unverified")) {
                    Intent intentUser = new Intent(LoginActivity.this, CreateParentProfile.class);

                    //if (dataUser.child("emailUser").getValue().toString().trim().equals(emailForVer)) {

                    //if (dataUser.child("isVerified").getValue().toString().equals("unverified")) {
                    //Intent intentUser = new Intent(LoginActivity.this, CreateParentProfile.class);
                    intentUser.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                    Toast.makeText(this, dataUser.child("userKey").getValue().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(intentUser);
                } else {

                    //  Intent intent = new Intent(LoginActivity.this, ParentActivity.class);
                    //Intent intent = new Intent(LoginActivity.this, ParentActivity.class);
                    Intent intennt = new Intent(LoginActivity.this, ParentTabbedActivity.class);
                    intennt.putExtra("parent_id", dataUser.child("userIdNumber").getValue().toString());
                    //intent.putExtra("kid_id", dataUser.child("id").getValue().toString());
                    startActivity(intennt);
                    Toast.makeText(this, "Welcome To Parents Page", Toast.LENGTH_SHORT).show();
                }
                break;

            case "admin":
                // Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                Intent intente = new Intent(LoginActivity.this, AdminTabbedActivity.class);

                // intent.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                intente.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                startActivity(intente);
                Toast.makeText(this, "Welcome To AdminActivity Page", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, dataUser.child("role").getValue().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, "Please Contact A heard Master To Assign you A Role", Toast.LENGTH_SHORT).show();
                break;


        }

//}
    }

    private void userLogin() {
        final String userEmailString, userPasswordString;

        userEmailString = editEmail.getText().toString().trim();
        userPasswordString = editPassword.getText().toString().trim();


        if (userPasswordString.isEmpty() || userPasswordString.length() < 6) {
            editPassword.setError("Password cannot be less than 6 characters!");

        } else {

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
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    */
}

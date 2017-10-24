package com.example.codetribe.my_kid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.Iterator;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView login_Button;
 ImageView backtowelcome,icon;

   //declaring buttons and textiew
    private FirebaseUser mFirebaseUser;
    private EditText editEmail,editPassword;

    //firebase Authentification
    private FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseRef;

    private ProgressDialog progressDialog;
    private TextView forgot;
    public static final String username = "usenameID";
    public static final String usersurname = "userSurname";


    TextInputLayout passwordLayout;
    TextInputLayout emailLayout;
    String uid = "some-uid";
    private TextInputLayout input_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


       // signup= (TextView)findViewById(R.id.sinup);
        editEmail = (EditText)findViewById(R.id.email);
        editPassword= (EditText)findViewById(R.id.password);
        forgot = (TextView)findViewById(R.id.forget_password) ;
        login_Button = (TextView) findViewById(R.id.login);

        input_email= (TextInputLayout)findViewById(R.id.input_reg_fullname);


        //firebase
        mDatabaseRef  = FirebaseDatabase.getInstance().getReference("Users");

        mFirebaseAuth  = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);





        //database
        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser  user = firebaseAuth.getCurrentUser();

               //final String keyId = firebaseAuth.getCurrentUser().getEmail();
                if(user != null){

                    //final String emailForVer = user.getEmail();

                    final String emailForVer = user.getEmail().toString();
                    mDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            checkUserValidation(dataSnapshot,emailForVer);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }else{


                }
            }
        };

       //setOnclick
      login_Button.setOnClickListener(this);
        //signup.setOnClickListener(this);
        forgot.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
   if(view ==login_Button){
       userLogin();

   }

   if(view == forgot){
       finish();
      // startActivity(new Intent(this, UploadKidsMemo.class));
       Intent i=new Intent(LoginActivity.this,ResetPassword.class);
       startActivity(i);

         }
    }

    private void checkUserValidation(DataSnapshot dataSnapshot, String emailForVer){

        Iterator iterator = dataSnapshot.getChildren().iterator();

        while(iterator.hasNext())
        {
            DataSnapshot dataUser  = (DataSnapshot) iterator.next();

            if(dataUser.child("emailUser").getValue().toString().trim().equals(emailForVer))
            {


                if(dataUser.child("isVerified").getValue().toString().equals("unverified")){
                    Intent intentUser = new Intent(LoginActivity.this,ProfileUpdate.class);
                    intentUser.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                    Toast.makeText(this, dataUser.child("userKey").getValue().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(intentUser);

                }else {

                    if (dataUser.child("role").getValue().toString().equals("teacher")) {
                        Intent intent = new Intent(LoginActivity.this, TeachersActivity.class);
                        intent.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                        startActivity(intent);
                        Toast.makeText(this, "Welcome To TeachersActivity Page", Toast.LENGTH_SHORT).show();


                    } else if (dataUser.child("role").getValue().toString().equals("parent")) {
                      //  Intent intent = new Intent(LoginActivity.this, ParentActivity.class);
                        Intent intent = new Intent(LoginActivity.this, ParentActivity.class);
                        intent.putExtra("parent_id", dataUser.child("userIdNumber").getValue().toString());
                        //intent.putExtra("kid_id", dataUser.child("id").getValue().toString());
                        startActivity(intent);
                        Toast.makeText(this, "Welcome To Parents Page", Toast.LENGTH_SHORT).show();

                    } else if (dataUser.child("role").getValue().toString().equals("admin")) {
                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                       // intent.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                        intent.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                        startActivity(intent);
                        Toast.makeText(this, "Welcome To AdminActivity Page", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(this, dataUser.child("role").getValue().toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, "Please Contact A heard Master To Assign you A Role", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        }

    }
    private void userLogin(){
        final String userEmailString, userPasswordString;

        userEmailString  = editEmail.getText().toString().trim();
        userPasswordString = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty((userEmailString))) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPasswordString)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

    
        progressDialog.setMessage("Wait While Logging In");
        progressDialog.show();



        if(!TextUtils.isEmpty(userEmailString) && !TextUtils.isEmpty(userPasswordString)){

            mFirebaseAuth.signInWithEmailAndPassword(userEmailString,userPasswordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        mDatabaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                checkUserValidation(dataSnapshot, userEmailString);
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



    }

}

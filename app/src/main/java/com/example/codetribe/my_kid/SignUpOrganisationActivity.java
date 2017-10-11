package com.example.codetribe.my_kid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.example.codetribe.my_kid.R.id.orgAddress;
import static com.example.codetribe.my_kid.R.id.orgCity;
import static com.example.codetribe.my_kid.R.id.orgEmail;


public class SignUpOrganisationActivity extends AppCompatActivity {

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
    private RadioButton  genderMale,genderFemale;
    private RadioGroup gender;



    //firebase Authentification
    private FirebaseAuth orgAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference mOrganizationRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_organisation);

        signup  = (TextView)findViewById(R.id.btnRegisterCreche);


        orgAuth = FirebaseAuth.getInstance();
        mOrganizationRef = FirebaseDatabase.getInstance().getReference();



              orgaEmail = (EditText)findViewById(orgEmail);
             orgPassword= (EditText)findViewById(R.id.orgPassword);
              crechName = (EditText)findViewById(R.id.orgName);
                crechAddress = (EditText)findViewById(orgAddress);
                crechCity = (EditText)findViewById(orgCity);
                crechPhoneNo  = (EditText)findViewById(R.id.orgPhoneNumber);
                adminName = (EditText)findViewById(R.id.orgAdminName);
                adminSurname = (EditText)findViewById(R.id.orgAdminSurname);
                adminIdNo = (EditText)findViewById(R.id.orgAdminIDNumber);




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = orgaEmail.getText().toString().trim();
                final String password = orgPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }







                final String crechNameOrg,crechAddressOrg,crechCityOrg,crechPhoneNoOrg,adminNameOrg, adminSurnameOrg,adminIdNoOrg;



                crechNameOrg  = crechName.getText().toString().trim();
                crechAddressOrg = crechAddress.getText().toString().trim();
                crechCityOrg =crechCity.getText().toString().trim();
                crechPhoneNoOrg = crechPhoneNo.getText().toString().trim();
                adminNameOrg = adminName.getText().toString().trim();
                adminSurnameOrg =adminSurname.getText().toString().trim();
                adminIdNoOrg = adminIdNo.getText().toString().trim();


                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    orgAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                               // String user_id = task.getResult().getUser().getUid();


                                DatabaseReference mChildDatabase = mOrganizationRef.child("Creche");
                                String key = mChildDatabase.child("Creche").child(crechNameOrg).push().getKey();

                                OrganizationRegister  orgReg = new OrganizationRegister(key,crechNameOrg,crechAddressOrg,crechCityOrg,email,crechPhoneNoOrg,password);

                                //Map
                                Map <String,Object> postingOrg =orgReg.toMap();

                                Map<String, Object> organizationUpdate =  new HashMap<>();

                                organizationUpdate.put(crechNameOrg +key, postingOrg);

                                mChildDatabase.updateChildren(organizationUpdate);

                                Toast.makeText(SignUpOrganisationActivity.this, key, Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignUpOrganisationActivity.this, "Organization Sccessfully Created", Toast.LENGTH_SHORT).show();

                                orgAuth.signOut();


                            } else {
                                Toast.makeText(SignUpOrganisationActivity.this, "Organizational Failed to SignUp", Toast.LENGTH_SHORT).show();



                            }
                        }
                    });
                }

            }
        });






    }

    /*public void checkUserValidation(DataSnapshot dataSnapshot, String emailForVer){
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while(iterator.hasNext())
        {
            DataSnapshot dataUser  = (DataSnapshot) iterator.next();

            if(dataUser.child("emailUser").getValue().toString().equals(emailForVer)) {
                if (dataUser.child("isVerified").getValue().toString().equals("unverified")) {
                    Intent intent = new Intent(SignUpOrganisationActivity.this, LoginActivity.class);
                    intent.putExtra("User_KEY", dataUser.child("userKey").getValue().toString());
                    startActivity(intent);
                } else {
                    startActivity(new Intent(SignUpOrganisationActivity.this, Parent_activity.class));
                }
            }
        }

    }*/
}
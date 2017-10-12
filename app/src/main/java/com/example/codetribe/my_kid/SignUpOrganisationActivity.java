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

<<<<<<< HEAD
//import static com.example.codetribe.my_kid.R.id.orgAddress;
=======
>>>>>>> fcb69ce7254611b6e328c24fba4791f8db5c5171
import static com.example.codetribe.my_kid.R.id.orgCity;


public class SignUpOrganisationActivity extends AppCompatActivity {

    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mOrganizationRef;
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
    private RadioButton radGender;


    private RadioButton genderMale, genderFemale;

    private RadioGroup gender;
    //firebase Authentification
    private FirebaseAuth orgAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_organisation);

        signup = (TextView) findViewById(R.id.btnRegisterCreche);


        orgAuth = FirebaseAuth.getInstance();
        mOrganizationRef = FirebaseDatabase.getInstance().getReference();


<<<<<<< HEAD

        orgaEmail = (EditText) findViewById(R.id.orgEmail);
        orgPassword = (EditText) findViewById(R.id.orgPassword);
        crechName = (EditText) findViewById(R.id.orgName);
       // crechAddress = (EditText) findViewById(R.id.orgAddress);
        crechCity = (EditText) findViewById(R.id.orgCity);
=======
        orgaEmail = (EditText) findViewById(R.id.orgEmail);
        orgPassword = (EditText) findViewById(R.id.orgPassword);
        crechName = (EditText) findViewById(R.id.orgName);
        crechAddress = (EditText) findViewById(R.id.orgStrName);
        crechCity = (EditText) findViewById(orgCity);
>>>>>>> fcb69ce7254611b6e328c24fba4791f8db5c5171
        crechPhoneNo = (EditText) findViewById(R.id.orgTelNumber);
        adminName = (EditText) findViewById(R.id.orgAdminName);
        adminSurname = (EditText) findViewById(R.id.orgAdminSurname);
        adminIdNo = (EditText) findViewById(R.id.orgAdminIDNumber);
<<<<<<< HEAD


              orgaEmail = (EditText)findViewById(orgEmail);
             orgPassword= (EditText)findViewById(R.id.orgPassword);
              crechName = (EditText)findViewById(R.id.orgName);
              //  crechAddress = (EditText)findViewById(orgAddress);
                crechCity = (EditText)findViewById(orgCity);
            //    crechPhoneNo  = (EditText)findViewById(R.id.orgPhoneNumber);
                adminName = (EditText)findViewById(R.id.orgAdminName);
                adminSurname = (EditText)findViewById(R.id.orgAdminSurname);
                adminIdNo = (EditText)findViewById(R.id.orgAdminIDNumber);



=======
        gender = (RadioGroup) findViewById(R.id.AdminGender);
>>>>>>> fcb69ce7254611b6e328c24fba4791f8db5c5171


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


                final String crechNameOrg, crechAddressOrg, crechCityOrg, crechPhoneNoOrg, adminNameOrg, adminSurnameOrg, adminIdNoOrg, adminGender;


                crechNameOrg = crechName.getText().toString().trim();

                crechAddressOrg = crechAddress.getText().toString().trim();
                crechCityOrg = crechCity.getText().toString().trim();
                crechPhoneNoOrg = crechPhoneNo.getText().toString().trim();
                adminNameOrg = adminName.getText().toString().trim();
                adminSurnameOrg = adminSurname.getText().toString().trim();
                adminIdNoOrg = adminIdNo.getText().toString().trim();
                int selectedId = gender.getCheckedRadioButtonId();
                radGender = (RadioButton) findViewById(selectedId);
                adminGender = radGender.getText().toString().trim();


                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    orgAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // String user_id = task.getResult().getUser().getUid();
                                String adminRole = "admin";

<<<<<<< HEAD
                                DatabaseReference mChildDatabase = mOrganizationRef.child("Creche");
=======
                                DatabaseReference mChildDatabase = mOrganizationRef.child("Creche").child(crechNameOrg);
                                DatabaseReference mAdminRef = mChildDatabase.child("Users");

>>>>>>> fcb69ce7254611b6e328c24fba4791f8db5c5171
                                String key = mChildDatabase.child("Creche").child(crechNameOrg).push().getKey();
                                String adminKey = mAdminRef.child("Creche").child(crechNameOrg).child("Users").push().getKey();

                                OrganizationRegister orgReg = new OrganizationRegister(key, crechNameOrg, crechAddressOrg, crechCityOrg, email, crechPhoneNoOrg, password);

                                CrecheOnwer_Class adminReg = new CrecheOnwer_Class(adminKey, adminNameOrg, adminSurnameOrg, adminIdNoOrg, adminGender, adminRole, email, crechNameOrg);


                                //Map
                                Map<String, Object> postingOrg = orgReg.toMap();
                                Map<String, Object> organizationUpdate = new HashMap<>();

                                organizationUpdate.put(key, postingOrg);


                                Map<String, Object> postingAdmin = adminReg.toMap();
                                Map<String, Object> adminUpdate = new HashMap<>();


                                adminUpdate.put(adminKey, postingAdmin);


                                //Updating Messages
                                mAdminRef.updateChildren(adminUpdate);
                                mChildDatabase.updateChildren(organizationUpdate);

                                Toast.makeText(SignUpOrganisationActivity.this, key, Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignUpOrganisationActivity.this, "Organization Successfully Created", Toast.LENGTH_SHORT).show();

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
                    startActivity(new Intent(SignUpOrganisationActivity.this, ParentActivity.class));
                }
            }
        }

    }*/
}

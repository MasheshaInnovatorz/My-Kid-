package com.example.codetribe.my_kid.kids_Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.codetribe.my_kid.admin_Activities.AdminTabbedActivity;
import com.example.codetribe.my_kid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KidActivity extends AppCompatActivity {


    // private EditText;
    private TextInputLayout hintname, hintsurname, hintkidid, hintpid, hintParentId, hintGradeAllocated, hintYear;
    private EditText kidname,
            kidsurname,
            kidaddress,
            kididNumber,
            kidparentid,
            kidAllocated,
            registeredYears;

    private ImageView imagepic;
    private RadioGroup radKidGender;
    private RadioButton radGender;
    private TextView btnCreate;

    private String genderString, keyUser;
    private String kidStringname,
            kidStringsurname,
            kidStringaddress,
            kididStringNumber,
            kidStringparentid,
            kidsKidsAllocated,
            kidsYearRegistered;


    private ProgressDialog progressDialog;
    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    //database
    DatabaseReference databaseKids, adminOrgNameRef;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_kids_activity);

        context = getApplicationContext();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register kid");

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //initializing
        kidname = (EditText) findViewById(R.id.editname);
        kidsurname = (EditText) findViewById(R.id.editSurname);
        kidaddress = (EditText) findViewById(R.id.editAdress);
        kididNumber = (EditText) findViewById(R.id.editkidid);
        kidparentid = (EditText) findViewById(R.id.editParentId);
        kidAllocated = (EditText) findViewById(R.id.editGrade);
        registeredYears = (EditText) findViewById(R.id.editYear);
        radKidGender = (RadioGroup) findViewById(R.id.genders);
        btnCreate = (TextView) findViewById(R.id.btnKidUpdate);


        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.editname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.editSurname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.surnameerror);
        awesomeValidation.addValidation(this, R.id.editAdress, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.address);
        awesomeValidation.addValidation(this, R.id.editkidid, "^^[0-9]{13}$", R.string.iderror);
        awesomeValidation.addValidation(this, R.id.editParentId, "^^[0-9]{13}$", R.string.iderror);
        awesomeValidation.addValidation(this, R.id.editYear, "^^[0-9]{4}$", R.string.iderror);
        awesomeValidation.addValidation(this, R.id.editGrade, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}[0-9]$", R.string.classerror);


        //hint editext
        hintname = (TextInputLayout) findViewById(R.id.hname);
        hintsurname = (TextInputLayout) findViewById(R.id.hSurname);
        hintkidid = (TextInputLayout) findViewById(R.id.hAdress);
        hintpid = (TextInputLayout) findViewById(R.id.hkidid);
        hintParentId = (TextInputLayout) findViewById(R.id.hpid);
        hintGradeAllocated = (TextInputLayout) findViewById(R.id.hpgrade);
        hintYear = (TextInputLayout) findViewById(R.id.hpYear);


        Intent intent = getIntent();

        //String id = intent.getStringExtra(Teachers_activity.ARTIST_ID);
        keyUser = intent.getStringExtra("User_KEY");

        progressDialog = new ProgressDialog(this);
        //database
        databaseKids = FirebaseDatabase.getInstance().getReference().child("Kids");
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        adminOrgNameRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("orgName");
        //databaseKids = FirebaseDatabase.getInstance().getReference().child("Kids").child(keyUser);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {

                    progressDialog.setMessage("Wait While Adding Kid");
                    progressDialog.show();
                    adminOrgNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String org_name = dataSnapshot.getValue(String.class);
                            adminSerach(org_name);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    Toast.makeText(context, "Make sure you fix all the error shown in your input space", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

        });

    }

    public void adminSerach(String orgName) {


        kidStringname = kidname.getText().toString().trim();
        kidStringsurname = kidsurname.getText().toString().trim();
        kidStringaddress = kidaddress.getText().toString().trim();
        kididStringNumber = kididNumber.getText().toString().trim();
        kidStringparentid = kidparentid.getText().toString().trim();
        kidsKidsAllocated = kidAllocated.getText().toString().trim();
        kidsYearRegistered = registeredYears.getText().toString().trim();

        int selectedId = radKidGender.getCheckedRadioButtonId();


        if (selectedId != -1) {

            radGender = (RadioButton) findViewById(selectedId);
            genderString = radGender.getText().toString();

            String id = databaseKids.push().getKey();


            Kids kids = new Kids(id, kidStringname, kidStringsurname, kidStringaddress, kididStringNumber, kidStringparentid, kidsKidsAllocated, kidsYearRegistered, genderString, orgName);

            databaseKids.child(id).setValue(kids);

            startActivity(new Intent(getApplication(), AdminTabbedActivity.class));

        } else {
            Toast.makeText(this, "Make sure you select gender before you continue", Toast.LENGTH_SHORT).show();
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


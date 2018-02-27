package com.example.codetribe.my_kid.kids_Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.account_Activities.SignUp;
import com.example.codetribe.my_kid.admin_Activities.AdminTabbedActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KidActivity extends AppCompatActivity {

    //spinner
    private ArrayAdapter<String> dataAdapter;
    private List<String> list;
    // private EditText;
    private TextInputLayout hintname, hintsurname, hintkidid, hintpid, hintParentId, hintGradeAllocated, hintYear;
    private EditText kidname,
            kidsurname,
            kidaddress,
            kididNumber,
            kidparentid,
            registeredYears;

    private Spinner kidAllocated;
    //classNameList
    int counter = 0;
    private ImageView imagepic;
    private RadioGroup radKidGender;
    private RadioButton radGender;
    private TextView btnCreate;
    private String userId;
    private String genderString, keyUser;
    private String kidStringname,
            kidStringsurname,
            kidStringaddress,
            kididStringNumber,
            kidStringparentid,
            kidsKidsAllocated,
            kidsYearRegistered,
            classname;


    private ProgressDialog progressDialog;
    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    //database
    DatabaseReference databaseKids, currentUserRef, adminOrgNameRef, kidclassdata,innerClassRef;
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

        kidAllocated = (Spinner) findViewById(R.id.editGrade);
        list = new ArrayList<String>();

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        //initializing
        kidname = (EditText) findViewById(R.id.editname);
        kidsurname = (EditText) findViewById(R.id.editSurname);
        kidaddress = (EditText) findViewById(R.id.editAdress);
        kididNumber = (EditText) findViewById(R.id.editkidid);
        kidparentid = (EditText) findViewById(R.id.editParentId);
        // kidAllocated = (EditText) findViewById(R.id.editGrade);
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

        kidAllocated.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classname = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //hint editext

        hintname = (TextInputLayout) findViewById(R.id.hname);
        hintsurname = (TextInputLayout) findViewById(R.id.hSurname);
        hintkidid = (TextInputLayout) findViewById(R.id.hAdress);
        hintpid = (TextInputLayout) findViewById(R.id.hkidid);
        hintParentId = (TextInputLayout) findViewById(R.id.hpid);
        //hintGradeAllocated = (TextInputLayout) findViewById(R.id.hpgrade);
        hintYear = (TextInputLayout) findViewById(R.id.hpYear);


        Intent intent = getIntent();

        //String id = intent.getStringExtra(Teachers_activity.ARTIST_ID);
        keyUser = intent.getStringExtra("User_KEY");

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        progressDialog = new ProgressDialog(this);
        //database
        kidclassdata = FirebaseDatabase.getInstance().getReference("kidclass").child(userId);
        currentUserRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        databaseKids = FirebaseDatabase.getInstance().getReference().child("Kids");

        innerClassRef = FirebaseDatabase.getInstance().getReference("kidclass");

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
                        public void onDataChange(final DataSnapshot kdataSnapshot) {
                            if (counter != 1) {

                                String org_name = kdataSnapshot.getValue(String.class);
                                addkids(org_name);
                                Toast.makeText(context, "Kid added", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    databaseKids.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            innerClassRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {

                                if (dataSnap.child("idNumber").getValue().toString().equals(kididNumber)) {
                                    counter = 1;
                                } else {


                                    Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                }


                            }
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


    public void addkids(String orgName) {
        final String kidsKidsAllocated;
        kidsKidsAllocated = classname.trim();
        kidStringname = kidname.getText().toString().trim();
        kidStringsurname = kidsurname.getText().toString().trim();
        kidStringaddress = kidaddress.getText().toString().trim();
        kididStringNumber = kididNumber.getText().toString().trim();
        kidStringparentid = kidparentid.getText().toString().trim();
        //  kidsKidsAllocated = kidAllocated.getText().toString().trim();
        kidsYearRegistered = registeredYears.getText().toString().trim();

        int selectedId = radKidGender.getCheckedRadioButtonId();

        if (!kididStringNumber.matches(kidStringparentid)) {
            if (selectedId != -1) {

                radGender = (RadioButton) findViewById(selectedId);
                genderString = radGender.getText().toString();

                String id = databaseKids.push().getKey();


                Kids kids = new Kids(id, kidStringname, kidStringsurname, kidStringaddress, kididStringNumber, kidStringparentid, kidsKidsAllocated, kidsYearRegistered, genderString, orgName);

                databaseKids.child(id).setValue(kids);
                // Toast.makeText(context, "Kid added ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplication(), AdminTabbedActivity.class));
            } else {
                Toast.makeText(this, "Make sure you select gender before you continue", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Kid id cant be the same as parent", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setMessage("Wait While searching for class list..");
       // progressDialog.show();

        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot currentSnap) {


                kidclassdata.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot outerSnap) {




                              for (DataSnapshot classSnapshot : outerSnap.getChildren()) {

                                  //  if (!classSnapshot.child("className").getValue().toString().equals("")) {

                                  list.add(classSnapshot.child("className").getValue().toString());
                                      Toast.makeText(context, classSnapshot.child("className").getValue().toString(), Toast.LENGTH_SHORT).show();
                                  dataAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
                                  //simple_spinner_dropdown_item
                                  Toast.makeText(context, classSnapshot.child("className").getValue().toString(), Toast.LENGTH_SHORT).show();
                                  kidAllocated.setAdapter(dataAdapter);
                                  progressDialog.dismiss();
                                  //   } else {
                                  //      Toast.makeText(KidActivity.this, "There is no Class", Toast.LENGTH_SHORT).show();
                                  //   }
                              }





                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}


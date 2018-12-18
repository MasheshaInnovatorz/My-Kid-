package com.example.codetribe.my_kid.kids_Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.example.codetribe.my_kid.databinding.AddKidsActivityBinding;
import com.example.codetribe.my_kid.databinding.CreateParentProfileBinding;
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
    int selectedId;
    private Spinner kidAllocated;
    //classNameList
    int counter = 0;
    private ImageView imagepic;
    private RadioGroup radKidGender;
    private RadioButton radGender;
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

    private EditText kidsName, kidsSurname, kidsAddress, kidsid, kidsParentId, kidsYear;

    private ProgressDialog progressDialog;
    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    TextView btnKid;
    //database
    DatabaseReference databaseKids, currentUserRef, adminOrgNameRef, kidclassdata, innerClassRef;
    private Context context;
    private AddKidsActivityBinding addKidsActivityBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addKidsActivityBinding = DataBindingUtil.setContentView(this, R.layout.add_kids_activity);

        setContentView(R.layout.add_kids_activity);

        context = getApplicationContext();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register kid");

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        kidAllocated = (Spinner) findViewById(R.id.editGrade);
        list = new ArrayList<String>();

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        //Edit text
        kidsName = (EditText) findViewById(R.id.editname);
        kidsSurname = (EditText) findViewById(R.id.editSurname);
        kidsAddress = (EditText) findViewById(R.id.editAdress);
        kidsid = (EditText) findViewById(R.id.editkidid);
        kidsParentId = (EditText) findViewById(R.id.editParentId);
        kidsYear = (EditText) findViewById(R.id.editYear);




        radKidGender = (RadioGroup) findViewById(R.id.genders);
        btnKid = (TextView) findViewById(R.id.btnKidUpdate);

        //kids input


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


        btnKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    //validate class
                    if (!kidAllocated.getSelectedItem().toString().trim().equals("Select Class")) {
                        selectedId = radKidGender.getCheckedRadioButtonId();
                        if (selectedId != -1) {

                            progressDialog.setMessage("Wait While Adding Kid");
                            progressDialog.show();


                            adminOrgNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(final DataSnapshot kdataSnapshot) {
                                    if (counter != 1) {

                                        String org_name = kdataSnapshot.getValue(String.class);
                                        addkids(org_name);

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

                                        if (dataSnap.child("idNumber").getValue().toString().equals(addKidsActivityBinding.editkidid)) {
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
                            Toast.makeText(context, "Make sure you select gender before you continue", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(context, "please select a class or add class", Toast.LENGTH_SHORT).show();
                    }
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
        kidStringname =kidsName.getText().toString().trim();
        kidStringsurname = kidsSurname.getText().toString().trim();
        kidStringaddress = kidsAddress.getText().toString().trim();
        kididStringNumber =kidsid.getText().toString().trim();
        kidStringparentid = kidsParentId.getText().toString().trim();
        kidsYearRegistered = kidsYear.getText().toString().trim();

        if (kididStringNumber != kidStringparentid) {

            //if (selectedId != -1) {

            radGender = (RadioButton) findViewById(selectedId);
            genderString = radGender.getText().toString();

            String id = databaseKids.push().getKey();


            Kids kids = new Kids(id, kidStringname, kidStringsurname, kidStringaddress, kididStringNumber, kidStringparentid, kidsKidsAllocated, kidsYearRegistered, genderString, orgName);

            databaseKids.child(id).setValue(kids);
            startActivity(new Intent(getApplication(), AdminTabbedActivity.class));
        } else {
            Toast.makeText(this, "Kid id can't be the same as parent", Toast.LENGTH_SHORT).show();
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
        list.add("Select Class");
        progressDialog.setMessage("Wait While searching for class list..");
        // progressDialog.show();

        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot currentSnap) {


                kidclassdata.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot outerSnap) {

                        for (DataSnapshot classSnapshot : outerSnap.getChildren()) {

                            list.add(classSnapshot.child("className").getValue().toString());

                            progressDialog.dismiss();

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
        dataAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        kidAllocated.setAdapter(dataAdapter);
    }
}


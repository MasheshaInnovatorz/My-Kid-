package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KidActivity extends AppCompatActivity {



   // private EditText;
    private TextInputLayout  hintname,hintsurname,hintkidid,hintpid,hintParentId,hintGradeAllocated,hintYear;
     EditText kidname,
           kidsurname,
           kidaddress,
           kididNumber,
           kidparentid,
             kidAllocated,
            registeredYears;

    ImageView imagepic;
        RadioGroup radKidGender;
        RadioButton radGender;
         TextView btnCreate;

    String genderString,keyUser;
    String  kidStringname,
            kidStringsurname,
            kidStringaddress,
            kididStringNumber,
            kidStringparentid,
            kidsKidsAllocated,
    kidsYearRegistered;

    // additional medical information
    EditText   allergies,
               dietRequirements,
              doctorsRecomendations,
                inkidHeight,
               bodyWeight;


    //database
   DatabaseReference databaseKids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_kids_activity);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add kid Profile_Update");


        //initializing
        kidname = (EditText)findViewById(R.id.editname);
        kidsurname =(EditText)findViewById(R.id.editSurname);
        kidaddress =  (EditText)findViewById(R.id.editAdress);
        kididNumber = (EditText)findViewById(R.id.editkidid);
        kidparentid = (EditText)findViewById(R.id.editParentId);
        kidAllocated = (EditText)findViewById(R.id.editGrade);
        registeredYears = (EditText)findViewById(R.id.editYear);
        radKidGender = (RadioGroup)findViewById(R.id.genders);
        btnCreate = (TextView) findViewById(R.id.btnKidUpdate);



        //hint editext
        hintname =(TextInputLayout)findViewById(R.id.hname);
        hintsurname =(TextInputLayout)findViewById(R.id.hSurname);
        hintkidid =(TextInputLayout)findViewById(R.id.hAdress);
        hintpid =(TextInputLayout)findViewById(R.id.hkidid);
        hintParentId=(TextInputLayout)findViewById(R.id.hpid);
        hintGradeAllocated = (TextInputLayout)findViewById(R.id.hpgrade);
        hintYear=  (TextInputLayout)findViewById(R.id.hpYear);



        Intent intent = getIntent();
        //String id = intent.getStringExtra(Teachers_activity.ARTIST_ID);
        keyUser =  intent.getStringExtra("User_KEY");

        //database
        databaseKids = FirebaseDatabase.getInstance().getReference().child("Kids").child("Class");
        //databaseKids = FirebaseDatabase.getInstance().getReference().child("Kids").child(keyUser);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            /*    kidStringname= kidname.getText().toString().trim();
                kidStringsurname= kidsurname.getText().toString().trim();
                kidStringaddress= kidaddress .getText().toString().trim();
                kididStringNumber= kididNumber.getText().toString().trim();
                kidStringparentid= kidparentid.getText().toString().trim();

                int selectedId= radKidGender.getCheckedRadioButtonId();
                radGender =(RadioButton)findViewById(selectedId);
                genderString  = radGender.getText().toString();

                databaseKids.child("userName").setValue(kidStringname);
                databaseKids.child("userSurname").setValue(kidStringsurname);
                databaseKids.child("userIdNumber").setValue(kidStringaddress);
                databaseKids.child("userContact").setValue(kididStringNumber);
                databaseKids.child("userAddress").setValue(kidStringparentid);
                databaseKids.child("userCity").setValue(genderString);

                Toast.makeText(KidActivity.this, "User Profile_Update Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(KidActivity.this, Parent_activity.class));

*/
                saveKids();




            }
        });

    }

    private void saveKids(){

        kidStringname= kidname.getText().toString().trim();
        kidStringsurname= kidsurname.getText().toString().trim();
        kidStringaddress= kidaddress .getText().toString().trim();
        kididStringNumber= kididNumber.getText().toString().trim();
        kidStringparentid= kidparentid.getText().toString().trim();
         kidsKidsAllocated = kidAllocated.getText().toString().trim();
        kidsYearRegistered = registeredYears.getText().toString().trim();

        int selectedId= radKidGender.getCheckedRadioButtonId();
        radGender =(RadioButton)findViewById(selectedId);
        genderString  = radGender.getText().toString();

        if(!TextUtils.isEmpty(kidStringparentid)){


            String id = databaseKids.push().getKey();

            Kids kids = new Kids(id,kidsKidsAllocated, kidStringname, kidStringsurname, kidStringaddress, kididStringNumber, kidStringparentid,  genderString,kidsYearRegistered);

            databaseKids.child(kidsYearRegistered).child(id).setValue(kids);

            Toast.makeText(this, "Kids saved successfully", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getApplication(),Admin_activity.class));


        }else{
            Toast.makeText(this, "Kid name should not be empty", Toast.LENGTH_SHORT).show();
        }
    }
}


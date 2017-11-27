package com.example.codetribe.my_kid.teachers_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.account_Activities.LoginActivity;
import com.example.codetribe.my_kid.kids_Activities.Kids;
import com.example.codetribe.my_kid.kids_Activities.KidsArray;
import com.example.codetribe.my_kid.kids_Activities.KidsmemoListsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeachersActivity extends AppCompatActivity {

    private String userKey, teacherRole;
    //public static final String ARTIST_ID = "artistid";
    private TextView add_Kids;

    //initialization for kids
    private ListView listViewKids;
    private List<Kids> kid;

    private String org_name,orgname;
    private String userId, idLoged;
    private FirebaseAuth fireAuth;
    //database
    private DatabaseReference kidsRetriveRef, creachRef, orgNameReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);


        getSupportActionBar().setTitle("Teacher");
        listViewKids = (ListView) findViewById(R.id.listViewkids);
        fireAuth = FirebaseAuth.getInstance();




        kid = new ArrayList<>();


        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //orgNameReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("teacherClassroom");
        orgNameReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        //roleRefer = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("role");

        //kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids").child(userKey);

        //kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids");
        creachRef = FirebaseDatabase.getInstance().getReference().child("Creche");

        kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids");

        orgNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                org_name = dataSnapshot.child("teacherClassroom").getValue(String.class);
                teacherRole = dataSnapshot.child("role").getValue(String.class);
                orgname = dataSnapshot.child("orgName").getValue(String.class);

                kidsRetriveRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        kid.clear();

                        for (DataSnapshot kidssnapshot : dataSnapshot.getChildren()) {

                            if(kidssnapshot.exists()) {
                                if (kidssnapshot.child("orgName").getValue().toString().equals(orgname)) {
                                    if (kidssnapshot.child("kidsGrade").getValue().toString().equals(org_name)) {
                                        Kids kidInf = kidssnapshot.getValue(Kids.class);
                                        kid.add(kidInf);
                                        //    Toast.makeText(TeachersActivity.this,kidInf.getId(), Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(TeachersActivity.this, "You are not assigned kids yet", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(TeachersActivity.this, "You are not assigned kids yet", Toast.LENGTH_LONG).show();
                                }
                            }else
                            {
                                Toast.makeText(TeachersActivity.this, "You dont have data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        KidsArray trackListAdapter = new KidsArray(TeachersActivity.this, kid);
                        listViewKids.setAdapter(trackListAdapter);
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


        listViewKids.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Kids kido = kid.get(i);
                Intent intent = new Intent(getApplicationContext(), KidsmemoListsActivity.class);
                intent.putExtra("kid_id", kido.getId());
                intent.putExtra("user_role", teacherRole);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuteacher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
            switch (item.getItemId()) {

                case R.id.aboutus:
                    Intent intentUs = new Intent(TeachersActivity.this, AboutUs.class);
                    startActivity(intentUs);
                    return true;

                case R.id.logout:
                    logout();
                    return true;


                case R.id.view_profile:
                    Intent intent = new Intent(TeachersActivity.this, ViewProfile.class);
                    // intent.putExtra("User_KEY",userId);
                    intent.putExtra("user_id", idLoged);
                    startActivity(intent);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }*/

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(TeachersActivity.this, LoginActivity.class);
        startActivity(intent);
    }


}

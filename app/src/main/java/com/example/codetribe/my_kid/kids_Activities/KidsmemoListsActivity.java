package com.example.codetribe.my_kid.kids_Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.account_Activities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KidsmemoListsActivity extends AppCompatActivity {

    //recyclerview object
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    //database reference
    private DatabaseReference mDatabaseRef, childRef, mUserInfor;

    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<MemokidsUpload_class> imgList;

    //  private KidsmemoListAdapter adapter;
    private String KidsId, kidsUserId;
    private String parentid, userKey, user_roles;
    private Button btnparticipate;
    private TextView sendKids,getTime;
    private String Surname, name;
    private FloatingActionButton share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kidsmemo_lists);

//taskbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kids Creativity");
        //get key from upload


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        share = (FloatingActionButton) findViewById(R.id.share_add);

        progressDialog = new ProgressDialog(this);

        imgList = new ArrayList<>();
        //show progress dialog during list image loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait While Loading Kid Memories");
        progressDialog.show();


        mDatabaseRef = FirebaseDatabase.getInstance().getReference(UploadKidsMemo.FB_DATABASE_PATH);
        childRef = FirebaseDatabase.getInstance().getReference("Kids");
        //  btnparticipate = (Button) findViewById(R.id.btnParticipate);

    }

    public void InforTeacher(DataSnapshot kidSnapshot, DataSnapshot dataSnapshot, String kidsIdentity) {

        Iterator iterator = dataSnapshot.getChildren().iterator();
        Iterator kidsIterator = kidSnapshot.getChildren().iterator();

        // DatabaseReference teacher = FirebaseDatabase.getInstance().getReference("Users");

        while (kidsIterator.hasNext()) {

            final DataSnapshot kidsUser = (DataSnapshot) kidsIterator.next();

            if (kidsUser.child("id").getValue().equals(kidsIdentity)) {
                Surname = kidsUser.child("surname").getValue().toString();
                name = kidsUser.child("name").getValue().toString();

                while (iterator.hasNext()) {
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();


                    if (kidsUser.child("id").getValue().equals(dataUser.getKey())) {
                        if (dataUser.getKey().equals(kidsIdentity)) {

                            imgList.clear();
                            for (DataSnapshot snapshot : dataUser.getChildren()) {
                                MemokidsUpload_class img = snapshot.getValue(MemokidsUpload_class.class);
                                imgList.add(img);
                                KidsId = kidsUser.child("id").getValue().toString();
                            }
                            //init adapter
                            adapter = new KidsmemoListAdapter(getApplicationContext(), imgList);

                            //adding adapter to recyclerview
                            recyclerView.setAdapter(adapter);
                        }


                    }
                }
            }


        }
    }

    private void Infor(DataSnapshot kidSnapshot, DataSnapshot dataSnapshot, String userId) {

        Iterator iterator = dataSnapshot.getChildren().iterator();
        Iterator kidsIterator = kidSnapshot.getChildren().iterator();

        while (kidsIterator.hasNext()) {
            final DataSnapshot kidsUser = (DataSnapshot) kidsIterator.next();


            if (kidsUser.child("parentid").getValue().toString().equals(userId)) {

                Surname = kidsUser.child("surname").getValue().toString();
                name = kidsUser.child("name").getValue().toString();

                while (iterator.hasNext()) {
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();

                    if (kidsUser.child("id").getValue().toString().equals(dataUser.getKey())) {
                        imgList.clear();
                        for (DataSnapshot snapshot : dataUser.getChildren()) {

                            MemokidsUpload_class img = snapshot.getValue(MemokidsUpload_class.class);
                            imgList.add(img);
                            KidsId = kidsUser.child("id").getValue().toString();

                        }

                        //init adapter
                        adapter = new KidsmemoListAdapter(getApplicationContext(), imgList);

                        //adding adapter to recyclerview
                        recyclerView.setAdapter(adapter);


                    } else {
                        Toast.makeText(this, "You don't have a kid you are linked with", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(KidsmemoListsActivity.this, "You dont have a Kid you are linked with from this creche", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        parentid = intent.getStringExtra("parentIdentity");
        // userKey = intent.getStringExtra("User_KEY");
        kidsUserId = intent.getStringExtra("kid_id");

        userKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserInfor = FirebaseDatabase.getInstance().getReference("Users").child(userKey);

        mUserInfor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot userSnapshot) {

                childRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot kidSnapshot) {
                        mDatabaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                progressDialog.dismiss();

                                if (userSnapshot.child("role").getValue().toString().equals("parent")) {
                                    Infor(kidSnapshot, dataSnapshot, parentid);
                                } else if (userSnapshot.child("role").getValue().toString().equals("teacher")) {
                                    InforTeacher(kidSnapshot, dataSnapshot, kidsUserId);
                                } else {
                                    Toast.makeText(KidsmemoListsActivity.this, "Ahh you dont belong to any categories", Toast.LENGTH_SHORT).show();
                                }


                                //Working at it
                                share.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(KidsmemoListsActivity.this);
                                        View mView = getLayoutInflater().inflate(R.layout.activity_reset_password, null);
                                        mBuilder.setTitle("Reset Password");

                                        final TextInputEditText resetEmail = (TextInputEditText) mView.findViewById(R.id.reset_email);


                                        mBuilder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {


                                                final String email = resetEmail.getText().toString();

                                                progressDialog.setMessage("Wait While Reseting Password");
                                                progressDialog.show();



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

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                progressDialog.dismiss();
                            }
                        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }



}

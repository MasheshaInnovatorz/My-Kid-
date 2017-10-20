package com.example.codetribe.my_kid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KidsmemoListsActivity  extends AppCompatActivity {
    //recyclerview object
    private RecyclerView recyclerView;

    //adapter object
  //  private RecyclerView.Adapter iv;
    //adapter object
    private RecyclerView.Adapter adapter;
    //database reference
    private DatabaseReference mDatabaseRef,childRef, mUserInfor;

    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<MemokidsUpload_class> imgList;


  //=================
  //  private List<MemokidsUpload_class> imgList;
   // private ListView iv;

  //  private KidsmemoListAdapter adapter;
    private String KidsId, kidsUserId;
    String parentid,userKey, user_roles;
    private Button btnparticipate;
    private TextView sendKids;
    String Surname,name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kidsmemo_lists);

//taskbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Child Story");
        //get key from upload


        //user_role  = intent.getStringExtra("user_role");














        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        progressDialog = new ProgressDialog(this);





        imgList = new ArrayList<>();
        //show progress dialog during list image loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait While Loading Kid Memories");
        progressDialog.show();




        //   mDatabase = FirebaseDatabase.getInstance().getReference(SyncStateContract.Constants.DATABASE_PATH_UPLOADS);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(UploadKidsMemo.FB_DATABASE_PATH);
        childRef = FirebaseDatabase.getInstance().getReference("Kids");
        btnparticipate = (Button) findViewById(R.id.btnParticipate);

    }

    public void InforTeacher(DataSnapshot kidSnapshot, DataSnapshot dataSnapshot, String kidsIdentity){

        Iterator iterator = dataSnapshot.getChildren().iterator();
        Iterator kidsIterator = kidSnapshot.getChildren().iterator();

        // DatabaseReference teacher = FirebaseDatabase.getInstance().getReference("Users");

        while(kidsIterator.hasNext()) {

            final DataSnapshot kidsUser = (DataSnapshot) kidsIterator.next();

            if(kidsUser.child("id").getValue().equals(kidsIdentity)) {
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


    private void Infor(DataSnapshot kidSnapshot, DataSnapshot dataSnapshot, String userId){

        Iterator iterator = dataSnapshot.getChildren().iterator();

        Iterator kidsIterator = kidSnapshot.getChildren().iterator();

      // DatabaseReference teacher = FirebaseDatabase.getInstance().getReference("Users");

        while(kidsIterator.hasNext()) {
            final DataSnapshot kidsUser = (DataSnapshot) kidsIterator.next();


                if (kidsUser.child("parentid").getValue().toString().equals(userId)) {

                    Surname = kidsUser.child("surname").getValue().toString();
                    name = kidsUser.child("name").getValue().toString();


                    while (iterator.hasNext()) {
                        DataSnapshot dataUser = (DataSnapshot) iterator.next();

                        if (kidsUser.child("id").getValue().toString().equals(dataUser.getKey())) {
                            imgList.clear();

                            for (DataSnapshot snapshot : dataUser.getChildren()) {


                               // Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

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


                }else{
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
            public void onDataChange( final DataSnapshot userSnapshot) {

                childRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot kidSnapshot) {


                        mDatabaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                progressDialog.dismiss();


                                if(userSnapshot.child("role").getValue().toString().equals("parent")) {

                                    Infor(kidSnapshot, dataSnapshot, parentid);

                                }else if (userSnapshot.child("role").getValue().toString().equals("teacher")){
                                    btnparticipate.setText("Share_Activity");

                                    InforTeacher(kidSnapshot,dataSnapshot,kidsUserId);
                                }else{
                                    Toast.makeText(KidsmemoListsActivity.this, "Ahh you dont belong to any categories", Toast.LENGTH_SHORT).show();
                                }


                                // Toast.makeText(KidsmemoListsActivity.this, parentid, Toast.LENGTH_SHORT).show();



// senderId.setText(kids);

                                btnparticipate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {



                                        if(KidsId != null || kidsUserId !=null) {

                                            Intent intent = new Intent(KidsmemoListsActivity.this, UploadKidsMemo.class);
                                            intent.putExtra("kid_id", KidsId);
                                            intent.putExtra("kidsTeacherId", kidsUserId);

                                            intent.putExtra("User_KEY", userKey);



                                            startActivity(intent);

                                        }else{
                                            Toast.makeText(KidsmemoListsActivity.this, "You dont have a kids in this creche or maybe made a mistake", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                    }
                                });




                                //fetch image from firebase
                      /*  for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //imageupload class require default contractor

                            snapshot.child("parentid").getValue().toString().equals(parentid);
                             img=snapshot.getValue(MemokidsUpload_class.class);
                            imgListMemokidsUpload_class.add(img);
                        }
                        //init adapter
                        /*adapter=new KidsmemoListAdapter(KidsmemoListsActivity.this,R.layout.kids_memo_lists_activity,imgList);
                        iv.setAdapter(adapter);
*/

                                //init adapter
                                //    adapter = new KidsmemoListAdapter(KidsmemoListsActivity.this, imgList);

                                //adding adapter to recyclerview
                                //       recyclerView.setAdapter(adapter);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mainapp, menu);
        return true;
    }
}

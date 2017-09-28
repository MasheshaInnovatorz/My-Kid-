package com.example.codetribe.my_kid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageListsActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseRef,childRef;

    private List<ImageUpload> imgList;
    private ListView iv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;
    private String KidsId;
    String parentid,userKey;
    private Button btnparticipate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_lists);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Child Story");


        Intent intent = getIntent();
       parentid= intent.getStringExtra("parentIdentity");
        userKey= intent.getStringExtra("User_KEY");



        imgList=new ArrayList<>();
        iv=(ListView)findViewById(R.id.listViewImage);

        //show progress dialog during list image loading
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait While Loading List Image");
        progressDialog.show();

        mDatabaseRef= FirebaseDatabase.getInstance().getReference(Chat.FB_DATABASE_PATH);
        childRef = FirebaseDatabase.getInstance().getReference("Kids");
        btnparticipate = (Button)findViewById(R.id.btnParticipate);


    }

    private void Infor(DataSnapshot kidSnapshot, DataSnapshot dataSnapshot, String userId){

        Iterator iterator = dataSnapshot.getChildren().iterator();

        Iterator kidsIterator = kidSnapshot.getChildren().iterator();

        while(kidsIterator.hasNext()) {
            final DataSnapshot kidsUser = (DataSnapshot) kidsIterator.next();

            if (kidsUser.child("parentid").getValue().toString().equals(userId)) {

                while(iterator.hasNext()) {
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();

                    if (kidsUser.getKey().equals(dataUser.getKey())) {
                        imgList.clear();

                        for(DataSnapshot snapshot : dataUser.getChildren()){

                       ImageUpload img = snapshot.getValue(ImageUpload.class);
                            imgList.add(img);

                            KidsId = kidsUser.getKey();
                        }
                        //init adapter
                        adapter=new ImageListAdapter(ImageListsActivity.this,R.layout.image_item,imgList);
                        iv.setAdapter(adapter);

                        Toast.makeText(this, kidsUser.child("parentid").getValue().toString(), Toast.LENGTH_SHORT).show();


                    }
                }




            }

           /* while(iterator.hasNext()) {
                DataSnapshot dataUser = (DataSnapshot) iterator.next();

                if (dataUser.child("parentid").getValue().toString().equals(userId)) {

                    Toast.makeText(this, dataUser.child("parentid").getValue().toString(), Toast.LENGTH_SHORT).show();


                }
            }*/


        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot kidSnapshot) {


                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        progressDialog.dismiss();



                        Infor(kidSnapshot,dataSnapshot, parentid);

                        btnparticipate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(ImageListsActivity.this,Chat.class);
                                intent.putExtra("kid_id",KidsId);
                                intent.putExtra("User_KEY",userKey);

                                startActivity(intent);

                                Toast.makeText(ImageListsActivity.this, KidsId, Toast.LENGTH_SHORT).show();
                            }
                        });



                        //fetch image from firebase
                       /* for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //imageupload class require default contractor


                            snapshot.child("parentid").getValue().toString().equals(parentid);
                            ImageUpload img=snapshot.getValue(ImageUpload.class);
                            imgList.add(img);
                        }
                        //init adapter
                        adapter=new ImageListAdapter(ImageListsActivity.this,R.layout.image_item,imgList);
                        iv.setAdapter(adapter);*/
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

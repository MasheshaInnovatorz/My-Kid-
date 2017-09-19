package com.example.codetribe.my_kid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageListsActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;

    private List<ImageUpload> imgList;
    private ListView iv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;
    String parentid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_lists);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Child Story");

        Intent intent = getIntent();
       parentid= intent.getStringExtra("parentIdentity");


        imgList=new ArrayList<>();
        iv=(ListView)findViewById(R.id.listViewImage);

        //show progress dialog during list image loading
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait While Loading List Image");
        progressDialog.show();

        mDatabaseRef= FirebaseDatabase.getInstance().getReference(Chat.FB_DATABASE_PATH);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                Iterator iterator = dataSnapshot.getChildren().iterator();
                //fetch image from firebase
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //imageupload class require default contractor

                    snapshot.child("parentid").getValue().toString().equals(parentid);
                    ImageUpload img=snapshot.getValue(ImageUpload.class);
                    imgList.add(img);
                }
                //init adapter
                adapter=new ImageListAdapter(ImageListsActivity.this,R.layout.image_item,imgList);
                iv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
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

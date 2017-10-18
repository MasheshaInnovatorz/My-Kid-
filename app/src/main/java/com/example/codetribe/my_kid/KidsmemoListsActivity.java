package com.example.codetribe.my_kid;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.provider.SyncStateContract;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

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
    private DatabaseReference mDatabaseRef,childRef;

    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<MemokidsUpload_class> imgList;


  //=================
  //  private List<MemokidsUpload_class> imgList;
   // private ListView iv;

  //  private KidsmemoListAdapter adapter;
    private String KidsId, kidsId_key;
    String parentid,userKey;
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
        Intent intent = getIntent();
        parentid = intent.getStringExtra("parentIdentity");
        userKey = intent.getStringExtra("User_KEY");
        kidsId_key = intent.getStringExtra("kid_id");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        progressDialog = new ProgressDialog(this);

<<<<<<< HEAD
        imgList = new ArrayList<>();

=======
>>>>>>> 161ed6b62c106a7c5dcf9d53d01f8123ae4a60ae
        //show progress dialog during list image loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait While Loading Kid Memories");
        progressDialog.show();


        //   mDatabase = FirebaseDatabase.getInstance().getReference(SyncStateContract.Constants.DATABASE_PATH_UPLOADS);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(UploadKidsMemo.FB_DATABASE_PATH);
        childRef = FirebaseDatabase.getInstance().getReference("Kids");
        btnparticipate = (Button) findViewById(R.id.btnParticipate);

    }

    private void Infor(DataSnapshot kidSnapshot, DataSnapshot dataSnapshot, String userId){

        Iterator iterator = dataSnapshot.getChildren().iterator();

        Iterator kidsIterator = kidSnapshot.getChildren().iterator();

        while(kidsIterator.hasNext()) {
            final DataSnapshot kidsUser = (DataSnapshot) kidsIterator.next();

            if (kidsUser.child("parentid").getValue().toString().equals(userId)) {

                Surname = kidsUser.child("surname").getValue().toString();
                name = kidsUser.child("name").getValue().toString();


                while (iterator.hasNext()) {
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();

                    if (kidsUser.child("parentid").getValue().toString().equals(userId)) {
                        imgList.clear();

                        for (DataSnapshot snapshot : dataUser.getChildren()) {


                            //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

                            MemokidsUpload_class img = snapshot.getValue(MemokidsUpload_class.class);


                            imgList.add(img);


<<<<<<< HEAD
                            KidsId = kidsUser.getKey();
=======
                            KidsId =kidsUser.child("id").getValue().toString();
>>>>>>> 161ed6b62c106a7c5dcf9d53d01f8123ae4a60ae


                        }


                        //init adapter
                        adapter = new KidsmemoListAdapter(getApplicationContext(), imgList);

                        //adding adapter to recyclerview
                        recyclerView.setAdapter(adapter);


                    }else{
                        Toast.makeText(this, "You don't have a kid you are linked with", Toast.LENGTH_SHORT).show();
                    }
                }


            }}
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

<<<<<<< HEAD
=======
                       // Toast.makeText(KidsmemoListsActivity.this, parentid, Toast.LENGTH_SHORT).show();



// senderId.setText(kids);
>>>>>>> 161ed6b62c106a7c5dcf9d53d01f8123ae4a60ae
                        btnparticipate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

<<<<<<< HEAD
                                Intent intent = new Intent(KidsmemoListsActivity.this,UploadKidsMemo.class);
                                intent.putExtra("kid_id",KidsId);
=======
                                if(KidsId != null) {

                                    Intent intent = new Intent(KidsmemoListsActivity.this, UploadKidsMemo.class);
                                    intent.putExtra("kid_id", KidsId);

                                    intent.putExtra("User_KEY", userKey);
>>>>>>> 161ed6b62c106a7c5dcf9d53d01f8123ae4a60ae


                                    startActivity(intent);

                                }else{
                                    Toast.makeText(KidsmemoListsActivity.this, "You dont have a kids in this creche or maybe made a mistake", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            }
                        });

<<<<<<< HEAD
=======



                        //fetch image from firebase
                       /* for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //imageupload class require default contractor


                            snapshot.child("parentid").getValue().toString().equals(parentid);
                            MemokidsUpload_class img=snapshot.getValue(MemokidsUpload_class.class);
                            imgList.add(img);
                        }
                        //init adapter
                        adapter=new KidsmemoListAdapter(KidsmemoListsActivity.this,R.layout.kids_memo_lists_activity,imgList);
                        iv.setAdapter(adapter);*/
>>>>>>> 161ed6b62c106a7c5dcf9d53d01f8123ae4a60ae
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

package com.example.codetribe.my_kid;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

//import static com.example.codetribe.my_kid.R.id.parentId;

public class UploadKidsMemo extends AppCompatActivity {


    private StorageReference mStorageRef;
    private DatabaseReference mDatabseRef, mDatabaseUser, mRef, uploadedName;

    private String name, surname, results;
    private ImageView imageview;
    private EditText txtImageName;
    private Uri imgUri;
    private TextView txtUpload, txtBrowse, personUploaded;

    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";
    public static final int REQUEST_CODE = 1234;

    String idKid, userId, kidTeacherId, identity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploud_kids_memo);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Uploud Kids Memo");


        Intent intent = getIntent();
        idKid = intent.getStringExtra("kid_id");
        kidTeacherId = intent.getStringExtra("kidsTeacherId");
        results = intent.getStringExtra("userUpLoader");
        userId = intent.getStringExtra("User_KEY");


        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");


        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                   //Infor(dataSnapshot,identity);

                //Toast.makeText(Uploud_kids_memo.this, userId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //intent.putExtra("parentIdentity",parentId);
        identity = FirebaseAuth.getInstance().getCurrentUser().getUid();


        mRef = FirebaseDatabase.getInstance().getReference("Users").child(identity).child("userIdNumber");
        // intent.putExtra("parentIdentity",parentId);


        uploadedName = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String org_name = dataSnapshot.getValue(String.class);

                if (idKid != null) {
                    passingValue(idKid);
                }
                if (kidTeacherId != null) {
                    passingValue(kidTeacherId);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mStorageRef = FirebaseStorage.getInstance().getReference();


        imageview = (ImageView) findViewById(R.id.imageview);
        txtImageName = (EditText) findViewById(R.id.entername);


        txtBrowse = (TextView) findViewById(R.id.txtBrowse_click);
        txtUpload = (TextView) findViewById(R.id.txtUpload_click);
        //  personUploaded=(TextView)findViewById(R.id.kidsNameId);


        txtBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);

            }
        });

        txtUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });

    }
	/*
//camera upload
	public void btnClick(View view){
		Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		onActivityResult(intent,CAMERA_REQUEST);
	}
	*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageview.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void upload() {
        if (imgUri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading image");
            dialog.show();
            //get the storage reference
            StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));

            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();


                    uploadedName.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            name = dataSnapshot.child("userName").getValue().toString();
                            surname = dataSnapshot.child("userSurname").getValue().toString();
                            results = name + " " + surname;

                            MemokidsUpload_class imageUpload = new MemokidsUpload_class(txtImageName.getText().toString(), taskSnapshot.getDownloadUrl().toString(), results);


                            //save image infor in to firebase database
                            String uploadId = mDatabseRef.push().getKey();
                            mDatabseRef.child(uploadId).setValue(imageUpload);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                            dialog.dismiss();

                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int) progress);

                        }
                    });

        } else {
            Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnShowListImage_Click(View v) {

    }

    private void passingValue(String IdNumber) {

        mDatabseRef = FirebaseDatabase.getInstance().getReference().child(FB_DATABASE_PATH).child(IdNumber);
    }

    private void Infor(DataSnapshot dataSnapshot, String userId) {

        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()) {
            DataSnapshot dataUser = (DataSnapshot) iterator.next();

            if (dataUser.child("userKey").getValue().toString().equals(userId)) {
                name = dataUser.child("userName").getValue().toString();
                surname = dataUser.child("userSurname").getValue().toString();
                results = name + " " + surname;
            }

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



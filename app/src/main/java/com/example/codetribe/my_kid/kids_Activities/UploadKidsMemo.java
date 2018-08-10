package com.example.codetribe.my_kid.kids_Activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.databinding.UploudKidsMemoBinding;
import com.example.codetribe.my_kid.groupChat_Activities.ChatMessage;
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
import java.util.Date;
import java.util.Iterator;

//import static com.example.codetribe.my_kid.R.id.parentId;

public class UploadKidsMemo extends AppCompatActivity {

    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";
    public static final int REQUEST_CODE = 1234;
    private static final int CAMERA_REQUEST = 123;
    //database
    private StorageReference mStorageRef;
    private DatabaseReference mDatabseRef, mDatabaseUser, mRef, uploadedName, databaseReference;
    //uri
    private Uri imgUri;

    private String idKid, userId, kidTeacherId, identity, typeRequest;
    private String orgIdKey;
    private String name, surname, results;
    private long time;
    //binding
    private UploudKidsMemoBinding uploadBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uploadBinding = DataBindingUtil.setContentView(this,R.layout.uploud_kids_memo);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Uploud Kids Memo");

        //intent
        Intent intent = getIntent();
        //variable assigned infor from intent
        idKid = intent.getStringExtra("kid_id");
        kidTeacherId = intent.getStringExtra("kidsTeacherId");
        results = intent.getStringExtra("userUpLoader");
        userId = intent.getStringExtra("User_KEY");
        typeRequest = intent.getStringExtra("requestType");

        //assigning userId for a logged-in person
        identity = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //database initialization
        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("groupChat");
        mRef = FirebaseDatabase.getInstance().getReference("Users").child(identity).child("userIdNumber");
        uploadedName = FirebaseDatabase.getInstance().getReference("Users").child(identity);

        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String org_name = dataSnapshot.getValue(String.class);
                if (typeRequest == "kidsMemory") {



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference();

        uploadBinding.txtBrowseClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile_save) {
            upload(typeRequest);

            return true;

        }
        return super.onOptionsItemSelected(item);

    }
    //camera upload

    /*
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
                uploadBinding.imageview.setImageBitmap(bm);
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

    public void upload(String typofUpload) {

        Toast.makeText(this, typeRequest, Toast.LENGTH_SHORT).show();
        switch (typofUpload) {
            case "kidsMemory":
                if (idKid != null) {
                    passingValue(idKid);
                }else
                if (kidTeacherId != null) {
                    passingValue(kidTeacherId);
                }
                if (imgUri != null) {
                    final ProgressDialog dialog = new ProgressDialog(this);

                    dialog.setTitle("Uploading Kids Memories");
                    dialog.show();
                    //get the storage reference
                    StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));

                    ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                            dialog.dismiss();

                            Toast.makeText(getApplicationContext(), "Kid Memory Uploaded", Toast.LENGTH_SHORT).show();
                            // startActivity(new Intent(this,UploadKidsMemo.this,KidsmemoListsActivity.class));

                            // Intent i = new Intent(UploadKidsMemo.this, KidsMemoListFragment.class);
                            // startActivity(i);

                            uploadedName.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    name = dataSnapshot.child("userName").getValue().toString();
                                    surname = dataSnapshot.child("userSurname").getValue().toString();

                                    results = name + " " + surname;

                                    MemokidsUpload_class imageUpload = new MemokidsUpload_class(uploadBinding.entername.getText().toString(), taskSnapshot.getDownloadUrl().toString(), results, new Date().getTime());


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
                                    dialog.setMessage("Uploaded " + (int) progress + "%");

                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_SHORT).show();
                }
                break;
            case "groupChat":
                    if (imgUri != null) {
                        final ProgressDialog dialog = new ProgressDialog(getApplication());
                        //dialog.setTitle("Sending chat Message");
                        //dialog.show();
                        //get the storage reference

                        uploadedName.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                orgIdKey = dataSnapshot.child("userOrgId").getValue().toString();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        StorageReference ref = mStorageRef.child("ChatImage/" + System.currentTimeMillis() + "." + getImageExt(imgUri));

                        ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                                //dialog.dismiss();


                                Toast.makeText(getApplication(), "Sent", Toast.LENGTH_SHORT).show();

                                // startActivity(new Intent(this,UploadKidsMemo.this,KidsmemoListsActivity.class));

                                // Intent i = new Intent(UploadKidsMemo.this, KidsMemoListFragment.class);
                                // startActivity(i);


                                // imageUpload = new MemokidsUpload_class(txtImageName.getText().toString(), taskSnapshot.getDownloadUrl().toString(), results,  new Date().getTime());
                                if (taskSnapshot.getDownloadUrl().toString() != " ") {


                                    ChatMessage chatMessage = new ChatMessage(uploadBinding.entername.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), 0, FirebaseAuth.getInstance().getCurrentUser().getUid(), taskSnapshot.getDownloadUrl().toString(), orgIdKey);
                                    databaseReference.push().setValue(chatMessage);


                                    //save image infor in to firebase database
                       /* String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(chatMessage);*/
                                } else {
                                    ChatMessage chatMessage = new ChatMessage(uploadBinding.entername.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), 0, FirebaseAuth.getInstance().getCurrentUser().getUid(), "", orgIdKey);
                                    databaseReference.push().setValue(chatMessage);


                                    //save image infor in to firebase database
                       /* String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(chatMessage);*/
                                }

                                uploadBinding.entername.setText("");


                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                        Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                        dialog.setMessage("Uploaded " + (int) progress + "%");

                                    }
                                });

                    }

                    break;

        }
    };



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



}



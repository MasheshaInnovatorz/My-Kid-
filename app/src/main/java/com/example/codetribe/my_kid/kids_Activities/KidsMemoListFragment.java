package com.example.codetribe.my_kid.kids_Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codetribe.my_kid.R;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class KidsMemoListFragment extends Fragment {

    private RecyclerView recyclerView;
    public static final int REQUEST_CODE = 1234;
    //adapter object
    private RecyclerView.Adapter adapter;
    //database reference
    private DatabaseReference mDatabaseRef,mDatabseRef, childRef, mUserInfor;
    private StorageReference mStorageRef;
    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";

    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<MemokidsUpload_class> imgList;

    private FloatingActionButton fab;

    //  private KidsmemoListAdapter adapter;
    private String KidsId, kidsUserId;
    private String parentid, userKey, user_roles;
    private Button btnparticipate;
    private EditText messageEntered;
    private String  Surname,name, surname, results;
    private ImageView selectedImage;
    //uri
    private Uri imgUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_kidsmemo_lists, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mStorageRef = FirebaseStorage.getInstance().getReference();

        fab = (FloatingActionButton) rootView.findViewById(R.id.share_add);
        progressDialog = new ProgressDialog(getContext());
        imgList = new ArrayList<>();

        //show progress dialog during list image loading
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait While Loading Kid Memories");
        progressDialog.show();


        //   mDatabase = FirebaseDatabase.getInstance().getReference(SyncStateContract.Constants.DATABASE_PATH_UPLOADS);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);
        childRef = FirebaseDatabase.getInstance().getReference("Kids");

        Intent intent = getActivity().getIntent();
        parentid = intent.getStringExtra("parentIdentity");
        // userKey = intent.getStringExtra("User_KEY");
        kidsUserId = intent.getStringExtra("kid_id");

        userKey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mUserInfor = FirebaseDatabase.getInstance().getReference("Users").child(userKey);
        mUserInfor.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                    Infor(kidSnapshot, dataSnapshot,userSnapshot);
                                }

                                if (userSnapshot.child("role").getValue().toString().equals("teacher")) {
                                    btnparticipate.setText("Share_Activity");

                                    InforTeacher(kidSnapshot, dataSnapshot, kidsUserId);
                                }


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



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.uploud_kids_memo, null);
                mBuilder.setTitle("Share Activities");

                ImageButton browseImage = (ImageButton) mView.findViewById(R.id.txtBrowse_click);
                messageEntered = (EditText)mView.findViewById(R.id.entername);
                selectedImage = (ImageView)mView.findViewById(R.id.selectedImage);
               // final TextInputEditText resetEmail = (TextInputEditText) mView.findViewById(R.id.reset_email);


                mBuilder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        upload();

                       // final String email = resetEmail.getText().toString();

                       // progressDialog.setMessage("Wait While Reseting Password");
                       // progressDialog.show();



                    }
                });
                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                browseImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);

                    }
                });


            }
        });

        return rootView;
    }


    private void passingValue(String IdNumber) {

        mDatabseRef = FirebaseDatabase.getInstance().getReference().child(FB_DATABASE_PATH).child(IdNumber);
    }
    public void upload() {

        if (KidsId != null) {
            passingValue(KidsId);
            if (imgUri != null) {
                final ProgressDialog dialog = new ProgressDialog(getContext());

                dialog.setTitle("Uploading Kids Memories");
                dialog.show();
                //get the storage reference
                StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));

                ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                        dialog.dismiss();

                        Toast.makeText(getContext(), "Kid Memory Uploaded", Toast.LENGTH_SHORT).show();

                        mUserInfor.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                name = dataSnapshot.child("userName").getValue().toString();
                                surname = dataSnapshot.child("userSurname").getValue().toString();

                                results = name + " " + surname;

                                MemokidsUpload_class imageUpload = new MemokidsUpload_class(messageEntered.getText().toString(), taskSnapshot.getDownloadUrl().toString(), results, new Date().getTime());


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
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getContext(), "Please select image", Toast.LENGTH_SHORT).show();
            }
        }

        }
    public void InforTeacher(DataSnapshot kidSnapshot, DataSnapshot dataSnapshot, String kidsIdentity) {

        Iterator iterator = dataSnapshot.getChildren().iterator();
        Iterator kidsIterator = kidSnapshot.getChildren().iterator();

        while (kidsIterator.hasNext()) {

            final DataSnapshot kidsUser = (DataSnapshot) kidsIterator.next();

            if (kidsUser.child("id").getValue().equals(kidsIdentity)) {
                Surname = kidsUser.child("surname").getValue().toString();
                name = kidsUser.child("name").getValue().toString();

                while (iterator.hasNext()) {
                    DataSnapshot dataUser = (DataSnapshot) iterator.next();


                    imgList.clear();
                    for (DataSnapshot snapshot : dataUser.getChildren()) {

                        if (kidsUser.child("id").getValue().equals(snapshot.getKey())) {
                            if (snapshot.getKey().equals(kidsIdentity)) {
                                if (kidsUser.child("orgName").getValue().toString().equals(snapshot.child("orgName").getValue().toString())) {

                                    MemokidsUpload_class img = snapshot.getValue(MemokidsUpload_class.class);
                                    imgList.add(img);
                                    KidsId = kidsUser.child("id").getValue().toString();


                                    //init adapter
                                    adapter = new KidsmemoListAdapter(getContext(), imgList);

                                    //adding adapter to recyclerview
                                    recyclerView.setAdapter(adapter);
                                }
                            }

                        }


                    }
                }
            }


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
                selectedImage.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void Infor(DataSnapshot kidSnapshot, DataSnapshot dataSnapshot, DataSnapshot userSnapshot) {


        for (DataSnapshot kids : kidSnapshot.getChildren()) {

            if (kids.child("parentid").getValue().toString().equals(userSnapshot.child("userIdNumber").getValue().toString())) {
                if (userSnapshot.child("orgName").getValue().toString().equals(userSnapshot.child("orgName").getValue().toString())) {

                    KidsId = kids.child("id").getValue().toString();
                    Surname = userSnapshot.child("userSurname").getValue().toString();
                    name = userSnapshot.child("userName").getValue().toString();


                    for (DataSnapshot imageFire : dataSnapshot.getChildren()) {


                        if (kids.child("id").getValue().toString().equals(imageFire.getKey())) {

                            imgList.clear();
                            for(DataSnapshot retrieveimage : imageFire.getChildren()) {

                                MemokidsUpload_class img = retrieveimage.getValue(MemokidsUpload_class.class);
                                imgList.add(img);


                                //init adapter
                                adapter = new KidsmemoListAdapter(getContext(), imgList);

                                //adding adapter to recyclerview
                                recyclerView.setAdapter(adapter);

                            }
                        }



                    }

                }
            }

        }

    }


}

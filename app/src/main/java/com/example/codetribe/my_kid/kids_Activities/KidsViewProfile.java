package com.example.codetribe.my_kid.kids_Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.databinding.ActivityKidsViewProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class KidsViewProfile extends AppCompatActivity {

    //uri
    private Uri filePath;
    //Firebase
    private FirebaseStorage storage;
    private StorageReference mStorage;
    private DatabaseReference kidsDataProf, userDataRef, kidsppic;
    private FirebaseUser fireAuthorization;
    //varibles declared
    private final int PICK_IMAGE_REQUEST = 71;
    private String id_Key;
    //binding
    private ActivityKidsViewProfileBinding kidsProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding initialization
        kidsProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_kids_view_profile);

        //authorization
        fireAuthorization = FirebaseAuth.getInstance().getCurrentUser();
        //instance
        kidsDataProf = FirebaseDatabase.getInstance().getReference("Kids");
        userDataRef = FirebaseDatabase.getInstance().getReference("Users").child(fireAuthorization.getUid());
        kidsppic = FirebaseDatabase.getInstance().getReference().child("Kids");
        //storage instance
        storage = FirebaseStorage.getInstance();
        mStorage = storage.getReference();

        kidsProfileBinding.editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplication(), EditKidsProfile.class);
                intent.putExtra("kidId", id_Key);
                startActivity(intent);
            }
        });


        kidsProfileBinding.uploadkidpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseImage();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                kidsProfileBinding.headerCoverImage.setImageBitmap(bitmap);

                final ProgressDialog pressDialog = new ProgressDialog(this);
                pressDialog.setTitle("Uploading Kid Profile...");
                pressDialog.show();

                StorageReference ref = mStorage.child("images/" + id_Key);

                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                pressDialog.dismiss();


                                kidsppic.child(id_Key).child("profilePic").setValue(taskSnapshot.getDownloadUrl().toString());

                                Toast.makeText(KidsViewProfile.this, "Kid Profile Picture Uploaded", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                pressDialog.dismiss();
                                Toast.makeText(KidsViewProfile.this, "Failure" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                pressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void chooseImage() {
        // Intent intent = new Intent(Intent.ACTION_PICK);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onStart() {
        super.onStart();


        userDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot userSnapshot) {


                kidsDataProf.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot kidSnapshot : dataSnapshot.getChildren()) {


                            if (userSnapshot.child("userIdNumber").getValue().toString().equals(kidSnapshot.child("parentid").getValue().toString())) {

                                if (userSnapshot.child("orgName").getValue().toString().equals(kidSnapshot.child("orgName").getValue().toString())) {

                                    //parent name
                                    kidsProfileBinding.parentName.setText(userSnapshot.child("userName").getValue().toString() + " " + userSnapshot.child("userName").getValue().toString());
                                    //kids names
                                    kidsProfileBinding.kidsProfileName.setText(kidSnapshot.child("surname").getValue().toString() + " " + kidSnapshot.child("name").getValue().toString());
                                    kidsProfileBinding.kidsGenderView.setText("Gender :" + kidSnapshot.child("gender").getValue().toString());
                                    kidsProfileBinding.kidsPhoneView.setText("Parent Contact :" + userSnapshot.child("userContact").getValue().toString());
                                    kidsProfileBinding.kidsEmailView.setText("Parent Email :" + userSnapshot.child("emailUser").getValue().toString());
                                    id_Key = kidSnapshot.child("id").getValue().toString();
                                    kidsProfileBinding.kidsCityView.setText("Class :" + kidSnapshot.child("kidsGrade").getValue().toString());
                                    kidsProfileBinding.kidsAddressView.setText("Address :" + kidSnapshot.child("address").getValue().toString());

                                    Glide.with(getApplicationContext()).load(kidSnapshot.child("profilePic").getValue().toString()).centerCrop().into(kidsProfileBinding.headerCoverImage);

                                }
                            } else if (userSnapshot.child("role").getValue().toString().equals("teacher")) {
                                Intent intent = getIntent();
                                String idsKid = intent.getStringExtra("nwana");

                                if (kidSnapshot.child("id").getValue().toString().equals(idsKid)) {


                                    kidsProfileBinding.kidsGenderView.setText("Gender :" + kidSnapshot.child("gender").getValue().toString());
                                    kidsProfileBinding.kidsPhoneView.setText("Parent Contact :" + userSnapshot.child("userContact").getValue().toString());
                                    kidsProfileBinding.kidsEmailView.setText("Parent Email :" + userSnapshot.child("emailUser").getValue().toString());
                                    id_Key = kidSnapshot.child("id").getValue().toString();
                                    kidsProfileBinding.kidsCityView.setText("Class :" + kidSnapshot.child("kidsGrade").getValue().toString());
                                    kidsProfileBinding.kidsAddressView.setText("Address :" + kidSnapshot.child("address").getValue().toString());

                                    Glide.with(getApplicationContext()).load(kidSnapshot.child("profilePic").getValue().toString()).centerCrop().into(kidsProfileBinding.headerCoverImage);
                                }
                            }
                        }

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
}

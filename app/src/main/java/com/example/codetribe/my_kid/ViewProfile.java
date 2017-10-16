package com.example.codetribe.my_kid;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;

public class ViewProfile extends AppCompatActivity {

    private Uri imgUri;


    TextView name,surname,gender,phonenumber,address,email,editprofile;
    String iduser;
    String image_url;

    ImageView photo;

    int RESULT_LOAD_IMG = 1;

    //database
    FirebaseStorage storage;
    private DatabaseReference databaseReference;

    StorageReference storageRef,imagesRef,userProfileRef,callImage;

    String user_id;


    ImageView profilecover;
    String idLoged;

    String nameString,surnameString;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        //ProfileUpdate edit
        editprofile=(TextView)findViewById(R.id.editprofile);
        //initialize
        name = (TextView) findViewById(R.id.user_profile_name);
        surname= (TextView)findViewById(R.id.user_profile_status);
        gender = (TextView) findViewById(R.id.gender_view);
        phonenumber= (TextView)findViewById(R.id.phone_view);
        address= (TextView)findViewById(R.id.address_view);
        email= (TextView)findViewById(R.id.email_view);

        photo = (ImageView) findViewById(R.id.user_profile_photo);



        profilecover=(ImageView) findViewById(R.id.header_cover_image);

        

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();


        imagesRef = storageRef.child("images");
        userProfileRef = storageRef.child("images/"+user.getUid()+".jpg");




        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(ViewProfile.this,ProfileUpdate.class);
                startActivity(i);
            }
        });




        if ( user != null) {
            user_id = user.getUid();
            if (user.getPhotoUrl() != null) {
                String profile_pic = user.getPhotoUrl().toString();
                showProfilePic(profile_pic);
            }
        } else {
            user_id = "unknown_uid";
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent photoPickerIntent =  new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Infor(dataSnapshot,user.getUid());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView editprofile=(TextView) findViewById(R.id.editprofile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfile.this,ProfileUpdate.class) ;
                startActivity(intent);
            }

        });

        /*
        databaseReference = FirebaseDatabase.getInstance().getReference().child("courses").child("Business");
        name = (TextView) findViewById(R.id.user_profile_name);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CourseDetails courseDetails = dataSnapshot.getValue(CourseDetails.class);
                code = courseDetails.getCourseCode();
                name = courseDetails.getCourseName();

                name.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intentData) {

        if (requestCode == RESULT_LOAD_IMG) {

            if (resultCode == RESULT_OK) {

                Uri selectedImage = intentData.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                Log.i("Ygritte", imgDecodableString);

                //Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
                //Uri file = Uri.fromFile(new File(imgDecodableString));

                try {
                    InputStream stream = new FileInputStream(new File(imgDecodableString));
                    //photo.setImageBitmap(bitmap);
                    // StorageReference userProfileRef = storageRef.child("images/"+file.getLastPathSegment());

                    UploadTask uploadTask = userProfileRef.putStream(stream);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Log.i("Ygritte", exception.getMessage());

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            assert downloadUrl != null;
                            image_url = downloadUrl.toString();

                             //listImage.get(position).getUri()).into(img)
                            showProfilePic(image_url);
                            Log.i("Ygritte", image_url);

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(downloadUrl)
                                    .build();

                            if ( user != null ) {
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("Profile Updated ", "User profile updated.");
                                                }
                                            }
                                        });
                            }

                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.i("Ygritte", e.getMessage());
                }


            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void showProfilePic(String image_url) {
        Glide.with(getApplicationContext()).load(image_url).into(photo);
        Glide.with(getApplicationContext()).load(image_url).into(profilecover);
    }

    private void Infor(DataSnapshot dataSnapshot, String userId){

        Iterator iterator = dataSnapshot.getChildren().iterator();

// NEW CODE


        while(iterator.hasNext()) {
            DataSnapshot dataUser = (DataSnapshot) iterator.next();

            if (dataUser.child("userKey").getValue().toString().equals(userId))
            {



                name.setText("Name : " + dataUser.child("userName").getValue().toString());
                surname.setText("Surname : " + dataUser.child("userSurname").getValue().toString());
                gender.setText(" Gender :"+ dataUser.child("userGender").getValue().toString());
                phonenumber.setText("  phone number :"+ dataUser.child("userContact").getValue().toString());
                address.setText("  Lives in :"+ dataUser.child("userAddress").getValue().toString());
                email.setText("  Email :"+ dataUser.child("emailUser").getValue().toString());


        //     profilecover.setImageDrawable(dataSnapshot.child("fdsdfs").getRef());

          //Lives in

            }


        }

    }


}
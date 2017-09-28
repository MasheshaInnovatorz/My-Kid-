package com.example.codetribe.my_kid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Ref;
import java.util.Iterator;

import static android.R.attr.data;
import static android.R.attr.duration;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.codetribe.my_kid.R.drawable.user;

public class View_profile extends AppCompatActivity {

    private Uri imgUri;
    private DatabaseReference databaseReference;
    TextView name,surname,gender,phonenumber,address,email,editprofile;
    String iduser;
<<<<<<< HEAD
    ImageView profilecover,photo;

    int RESULT_LOAD_IMG = 1;

    FirebaseStorage storage;
    StorageReference storageRef,imagesRef,userProfileRef;
    String user_id;

=======
    ImageView profilecover;
    String idLoged;
>>>>>>> c3a5ee674950c0a8f60e8dcfd7b56401525a6892
    String nameString,surnameString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if ( user != null) {
            user_id = user.getUid();
        } else {
            user_id = "unknown_uid";
        }
        imagesRef = storageRef.child("images");
        userProfileRef = storageRef.child("images/"+user_id+".jpg");

        //initialize
        name = (TextView) findViewById(R.id.user_profile_name);
        surname= (TextView)findViewById(R.id.user_profile_status);
        gender = (TextView) findViewById(R.id.gender_view);
        phonenumber= (TextView)findViewById(R.id.phone_view);
        address= (TextView)findViewById(R.id.address_view);
        email= (TextView)findViewById(R.id.email_view);

        photo = (ImageView) findViewById(R.id.user_profile_photo);
        profilecover=(ImageView) findViewById(R.id.header_cover_image);


        //profile edit
        editprofile=(TextView)findViewById(R.id.editprofile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(View_profile.this,profile.class);
                startActivity(i);
            }
        });


        Intent intentId =getIntent();
        iduser = intentId.getStringExtra("parent_user");

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");


       /* UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jane Q. User")
                .setPhotoUri(Uri.parse("https://lufuno-a6da6.firebaseio.com/Users/1506418683237.png"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                         //   Log.d(TAG, "User profile updated.");
                        }
                    }
                });*/



       /* try {
            Uri imageUri = user.getPhotoUrl();
            if(imageUri != null)
            {
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                photo.setImageBitmap(selectedImage);
            }
            else
            {
                photo.setImageResource(R.drawable.benny);
            }

        } catch (FileNotFoundException e) {


        }*/
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

                Infor(dataSnapshot,iduser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        TextView editprofile=(TextView) findViewById(R.id.editprofile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(View_profile.this,profile.class) ;
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
                            String image_url = downloadUrl.toString();
                            Log.i("Ygritte", image_url);

                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.i("Ygritte", e.getMessage());
                }


            }
        }
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
                gender.setText("  "+ dataUser.child("userGender").getValue().toString());
                phonenumber.setText("  "+ dataUser.child("userContact").getValue().toString());
                address.setText("  "+ dataUser.child("userAddress").getValue().toString());
                email.setText("  "+ dataUser.child("emailUser").getValue().toString());


        //     profilecover.setImageDrawable(dataSnapshot.child("fdsdfs").getRef());

          //Lives in

            }


        }

    }


}
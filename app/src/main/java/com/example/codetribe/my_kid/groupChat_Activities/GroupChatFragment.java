package com.example.codetribe.my_kid.groupChat_Activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.codetribe.my_kid.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * Created by CodeTribe on 10/24/2017.
 */

public class GroupChatFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    DatabaseReference databaseReference, userRef, userChatReference;
    private StorageReference mStorageRef;
    LinearLayout chat_right;
    RelativeLayout chat_left;
    ImageView sendButton;
    ImageButton imageAttachment;
    EditText messageArea;
    ScrollView scrollView;
    String userId;
    //progress dialog
    ImageView image;
    private Uri imgUri;


    public static final int REQUEST_CODE = 1234;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_group_chat, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        progressDialog = new ProgressDialog(getContext());

        chat_right = (LinearLayout) rootView.findViewById(R.id.chat_right);
        chat_left = (RelativeLayout) rootView.findViewById(R.id.chat_left);

        sendButton = (ImageView) rootView.findViewById(R.id.sendButton);
        messageArea = (EditText) rootView.findViewById(R.id.messageArea);
        imageAttachment = (ImageButton) rootView.findViewById(R.id.imageAttachement);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("groupChat");
        userChatReference = FirebaseDatabase.getInstance().getReference().child("groupChat");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid());


        //storage database
        mStorageRef = FirebaseStorage.getInstance().getReference();


        imageAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);


            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageArea.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    //   messageSend();
                    upload();

                }

            }
        });

        messageRecieved();
        return rootView;
    }


    void messageRecieved() {

        //load msgs
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait While Loading Chat messages");
        progressDialog.show();


        userChatReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ChatMessage chatFax = dataSnapshot.getValue(ChatMessage.class);


                if (dataSnapshot.child("userId").getValue().toString().equals(userId)) {


                    addMessageBox(chatFax.getName().toString() + "\n" + chatFax.getMessage(), 1, chatFax);


                } else {

                    addMessageBox(chatFax.getName().toString() + "\n" + chatFax.getMessage(), 2, chatFax);
                }
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        progressDialog.dismiss();
    }


    /* public void messageSend() {



         ChatMessage chatMessage = new ChatMessage(messageArea.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail(),0,FirebaseAuth.getInstance().getCurrentUser().getUid(),"");
         databaseReference.push().setValue(chatMessage);

     }*/
    public void addMessageBox(String message, int type, ChatMessage chat) {
        TextView msg = new TextView(getActivity());
        image = new ImageView(getActivity());
        msg.setText(DateFormat.format("dd-MM-yyyy (HH:mm)", chat.getTime()) + "\n" + message);

        Glide.with(GroupChatFragment.this).load(chat.getImageURL()).centerCrop().into(image);

        LinearLayout.LayoutParams textmsg = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        image.setBackgroundResource(R.drawable.bubble_in);

        if (type == 1) {
            textmsg.gravity = Gravity.LEFT;
            msg.setBackgroundResource(R.drawable.bubble_in);
            msg.setLayoutParams(textmsg);
            image.layout(100,100,100,100);

            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            image.setBackgroundResource(R.drawable.bubble_in);
            image.setLayoutParams(textmsg);

        } else {
            textmsg.gravity = Gravity.RIGHT;
            msg.setLayoutParams(textmsg);
            msg.setBackgroundResource(R.drawable.bubble_out);

            image.layout(100,100,100,100);;
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            image.setBackgroundResource(R.drawable.bubble_out);
            image.setLayoutParams(textmsg);
        }


        chat_right.addView(msg);
        chat_right.addView(image);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
//                image.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void upload() {
        if (imgUri != null) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Uploading Kids Memories");
            dialog.show();
            //get the storage reference
            StorageReference ref = mStorageRef.child("ChatImage/" + System.currentTimeMillis() + "." + getImageExt(imgUri));

            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                    dialog.dismiss();


                    Toast.makeText(getContext(), "Kid Memory Uploaded", Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(this,UploadKidsMemo.this,KidsmemoListsActivity.class));

                    // Intent i = new Intent(UploadKidsMemo.this, KidsMemoListFragment.class);
                    // startActivity(i);


                    // imageUpload = new MemokidsUpload_class(txtImageName.getText().toString(), taskSnapshot.getDownloadUrl().toString(), results,  new Date().getTime());
                    if (taskSnapshot.getDownloadUrl().toString() != "") {
                        ChatMessage chatMessage = new ChatMessage(messageArea.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), 0, FirebaseAuth.getInstance().getCurrentUser().getUid(), taskSnapshot.getDownloadUrl().toString());
                        databaseReference.push().setValue(chatMessage);

                        Toast.makeText(getContext(), "I am here", Toast.LENGTH_SHORT).show();

                        //save image infor in to firebase database
                       /* String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(chatMessage);*/
                    } else {
                        ChatMessage chatMessage = new ChatMessage(messageArea.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), 0, FirebaseAuth.getInstance().getCurrentUser().getUid(), "");
                        databaseReference.push().setValue(chatMessage);

                        //save image infor in to firebase database
                       /* String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(chatMessage);*/
                    }
                    messageArea.setText(" ");

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

    public void showProfilePic(String image_url) {
        Glide.with(GroupChatFragment.this).load(image_url).centerCrop().into(image);

    }


}



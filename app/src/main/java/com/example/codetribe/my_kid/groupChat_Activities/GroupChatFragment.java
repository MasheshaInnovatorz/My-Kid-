package com.example.codetribe.my_kid.groupChat_Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.codetribe.my_kid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by CodeTribe on 10/24/2017.
 */

public class GroupChatFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    DatabaseReference databaseReference, userRef, userReference;
    LinearLayout chat_right;
    RelativeLayout chat_left;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_group_chat, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        progressDialog = new ProgressDialog(getContext());

        chat_right = (LinearLayout) rootView.findViewById(R.id.chat_right);
        chat_left = (RelativeLayout) rootView.findViewById(R.id.chat_left);

        sendButton = (ImageView) rootView.findViewById(R.id.sendButton);
        messageArea = (EditText) rootView.findViewById(R.id.messageArea);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("groupChat");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid());





        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageSend();
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

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot owhsom : dataSnapshot.getChildren()) {


                            ChatMessage chatFax = owhsom.getValue(ChatMessage.class);


                            if (owhsom.child("userId").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                addMessageBox(chatFax.getName().toString() + "\n" + chatFax.getMessage(), 1,chatFax);

                            } else {
                                addMessageBox(chatFax.getName().toString() + "\n" + chatFax.getMessage(), 2,chatFax);
                            }


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        progressDialog.dismiss();
    }


    public void messageSend() {

        ChatMessage chatMessage = new ChatMessage(messageArea.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail(),0,FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.push().setValue(chatMessage);

    }
    public void addMessageBox(String message, int type, ChatMessage chat){
        TextView msg = new TextView(getActivity());
        msg.setText(DateFormat.format("dd-MM-yyyy (HH:mm)",chat.getTime()) +"\n" + message );



        LinearLayout.LayoutParams textmsg = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textmsg.weight = 1.0f;

        if(type == 1) {
            textmsg.gravity = Gravity.LEFT;
            msg.setMaxWidth(300);
            msg.setBackgroundResource(R.drawable.bubble_in);

        }
        else{
            textmsg.gravity = Gravity.RIGHT;

            msg.setBackgroundResource(R.drawable.bubble_out);
        }
        msg.setLayoutParams(textmsg);
        chat_right.addView(msg);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

}



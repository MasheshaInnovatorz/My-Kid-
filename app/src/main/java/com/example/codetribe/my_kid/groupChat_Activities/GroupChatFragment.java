package com.example.codetribe.my_kid.groupChat_Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
<<<<<<< HEAD
import android.text.format.DateFormat;
import android.util.Log;
=======
import android.view.Gravity;
>>>>>>> 1d4bf809acfb3392a0fae065ff21260dfe63c6bc
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
<<<<<<< HEAD
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;



=======
    ImageView send;
    EditText messgae;
    ListView messagelist;
    FirebaseListAdapter<Chat> firebaseListAdapter;
<<<<<<< HEAD
    int i = 0;

=======
    int i=0;
>>>>>>> 83eaabf26f6e851cdf5b47a3bc670dfd7937547f
>>>>>>> 1d4bf809acfb3392a0fae065ff21260dfe63c6bc
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_group_chat, container, false);


        firebaseAuth = FirebaseAuth.getInstance();

<<<<<<< HEAD
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        send = (ImageView) rootView.findViewById(R.id.buttonsend);
        // send = (Button) rootView.findViewById(R.id.buttonsend);
        messgae = (EditText) rootView.findViewById(R.id.mess_text);
        messagelist = (ListView) rootView.findViewById(R.id.mess_list);
=======
>>>>>>> 1d4bf809acfb3392a0fae065ff21260dfe63c6bc

        layout = (LinearLayout) rootView.findViewById(R.id.layout1);
        layout_2 = (RelativeLayout) rootView.findViewById(R.id.layout2);
        sendButton = (ImageView) rootView.findViewById(R.id.sendButton);
        messageArea = (EditText) rootView.findViewById(R.id.messageArea);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("groupChat");
        userReference = FirebaseDatabase.getInstance().getReference().child("groupChat");

        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid());

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageSend();
            }
        });

<<<<<<< HEAD
        messageRecieved();
        return rootView;
    }


    void messageSend() {
        databaseReference.push().setValue(new Chat(messgae.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), 0,FirebaseAuth.getInstance().getCurrentUser().getUid()));
        messgae.setText(" ");
=======
        //messageRecieved();


<<<<<<< HEAD
=======
>>>>>>> 1d4bf809acfb3392a0fae065ff21260dfe63c6bc
    }


    void messageRecieved() {

        firebaseListAdapter = new FirebaseListAdapter<Chat>(getActivity(), Chat.class, R.layout.item_chat_right, databaseReference) {
            @Override
<<<<<<< HEAD
            protected void populateView(View v, Chat model, int position) {
                if(model.getName() == "lufuno@gmail.com") {
=======
            public void onDataChange(final DataSnapshot userSnapshot) {
>>>>>>> 83eaabf26f6e851cdf5b47a3bc670dfd7937547f

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot owhsom : dataSnapshot.getChildren()) {

<<<<<<< HEAD
                            ChatMessage chatFax = owhsom.getValue(ChatMessage.class);

                            if(owhsom.child("userId").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                addMessageBox(chatFax.getName().toString()+"\n" + chatFax.getMessage(),1 );

                            }else{
                                addMessageBox(chatFax.getName().toString()+"\n" + chatFax.getMessage(),2 );
                            }

=======
                            if (userSnapshot.child("emailUser").getValue().equals(owhsom.child("name").getValue().toString())) {
                                i = 1;


                            }else{
                                i=0;
                            }

                            if(i==1){
                                firebaseListAdapter = new FirebaseListAdapter<Chat>(getActivity(), Chat.class, R.layout.item_chat_right, databaseReference) {
                                    @Override
                                    protected void populateView(View v, Chat model, int position) {
                                        ((TextView) v.findViewById(R.id.msg)).setText(model.getMessage());
                                        // ((TextView) v.findViewById(R.id.sendname)).setText(model.getName());
                                        //  ((TextView) v.findViewById(R.id.time)).setText(DateFormat.format("dd-MM-yyyy (HH:mm)",
                                        //    model.getTime()));
                                    }
                                };

                                messagelist.setAdapter(firebaseListAdapter);
                                messagelist.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
                                messagelist.setStackFromBottom(true);
                            }else {
>>>>>>> 83eaabf26f6e851cdf5b47a3bc670dfd7937547f
>>>>>>> 1d4bf809acfb3392a0fae065ff21260dfe63c6bc

                    ((TextView) v.findViewById(R.id.msg)).setText(model.getMessage());

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
                                firebaseListAdapter = new FirebaseListAdapter<Chat>(getActivity(), Chat.class, R.layout.item_chat_left, databaseReference) {
                                    @Override
                                    protected void populateView(View v, Chat model, int position) {
                                        ((TextView) v.findViewById(R.id.msg)).setText(model.getMessage());
                                        // ((TextView) v.findViewById(R.id.sendname)).setText(model.getName());
                                        //  ((TextView) v.findViewById(R.id.time)).setText(DateFormat.format("dd-MM-yyyy (HH:mm)",
                                        //    model.getTime()));
                                    }
                                };
>>>>>>> 1d4bf809acfb3392a0fae065ff21260dfe63c6bc

                }else
                {
                  position = R.layout.item_chat_left;
                    ((TextView) v.findViewById(R.id.msg)).setText(model.getMessage());

                }

            }
        };
/*
        firebaseListAdapter = new FirebaseListAdapter<Chat>(getActivity(), Chat.class, R.layout.item_chat_left, databaseReference) {
            @Override
            protected void populateView(View v, Chat model, int position) {
                if(!model.getName().equals("lufuno@gmail.com"))
                    ((TextView) v.findViewById(R.id.msg)).setText(model.getMessage());
                // ((TextView) v.findViewById(R.id.sendname)).setText(model.getName());
                //  ((TextView) v.findViewById(R.id.time)).setText(DateFormat.format("dd-MM-yyyy (HH:mm)",
                //    model.getTime()));
            }
        };*/


>>>>>>> 83eaabf26f6e851cdf5b47a3bc670dfd7937547f


        messagelist.setAdapter(firebaseListAdapter);
        messagelist.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        messagelist.setStackFromBottom(true);

      /*  messagelist.setAdapter(firebaseListAdapter);
        messagelist.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        messagelist.setStackFromBottom(true);*/

<<<<<<< HEAD
    }



=======
                    }
                });






       /* messagelist = (ListView) rootView.findViewById(R.id.mess_list);
        btnSend = (ImageView) rootView.findViewById(R.id.buttonsend);
        editText = (EditText) rootView.findViewById(R.id.mess_text);

        // databaseReference = FirebaseDatabase.getInstance().getReference().child("groupChatnw");
        //set ListView adapter first
        adapter = new MessageAdapter(getActivity(), R.layout.item_chat_left, chatMessages);
        messagelist.setAdapter(adapter);

        //event for button SEND
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    //add message to list


                    adapter.notifyDataSetChanged();
                    editText.setText("");
                    if (isMine) {
                        isMine = false;
                    } else {
                        isMine = true;
                    }
                }
            }
        });
*/

        return rootView;
    }


    void messageSend() {

        ChatMessage chatMessage = new ChatMessage();


        chatMessage.setMessage(messageArea.getText().toString());
        chatMessage.setName(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        chatMessage.setTime(0);
        chatMessage.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());


        databaseReference.push().setValue(chatMessage);



    }
    public void addMessageBox(String message, int type){
        TextView textView = new TextView(getActivity());
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
>>>>>>> 1d4bf809acfb3392a0fae065ff21260dfe63c6bc
}



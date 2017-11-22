package com.example.codetribe.my_kid.groupChat_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.account_Activities.LoginActivity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
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
    DatabaseReference databaseReference, userRef, userReference;
    ImageView send;
    EditText messgae;
    ListView messagelist;
    FirebaseListAdapter<Chat> firebaseListAdapter;
    int i=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_group_chat, container, false);


        firebaseAuth = FirebaseAuth.getInstance();

        send = (ImageView) rootView.findViewById(R.id.buttonsend);
        // send = (Button) rootView.findViewById(R.id.buttonsend);
        messgae = (EditText) rootView.findViewById(R.id.mess_text);
        messagelist = (ListView) rootView.findViewById(R.id.mess_list);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("groupChat");
        userReference = FirebaseDatabase.getInstance().getReference().child("groupChat");

        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageSend();
            }
        });

        messageRecieved();
        return rootView;
    }


    void messageSend() {
        databaseReference.push().setValue(new Chat(messgae.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), 0));
        messgae.setText(" ");


    }

    void messageRecieved() {

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot userSnapshot) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot owhsom : dataSnapshot.getChildren()) {

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


                                firebaseListAdapter = new FirebaseListAdapter<Chat>(getActivity(), Chat.class, R.layout.item_chat_left, databaseReference) {
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



package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by CodeTribe on 10/24/2017.
 */

public class GroupChatFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ImageView send;
    EditText messgae;
    ListView messagelist;
    FirebaseListAdapter<Chat> firebaseListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_group_chat, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        // log_out = (Button) findViewById(R.id.log_out);
        send = (ImageView) rootView.findViewById(R.id.buttonsend);
        messgae = (EditText) rootView.findViewById(R.id.mess_text);
        messagelist = (ListView) rootView.findViewById(R.id.mess_list);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("groupChat");
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }

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
        Toast.makeText(getActivity(), "sent", Toast.LENGTH_SHORT).show();
    }

    void messageRecieved() {
        firebaseListAdapter = new FirebaseListAdapter<Chat>(getActivity(), Chat.class, R.layout.groupchat_layout, databaseReference) {
            @Override
            protected void populateView(View v, Chat model, int position) {
                ((TextView) v.findViewById(R.id.msg)).setText(model.getMessage());
                //  ((TextView) v.findViewById(R.id.sendname)).setText(model.getName());
                ((TextView) v.findViewById(R.id.time)).setText(DateFormat.format("dd-MM-yyyy (HH:mm)",
                        model.getTime()));
            }
        };

        messagelist.setAdapter(firebaseListAdapter);
        messagelist.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        messagelist.setStackFromBottom(true);
    }
}



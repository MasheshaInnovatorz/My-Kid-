package com.example.codetribe.my_kid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseListAdapter;


public class GroupChat extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ImageView send;
    EditText messgae;
    ListView messagelist;
    FirebaseListAdapter<Chat> firebaseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setSubtitle("Rudzani");

        firebaseAuth = FirebaseAuth.getInstance();
       // log_out = (Button) findViewById(R.id.log_out);
        send = (ImageView) findViewById(R.id.buttonsend);
        messgae = (EditText) findViewById(R.id.mess_text);
        messagelist = (ListView) findViewById(R.id.mess_list);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("groupChat");
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageSend();
            }
        });

        messageRecieved();

    }

    void messageSend() {
        databaseReference.push().setValue(new Chat(messgae.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), 0));
        messgae.setText(" ");
        Toast.makeText(this, "sent", Toast.LENGTH_SHORT).show();
    }

    void messageRecieved() {
        firebaseListAdapter = new FirebaseListAdapter<Chat>(this, Chat.class, R.layout.groupchat_layout, databaseReference) {
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



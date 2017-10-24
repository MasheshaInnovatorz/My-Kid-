package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ParentActivity extends AppCompatActivity {

    String idLoged,parentId,kids_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity);

        //idLoged = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent keyId = getIntent();
        idLoged = keyId.getStringExtra("User_KEY");
        kids_id = keyId.getStringExtra("kid_id");

//r
        parentId = keyId.getStringExtra("parent_id");


        getSupportActionBar().setTitle("Parent");



        TextView chat = (TextView) findViewById(R.id.Chat);
        TextView share = (TextView)findViewById(R.id.Share);
        TextView groupchat = (TextView) findViewById(R.id.Group_Chat);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParentActivity.this, UploadKidsMemo.class);
                intent.putExtra("kid_id",idLoged);
                intent.putExtra("User_KEY",kids_id);
                intent.putExtra("parentIdentity",parentId);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParentActivity.this,KidsmemoListsActivity.class);
                intent.putExtra("kid_id",idLoged);
                intent.putExtra("User_KEY",idLoged);
                intent.putExtra("parentIdentity",parentId);
                startActivity(intent);
            }
        });

        groupchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParentActivity.this,GroupChat.class);
               // intent.putExtra("kid_id",idLoged);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mainapp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())   {
            case R.id.aboutus:
                 Intent intentUs = new Intent(ParentActivity.this,AboutUs.class);
                startActivity(intentUs);
                return true;

            case R.id.logout:
                    logout();
                return true;

            case R.id.editKid:
              //  Intent intent_kid = new Intent(ParentActivity.this,EditKidsProfile.class);
              //  intent_kid.putExtra("User_KEY",idLoged);
              //  startActivity(intent_kid);
                return true;

            case R.id.view_profile:
                Intent intent = new Intent(ParentActivity.this,ViewProfile.class);
                intent.putExtra("parent_user",idLoged);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ParentActivity.this,LoginActivity.class) ;
        startActivity(intent);
    }

}

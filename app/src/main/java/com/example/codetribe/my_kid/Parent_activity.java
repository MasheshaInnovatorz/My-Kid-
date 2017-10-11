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

public class Parent_activity extends AppCompatActivity {

    String idLoged,parentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity);

        Intent keyId = getIntent();
        idLoged = keyId.getStringExtra("User_KEY");
        parentId = keyId.getStringExtra("parent_id");


        getSupportActionBar().setTitle("Categories");
        TextView chat = (TextView) findViewById(R.id.Chat);
        TextView share = (TextView)findViewById(R.id.Share);
        TextView profilek = (TextView) findViewById(R.id.Update_Profile);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Parent_activity.this, Uploud_kids_memo.class);
                intent.putExtra("kid_id",idLoged);
                intent.putExtra("User_KEY",idLoged);
                intent.putExtra("parentIdentity",parentId);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Parent_activity.this,KidsmemoListsActivity.class);
                intent.putExtra("kid_id",idLoged);
                intent.putExtra("User_KEY",idLoged);
                intent.putExtra("parentIdentity",parentId);
                startActivity(intent);
            }
        });

        profilek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Parent_activity.this,Profile_Update.class);
                intent.putExtra("kid_id",idLoged);
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

            case R.id.action_settings:
                //();
                return true;
            case R.id.help:
              //  showHelp();
                return true;

            case R.id.logout:

                return true;

            case R.id.editKid:
                //  showHelp();
                Intent intent_kid = new Intent(Parent_activity.this,EditKidsProfile.class);
                intent_kid.putExtra("parent_user",idLoged);
                startActivity(intent_kid);
                return true;
            case R.id.view_profile:


                Intent intent = new Intent(Parent_activity.this,View_profile.class);
                intent.putExtra("parent_user",idLoged);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Parent_activity.this,LoginActivity.class) ;
        startActivity(intent);
    }

}

package com.example.codetribe.my_kid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Welcome_activity extends AppCompatActivity {

    TextView sin;
    LinearLayout sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);


        sign_up = (LinearLayout) findViewById(R.id.signup_welcome);
        sin = (TextView) findViewById(R.id.sin);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Welcome_activity.this,sign_up.class);
                startActivity(it);

            }
        });

        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Welcome_activity.this, MainActivity.class);
                startActivity(it);
            }
        });
    }
}
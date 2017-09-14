package com.example.codetribe.my_kid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Teachers extends AppCompatActivity {

    private TextView upload_kids,add_Kids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        upload_kids   =(TextView)findViewById(R.id.text_Upload);
        add_Kids = (TextView)findViewById(R.id.text_Add_Kids);

        upload_kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        add_Kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}

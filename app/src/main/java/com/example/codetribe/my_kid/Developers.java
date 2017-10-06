package com.example.codetribe.my_kid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Developers extends AppCompatActivity {

    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        spinner=(Spinner)findViewById(R.id.spinner2);
        String[] countries={"Select Creche Name","rudzy","tali","pfari"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android. R.layout.simple_dropdown_item_1line,countries);
        spinner.setAdapter(adapter);
    }
}

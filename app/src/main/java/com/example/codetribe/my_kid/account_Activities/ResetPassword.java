package com.example.codetribe.my_kid.account_Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.codetribe.my_kid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private TextView btnReset;
    private EditText inputEmail;
    private ImageView btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        //  getSupportActionBar().setTitle("Reset Password");


        inputEmail = (EditText) findViewById(R.id.email);
        btnReset = (TextView) findViewById(R.id.btn_reset_password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        auth = FirebaseAuth.getInstance();

        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String email = inputEmail.getText().toString().trim();

                if (!awesomeValidation.validate()) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                //  progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetPassword.this, "You Dont Have Account To Reset !", Toast.LENGTH_SHORT).show();
                                }

                                //    progressBar.setVisibility(View.GONE);
                            }
                        });
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
        int id = item.getItemId();
        if (id == android.R.id.home) {

            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
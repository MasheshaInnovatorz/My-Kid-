package com.example.codetribe.my_kid.account_Activities;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.databinding.ActivityResetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    //firebase Auth
    private FirebaseAuth auth;
    //progress bar declaration
    private ProgressDialog progressDialog;
    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;
     //binding
    ActivityResetPasswordBinding resetPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetPasswordBinding= DataBindingUtil.setContentView(this,R.layout.activity_reset_password);


        //title bar title
        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //progreess bra initialization
        progressDialog = new ProgressDialog(this);

        //initialization of an owesome Validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //database Authorization
        auth = FirebaseAuth.getInstance();

        //validation for an reset Email
        awesomeValidation.addValidation(this, R.id.reset_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);


        //reset Buttons
        resetPasswordBinding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {

                    final String email = resetPasswordBinding.resetEmail.getText().toString().trim();

                    progressDialog.setMessage("Wait While Reseting Password");
                    progressDialog.show();

                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ResetPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ResetPassword.this, "You Dont Have Account To Reset !", Toast.LENGTH_SHORT).show();
                                    }

                                    progressDialog.dismiss();
                                }
                            });

                } else {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();

                }


            }
        });
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

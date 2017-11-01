package com.example.codetribe.my_kid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

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


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(WelcomeActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialogue_spinner, null);
                mBuilder.setTitle("Select an option to create Account?");

                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);
                /*
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(WelcomeActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialogue_spinner, null);
                mBuilder.setTitle("Select an option to create Account?");
                */
               // final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(WelcomeActivity.this, android.R.layout.simple_spinner_item
                        , getResources().getStringArray(R.array.signUp_Options));

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose Sign-Up Option")) {
                    //dialogInterface.dismiss();
                }
                final String text = mSpinner.getSelectedItem().toString();

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose Sign-Up Option")) {
                            dialogInterface.dismiss();
                        }
                        final String text = mSpinner.getSelectedItem().toString();

                        switch (text) {
                            case "Parent":
                                Intent it = new Intent(WelcomeActivity.this, SignUp.class);
                                startActivity(it);

                                break;
                            case "Organization":
                                Intent i = new Intent(WelcomeActivity.this, SignUpOrganisationActivity.class);
                                startActivity(i);
                                break;
                            default:
                                Toast.makeText(WelcomeActivity.this, "Choose an option to sign-Up", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }


                });


                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


            }
        });

        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(it);

                           /* AlertDialog.Builder mBuilder = new AlertDialog.Builder(WelcomeActivity.this);
                            View mView = getLayoutInflater().inflate(R.layout.dialogue_spinner, null);
                            mBuilder.setTitle("Select A category to Login?");

                            final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(WelcomeActivity.this, android.R.layout.simple_spinner_item
                                    ,getResources().getStringArray(R.array.LoginType));

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinner.setAdapter(adapter);


                            mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {

                                    if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose Login type")) {
                                        //Toast.makeText(LoginActivity.this,
                                        // m = Spinner.getSelectedItem().toString(),
                                        //Toast.LENGTH_SHORT).show();

                                        dialogInterface.dismiss();
                                    }
                                    final String text= mSpinner.getSelectedItem().toString();

                                    switch(text) {
                                        case "Teacher":
                                            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            break;
                                        case "Parent":
                                            Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
                                            startActivity(i);
                                            break;
                                        default:
                                            Toast.makeText(WelcomeActivity.this, "To continue select an Item ", Toast.LENGTH_SHORT).show();
                                            break;
                                    }

                                }


                            });


                            mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                }
                            });
                            mBuilder.setView(mView);
                            AlertDialog dialog = mBuilder.create();
                            dialog.show();

                   }*/
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(haveNetworkConnection() ==false){
            Toast.makeText(this, "Please Confirm if You bundle is ", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "You  are connected to internet", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
                if (ni.isConnected())
                    haveConnectedWifi = true;
            }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
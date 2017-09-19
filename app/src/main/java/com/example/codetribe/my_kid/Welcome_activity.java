package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

                           /* AlertDialog.Builder mBuilder = new AlertDialog.Builder(Welcome_activity.this);
                            View mView = getLayoutInflater().inflate(R.layout.dialogue_spinner, null);
                            mBuilder.setTitle("Select A category to Login?");

                            final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Welcome_activity.this, android.R.layout.simple_spinner_item
                                    ,getResources().getStringArray(R.array.LoginType));

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinner.setAdapter(adapter);


                            mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {

                                    if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose Login type")) {
                                        //Toast.makeText(MainActivity.this,
                                        // m = Spinner.getSelectedItem().toString(),
                                        //Toast.LENGTH_SHORT).show();

                                        dialogInterface.dismiss();
                                    }
                                    final String text= mSpinner.getSelectedItem().toString();

                                    switch(text) {
                                        case "Teacher":
                                            Intent intent = new Intent(Welcome_activity.this, MainActivity.class);
                                            startActivity(intent);
                                            break;
                                        case "Parent":
                                            Intent i = new Intent(Welcome_activity.this, MainActivity.class);
                                            startActivity(i);
                                            break;
                                        default:
                                            Toast.makeText(Welcome_activity.this, "To continue select an Item ", Toast.LENGTH_SHORT).show();
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
}
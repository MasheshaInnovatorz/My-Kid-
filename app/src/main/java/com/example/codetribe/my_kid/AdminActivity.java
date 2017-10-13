package com.example.codetribe.my_kid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    String userKey;
    //public static final String ARTIST_ID = "artistid";
    private TextView add_Kids;
    private TextView addkid,addteacher;



    //initialization for kids
    ListView listUsers;
    List<UserProfile> user;

    //database
    DatabaseReference usersRetriveRef;
   TextView sos;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


       /* spinner=(Spinner)findViewById(R.id.spinner2);
        String[] countries={"rudzy","tali","pfari"};
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android. R.layout.simple_dropdown_item_1line,countries);
        spinner.setAdapter(adapter);
*/

        listUsers = (ListView) findViewById(R.id.listUser);
        addkid =  (TextView) findViewById(R.id.addkids);
        addteacher=  (TextView) findViewById(R.id.addTeacher);
        Intent intent = getIntent();
        //String id = intent.getStringExtra(TeachersActivity.ARTIST_ID);
        userKey = intent.getStringExtra("User_KEY");

        user = new ArrayList<>();


        //kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids").child(userKey);

        usersRetriveRef = FirebaseDatabase.getInstance().getReference("Users");

        addteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/Admin_activity.java
                Intent intent =  new Intent(Admin_activity.this,Create_Teacher_Account.class);
=======
                Intent intent =  new Intent(AdminActivity.this,CreateTeacherAccount.class);
                intent.putExtra("User_KEY",userKey);
>>>>>>> c2b1ed454b683f8915a1a549e698267359aaf282:app/src/main/java/com/example/codetribe/my_kid/AdminActivity.java
                startActivity(intent);
                Toast.makeText(AdminActivity.this,userKey, Toast.LENGTH_SHORT).show();
            }
        });

        addkid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/Admin_activity.java
                Intent intent =  new Intent(Admin_activity.this,KidActivity.class);
=======
                Intent intent =  new Intent(AdminActivity.this,KidActivity.class);
                intent.putExtra("User_KEY",userKey);
>>>>>>> c2b1ed454b683f8915a1a549e698267359aaf282:app/src/main/java/com/example/codetribe/my_kid/AdminActivity.java
                startActivity(intent);
                Toast.makeText(AdminActivity.this,userKey, Toast.LENGTH_SHORT).show();
            }
        });


        listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //spinner code

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdminActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialogue_spinner, null);
                mBuilder.setTitle("Change User Role");

                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminActivity.this, android.R.layout.simple_spinner_item
                        ,getResources().getStringArray(R.array.user_role));

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);


                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Change User Role")) {
                            //Toast.makeText(LoginActivity.this,
                            // m = Spinner.getSelectedItem().toString(),
                            //Toast.LENGTH_SHORT).show();

                            dialogInterface.dismiss();
                        }
                        final String text= mSpinner.getSelectedItem().toString();

                        switch(text) {
                            case "Teacher":
                                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;
                            case "Parent":
                                Intent i = new Intent(AdminActivity.this, LoginActivity.class);
                                startActivity(i);
                                break;
                            default:
                                Toast.makeText(AdminActivity.this, "To continue select an Item ", Toast.LENGTH_SHORT).show();
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


    }

    @Override
    protected void onStart() {
        super.onStart();


        usersRetriveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.clear();
                for (DataSnapshot kidssnapshot : dataSnapshot.getChildren()) {


                    UserProfile kidInf = kidssnapshot.getValue(UserProfile.class);
                    user.add(kidInf);

                    Toast.makeText(Admin_activity.this, kidInf.getUserIdNumber(), Toast.LENGTH_SHORT).show();
                }
                UserArray userListAdapter = new UserArray(AdminActivity.this, user);
                listUsers.setAdapter(userListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
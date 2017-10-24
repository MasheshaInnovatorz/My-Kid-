package com.example.codetribe.my_kid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends Fragment {

    String userKey;
    //public static final String ARTIST_ID = "artistid";
    private TextView add_Kids;
    private TextView addkid,addteacher;
    Context context = getActivity().getApplicationContext();


    //initialization for kids
    ListView listUsers;
    List<UserProfile> user;
    List<Kids> kidses;

    private FirebaseAuth adminUser;
    //database
    DatabaseReference usersRetriveRef,kidsCreche;
    TextView sos;
    String Idadmin;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_admin, container, false);

        listUsers = (ListView) rootView.findViewById(R.id.listUser);
        addkid =  (TextView) rootView.findViewById(R.id.addkids);
        addteacher=  (TextView) rootView.findViewById(R.id.addTeacher);

        Intent intent = getActivity().getIntent();
        userKey = intent.getStringExtra("User_KEY");

        user = new ArrayList<>();
        kidses = new ArrayList<>();


        usersRetriveRef = FirebaseDatabase.getInstance().getReference("Users");
        kidsCreche = FirebaseDatabase.getInstance().getReference("Kids");

        addteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =  new Intent(context,CreateTeacherAccount.class);
                intent.putExtra("User_KEY",userKey);
                startActivity(intent);

            }
        });

        addkid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context,KidActivity.class);
                intent.putExtra("User_KEY",userKey);
                startActivity(intent);

            }
        });



        listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //spinner code

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialogue_spinner, null);
                mBuilder.setTitle("Change User Role");

                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item
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
                                Intent intent = new Intent(context, LoginActivity.class);
                                startActivity(intent);
                                break;
                            case "Parent":
                                Intent i = new Intent(context, LoginActivity.class);
                                startActivity(i);
                                break;
                            default:
                                Toast.makeText(context, "To continue select an Item ", Toast.LENGTH_SHORT).show();
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

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Idadmin=  FirebaseAuth.getInstance().getCurrentUser().getUid();

        usersRetriveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.clear();
                for (DataSnapshot kidssnapshot : dataSnapshot.getChildren()) {


                   if(kidssnapshot.child("role").getValue().toString().equals("teacher")) {

                       UserProfile kidInf = kidssnapshot.getValue(UserProfile.class);
                       user.add(kidInf);

                       kidInf.getKeyUser();

                   }

                }
                UserArray userListAdapter = new UserArray(getActivity(), user);
                listUsers.setAdapter(userListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.adminmenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId())   {
            case R.id.view_teachers:
                //  showHelp();
                usersRetriveRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user.clear();
                        for (DataSnapshot kidssnapshot : dataSnapshot.getChildren()) {


                            if(kidssnapshot.child("role").getValue().toString().equals("teacher")) {

                                UserProfile kidInf = kidssnapshot.getValue(UserProfile.class);
                                user.add(kidInf);

                                kidInf.getKeyUser();

                            }

                        }
                        UserArray userListAdapter = new UserArray(getActivity() , user);
                        listUsers.setAdapter(userListAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                return true;

            case R.id.view_Kids:



                usersRetriveRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( final DataSnapshot crecheSnapshot) {

                        for (final DataSnapshot adminSnap : crecheSnapshot.getChildren()) {

                        if (adminSnap.child("userKey").getValue().toString().equals(Idadmin)) {

                            kidsCreche.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    kidses.clear();
                                    for (DataSnapshot kidssnapshot : dataSnapshot.getChildren()) {


                                        if (kidssnapshot.child("orgName").getValue().toString().equals(adminSnap.child("orgName").getValue().toString())) {

                                            Kids kidInf = kidssnapshot.getValue(Kids.class);
                                            kidses.add(kidInf);

                                            kidInf.getId();

                                        }

                                    }
                                    KidsArray userListAdapter = new KidsArray(getActivity(), kidses);
                                    listUsers.setAdapter(userListAdapter);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context,LoginActivity.class) ;
        startActivity(intent);
    }
}
package com.example.codetribe.my_kid.admin_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codetribe.my_kid.teachers_Activities.CreateTeacherAccount;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.teachers_Activities.UserArray;
import com.example.codetribe.my_kid.teachers_Activities.TeacherClassAcc;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CodeTribe on 10/24/2017.
 */

public class AdminTeacherList extends Fragment {

    private String userKey;
    private TextView add_Kids ,totalteachercount;
    private TextView addkid, addteacher;
    //initialization for kids
    private ListView listUsers;
    private int counter = 0;
    private List<TeacherClassAcc> user;
    private String Idadmin;
    private Spinner spinner;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton userTeacher;

    //database
    private DatabaseReference usersRetriveRef, adminCreche;
    private FirebaseAuth adminUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activityteacherslist, container, false);

       totalteachercount=(TextView) rootView.findViewById(R.id.admintotalteacher);
        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.cordinatelayout);
        user = new ArrayList<>();


      userTeacher = (FloatingActionButton) rootView.findViewById(R.id.add_teacher_admin);


        listUsers = (ListView) rootView.findViewById(R.id.teacherlistView);

        Idadmin = FirebaseAuth.getInstance().getCurrentUser().getUid();


        usersRetriveRef = FirebaseDatabase.getInstance().getReference("Users");
        adminCreche = FirebaseDatabase.getInstance().getReference("Users").child(Idadmin);


        adminCreche.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot adminSnapshot) {
                usersRetriveRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user.clear();
                        for (DataSnapshot kidssnapshot : dataSnapshot.getChildren()) {


                            if (kidssnapshot.child("role").getValue().toString().equals("teacher")) {
                                if (kidssnapshot.child("orgName").getValue().equals(adminSnapshot.child("orgName").getValue().toString())) {
                                    counter++;
                                    TeacherClassAcc kidInf = kidssnapshot.getValue(TeacherClassAcc.class);
                                    user.add(kidInf);

                                    kidInf.getUserKey();


                                }

                            }

                        }
                        totalteachercount.setText(  "You have "+ counter +" teachers in your cresh");

                        UserArray userListAdapter = new UserArray(getActivity(), user);
                        listUsers.setAdapter(userListAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        userTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateTeacherAccount.class);
                intent.putExtra("User_KEY", userKey);
                startActivity(intent);
            }
        });
        registerForContextMenu(listUsers);
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("???");
       // menu.add(0,v.getId(),0,"Kids Information");
        menu.add(0,v.getId(),0,"Teacher Information");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Teacher Information") {
            Toast.makeText(getContext(), "Teacher Information", Toast.LENGTH_SHORT).show();
        }

        else {
            return false;
        }
        return true;
    }


    @Override
    public void onStop() {
        super.onStop();
        counter = 0;
    }
}

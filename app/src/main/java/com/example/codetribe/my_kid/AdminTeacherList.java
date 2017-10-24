package com.example.codetribe.my_kid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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

    String userKey;
    //public static final String ARTIST_ID = "artistid";
    private TextView add_Kids;
    private TextView addkid,addteacher;
    //initialization for kids
    ListView listUsers;
    List<UserProfile> user;


    private FirebaseAuth adminUser;
    //database
    DatabaseReference usersRetriveRef,kidsCreche;
    TextView sos;
    String Idadmin;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_teachers, container, false);


        user = new ArrayList<>();

        usersRetriveRef = FirebaseDatabase.getInstance().getReference("Users");


        listUsers = (ListView)rootView.findViewById(R.id.listViewkids);

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

        return rootView;
    }
}
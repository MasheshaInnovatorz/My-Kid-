package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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

public class AdminKidsList extends Fragment {

    String userKey;
    //public static final String ARTIST_ID = "artistid";
    private TextView add_Kids;
    private TextView addkid, addteacher;
    //initialization for kids
    ListView listUsers;
    String KidssKey;
    List<Kids> kidses;

    String idLoged, parentId, kids_id;

    private FirebaseAuth adminUser;
    //database
    DatabaseReference usersRetriveRef, kidsCreche;
    TextView sos;
    String Idadmin;
    Spinner spinner;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton kidsFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.kidaddactivityfrag, container, false);


        listUsers = (ListView) rootView.findViewById(R.id.listViewkids);
        //  fab = (FloatingActionButton) rootView.findViewById(R.id.addTeacher);

        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.cordinatelayout);

        kidsFab = (FloatingActionButton) rootView.findViewById(R.id.add_kids_admin);
        Intent keyId = getActivity().getIntent();
        idLoged = keyId.getStringExtra("User_KEY");
        kids_id = keyId.getStringExtra("kid_id");


        parentId = keyId.getStringExtra("parent_id");

        kidses = new ArrayList<>();


        kidsCreche = FirebaseDatabase.getInstance().getReference("Kids");
        usersRetriveRef = FirebaseDatabase.getInstance().getReference("Users");

        Idadmin = FirebaseAuth.getInstance().getCurrentUser().getUid();


        usersRetriveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot crecheSnapshot) {

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


                                        KidssKey = kidInf.getId();

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



        kidsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), KidActivity.class);
                intent.putExtra("kid_id", idLoged);
                intent.putExtra("User_KEY", kids_id);
                intent.putExtra("parentIdentity", parentId);
                startActivity(intent);
            }
        });

        return rootView;
    }


}



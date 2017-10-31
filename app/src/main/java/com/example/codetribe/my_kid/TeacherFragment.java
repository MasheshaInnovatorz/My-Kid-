package com.example.codetribe.my_kid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
 * Created by Codetribe on 24-Oct-17.
 */

public class TeacherFragment extends Fragment {

    String userKey, teacherRole;
    //public static final String ARTIST_ID = "artistid";
    private TextView add_Kids;

    //initialization for kids
    ListView listViewKids;
    List<Kids> kid;

    String org_name;
    String userId, idLoged;
    FirebaseAuth fireAuth;
    //database
    DatabaseReference kidsRetriveRef, creachRef, orgNameReference, roleRefer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_teachers, container, false);


        listViewKids = (ListView) rootView.findViewById(R.id.listViewkids);
        fireAuth = FirebaseAuth.getInstance();

        kid = new ArrayList<>();


        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //orgNameReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("teacherClassroom");
        orgNameReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        //roleRefer = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("role");

        //kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids").child(userKey);

        //kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids");
        creachRef = FirebaseDatabase.getInstance().getReference().child("Creche");

        kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids");

        orgNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                org_name = dataSnapshot.child("teacherClassroom").getValue(String.class);
                teacherRole = dataSnapshot.child("role").getValue(String.class);


                kidsRetriveRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        kid.clear();

                        for (DataSnapshot kidssnapshot : dataSnapshot.getChildren()) {
                            if (kidssnapshot.child("kidsGrade").getValue().toString().equals(org_name)) {
                                Kids kidInf = kidssnapshot.getValue(Kids.class);
                                kid.add(kidInf);
                                //    Toast.makeText(TeachersActivity.this,kidInf.getId(), Toast.LENGTH_SHORT).show();

                            } else {

                            }
                        }
                        KidsArray trackListAdapter = new KidsArray(getActivity(), kid);
                        listViewKids.setAdapter(trackListAdapter);
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

        listViewKids.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Kids kido = kid.get(i);
                Intent intent = new Intent(getContext(), KidsmemoListsActivity.class);
                intent.putExtra("kid_id", kido.getId());
                intent.putExtra("user_role", teacherRole);
                startActivity(intent);
            }
        });


        return rootView;
    }
}

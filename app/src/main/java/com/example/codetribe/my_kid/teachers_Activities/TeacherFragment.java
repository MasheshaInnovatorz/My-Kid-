package com.example.codetribe.my_kid.teachers_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.kids_Activities.Kids;
import com.example.codetribe.my_kid.kids_Activities.KidsArray;
import com.example.codetribe.my_kid.kids_Activities.KidsmemoListsActivity;
import com.google.common.cache.AbstractCache;
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

    private String userKey, teacherRole;
    private TextView add_Kids;

    //initialization for kids
    private ListView listViewKids;
    private List<Kids> kid;
    private int counter = 0;
    private String org_name,orgname;
    private String userId, idLoged;
    private FirebaseAuth fireAuth;
    private TextView countnumber;

    //database
    private DatabaseReference kidsRetriveRef, creachRef, orgNameReference, roleRefer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_teachers, container, false);

        countnumber  =(TextView) rootView.findViewById(R.id.totalkids);

        listViewKids = (ListView) rootView.findViewById(R.id.listViewkids);
        fireAuth = FirebaseAuth.getInstance();

        kid = new ArrayList<>();


        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        orgNameReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        creachRef = FirebaseDatabase.getInstance().getReference().child("Creche");

        kidsRetriveRef = FirebaseDatabase.getInstance().getReference("Kids");

        orgNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                org_name = dataSnapshot.child("teacherClassroom").getValue(String.class);
                orgname = dataSnapshot.child("orgName").getValue(String.class);
                teacherRole = dataSnapshot.child("role").getValue(String.class);


                kidsRetriveRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        kid.clear();

                        for (DataSnapshot kidssnapshot : dataSnapshot.getChildren()) {
                            if (kidssnapshot.child("kidsGrade").getValue().toString().equals(org_name)) {
                                if(kidssnapshot.child("orgName").getValue().toString().equals(orgname)) {
                                   counter++;
                                    Kids kidInf = kidssnapshot.getValue(Kids.class);
                                    kid.add(kidInf);
                                    //    Toast.makeText(TeachersActivity.this,kidInf.getId(), Toast.LENGTH_SHORT).show();
                                }
                            } else {

                            }
                        }
                        countnumber.setText("Total kids : " + counter);
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

        registerForContextMenu(listViewKids);






        return rootView;
    }


   @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("???");
        menu.add(0,v.getId(),0,"Kids Information");
        menu.add(0,v.getId(),0,"Parent Information");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Kids Information") {
            Obc();
        } else if (item.getTitle() == "Parent Information") {
            Toast.makeText(getContext(), "Parent Information", Toast.LENGTH_SHORT).show();
        } else {
            return false;
        }
        return true;
    }

    public void Obc(){
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
    }
}

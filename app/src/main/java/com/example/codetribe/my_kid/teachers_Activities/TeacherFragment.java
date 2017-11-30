package com.example.codetribe.my_kid.teachers_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.kids_Activities.Kids;
import com.example.codetribe.my_kid.kids_Activities.KidsArray;
import com.example.codetribe.my_kid.kids_Activities.KidsViewProfile;
import com.example.codetribe.my_kid.kids_Activities.KidsmemoListsActivity;
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
    private String org_name, orgname;
    private String userId, idLoged;
    private FirebaseAuth fireAuth;
    private TextView countnumber;
    private static final String KidsInformation = "Kids Information";
    private static final String ParentInformation = "Parent Information";
    private Kids listKidsSelect;

    Object mActionMode;

    //database
    private DatabaseReference kidsRetriveRef, creachRef, orgNameReference, roleRefer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_teachers, container, false);

        countnumber = (TextView) rootView.findViewById(R.id.totalkids);

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


                listViewKids.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                        listKidsSelect = kid.get(i);
                        if (mActionMode != null) {
                            return false;
                        }


                        mActionMode = getActivity().startActionMode(mActionModeCallback);
                        return true;
                    }
                });


                kidsRetriveRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        kid.clear();

                        for (DataSnapshot kidssnapshot : dataSnapshot.getChildren()) {
                            if (kidssnapshot.child("kidsGrade").getValue().toString().equals(org_name)) {
                                if (kidssnapshot.child("orgName").getValue().toString().equals(orgname)) {
                                    counter++;
                                    Kids kidInf = kidssnapshot.getValue(Kids.class);
                                    kid.add(kidInf);
                                    //    Toast.makeText(TeachersActivity.this,kidInf.getId(), Toast.LENGTH_SHORT).show();
                                }
                            } else {

                            }
                        }
<<<<<<< HEAD
                        countnumber.setText(  "You have "+ counter +" kids in your class");
=======
                        //countnumber.setText("Total kids : " + counter);
>>>>>>> 41f4d5c6297e27e898c407f51eff68b4ed199194
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
        //super.onCreateContextMenu(menu, v, menuInfo);
        //
        // getMenuInflate().inflate(R.menu.kids_menu,menu);

        // getActivity().getMenuInflater().inflate(R.menu.kids_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo infor = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

     /*   switch (item.getItemId()) {
            case R.id.kidsActivities:


        }*/

        return super.onContextItemSelected(item);
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            //actionMode.setTitle(listKidsSelect.getName());
            MenuInflater inflater = actionMode.getMenuInflater();


            inflater.inflate(R.menu.kids_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.kidsActivities:

                    Intent intent = new Intent(getContext(), KidsmemoListsActivity.class);
                    intent.putExtra("kid_id", listKidsSelect.getId());
                    intent.putExtra("user_role", teacherRole);
                    startActivity(intent);
                    mActionMode = null;
                    return true;
                case R.id.kidsProfile:

                    Intent intent2 = new Intent(getContext(), KidsViewProfile.class);
                    intent2.putExtra("nwana", listKidsSelect.getId());
                    startActivity(intent2);
                    mActionMode = null;
                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

            mActionMode = null;
        }
    };


}

package com.example.codetribe.my_kid.admin_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.kids_Activities.KidActivity;
import com.example.codetribe.my_kid.kids_Activities.Kids;
import com.example.codetribe.my_kid.kids_Activities.KidsArray;
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

    //initialization for kids
    private ListView listUsers;
    private String KidssKey;
    private List<Kids> kidses;
    private List<String> KidsListy;
    private int counter = 0;
    private String Idadmin;
    private Spinner spinner;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton kidsFab;
    private String idLoged, parentId, kids_id;
    private TextView admintotalkids;
    private Kids listKidsSelect;
    //database
    private DatabaseReference usersRetriveRef, kidsCreche;
    private FirebaseAuth adminUser;
    Object mActionMode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_kids, container, false);

        listUsers = (ListView) rootView.findViewById(R.id.listViewkidss);

        listUsers.setOnCreateContextMenuListener(getActivity());
        registerForContextMenu(listUsers);

        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.cordinatelayout);
        kidsFab = (FloatingActionButton) rootView.findViewById(R.id.add_kids_admin);
        admintotalkids = (TextView) rootView.findViewById(R.id.admintotalkids);

        Intent keyId = getActivity().getIntent();
        idLoged = keyId.getStringExtra("User_KEY");
        kids_id = keyId.getStringExtra("kid_id");


        parentId = keyId.getStringExtra("parent_id");
        kidses = new ArrayList<>();



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

        listUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                listKidsSelect = kidses.get(i);
                if (mActionMode != null) {
                    return false;
                }

                mActionMode = getActivity().startActionMode(mActionModeCallback);

                return true;
            }
        });


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
                                        counter++;
                                        Kids kidInf = kidssnapshot.getValue(Kids.class);
                                        kidses.add(kidInf);


                                        KidssKey = kidInf.getId();

                                    }

                                }
                                admintotalkids.setText("You have " + counter + " kids in your cresh");
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
            actionMode.setTitle(listKidsSelect.getName());
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
            switch (menuItem.getItemId()){
                case R.id.kidsActivities:
                    Intent intent = new Intent(getContext(), KidActivity.class);
                    intent.putExtra("kid_id", idLoged);
                    intent.putExtra("User_KEY", kids_id);
                    intent.putExtra("parentIdentity", parentId);
                    startActivity(intent);


            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mActionMode = null;
        }
    };


}



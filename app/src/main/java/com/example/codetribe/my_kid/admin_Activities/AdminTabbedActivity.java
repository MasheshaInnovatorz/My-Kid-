package com.example.codetribe.my_kid.admin_Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.aboutUs_Activity.AboutUs;
import com.example.codetribe.my_kid.account_Activities.LoginActivity;
import com.example.codetribe.my_kid.account_Activities.ViewProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminTabbedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private AlertDialog.Builder alertDialogBuilder;
    private ViewPager mViewPager;


    final Context context = this;

    private AwesomeValidation awesomeValidation;

    private EditText classkid;
    private TextView crechename,adminName;

    //database declaration
    private DatabaseReference databasekidclass, adminDataRef;
    private FirebaseUser user;

    AlertDialog alertDialog;
    //variable
    String classkidString;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_drawer_app);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setTitle("Admin");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_admin_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
          this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

      //  firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //database
        databasekidclass = FirebaseDatabase.getInstance().getReference().child("kidclass");
        adminDataRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());



        //validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.editClassAdd, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.classkiderror);

        crechename = (TextView)findViewById(R.id.crecheName);
        adminName = (TextView)findViewById(R.id.adminName);

        adminDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String orgname = dataSnapshot.child("orgName").getValue().toString();
                Toast.makeText(context, orgname, Toast.LENGTH_SHORT).show();

                String name = dataSnapshot.child("userName").getValue().toString() + "" + dataSnapshot.child("userSurname").getValue().toString();


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //dialogBogs
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);
        alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle("Add Class");
        alertDialogBuilder.setView(promptsView);

        classkid = (EditText) promptsView.findViewById(R.id.editClassAdd);

        classkid.setHint("Add Class");
        classkidString = classkid.getText().toString().trim();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_about_us) {

            Intent intent = new Intent(AdminTabbedActivity.this, AboutUs.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_admin_logout) {
            logout();
            return true;
        } else if (id == R.id.menu_adimin_profile) {
            Intent intent = new Intent(AdminTabbedActivity.this, ViewProfile.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_admin_logout) {
            logout();
        } else if (id == R.id.menu_admin_addclass) {
            // set dialog message
            //call class
            showChangeLangDialog();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_admin_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_about_us) {

            Intent intent = new Intent(AdminTabbedActivity.this, AboutUs.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_admin_logout) {
            logout();
            return true;
        } else if (id == R.id.menu_adimin_profile) {
            Intent intent = new Intent(AdminTabbedActivity.this, ViewProfile.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_admin_logout) {
            logout();
        } else if (id == R.id.menu_admin_addclass) {
            // set dialog message
            //call class
            showChangeLangDialog();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_admin_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).


            switch (position) {
                case 0:
                    return new AdminTeacherList();
                case 1:
                    return new AdminKidsList();


            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Teachers";
                case 1:
                    return "Kids";
            }
            return null;
        }
    }



    private void logout() {


        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AdminTabbedActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    //add class
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.prompts, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.editClassAdd);

        dialogBuilder.setTitle("Add Class");
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with

                String classkid;

                classkid = edt.getText().toString();
                edt.setHint("Enter Class Here");
                if (!classkid.isEmpty()) {

                    databasekidclass.child(user.getUid()).child(databasekidclass.push().getKey()).child("className").setValue(classkid);
                    Toast.makeText(AdminTabbedActivity.this, "Class Created Successfully", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(AdminTabbedActivity.this, "Please Enter Class", Toast.LENGTH_SHORT).show();
                }


            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}

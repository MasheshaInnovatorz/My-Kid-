package com.example.codetribe.my_kid.admin_Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.aboutUs_Activity.AboutUs;
import com.example.codetribe.my_kid.account_Activities.LoginActivity;
import com.example.codetribe.my_kid.account_Activities.ViewProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminTabbedActivity extends AppCompatActivity {

    String classkidString;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private AlertDialog.Builder alertDialogBuilder;
    private ViewPager mViewPager;


    private FirebaseUser user;

    final Context context = this;
    private AwesomeValidation awesomeValidation;

    EditText classkid;
    private DatabaseReference databasekidclass, adminDataRef;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tabbed2);

//databse
        databasekidclass = FirebaseDatabase.getInstance().getReference().child("kidclass");
        adminDataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        //validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.editClassAdd, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.classkiderror);
        //classkid = (EditText) findViewById(R.id.editClassAdd);

        user = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Admin");

        setSupportActionBar(toolbar);

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


            // create alert dialog

           // AlertDialog pfrid = alertDialogBuilder.create();
            //alert dialog box

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Add",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if (classkidString.isEmpty() ) {

                                        databasekidclass.child(user.getUid()).child(databasekidclass.push().getKey()).child("className").setValue(classkid.getText().toString());
                                        Toast.makeText(AdminTabbedActivity.this, "Class Created Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AdminTabbedActivity.this, "Please Enter Class", Toast.LENGTH_SHORT).show();
                                    }
                                    classkid.setText(" ");
                                    removeDialog(id);
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();



        }
        return super.onOptionsItemSelected(item);


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


    /*
        private void savekidclass() {
            if (awesomeValidation.validate()) {

              classkid.getText().toString().trim();
                DatabaseReference mChildDatabase = databasekidclass.child("kidclass");

                mChildDatabase.child("Class").setValue(classkid);
                Toast.makeText(AdminTabbedActivity.this, "Class Created Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AdminTabbedActivity.this, "Enter Class", Toast.LENGTH_SHORT).show();
            }
        }

    */
    private void logout() {


        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AdminTabbedActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

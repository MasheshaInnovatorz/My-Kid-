package com.example.codetribe.my_kid.parent_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.codetribe.my_kid.aboutUs_Activity.AboutUs;
import com.example.codetribe.my_kid.account_Activities.DividerDecorationNav;
import com.example.codetribe.my_kid.account_Activities.LoginActivity;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.account_Activities.ViewProfile;
import com.example.codetribe.my_kid.admin_Activities.AdminTabbedActivity;
import com.example.codetribe.my_kid.groupChat_Activities.GroupChatFragment;
import com.example.codetribe.my_kid.kids_Activities.KidsMemoListFragment;
import com.example.codetribe.my_kid.kids_Activities.KidsViewProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentTabbedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private NavigationView navigationParentView;

    private DatabaseReference parentRef;
    private FirebaseUser pUser;
    private TextView name, creacheName;
    private ImageView profilePic, proprofile;
    private  DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Parent");
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

       drawer = (DrawerLayout) findViewById(R.id.parent_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        pUser = FirebaseAuth.getInstance().getCurrentUser();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        navigationParentView = (NavigationView) findViewById(R.id.nav_parent_view);
        View headerNaming = navigationParentView.getHeaderView(0);
        NavigationMenuView itemsDecorators = (NavigationMenuView) navigationParentView.getChildAt(0);
        itemsDecorators.addItemDecoration(new DividerDecorationNav(this));

        navigationParentView.setNavigationItemSelectedListener(this);

        parentRef = FirebaseDatabase.getInstance().getReference("Users").child(pUser.getUid());
        name = (TextView) headerNaming.findViewById(R.id.adminName);

        creacheName = (TextView) headerNaming.findViewById(R.id.crecheName);

        profilePic = (ImageView) headerNaming.findViewById(R.id.imageProfile);

        proprofile = (ImageView) headerNaming.findViewById(R.id.proprofile);
        proprofile.setImageAlpha(160);


        parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String namesAd = dataSnapshot.child("userName").getValue().toString() + " " + " " + dataSnapshot.child("userSurname").getValue().toString();
                name.setText(namesAd);
                creacheName.setText(dataSnapshot.child("orgName").getValue().toString());

                Glide.with(getApplication()).load(dataSnapshot.child("userProfilePic").getValue().toString()).into(profilePic);

                Glide.with(getApplication()).load(dataSnapshot.child("userProfilePic").getValue().toString()).into(proprofile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Intent intent;
        //noinspection

        switch (id) {
            case R.id.menu_parent_aboutus:
                intent = new Intent(ParentTabbedActivity.this, AboutUs.class);
                startActivity(intent);

                drawer.closeDrawer(GravityCompat.START);
                break;


            case R.id.menu_parent_Logout:
                logout();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.menu_parent_profile:
                intent = new Intent(ParentTabbedActivity.this, ViewProfile.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.menu_kids_profile:
                intent = new Intent(ParentTabbedActivity.this, KidsViewProfile.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

                break;
            default:
                return false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
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
            switch (position) {
                case 0:

                    return new KidsMemoListFragment();
                case 1:
                    return new GroupChatFragment();

                default:
                    return null;
            }

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
                    return "Shared Creatives";
                case 1:
                    return "Group Chat";
                default:
                    return null;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.logout_menu){
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ParentTabbedActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

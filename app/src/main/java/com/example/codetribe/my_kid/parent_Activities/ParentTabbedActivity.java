package com.example.codetribe.my_kid.parent_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.codetribe.my_kid.aboutUs_Activity.AboutUs;
import com.example.codetribe.my_kid.account_Activities.LoginActivity;
import com.example.codetribe.my_kid.R;
import com.example.codetribe.my_kid.account_Activities.ViewProfile;
import com.example.codetribe.my_kid.groupChat_Activities.GroupChatFragment;
import com.example.codetribe.my_kid.kids_Activities.KidsMemoListFragment;
import com.example.codetribe.my_kid.kids_Activities.KidsViewProfile;
import com.google.firebase.auth.FirebaseAuth;

public class ParentTabbedActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_tabbed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parent_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_parent_profile) {
            Intent intent = new Intent(ParentTabbedActivity.this, ViewProfile.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_kids_profile) {
            Intent intent = new Intent(ParentTabbedActivity.this, KidsViewProfile.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_parent_aboutus) {
            Intent intent = new Intent(ParentTabbedActivity.this, AboutUs.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_parent_Logout) {
            logout();
            return true;
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

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ParentTabbedActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
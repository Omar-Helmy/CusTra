package com.hatly.hatly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {


    private HomeFragment homeFragment = new HomeFragment();
    private ExploreFragment exploreFragment = new ExploreFragment();
    private FrPagerAdapter frPagerAdapter;
    private FloatingActionButton fab;
    private Context context = this;

    private ViewPager viewPager;
    // icons:
    private final static int HOME_ICON_L = R.drawable.ic_home_white_36dp;
    private final static int TRAVEL_ICON_L = R.drawable.ic_card_travel_white_36dp;
    private final static int HOME_ICON_S = R.drawable.ic_home_white_24dp;
    private final static int TRAVEL_ICON_S = R.drawable.ic_card_travel_white_24dp;


    // tabs:
    private TabLayout.Tab homeTab, exploreTab;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(context);
        exploreFragment.setContext(getApplicationContext());

        fab = (FloatingActionButton) findViewById(R.id.fab);

        // tool bar:
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // tab layout:
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        // add tabs with icons
        homeTab = tabLayout.newTab().setIcon(HOME_ICON_L);
        exploreTab = tabLayout.newTab().setIcon(TRAVEL_ICON_S);
        tabLayout.addTab(homeTab);
        tabLayout.addTab(exploreTab);

        // find layout viewPager:
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        // attach adapter to the viewpager:
        frPagerAdapter = new FrPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(frPagerAdapter);
        // Create a tab listener that is called when the user changes tabs.
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                switch (position) {
                    case 0: {
                        homeTab.setIcon(HOME_ICON_L);
                        exploreTab.setIcon(TRAVEL_ICON_S);
                        break;
                    }
                    case 1: {
                        homeTab.setIcon(HOME_ICON_S);
                        exploreTab.setIcon(TRAVEL_ICON_L);
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // listener on FAB:
        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,PostRequestActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /////////////////////////////////FragmentPagerAdapter/////////////////////////////////
    private class FrPagerAdapter extends FragmentPagerAdapter {

        public FrPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0: {
                    return homeFragment;
                }
                case 1: {
                    return exploreFragment;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}

/*
https://icons8.com/web-app/for/androidL/travel
https://www.google.com/design/spec/style/icons.html
 */
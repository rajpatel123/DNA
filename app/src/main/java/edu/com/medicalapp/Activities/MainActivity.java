package edu.com.medicalapp.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import edu.com.medicalapp.R;
import edu.com.medicalapp.fragment.HomeFragment;
import edu.com.medicalapp.fragment.OnlineFragment;
import edu.com.medicalapp.fragment.QbankFragment;
import edu.com.medicalapp.fragment.TextFragment;
import edu.com.medicalapp.fragment.videoFragment;
import edu.com.medicalapp.interfaces.FragmentLifecycle;
import edu.com.medicalapp.utils.ImageUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public LinearLayout tabBar;
    public TabLayout tabLayout;

    public ViewPager pager;
    private HomeFragment dashboardHomeFragment;
    private videoFragment dashboardvideoFragment;
    private QbankFragment dashboardQbankFragment;
    private TextFragment dashboardTextFragment;
    private OnlineFragment dashboardOnlineFragment;
    private ViewPagerAdapter adapter;
    private TextView myDeviceTitle;
    private ImageView imgDeviceIcon;
    private TextView videoText;
    private ImageView imgVideoViewIcon;
    private TextView qbTitle;
    private ImageView imgQBIcon;
    private TextView testTitle;
    private ImageView testIcon;
    private ImageView imgOnlineIcon;
    private TextView onlineTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pager = findViewById(R.id.vp_pages);
        tabBar = findViewById(R.id.tabBar);
        tabLayout = findViewById(R.id.tabs);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpFragments();


    }


    private void setUpFragments() {
        setupViewPager(pager);
        tabLayout.setupWithViewPager(pager);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        dashboardHomeFragment = new HomeFragment();
        dashboardvideoFragment = new videoFragment();
        dashboardQbankFragment = new QbankFragment();
        dashboardTextFragment = new TextFragment();
        dashboardOnlineFragment = new OnlineFragment();

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(dashboardHomeFragment, "Home");
        adapter.addFragment(dashboardvideoFragment, "Video");
        adapter.addFragment(dashboardQbankFragment, "Q Bank");
        adapter.addFragment(dashboardTextFragment, "Test");
        adapter.addFragment(dashboardOnlineFragment, "Online");

        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(pageChangeListener);
        int limit = (adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        pager.setOffscreenPageLimit(limit);
    }

    private void setupTabIcons() {
        @SuppressLint("InflateParams") View deviceTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        myDeviceTitle = deviceTab.findViewById(R.id.tab);
        myDeviceTitle.setText("Home");
        imgDeviceIcon = deviceTab.findViewById(R.id.imgTab);
        ImageUtils.setTintedDrawable(this, R.drawable.homelogo, imgDeviceIcon, R.color.white);

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        if (tab != null) {
            tab.setCustomView(deviceTab);
        }

        @SuppressLint("InflateParams") View mapTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        videoText = mapTab.findViewById(R.id.tab);
        videoText.setText("Video");
        imgVideoViewIcon = mapTab.findViewById(R.id.imgTab);
        ImageUtils.setTintedDrawable(this, R.drawable.cameralogo, imgVideoViewIcon, R.color.white);

        tab = tabLayout.getTabAt(1);

        if (tab != null) {
            tab.setCustomView(mapTab);
        }

        @SuppressLint("InflateParams") View alertTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        qbTitle = alertTab.findViewById(R.id.tab);
        qbTitle.setText("Q Bank");
        imgQBIcon = alertTab.findViewById(R.id.imgTab);
        ImageUtils.setTintedDrawable(this, R.drawable.qm, imgQBIcon, R.color.white);

        tab = tabLayout.getTabAt(2);
        if (tab != null) {
            tab.setCustomView(alertTab);
        }

        @SuppressLint("InflateParams") View recordingTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        testTitle = recordingTab.findViewById(R.id.tab);
        testTitle.setText("Test");
        testIcon = recordingTab.findViewById(R.id.imgTab);
        ImageUtils.setTintedDrawable(this, R.drawable.text, testIcon, R.color.white);
        tab = tabLayout.getTabAt(3);
        if (tab != null) {
            tab.setCustomView(recordingTab);
        }

        @SuppressLint("InflateParams") View accountTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        onlineTitle = accountTab.findViewById(R.id.tab);
        onlineTitle.setText("Online");
        imgOnlineIcon = accountTab.findViewById(R.id.imgTab);
        ImageUtils.setTintedDrawable(this, R.drawable.live, imgOnlineIcon, R.color.white);

        tab = tabLayout.getTabAt(4);
        if (tab != null) {
            tab.setCustomView(accountTab);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.no_more) {
            // Handle the camera action
            Intent intent = new Intent(this, DNAKnowmoreActivity.class);
            startActivity(intent);
        } else if (id == R.id.subscribe) {
            Intent intent = new Intent(this, DNASuscribeActivity.class);
            startActivity(intent);
        } else if (id == R.id.notice_board) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.dna_faculy) {
            Intent intent = new Intent(this, DNAFacultyActivity.class);
            startActivity(intent);

        } else if (id == R.id.faq) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.rate) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.about) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.contact_us) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.report) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        int currentPosition = 0;

        @Override
        public void onPageSelected(int newPosition) {
            FragmentLifecycle fragmentToHide = (FragmentLifecycle) adapter.getItem(currentPosition);
            fragmentToHide.onPauseFragment();

            FragmentLifecycle fragmentToShow = (FragmentLifecycle) adapter.getItem(newPosition);
            fragmentToShow.onResumeFragment();
            //invalidateOptionsMenu();

            currentPosition = newPosition;

        }

        @Override
        public void onPageScrolled(int newPosition, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    };


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

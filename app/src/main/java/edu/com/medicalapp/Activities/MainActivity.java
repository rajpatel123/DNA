package edu.com.medicalapp.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import edu.com.medicalapp.Adapters.ExpandableListAdapter;
import edu.com.medicalapp.R;
import edu.com.medicalapp.fragment.HomeFragment;
import edu.com.medicalapp.fragment.OnlineFragment;
import edu.com.medicalapp.fragment.QbankFragment;
import edu.com.medicalapp.fragment.TextFragment;
import edu.com.medicalapp.fragment.videoFragment;
import edu.com.medicalapp.utils.Constants;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_navigationBar)
    BottomNavigationView bottomNavigationView;
/*
     @BindView(R.id.textview_name)
     TextView textViewName;


     @BindView(R.id.textview_email)
     TextView textViewID;*/

    @BindView(R.id.image_view)
    ImageView imageView;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;

    private TextView textName,textId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
         View header = navigationView.inflateHeaderView(R.layout.header_nav);
         textName = (TextView) header.findViewById(R.id.textview_name);
         textId=(TextView) header.findViewById(R.id.textview_email);
         drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
         mToggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open,R.string.close);
         drawerLayout.addDrawerListener(mToggle);
         mToggle.syncState();



         Bundle bundle=getIntent().getExtras();

         String username=bundle.getString("NAME");
         String Id=bundle.getString("ID");
         textName.setText(username);
         textId.setText(Id);

         String name=getIntent().getStringExtra(Constants.NAME);
         String email=getIntent().getStringExtra(Constants.EMAILID);


         textName.setText(name);
         textId.setText(email);




       // NavigationView navigationView1=(NavigationView) findViewById(R.id.nav_view);

       // Toast.makeText(this,username, Toast.LENGTH_SHORT).show();;\
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigationBar);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fraigment_container,new HomeFragment()).commit();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;
            switch (menuItem.getItemId())
            {
                case R.id.nav_home:
                    selectedFragment=new HomeFragment();
                     break;


                case R.id.nav_video:
                    selectedFragment=new videoFragment();
                    break;


                case R.id.nav_qbank:
                    selectedFragment=new QbankFragment();
                    break;

                case R.id.nav_text:
                    selectedFragment=new TextFragment();
                    break;

                case R.id.nav_online:
                    selectedFragment=new OnlineFragment();
                    break;
            }

           getSupportFragmentManager().beginTransaction().replace(R.id.fraigment_container,
                   selectedFragment).commit();


            return true;



        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        Fragment selectedFragment = null;
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
               selectedFragment=new HomeFragment();
                break;


            case R.id.navigation_video:
               selectedFragment=new videoFragment();
               break;


            case R.id.navigation_text:
                selectedFragment=new TextFragment();
                break;


            case R.id.navigation_qbank:
                selectedFragment=new QbankFragment();
                break;

            case R.id.navigation_online:
             selectedFragment=new OnlineFragment();
                break;

           case R.id.navigation_about:
               selectedFragment=new OnlineFragment();
                break;

            case R.id.navigation_setting:
                selectedFragment=new OnlineFragment();
                break;
        }


              DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
              drawer.closeDrawer(GravityCompat.START);

              getSupportFragmentManager().beginTransaction().replace(R.id.fraigment_container,
                    selectedFragment).commit();







        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}

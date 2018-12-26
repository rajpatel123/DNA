package edu.com.medicalapp.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ExpandableListView;

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

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigationBar)
    BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setSupportActionBar(toolbar);



        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigationBar);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fraigment_container,new HomeFragment()).commit();

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


}

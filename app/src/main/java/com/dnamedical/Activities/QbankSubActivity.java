package com.dnamedical.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dnamedical.Models.newqbankmodule.ChaptersModuleResponse;
import com.dnamedical.Models.newqbankmodule.Module;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.fragment.QbankAllFragment;
import com.dnamedical.fragment.QbankCompletedFragment;
import com.dnamedical.fragment.QbankFreeFragment;
import com.dnamedical.fragment.QbankPausedFragment;
import com.dnamedical.fragment.QbankUnattemptedFragment;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QbankSubActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public String qbankmoduleID;
    public String qbankmoduleName;
    TextView toolbarName;
    private QbankAllFragment qbankAllFragment;
    private QbankPausedFragment qbankPausedFragment;
    private QbankCompletedFragment qbankCompletedFragment;
    private QbankUnattemptedFragment qbankUnattemptedFragment;
    private QbankFreeFragment qbankFreeFragment;

    public List<Module> getqBankAll() {
        return qBankAll;
    }

    public List<Module> getqBankPaused() {
        return qBankPaused;
    }

    public List<Module> getqBankCompleted() {
        return qBankCompleted;
    }

    public List<Module> getqBankUnAttempted() {
        return qBankUnAttempted;
    }

    public List<Module> getqBankFree() {
        return qBankFree;
    }

    public List<Module> qBankAll = new ArrayList<>();
    public List<Module> allMCQS = new ArrayList<>();
    public List<Module> qBankPaused = new ArrayList<>();
    public List<Module> qBankCompleted = new ArrayList<>();
    public List<Module> qBankUnAttempted = new ArrayList<>();
    public List<Module> qBankFree = new ArrayList<>();
    private ChaptersModuleResponse chaptersModuleResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_sub);
        toolbarName = findViewById(R.id.qbank_subcategory_name);

        if (getIntent().hasExtra(Constants.MODULE_ID)) {
            qbankmoduleID = getIntent().getStringExtra(Constants.MODULE_ID);
            qbankmoduleName = getIntent().getStringExtra(Constants.MODULE_NAME);
        }

        viewPager =  findViewById(R.id.qbank_viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.qbank_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();




        toolbarName.setText(qbankmoduleName);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllChapterByModule();


    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabOne.setText("All");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabTwo.setText("Paused");
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabThree.setText("Completed");
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabFour.setText("Unattempted");
        tabLayout.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.vedio_custom_layout, null);
        tabFive.setText("Free");
        tabLayout.getTabAt(4).setCustomView(tabFive);

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        qbankAllFragment= new QbankAllFragment();
        qbankPausedFragment= new QbankPausedFragment();
        qbankCompletedFragment = new QbankCompletedFragment();
        qbankUnattemptedFragment = new QbankUnattemptedFragment();
        qbankFreeFragment= new QbankFreeFragment();


        viewPagerAdapter.addFrag(qbankAllFragment, "All");
        viewPagerAdapter.addFrag(qbankPausedFragment, "Paused");
        viewPagerAdapter.addFrag(qbankCompletedFragment, "Completed");
        viewPagerAdapter.addFrag(qbankUnattemptedFragment, "Unattempted");
        viewPagerAdapter.addFrag(qbankFreeFragment, "Free");
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(viewPagerAdapter);




    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void getAllChapterByModule() {

        qBankAll.clear();
        qBankFree.clear();
        qBankUnAttempted.clear();
        qBankPaused.clear();
        qBankCompleted.clear();

        String userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);

        if (TextUtils.isEmpty(userId)) {
            return;
        }

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
            RequestBody module_id = RequestBody.create(MediaType.parse("text/plain"), qbankmoduleID);

            RestClient.getAllChapterByModuleId(user_id,module_id,  new Callback<ChaptersModuleResponse>() {

                @Override
                public void onResponse(Call<ChaptersModuleResponse> call, Response<ChaptersModuleResponse> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();

                        if (chaptersModuleResponse != null) {
                            chaptersModuleResponse = null;
                        }

                        chaptersModuleResponse = response.body();


                        if (chaptersModuleResponse.getCompleted() != null
                                && chaptersModuleResponse.getCompleted().size() > 0) {
                            qBankCompleted = chaptersModuleResponse.getCompleted();

                        }


                        if (chaptersModuleResponse.getPaused() != null
                                && chaptersModuleResponse.getPaused().size() > 0) {
                            qBankPaused = chaptersModuleResponse.getPaused();

                        }

                        if (chaptersModuleResponse.getUnattempted() != null
                                && chaptersModuleResponse.getUnattempted().size() > 0) {
                            qBankUnAttempted = chaptersModuleResponse.getUnattempted();

                        }


                        if (chaptersModuleResponse.getFree() != null
                                && chaptersModuleResponse.getFree().size() > 0) {
                            qBankFree    = chaptersModuleResponse.getFree();
                        }

                        if (qBankCompleted.size() > 0) {
                            qBankAll.addAll(qBankCompleted);
                        }
                        if (qBankPaused.size() > 0) {
                            qBankAll.addAll(qBankPaused);
                        }
                        if (qBankUnAttempted.size() > 0) {
                            qBankAll.addAll(qBankUnAttempted);
                        }

//                        if (qBankFree.size() > 0) {
//                            qBankAll.addAll(qBankFree);
//                        }

                        updateAllModules();
                    }

                }

                @Override
                public void onFailure(Call<ChaptersModuleResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();

                }
            });
        } else {
            Utils.dismissProgressDialog();
            // Toast.makeText(getActivity(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateAllModules() {

        qbankAllFragment.showQList(qBankAll);
        qbankPausedFragment.showQList(qBankPaused);
        qbankCompletedFragment.showQList(qBankCompleted);
        qbankUnattemptedFragment.showQList(qBankUnAttempted);
        qbankFreeFragment.showQList(qBankFree);



    }


}

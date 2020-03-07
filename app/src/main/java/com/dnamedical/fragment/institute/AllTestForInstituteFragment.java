package com.dnamedical.fragment.institute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.dnamedical.Activities.DNAKnowmoreActivity;
import com.dnamedical.Activities.InstituteTestActivity;
import com.dnamedical.Activities.MainActivity;
import com.dnamedical.Activities.TestStartActivity;
import com.dnamedical.Adapters.TestAdapter;
import com.dnamedical.Models.test.TestQuestionData;
import com.dnamedical.Models.test.testp.Test;
import com.dnamedical.Models.test.testp.TestDataResponse;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.fragment.AllTestFragment;
import com.dnamedical.fragment.DailyTestFragment;
import com.dnamedical.fragment.GrandTestFragment;
import com.dnamedical.fragment.MockTestFragment;
import com.dnamedical.fragment.SubjectWiseTestFragment;
import com.dnamedical.fragment.TestFragment;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AllTestForInstituteFragment extends Fragment{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView subcategory;

    TestDataResponse testDataResponse;


    private List<Test> grandTests = new ArrayList<>();
    private List<Test> dailyTest = new ArrayList<>();
    private List<Test> miniTests = new ArrayList<>();
    private List<Test> subjectTests = new ArrayList<>();
    private List<Test> allTests = new ArrayList<>();

    public InstituteTestActivity mainActivity;
    public String subCatId;
    AllTestFragment allTestFragment;
    DailyTestFragment dailyTestFragment;
    MockTestFragment mockTestFragment;
    GrandTestFragment grandTestFragment;
    SubjectWiseTestFragment subjectWiseTestFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.textfragment, container, false);
        subcategory = view.findViewById(R.id.subcategory_name);
        viewPager = view.findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        getTest();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mainActivity = (InstituteTestActivity) getActivity();
    }

    private void setupTabIcons() {
//        TextView tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.test_custom_layout, null);
//        tabOne.setText("All Test");
//        tabLayout.getTabAt(0).setCustomView(tabOne);


        TextView tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.test_custom_layout, null);
        tabOne.setText("Class Test");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.test_custom_layout, null);
        tabTwo.setText("Grand Test");
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.test_custom_layout, null);
        tabThree.setText("Mock Test");
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.test_custom_layout, null);
        tabFour.setText("SWT");
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }

    private void setupViewPager(ViewPager viewPager) {
        dailyTestFragment = new DailyTestFragment();
        allTestFragment = new AllTestFragment();
        grandTestFragment = new GrandTestFragment();
        mockTestFragment = new MockTestFragment();
        subjectWiseTestFragment = new SubjectWiseTestFragment();

        AllTestForInstituteFragment.ViewPagerAdapter adapter = new AllTestForInstituteFragment.ViewPagerAdapter(getFragmentManager());
        //adapter.addFrag(allTestFragment, "All Test");
        adapter.addFrag(dailyTestFragment, "Class Test");
        adapter.addFrag(grandTestFragment, "Grand Test");
        adapter.addFrag(mockTestFragment, "Mock Test");
        adapter.addFrag(subjectWiseTestFragment, "SWT");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);


    }



    public void updateAllTest() {
        if (allTestFragment == null) {
            return;
        }
        dailyTestFragment.showTest(mainActivity.isDailyTest);
        //allTestFragment.showTest();
        mockTestFragment.showTest();
        grandTestFragment.showTest();
        subjectWiseTestFragment.showTest();

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
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

    public interface DisplayDataInterface {
        public void showVideos();
    }

    private void getTest() {

        String  userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        String  institute_id = getActivity().getIntent().getStringExtra("institute_id");

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());

            RestClient.getAllTestData(userId,institute_id,"",new Callback<TestDataResponse>() {
                @Override
                public void onResponse(Call<TestDataResponse> call, Response<TestDataResponse> response) {
                    if (response.code() == 200) {
                        Utils.dismissProgressDialog();


                        if (testDataResponse != null) {
                            testDataResponse = null;
                        }
                        testDataResponse = response.body();

                        if (testDataResponse.getData() != null
                                && testDataResponse.getData().getTestList() != null
                                && testDataResponse.getData().getTestList().size() > 0
                                && testDataResponse.getData().getTestList().get(0).getList().size() > 0) {
                            dailyTest = testDataResponse.getData().getTestList().get(0).getList();
                            mainActivity.setDailyTest(dailyTest);

                        }

                        if (testDataResponse.getData() != null
                                && testDataResponse.getData().getTestList() != null
                                && testDataResponse.getData().getTestList().size() > 0
                                && testDataResponse.getData().getTestList().get(1).getList().size() > 0) {
                            grandTests = testDataResponse.getData().getTestList().get(1).getList();
                            mainActivity.setGrandTests(grandTests);

                        }

                        if (testDataResponse.getData() != null
                                && testDataResponse.getData().getTestList() != null
                                && testDataResponse.getData().getTestList().size() > 0
                                && testDataResponse.getData().getTestList().get(2).getList().size() > 0) {
                            miniTests = testDataResponse.getData().getTestList().get(2).getList();
                            mainActivity.setMiniTests(miniTests);

                        }

                        if (testDataResponse.getData() != null
                                && testDataResponse.getData().getTestList() != null
                                && testDataResponse.getData().getTestList().size() > 0
                                && testDataResponse.getData().getTestList().get(3).getList().size() > 0) {
                            subjectTests = testDataResponse.getData().getTestList().get(3).getList();
                            mainActivity.setSubjectTests(subjectTests);
                        }


                        if (grandTests.size() > 0) {
                            allTests.addAll(grandTests);
                        }
                        if (miniTests.size() > 0) {
                            allTests.addAll(miniTests);
                        }
                        if (subjectTests.size() > 0) {
                            allTests.addAll(subjectTests);
                        }

                        mainActivity.setAllTests(allTests);
                        updateAllTest();

                    }

                }

                @Override
                public void onFailure(Call<TestDataResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    //Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Utils.dismissProgressDialog();
            // Toast.makeText(getActivity(), "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();
        }

    }


}

package com.dnamedical.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.dnamedical.Activities.MainActivity;
import com.dnamedical.Activities.VideoActivity;
import com.dnamedical.R;
import com.dnamedical.interfaces.FragmentLifecycle;

public class TestFragment extends Fragment implements FragmentLifecycle {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView subcategory;

    MainActivity mainActivity;
    public String subCatId;
    VideoActivity.DisplayDataInterface displayDataInterface;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.textfragment, container, false);
        subcategory = view.findViewById(R.id.subcategory_name);
        return view;

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
    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.test_custom_layout, null);
        tabOne.setText("All Test");
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

        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFrag(new AllTestFragment(), "All Test");
        adapter.addFrag(new GrandTestFragment(), "Grand Test");
        adapter.addFrag(new MockTestFragment(), "Mock Test");
        adapter.addFrag(new SubjectWiseTestFragment(), "SWT");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
    }

    public void setListener(VideoActivity.DisplayDataInterface displayDataInterface) {
        this.displayDataInterface = displayDataInterface;
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {
        viewPager = getView().findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

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


}

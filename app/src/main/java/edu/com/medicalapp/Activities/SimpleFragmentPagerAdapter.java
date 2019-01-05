package edu.com.medicalapp.Activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import edu.com.medicalapp.R;
import edu.com.medicalapp.fragment.OnlineFragment;
import edu.com.medicalapp.fragment.QbankFragment;
import edu.com.medicalapp.fragment.TextFragment;
import edu.com.medicalapp.fragment.videoFragment;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new videoFragment();
        } else if (position == 1) {
            return new videoFragment();
        } else if (position == 2) {
            return new QbankFragment();
        } else if (position == 3) {
            return new TextFragment();
        } else {
            return new OnlineFragment();
        }

    }

    // This determines the number of tabs
    @Override
    public int getCount() {

        return 5;


    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.home);
            case 1:
                return mContext.getString(R.string.video);
            case 2:
                return mContext.getString(R.string.QBank);
            case 3:
                return mContext.getString(R.string.Test);
            case 4:
                return mContext.getString(R.string.Online);
            default:
                return null;
        }


    }

}

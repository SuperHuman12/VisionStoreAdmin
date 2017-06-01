package com.digiclack.visionstoreadmin.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.digiclack.visionstoreadmin.Fragments.navigationActivity.BaseFragment;
import java.util.ArrayList;

/**
 * Created by Zar on 6/1/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<BaseFragment> fragments;
    public PagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

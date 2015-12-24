package com.sriram.user.project1;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    String[] tabNames;

    public ViewPagerAdapter(FragmentManager fr, String[] tabNames) {
        // TODO Auto-generated constructor stub
        super(fr);
        this.tabNames = tabNames;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        Fragment fr = null;
        if (position == 0) {
            fr = new SuperMarketFragment();
        } else if (position == 1) {
            fr = new homeFragment();

        } else if (position == 2) {
            fr = new LifeStyleFragment();
        }


        return fr;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return tabNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // TODO Auto-generated method stub

        return tabNames[position];
    }

}
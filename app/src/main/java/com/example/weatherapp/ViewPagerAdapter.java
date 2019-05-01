package com.example.weatherapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    //initialize Fragment Viewpager for TabLayout
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new Root_Today();
            case 1:
                return new Root_Tomorrow();
            case 2:
                return new Root_SevenDays();
            default:
                return null;
        }
    }

    @Override
    //return the number of the TabItem
    public int getCount() {
        return numOfTabs;
    }
}

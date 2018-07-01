package com.example.suleman.whatsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Awais on 11/19/2017.
 */

public class PagerAdapter2 extends FragmentStatePagerAdapter{

    int noOfTabs;
    boolean frn;

    public PagerAdapter2(FragmentManager fragmentManager, int numberOfTabs){
        super(fragmentManager);
        this.noOfTabs=numberOfTabs;
    }

    public PagerAdapter2(FragmentManager fragmentManager, int numberOfTabs, boolean isAllFriends){
        super(fragmentManager);
        this.noOfTabs=numberOfTabs;
        frn = true;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:

                CONTACTS2 c;

                if(frn){
                    c = new CONTACTS2(true);
                    return c;
                }
                else{
                    c = new CONTACTS2();
                    return c;
                }
                //return c;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}

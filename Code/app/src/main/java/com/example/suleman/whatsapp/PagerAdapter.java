 package com.example.suleman.whatsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

 /**
 * Created by Awais on 11/19/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter{
    int noOfTabs;
     List<chatTab> t;
     boolean i=true;

    public PagerAdapter(FragmentManager fragmentManager, int numberOfTabs){
        super(fragmentManager);
        this.noOfTabs=numberOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Wall chats = null; //suleman
                chats=new Wall();

                //CHATS chats = new CHATS();
                return chats;
            case 1:

                CHATS contacts= null;
                contacts = new CHATS();
                return contacts;
            case 2:
                CONTACTS contacts1= null;
                contacts1 = new CONTACTS();
                return contacts1;


            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}

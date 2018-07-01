package com.example.suleman.whatsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Suleman on 1/3/2018.
 */

public class pagerAdapterDoctor extends FragmentStatePagerAdapter {
    int noOfTabs;
    List<chatTab> t;
    boolean i=true;

    public pagerAdapterDoctor(FragmentManager fragmentManager, int numberOfTabs){
        super(fragmentManager);
        this.noOfTabs=numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 2:
                CHATSNotPatient chats = null; //suleman

                chats=new CHATSNotPatient();


                return chats;
            case 4:
                CONTACTSStudent students = new CONTACTSStudent();
                return students;
            case 1:
                CONTACTSCases students1 = new CONTACTSCases();
                return students1;
            case 3:

                CONTACTSNew students123 = new CONTACTSNew();
                return students123;


            case 0:
                Wall1 contacts1= null;
                contacts1 = new Wall1();
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

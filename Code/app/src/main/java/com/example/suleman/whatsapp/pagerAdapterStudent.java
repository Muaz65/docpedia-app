package com.example.suleman.whatsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Suleman on 1/4/2018.
 */

public class pagerAdapterStudent extends FragmentStatePagerAdapter {
    int noOfTabs;
    List<chatTab> t;
    boolean i=true;

    public pagerAdapterStudent(FragmentManager fragmentManager, int numberOfTabs){
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
            case 1:

                CONTACTSCases contacts= null;
                contacts = new CONTACTSCases();
                return contacts;

            case 3:
                CONTACTSNew students = new CONTACTSNew();
                return students;

            case 4:
                CONTACTSStudent students123 = new CONTACTSStudent();
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

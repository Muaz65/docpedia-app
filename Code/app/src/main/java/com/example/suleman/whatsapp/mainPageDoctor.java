package com.example.suleman.whatsapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Suleman on 1/3/2018.
 */

public class mainPageDoctor extends AppCompatActivity {
    private List<chatTab> obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);


        setContentView(R.layout.activity_main_page);



        TabLayout tabLayout=(TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Q/A"));
        tabLayout.addTab(tabLayout.newTab().setText("Case Studies"));

        tabLayout.addTab(tabLayout.newTab().setText("Chats"));

        tabLayout.addTab(tabLayout.newTab().setText("Doctors"));

        tabLayout.addTab(tabLayout.newTab().setText("Students"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        //yaha se Suleman
        final ViewPager viewPager=(ViewPager)findViewById(R.id.pager);
        final pagerAdapterDoctor pagerAdapter=new pagerAdapterDoctor(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        //viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


   /* @Override
    public void onBackPressed()
    {
       // FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
       // fragmentTransaction.replace(R.id.containerView,new Recommendation()).commit();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //MenuInflater inflater=getMenuInflater();
        //inflater.inflate(R.menu.menu,menu);
        //return super.onCreateOptionsMenu(menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == android.R.id.home){
            finish();
            return super.onOptionsItemSelected(item);
        }

        switch (item.getItemId()){
            case R.id.refresh:
                Toast.makeText(mainPageDoctor.this, "Refreshed" , Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                Toast.makeText(mainPageDoctor.this, "Logout" ,Toast.LENGTH_SHORT).show();
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

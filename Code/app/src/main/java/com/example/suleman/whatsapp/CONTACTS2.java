package com.example.suleman.whatsapp;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class CONTACTS2 extends Fragment implements ContactAdapter.ViewHolder.ClickListener, ContactAdapter2.ViewHolder.ClickListener{
    private RecyclerView mRecyclerView;
    private ContactAdapter mAdapter;
    private ContactAdapter2 mAdapter2;
    private String queryy = "";
    private boolean frs = false;
    private List<String>d1=new ArrayList<>();
    private List<String>userName=new ArrayList<>();
    private List<Contact> obj1=new ArrayList<>();
    List<Contact> data = new ArrayList<>();
    private String cId;

    public CONTACTS2(){
        setHasOptionsMenu(true);
        getData1();
    }

    public CONTACTS2(boolean f){
        setHasOptionsMenu(true);
        frs = true;
    }

    public void onCreate(Bundle a){
        super.onCreate(a);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Button b;
        View view;

        if(frs){
            view = inflater.inflate(R.layout.fragment_contacts3, null, false);
            b = (Button)getActivity().findViewById(R.id.butt1);

//            b.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //if(mAdapter)
//                }
//            });
        }
        else{
            view = inflater.inflate(R.layout.fragment_contacts2, null, false);
        }

        getActivity().supportInvalidateOptionsMenu();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if(frs){
            mAdapter2 = new ContactAdapter2(getContext(), setNewData(), this);
            mRecyclerView.setAdapter (mAdapter2);
        }
        else{
            mAdapter = new ContactAdapter(getContext(), data, this);
            mRecyclerView.setAdapter (mAdapter);
            //  mAdapter.notifyDataSetChanged();
        }

        return view;
    }

    void add(String a)
    {
        d1.add(a);
        Toast.makeText(getContext(),""+d1.size(),Toast.LENGTH_SHORT).show();
        setData();
    }


    public void getData1()
    {
        //List<String> obj=new ArrayList<>();
        //Toast.makeText(getContext(),"in contact",Toast.LENGTH_SHORT).show();
        FirebaseDatabase.getInstance().getReference("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String test;
                test=dataSnapshot.child("phone").getValue(String.class);
                cId=dataSnapshot.getKey();
                add(test);
                //obj.add(test);
                // Toast.makeText(getContext(),""+obj.size(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //  return obj;

    }

    public ArrayList<Contact> setNewData(){

        ArrayList data1 = new ArrayList<Contact>();

        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);

        while (phones.moveToNext())
        {
            String namec = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumberc = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Contact contact = new Contact();
            contact.setName(namec);
            contact.setnumber(phoneNumberc);
            contact.setImage(R.drawable.userpic);

            data1.add(contact);
        }

        phones.close();

        return data1;
    }

    public void setData(){

        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);

        while (phones.moveToNext())
        {
            String namec = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumberc = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            if(frs){
                for (int i = 0; i < d1.size(); i++) {
                    if (namec.contains(queryy) && !(phoneNumberc.equals(d1.get(i)) || phoneNumberc.equals("+92" + d1.get(i).substring(1)))) {
                        Contact contact = new Contact();
                        contact.setName(namec);
                        contact.setnumber(phoneNumberc);
                        contact.setImage(R.drawable.userpic);


                        data.add(contact);
                    }
                }
            }
            else{
                for (int i = 0; i < d1.size(); i++) {
                    if (data.contains(queryy) && (phoneNumberc.equals(d1.get(i)) || phoneNumberc.equals("+92" + d1.get(i).substring(1)))) {
                        Contact contact = new Contact();
                        contact.setName(namec);
                        userName.add(namec);
                        contact.setnumber(phoneNumberc);
                        contact.setImage(R.drawable.userpic);
                        contact.setCid(cId);


                        data.add(contact);
                        mAdapter.notifyDataSetChanged();
                        mAdapter2.notifyDataSetChanged();
                    }
                }
            }

        }

        cId=null;
        d1.clear();
        phones.close();
        // mAdapter.notifyDataSetChanged();

        // return data;
    }

    @Override
    public void onItemClicked (int position) {


        Intent intent = new Intent("com.example.suleman.whatsapp.Conversation");

        if(!frs){
            intent.putExtra("object",mAdapter.getValue(position));
            startActivity(intent);
            getActivity().finish();
        }
        else{

        }
    }

    @Override
    public boolean onItemLongClicked (int position) {
        toggleSelection(position);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onBackPressed() {

    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection (position);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.menu_add2, menu);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        MenuItem m = (MenuItem) menu.findItem(R.id.search3);
        MenuItem k = (MenuItem) menu.findItem(R.id.rfr);

        k.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(frs){
                    mAdapter2 = new ContactAdapter2(getContext(), data, (ContactAdapter2.ViewHolder.ClickListener)CONTACTS2.this);
                    mRecyclerView.setAdapter (mAdapter2);
                }
                else{
                    mAdapter = new ContactAdapter(getContext(), data, CONTACTS2.this);
                    mRecyclerView.setAdapter (mAdapter);
                }

                return true;
            }
        });

        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) m.getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                        return false;
                }

                setData();

                queryy = query;

                if(frs){
                    mAdapter2 = new ContactAdapter2(getContext(), data, (ContactAdapter2.ViewHolder.ClickListener)CONTACTS2.this);
                    mRecyclerView.setAdapter (mAdapter2);
                }
                else{
                    mAdapter = new ContactAdapter(getContext(), data, CONTACTS2.this);
                    mRecyclerView.setAdapter (mAdapter);
                }

                queryy = "";

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                        return false;
                }

                setData();

                if(frs){
                    if(newText != null){
                        queryy = newText;

                        mAdapter2 = new ContactAdapter2(getContext(), data ,(ContactAdapter2.ViewHolder.ClickListener)CONTACTS2.this);
                        mRecyclerView.setAdapter (mAdapter2);

                        queryy = "";
                    }
                    else{
                        queryy = "";
                        mAdapter2 = new ContactAdapter2(getContext(),data, (ContactAdapter2.ViewHolder.ClickListener)CONTACTS2.this);
                        mRecyclerView.setAdapter (mAdapter2);
                    }
                }
                else{
                    if(newText != null){
                        queryy = newText;

                        mAdapter = new ContactAdapter(getContext(), data ,CONTACTS2.this);
                        mRecyclerView.setAdapter (mAdapter);

                        queryy = "";
                    }
                    else{
                        queryy = "";
                        mAdapter = new ContactAdapter(getContext(),data,CONTACTS2.this);
                        mRecyclerView.setAdapter (mAdapter);
                    }
                }

                return true;
            }
        });
    }
}

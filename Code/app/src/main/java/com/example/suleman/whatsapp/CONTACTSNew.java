package com.example.suleman.whatsapp;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suleman on 1/6/2018.
 */

public class CONTACTSNew extends Fragment implements ContactAdapter.ViewHolder.ClickListener{
    private RecyclerView mRecyclerView;
    private ContactAdapter mAdapter;
    private String queryy = "";
    private List<String> d1=new ArrayList<>();
    private List<String>userName=new ArrayList<>();
    private String cid;
    List<Contact> data = new ArrayList<>();
    private  String userId;
    FloatingActionButton fab;
    LinearLayout confirm;
    //  private CHATS.OnFragmentInteractionListener mListener;
    Button cancel, ok;
    KamChalau chal;

    public CONTACTSNew(){
        //setHasOptionsMenu(true);
        getData1();

        Log.e("testing contacts : ", "test partially successful");

    }
    public void onCreate(Bundle a){
        super.onCreate(a);
        //setHasOptionsMenu(true);
        //FirebaseApp.initializeApp(this);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, null, false);
        confirm = (LinearLayout) view.findViewById(R.id.confirm);
        ok = (Button) view.findViewById(R.id.btn_ok);
        cancel = (Button) view.findViewById(R.id.btn_cancel);
        /*
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);


        if(fab != null){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                    startActivity(i);
                }
            });
        }
*/
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                confirm.setVisibility(View.INVISIBLE);
            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                confirm.setVisibility(View.INVISIBLE);
                Intent i=new Intent(getActivity(), ConversationWithoutPause.class);
                //Contact temp = mAdapter.getValue(position);

                //KamChalau chal = new KamChalau();
                //chal.name = temp.getName();
                //chal.cid = temp.getCid();


                i.putExtra("object",chal);
                startActivity(i);




            }
        });

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        getActivity().supportInvalidateOptionsMenu();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
            }
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ContactAdapter(getContext(),data,this);
        mRecyclerView.setAdapter (mAdapter);

        return view;
    }
    void add(String a)
    {
        d1.add(a);
        setData();
    }

    public void getData1()
    {
        FirebaseDatabase.getInstance().getReference("Doctors").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String test;
                test=dataSnapshot.child("name").getValue(String.class);

                cid=dataSnapshot.getKey();
                if(!cid.equalsIgnoreCase(userId))
                {
                    add(test);
                }

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


    public void setData()
    {



        //getData1();

        // Toast.makeText(getContext(),""+d1.size(),Toast.LENGTH_SHORT).show();

        String number[]= new String[]{"0313 4303055", "0322 4695254", "+92 332 8621146", "0322 4690991", "+92 313 4187778", "0322 4042476", "+92 334 4344205"};
        String name[]= new String[]{"Awais Jamil", "Haziq Farooq", "Sheikh Daniyal", "Hamza Imran", "Ahmed Saleem", "Aurangzeb Rathore", "Azed Raja"};
        @DrawableRes int img[]= {R.drawable.userpic , R.drawable.user1, R.drawable.user1, R.drawable.user1, R.drawable.user1, R.drawable.user1, R.drawable.user1};
        //  final String test;


        //Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        // Toast.makeText(getContext(),""+d1.size(),Toast.LENGTH_SHORT).show();
        //while (phones.moveToNext()) {
        //   String namec = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        //   String phoneNumberc = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

        for (int i = 0; i < d1.size(); i++) {
            //if (/*name[i].contains(queryy) &&*/ (phoneNumberc.equals(d1.get(i)) || phoneNumberc.equals("+92" + d1.get(i).substring(1)))) {
            Contact contact = new Contact();
            contact.setName(d1.get(i));//namec);
            userName.add(d1.get(i));//namec);
            //contact.setnumber(phoneNumberc);
            contact.setImage(R.drawable.user1);
            contact.setCid(cid);

            Log.e("CID", cid+"");
            data.add(contact);
            mAdapter.notifyDataSetChanged();
            //}
        }

//            }
        cid=null;
        d1.clear();

        //          phones.close();

        //return data;
    }
    public List<Contact> setData2()
    {
        List <Contact> data1=new ArrayList<>();

        for (int i = 0; i < userName.size(); i++) {
            int comp= queryy.compareToIgnoreCase(userName.get(i).toString());
            if (userName.get(i).toLowerCase().contains(queryy.toLowerCase()) )

                data1.add(data.get(i));
        }

        return data1;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onItemClicked (int position) {


//        confirm.setVisibility(View.VISIBLE);
        //Intent intent = new Intent("com.example.suleman.whatsapp.contactDetail");

        Intent i=new Intent(getActivity(), ConversationWithoutPause.class);
        Contact temp = mAdapter.getValue(position);

        chal = new KamChalau();
        chal.name = temp.getName();
        chal.cid = temp.getCid();


        i.putExtra("object",chal);
        startActivity(i);



        // finish();
        //startActivity(intent);

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

    private void toggleSelection(int position) {
        mAdapter.toggleSelection (position);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        /*menu.clear();
        inflater.inflate(R.menu.menu_add, menu);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        MenuItem  m = (MenuItem) menu.findItem(R.id.search2);

        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) m.getActionView();;
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                        return false;
                }

                queryy = query;
                int a=1;
                mAdapter = new ContactAdapter(getContext(),setData2(),CONTACTS.this);
                mRecyclerView.setAdapter (mAdapter);

                queryy = "";

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                int a=1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                        return false;
                }

                if(newText != null){
                    queryy = newText;

                    mAdapter = new ContactAdapter(getContext(),setData2(),CONTACTS.this);
                    mRecyclerView.setAdapter (mAdapter);

                    queryy = "";
                }
                else{
                    queryy = "";
                    mAdapter = new ContactAdapter(getContext(),data,CONTACTS.this);
                    mRecyclerView.setAdapter (mAdapter);
                }

                return true;
            }
        });
        */
    }

    public boolean onOptionsItemSelected(MenuItem item){
        /*if(item.getItemId() == R.id.add_contact){
            Intent intent = new Intent("com.example.awais.test.SetupPhoneNumber");
            startActivity(intent);
        }*/

        return super.onOptionsItemSelected(item);
    }


}

package com.example.suleman.whatsapp;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CHATS extends Fragment implements ChatAdapter.ViewHolder.ClickListener{
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private TextView tv_selection;
    private String queryy = "";
    private List<chatTab> obj=new ArrayList<>();
    private List<Chat> obj1=new ArrayList<>();
    List<Chat> data = new ArrayList<>();
    private String chatId;
    private  String userId;
    FloatingActionButton fab;

    public CHATS(){
        setHasOptionsMenu(true);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

        getData2();
        //Toast.makeText(getActivity(),"IN Chats",Toast.LENGTH_SHORT).show();
    }
   // public void setList(List<chatTab> o)
    //{
       // obj=o;
    //

    public void onCreate(Bundle a){
        super.onCreate(a);
        setHasOptionsMenu(true);

        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null, false);

       // getActivity().supportInvalidateOptionsMenu();
        //((MainActivity)getActivity()).changeTitle(R.id.toolbar, "Messages");

        //FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);


/*
        if(fab != null){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), SelectContact.class);
                    startActivity(i);
                }
            });
        }
*/

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
            if (getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }
        }
        tv_selection = (TextView) view.findViewById(R.id.tv_selection);

        tv_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //obj1=obj;
        mAdapter = new ChatAdapter(getContext(),data,this);
        //Toast.makeText(getContext(),"in chat",Toast.LENGTH_SHORT).show();
        mRecyclerView.setAdapter (mAdapter);
        //mAdapter.notifyDataSetChanged();


        return view;
    }

    private void check() {
        Toast.makeText(getContext(),""+obj1.size(),Toast.LENGTH_SHORT).show();
    }

    void add1(chatTab o)
    {

        obj.add(o);
        setData();
    }


    public void getData2()
    {
        //List<String> obj=new ArrayList<>();
        //Toast.makeText(getContext(),"in chat1",Toast.LENGTH_SHORT).show();
        //qUaq5cLEBCRFik5dtnfN6qaQdCo4
            FirebaseDatabase.getInstance().getReference("ChatTab").child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.exists()) {
                    chatTab c;
                    // for(int i=0;i<dataSnapshot.getChildrenCount();i++) {
                    c = dataSnapshot.getValue(chatTab.class);
                    chatId = dataSnapshot.getKey();


                    add1(c);
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

    }



    public void setData(){
       // List<Chat> data = new ArrayList<>();
        //mAdapter = new ChatAdapter(getContext(),setData(),this);
        String name[]= {"Laura Owens", "Angela Price", "Donald Turner", "Kelly", "Julia Harris", "Laura Owens", "Angela Price", "Donald Turner", "Kelly", "Julia Harris" };
        String lastchat[]= {"Hi This is Fast", "Let the game begin", "Is this SMD Project?", "Ow this awesome", "Ragra Days?", "Alpha,Beta,Gemma", "How are you?", "Ow this awesome", "Pindi Boys?", "I  need your helf" };
        @DrawableRes int img[]= {R.drawable.userpic , R.drawable.user1, R.drawable.user2, R.drawable.user3, R.drawable.user4 , R.drawable.userpic , R.drawable.user1, R.drawable.user2, R.drawable.user3, R.drawable.user4 };
        boolean online[] = {true, false, true, false, true, true, true, false, false, true};

        //Toast.makeText(getContext(),""+obj.size(),Toast.LENGTH_SHORT).show();
        //for (int i = 0; i<obj.size(); i++){

            //if(name[i].contains(queryy)){
            Chat chat = new Chat();
            chat.setmTime(obj.get(0).getTime());
            chat.setName(obj.get(0).getName());
            chat.setImage(R.drawable.user1);
            chat.setOnline(true);
            chat.setLastChat(obj.get(0).getLastmessage());
            chat.setChatId(chatId);
            data.add(chat);
            mAdapter.notifyDataSetChanged();
            obj.clear();
            chatId=null;

    }


    @Override
    public void onItemClicked (int position) {
        try{
            if(ChatAdapter.isPicClick())
                startActivity(new Intent(getActivity(), contactDetail.class));
            else {
                Intent i=new Intent(getActivity(), ConversationWOInput.class);
                Chat temp = mAdapter.getValue(position);

                KamChalau chal = new KamChalau();
                chal.name = temp.getName();
                chal.cid = temp.getChatId();

                i.putExtra("object", chal);
                startActivity(i);
            }
        }
        catch (Exception e){
            //Toast.makeText(CHATS.this,"Exception in intent",Toast.LENGTH_SHORT).show();
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

    private void toggleSelection(int position) {
        mAdapter.toggleSelection (position);
        if (mAdapter.getSelectedItemCount()>0){
            tv_selection.setVisibility(View.VISIBLE);
        }else
            tv_selection.setVisibility(View.GONE);


        getActivity().runOnUiThread(new Runnable() {
            public void run()
            {
                tv_selection.setText("Delete ("+mAdapter.getSelectedItemCount()+")");
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.nav_settings:
                Intent intent = new Intent("com.example.suleman.whatsapp.setting");
                startActivity(intent);
                return true;
            case R.id.nav_logout:
                Toast.makeText(getContext(),"Logout",Toast.LENGTH_SHORT).show();
                //finishAffinity();
                return true;
             default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        /*menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);


        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        MenuItem  m = (MenuItem) menu.findItem(R.id.search);

        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) m.getActionView();;
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                queryy = query;
               // mAdapter = new ChatAdapter(getContext(),setData(),CHATS.this);
               // mRecyclerView.setAdapter (mAdapter);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText != null){
                    queryy = newText;

                    //mAdapter = new ChatAdapter(getContext(),setData(),CHATS.this);
                   // mRecyclerView.setAdapter (mAdapter);

                    queryy = "";
                }
                else{


                    queryy = "";
                   // mAdapter = new ChatAdapter(getContext(),setData(),CHATS.this);
                   // mRecyclerView.setAdapter (mAdapter);

                    queryy = "";
                }

                return true;

                return false;
            }
        });*/
//        return false;
    }
}

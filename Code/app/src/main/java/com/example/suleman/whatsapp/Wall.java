package com.example.suleman.whatsapp;


import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;



import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;




import android.*;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suleman on 12/31/2017.
 */

public class Wall extends Fragment implements newMoviesAdapter.MoviesAdapterListener{


    private DatabaseReference mRootRef;
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private newMoviesAdapter moAdapter;
    FloatingActionButton myFab;
    private String queryy = "";
    private SearchView searchView;
    EditText titleET;
    EditText genreET;
Button ask, submit;



    public Wall(){
        mRootRef  = FirebaseDatabase.getInstance().getReference().getRoot();
    }
    public void onCreate(Bundle a){
        super.onCreate(a);
        setHasOptionsMenu(true);
    }



    private void prepareMovieData() {
        Movie movie = new Movie("Pain in Elbow", "Doctors, I am having too much pain in my elbow after i fell down.", "12/09/2015");
        movieList.add(movie);

        movie = new Movie("Endless Fever", "Plz tell me what to do ? I am suffering from fever for last 2 months,, plz help", "03/09/2016");
        movieList.add(movie);

        movie = new Movie("Healthy Diet", "Plz suggest some healthy diet plan for an aged person. I am also suffering from diabetes", "03/09/2015");
        movieList.add(movie);

        movie = new Movie("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new Movie("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new Movie("The LEGO Movie", "Animation", "2014");
        movieList.add(movie);

        movie = new Movie("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new Movie("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new Movie("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new Movie("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new Movie("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        moAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // suleman
        //mRootRef=FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.fragment_wall, null, false);
        genreET = (EditText) view.findViewById(R.id.rating_rating_bar);
        titleET = (EditText) view.findViewById(R.id.etTitle);
        ask = (Button) view.findViewById(R.id.ask);
        submit = (Button) view.findViewById(R.id.btn_ok);
        final LinearLayout askingLayout  = (LinearLayout) view.findViewById(R.id.confirm);


        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askingLayout.setVisibility(View.VISIBLE);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askingLayout.setVisibility(View.INVISIBLE);

                String titleString = titleET.getText().toString()+"";
                String genreString = genreET.getText().toString()+"";
                titleET.setText("");
                genreET.setText("");


                if(!(titleString.equals("") || genreString.equals("")))
                {
                    //

                  String curr_user_ref = "posts";
                  //String chat_user_ref = "messages/" + chatUser.getChatId() + "/" + user;

                    DatabaseReference user_message_push = mRootRef.child("posts").push();

                    String push_id = user_message_push.getKey();
                    // curr=df.format(c.getTime());

                    Map messageMap = new HashMap();
                    messageMap.put("title", titleString);
                    messageMap.put("genre", genreString);


                   Map messageUserMap = new HashMap();
                   messageUserMap.put(curr_user_ref + "/" + push_id, messageMap);
                    //messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);
                    //text.setText("");


                    mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Log.d("Chat_log", databaseError.getMessage().toString());
                            }
                        }
                    });




                    //
                }


            }
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        //RecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        moAdapter = new newMoviesAdapter(getActivity(),movieList, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(moAdapter);


//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        /*mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ContactAdapter(getContext(),data,this);
        mRecyclerView.setAdapter (mAdapter);
*/



        myFab = (FloatingActionButton) view.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });


//        logout = (Button) view.findViewById(R.id.fab);

       prepareMovieData();

        //recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //moAdapter = new newMoviesAdapter(getContext(),movieList,this);
        //recyclerView.setAdapter (moAdapter);


/*        moAdapter = new newMoviesAdapter(getActivity(),movieList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());  //getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, ));


        recyclerView.setAdapter(moAdapter);
*/

        //
         loadMessage();
        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });*/

        return view;
    }






    private void toggleSelection(int position) {
        //mAdapter.toggleSelection (position);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                moAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                moAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                moAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                moAdapter.getFilter().filter(query);
                return false;
            }
        });


/*
        menu.clear();
        inflater.inflate(R.menu.menu_add, menu);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        MenuItem m = (MenuItem) menu.findItem(R.id.search2);

        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) m.getActionView();;
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override



            public boolean onQueryTextSubmit(String query) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                        return false;
                }

                queryy = query;
                int a=1;
  //              moAdapter = new MoviesAdapter(getContext(),setData2(),Wall.this);
//                RecyclerView.setAdapter(moAdapter);

                queryy = "";

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                int a=1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                        return false;
                }

                if(newText != null){
                    queryy = newText;

                //    mAdapter = new ContactAdapter(getContext(),setData2(),CONTACTS.this);
                 //   mRecyclerView.setAdapter (mAdapter);

                    queryy = "";
                }
                else{
                    queryy = "";
//                    mAdapter = new ContactAdapter(getContext(),data,CONTACTS.this);
  //                  mRecyclerView.setAdapter (mAdapter);
                }

                return true;
            }
        });*/
    }

    public boolean onOptionsItemSelected(MenuItem item){
        /*if(item.getItemId() == R.id.add_contact){
            Intent intent = new Intent("com.example.awais.test.SetupPhoneNumber");
            startActivity(intent);
        }*/

        return super.onOptionsItemSelected(item);
    }









    //is ka baad
/*
    private void sendMessage() {
        if (!end) {
            final String message = text.getText().toString();



        /*Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("h:mm a");
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:00"));
        final String localTime = date.format(currentLocalTime);*/
/*

            if (!TextUtils.isEmpty(message)) {
                // c=Calendar.;
                //SimpleDateFormat df = new SimpleDateFormat("h:mm a");
                String curr_user_ref = "messages/" + user + "/" + chatUser.getChatId();
                String chat_user_ref = "messages/" + chatUser.getChatId() + "/" + user;

                DatabaseReference user_message_push = mRootRef.child("messages")
                        .child(user).child(chatUser.getChatId()).push();

                String push_id = user_message_push.getKey();
                // curr=df.format(c.getTime());

                Map messageMap = new HashMap();
                messageMap.put("message", message);
                messageMap.put("type", "text");
                messageMap.put("time", "5:00");
                messageMap.put("from", user);

                Map messageUserMap = new HashMap();
                messageUserMap.put(curr_user_ref + "/" + push_id, messageMap);
                messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);
                text.setText("");


                mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.d("Chat_log", databaseError.getMessage().toString());
                        }
                    }
                });


                Map chatInfoMap = new HashMap();
                chatInfoMap.put("name", chatUser.getName());
                chatInfoMap.put("time", "5:00");
                chatInfoMap.put("lastmessage", message);

                Map chatUserMap = new HashMap();
                chatUserMap.put("ChatTab/" + user + "/" + chatUser.getChatId(), chatInfoMap);
                chatUserMap.put("ChatTab/" + chatUser.getChatId() + "/" + user, chatInfoMap);

                mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.d("Chat_log", databaseError.getMessage().toString());
                        }
                    }
                });
*/
                // mRootRef.child("ChatTab").child("qUaq5cLEBCRFik5dtnfN6qaQdCo1").keepSynced(true);
/*
            mRootRef.child("ChatTab").child(user).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
*/
/*
            }
        }
    }

*/

    private void loadMessage() {

        //Log.e("ID",""+chatUser.getChatId());
        // mRootRef.child("messages").child("qUaq5cLEBCRFik5dtnfN6qaQdCo1").keepSynced(true);
        FirebaseDatabase.getInstance().getReference("posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Movie message= new Movie();
                //message =    (ChatData)(dataSnapshot.getValue(ChatData.class));

                //ChatData newmessage = ChatData();
                //dataSnapshot.child("message").getValue().toString();

                //Log.e("text1",dataSnapshot.child("message").getValue().toString()+"");
                //Log.e("text1",message.getText()+"?");
                message.setTitle(dataSnapshot.child("title").getValue().toString()+"");
                message.setGenre(dataSnapshot.child("genre").getValue().toString()+"");
                message.setYear("2000");

                //message.setTime(dataSnapshot.child("time").getValue().toString()+"");
                //message.setType(dataSnapshot.child("type").getValue().toString()+"");

                movieList.add(message);
                // Toast.makeText(getBaseContext(),""+message.getText(),Toast.LENGTH_SHORT).show();

                moAdapter.notifyDataSetChanged();
                // Toast.makeText(getBaseContext(),"Gud",Toast.LENGTH_SHORT).show();

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



        /*
        mRootRef.child("posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               Movie message= new Movie();
                //message =    (ChatData)(dataSnapshot.getValue(ChatData.class));

                //ChatData newmessage = ChatData();
                //dataSnapshot.child("message").getValue().toString();

                //Log.e("text1",dataSnapshot.child("message").getValue().toString()+"");
                //Log.e("text1",message.getText()+"?");
                message.setTitle(dataSnapshot.child("title").getValue().toString()+"");
                message.setGenre(dataSnapshot.child("genre").getValue().toString()+"");
                message.setYear("2000");

                //message.setTime(dataSnapshot.child("time").getValue().toString()+"");
                //message.setType(dataSnapshot.child("type").getValue().toString()+"");

                movieList.add(message);
                // Toast.makeText(getBaseContext(),""+message.getText(),Toast.LENGTH_SHORT).show();

                //moAdapter.notifyDataSetChanged();
                // Toast.makeText(getBaseContext(),"Gud",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               Movie message=   new Movie();// dataSnapshot.getValue(ChatData.class);

                message.setTitle(dataSnapshot.child("title").getValue().toString()+"");
                message.setGenre(dataSnapshot.child("genre").getValue().toString()+"");

                //message.setTime(dataSnapshot.child("time").getValue().toString()+"");
                //message.setType(dataSnapshot.child("type").getValue().toString()+"");
                message.setYear("2000");
                //Log.e("text2",message.getText()+"");
                movieList.add(message);
                // Toast.makeText(getBaseContext(),""+message.getText(),Toast.LENGTH_SHORT).show();

//                moAdapter.notifyDataSetChanged();
                // Toast.makeText(getBaseContext(),"Gud",Toast.LENGTH_SHORT).show();



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
        });*/
        //FirebaseDatabase.getInstance().getReference().keepSynced(true);
    }





    @Override
    public void onContactSelected(Movie contact) {

    }
}

package com.example.suleman.whatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suleman on 1/4/2018.
 */

public class CONTACTSCases extends Fragment implements ContactAdapter.ViewHolder.ClickListener{

    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter moAdapter;
    FloatingActionButton fab;




    //  private CHATS.OnFragmentInteractionListener mListener;

    public CONTACTSCases(){
        setHasOptionsMenu(true);
        //getData1();

    }
    public void onCreate(Bundle a){
        super.onCreate(a);
        setHasOptionsMenu(true);
        //FirebaseApp.initializeApp(this);


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

        View view = inflater.inflate(R.layout.fragment_wall1, null, false);


        final LinearLayout askingLayout  = (LinearLayout) view.findViewById(R.id.confirm);
        askingLayout.setVisibility(View.GONE);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        moAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());  //getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, ));


        recyclerView.setAdapter(moAdapter);

        prepareMovieData();


        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });


        return view;
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public void onItemClicked (int position) {


        Intent intent = new Intent("com.example.suleman.whatsapp.contactDetail");
        startActivity(intent);
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
        //mAdapter.toggleSelection (position);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {/* Suleman


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
                mAdapter = new ContactAdapter(getContext(),setData2(),CONTACTS.this);
                mRecyclerView.setAdapter (mAdapter);

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
        });*/
    }

    public boolean onOptionsItemSelected(MenuItem item){
        /*if(item.getItemId() == R.id.add_contact){
            Intent intent = new Intent("com.example.awais.test.SetupPhoneNumber");
            startActivity(intent);
        }*/

        return super.onOptionsItemSelected(item);
    }


}
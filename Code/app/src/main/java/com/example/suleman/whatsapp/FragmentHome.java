package com.example.suleman.whatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class FragmentHome extends Fragment implements ChatAdapter.ViewHolder.ClickListener{
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private TextView tv_selection;
    List<chatTab> obj=new ArrayList<>();
    String userId;



    public FragmentHome(){
        setHasOptionsMenu(true);
       // getData1();

    }
    public void onCreate(Bundle a){
        super.onCreate(a);
        setHasOptionsMenu(true);

        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

           // getData1();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null, false);

        getActivity().supportInvalidateOptionsMenu();
       // ((MainActivity)getActivity()).changeTitle(R.id.toolbar, "Messages");

        tv_selection = (TextView) view.findViewById(R.id.tv_selection);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

       // getData1();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ChatAdapter(getContext(),setData(),this);
        mRecyclerView.setAdapter (mAdapter);

        return view;
    }
    public List<Chat> setData(){
       // getData1();
        List<Chat> data = new ArrayList<>();
        String name[]= {"Laura Owens", "Angela Price", "Donald Turner", "Kelly", "Julia Harris", "Laura Owens", "Angela Price", "Donald Turner", "Kelly", "Julia Harris" };
        String lastchat[]= {"Hi Laura Owens", "Hi there how are you", "Can we meet?", "Ow this awesome", "How are you?", "Ow this awesome", "How are you?", "Ow this awesome", "How are you?", "How are you?" };
        @DrawableRes int img[]= {R.drawable.userpic , R.drawable.user1, R.drawable.user2, R.drawable.user3, R.drawable.user4 , R.drawable.userpic , R.drawable.user1, R.drawable.user2, R.drawable.user3, R.drawable.user4 };
        boolean online[] = {true, false, true, false, true, true, true, false, false, true};

        for (int i = 0; i<obj.size(); i++){
            Chat chat = new Chat();
            chat.setmTime(obj.get(i).getTime());
            chat.setName(obj.get(i).getName());
            chat.setImage(img[i]);
            chat.setOnline(online[i]);
            chat.setLastChat(obj.get(i).getLastmessage());
            data.add(chat);
        }
        return data;
    }

    public void getData1() {
        //List<String> obj=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("ChatTab").child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatTab c;

                c = dataSnapshot.getValue(chatTab.class);
                obj.add(c);
                //add(test);
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
    }


        @Override
    public void onItemClicked (int position) {
        startActivity(new Intent(getActivity(), Conversation.class));
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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);
    }
}

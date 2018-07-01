package com.example.suleman.whatsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by awais
 */

public class ConversationRecyclerView extends SelectableAdapter<RecyclerView.ViewHolder > {

    // The items to display in your RecyclerView
    private List<ChatData> items;
    private Context mContext;
    //private FirebaseAuth curr;
    private HolderMe.ClickListener listener1;
    private HolderYou.ClickListener listener2;
    private String user= FirebaseAuth.getInstance().getCurrentUser().getUid();

    private final int DATE = 0, YOU = 1, ME = 2;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ConversationRecyclerView(Context context, List<ChatData> items,HolderMe.ClickListener listener1,HolderYou.ClickListener listener3) {
        this.mContext = context;
        this.items = items;
        this.listener1=listener1;
        this.listener2=listener3;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {

        //More to come
       /* if (items.get(position).getType().equals(user)) {
            return ME;
        } else
        {
            return YOU;
        }*/
        if (items.get(position).getFrom().equalsIgnoreCase(user))
        {
            return ME;
        }
        else
        {
            return YOU;
        }
       // return -1;




    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case DATE:
                View v1 = inflater.inflate(R.layout.layout_holder_date, viewGroup, false);
                viewHolder = new HolderDate(v1);
                break;
            case YOU:
                View v2 = inflater.inflate(R.layout.layout_holder_you, viewGroup, false);
                viewHolder = new HolderYou(v2,listener2);
                break;
            default:
                View v = inflater.inflate(R.layout.layout_holder_me, viewGroup, false);
                viewHolder = new HolderMe(v,listener1);
                break;
        }
        return viewHolder;
    }
    public void addItem(List<ChatData> item) {
      //  items.addAll(item);
       // notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {



        switch (viewHolder.getItemViewType()) {
            case DATE:
                HolderDate vh1 = (HolderDate) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case YOU:
                HolderYou vh2 = (HolderYou) viewHolder;
                configureViewHolder2(vh2, position);
                break;
            default:
                HolderMe vh = (HolderMe) viewHolder;
                configureViewHolder3(vh, position);
                break;
        }

    }

    private void configureViewHolder3(final HolderMe vh1, final int position) {
            //vh1.getTime().setText(items.get(position).getTime());
        String type=items.get(position).getType();
        if(type.equalsIgnoreCase("text"))
        {
            vh1.getChatText().setText(items.get(position).getText());
            vh1.getImage().setVisibility(View.INVISIBLE);
        }
        else
        {
            vh1.getChatText().setVisibility(View.INVISIBLE);
            vh1.getImage().setVisibility(View.VISIBLE);
            Picasso.with(vh1.getImage().getContext()).load(items.get(position).getText())
                    .into(vh1.getImage());

            /*Picasso.with(vh1.getImage().getContext()).load(items.get(position).getText()).networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.user1).into(vh1.getImage(), new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(vh1.getImage().getContext()).load(items.get(position).getText()).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.user1).into(vh1.getImage());
                }
            });*/
        }
    }

    private void configureViewHolder2(final HolderYou vh1, final int position) {
           // vh1.getTime().setText(items.get(position).getTime());
        String type=items.get(position).getType();
        if(type.equalsIgnoreCase("text"))
        {
            vh1.getChatText().setText(items.get(position).getText());
            vh1.getImage().setVisibility(View.INVISIBLE);
        }
        else
        {
            vh1.getChatText().setVisibility(View.INVISIBLE);
            vh1.getImage().setVisibility(View.VISIBLE);
            Picasso.with(vh1.getImage().getContext()).load(items.get(position).getText())
                    .into(vh1.getImage());

           /* Picasso.with(vh1.getImage().getContext()).load(items.get(position).getText()).networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.user1).into(vh1.getImage(), new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(vh1.getImage().getContext()).load(items.get(position).getText())
                            .placeholder(R.drawable.user1).into(vh1.getImage());
                }
            });*/
        }

    }
    private void configureViewHolder1(HolderDate vh1, int position) {
            vh1.getDate().setText(items.get(position).getText());
    }

}

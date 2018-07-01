package com.example.suleman.whatsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class ChatAdapter extends SelectableAdapter<ChatAdapter.ViewHolder> {

    private List<Chat> mArrayList;
    private Context mContext;
    private ViewHolder.ClickListener clickListener;
    private com.example.suleman.whatsapp.ChatAdapter.ViewHolder v;
    private static boolean isPictureClicked;

    public static void setPicClicked(boolean p){ isPictureClicked = p; }

    public ChatAdapter (Context context, List<Chat> arrayList,ViewHolder.ClickListener clickListener) {
        this.mArrayList = arrayList;
        this.mContext = context;
        this.clickListener = clickListener;

    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_chat, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView,clickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.tvName.setText(mArrayList.get(position).getName());
        if (isSelected(position)) {
            viewHolder.checked.setChecked(true);
            viewHolder.checked.setVisibility(View.VISIBLE);
        }else{
            viewHolder.checked.setChecked(false);
            viewHolder.checked.setVisibility(View.GONE);
        }
        viewHolder.tvTime.setText(""+mArrayList.get(position).getTime());
        viewHolder.userPhoto.setImageResource(mArrayList.get(position).getImage());
        if (mArrayList.get(position).getOnline()){
            viewHolder.onlineView.setVisibility(View.VISIBLE);
        }else
            viewHolder.onlineView.setVisibility(View.INVISIBLE);

        viewHolder.tvLastChat.setText(mArrayList.get(position).getLastChat());

        v = viewHolder;
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }
    public static boolean isPicClick(){ return isPictureClicked; }

    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener  {

        public TextView tvName;
        public TextView tvTime;
        public TextView tvLastChat;
        public ImageView userPhoto;
        public boolean online = false;
        private final View onlineView;
        public CheckBox checked;
        private ClickListener listener;

        public ViewHolder(View itemLayoutView,ClickListener listener) {
            super(itemLayoutView);
            this.listener = listener;

            tvName = (TextView) itemLayoutView.findViewById(R.id.tv_user_name);
            //selectedOverlay = (View) itemView.findViewById(R.id.selected_overlay);
            tvTime = (TextView) itemLayoutView.findViewById(R.id.tv_time);
            tvLastChat = (TextView) itemLayoutView.findViewById(R.id.tv_last_chat);
            userPhoto = (ImageView) itemLayoutView.findViewById(R.id.iv_user_photo);
            userPhoto.setOnClickListener(this);
            onlineView = (View) itemLayoutView.findViewById(R.id.online_indicator);
            checked = (CheckBox) itemLayoutView.findViewById(R.id.chk_list);

            itemLayoutView.setOnClickListener(this);
            itemLayoutView.setOnLongClickListener (this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                try{

                    if(v.getId() == R.id.iv_user_photo)
                        ChatAdapter.setPicClicked(true);
                    else
                        ChatAdapter.setPicClicked(false);

                    listener.onItemClicked(getAdapterPosition ());
                }
                catch (Exception e){

                }
            }
        }

        @Override
        public boolean onLongClick (View view) {
            if (listener != null) {
                return listener.onItemLongClicked(getAdapterPosition ());
            }
            return false;
        }

        public interface ClickListener {
            public void onItemClicked(int position);

            public boolean onItemLongClicked(int position);

            boolean onCreateOptionsMenu(Menu menu);
        }
    }

    public Chat getValue(int position)
    {
         return mArrayList.get(position);
    }
}

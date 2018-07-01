package com.example.suleman.whatsapp;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.uiresource.messenger.R;


public  class HolderYou extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

    private TextView time, chatText;
    private ClickListener listener;
    private ImageView image;

    public HolderYou(View v,ClickListener listener) {
        super(v);
        //time = (TextView) v.findViewById(R.id.tv_time);
        chatText = (TextView) v.findViewById(R.id.tv_chat_text);
        image=(ImageView) v.findViewById(R.id.messImage);
        this.listener=listener;
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public TextView getChatText() {
        return chatText;
    }

    public void setChatText(TextView chatText) {
        this.chatText = chatText;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
                listener.onItemClicked(getAdapterPosition ());
        }

    }

    @Override
    public boolean onLongClick(View view) {

        if (listener != null) {
            return listener.onItemLongClicked(getAdapterPosition ());
        }
        return false;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public interface ClickListener {
        public void onItemClicked(int position);

        public boolean onItemLongClicked(int position);

        boolean onCreateOptionsMenu(Menu menu);
    }
}

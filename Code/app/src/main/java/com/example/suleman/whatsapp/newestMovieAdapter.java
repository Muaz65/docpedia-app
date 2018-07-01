package com.example.suleman.whatsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suleman on 1/7/2018.
 */

public class newestMovieAdapter extends SelectableAdapter<newestMovieAdapter.ViewHolder> implements  Filterable{

private List<Movie> mArrayList;
private List<Movie> filteredArrayList;
    private Context mContext;
private ViewHolder.ClickListener clickListener;
public newestMovieAdapter (Context context, List<Movie> arrayList,ViewHolder.ClickListener clickListener) {
        this.mArrayList = arrayList;
        this.mContext = context;
        this.clickListener = clickListener;
        this.filteredArrayList = new ArrayList<Movie>();
        }

// Create new views
@Override
public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {

        View itemLayoutView;

        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
        R.layout.movie_list_row, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView,clickListener);

        return viewHolder;
        }

@Override
public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.tvName.setText(mArrayList.get(position).getTitle());
        viewHolder.tvExplain.setText(mArrayList.get(position).getGenre());
    // viewHolder.tvNumber.setText(mArrayList.get(position).getNumber());
        //viewHolder.userPhoto.setImageResource(mArrayList.get(position).getImage());
        }

@Override
public int getItemCount() {
        return mArrayList.size();
        }

public Movie getValue(int position)
        {
        return mArrayList.get(position);
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        filteredArrayList = mArrayList;
                    } else {
                        List<Movie> filteredList = new ArrayList<>();
                        for (Movie row : mArrayList) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getGenre().contains(charSequence)) {
                                filteredList.add(row);
                            }
                        }

                        filteredArrayList = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filteredArrayList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    filteredArrayList = (ArrayList<Movie>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }


    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener  {

    public TextView tvName;
    public TextView tvExplain;
    //public ImageView userPhoto;
    private newestMovieAdapter.ViewHolder.ClickListener listener;
    //private final View selectedOverlay;

    public ViewHolder(View itemLayoutView,newestMovieAdapter.ViewHolder.ClickListener listener) {
        super(itemLayoutView);

        this.listener = listener;
        tvName = (TextView) itemLayoutView.findViewById(R.id.title);
        tvExplain= (TextView) itemLayoutView.findViewById(R.id.genre);
        //userPhoto = (ImageView) itemLayoutView.findViewById(R.id.iv_user_photo);

        itemLayoutView.setOnClickListener(this);

        itemLayoutView.setOnLongClickListener (this);
    }



    @Override
    public void onClick(View v) {


        if (listener != null) {
            listener.onItemClicked(getAdapterPosition ());



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
        public void onBackPressed();
    }
}
}

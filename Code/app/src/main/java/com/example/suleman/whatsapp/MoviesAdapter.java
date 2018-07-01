package com.example.suleman.whatsapp;

/**
 * Created by Suleman on 12/3/2017.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movie> moviesList;
    private Context mContext;
    private ContactAdapter.ViewHolder.ClickListener clickListener;


    public MoviesAdapter (Context context, List<Movie> arrayList, ContactAdapter.ViewHolder.ClickListener clickListener) {
        this.moviesList = arrayList;
        this.mContext = context;
        this.clickListener = clickListener;

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public MoviesAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        //holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());

        //extView tv = (TextView) view.findViewById(R.id.tv);
        SpannableString content = new SpannableString(movie.getTitle());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        holder.title.setText(content);


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}

package com.scm.app.marvelapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.MyViewHolder> {
    private Context mContext;
    private Response myDataset;

    public FilmesAdapter(Context context, Response myDataset){
        this.myDataset = myDataset;

        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public CardView view;
        public TextView filmeTitle;
        public MyViewHolder(CardView v){
            super(v);
            view = v;
            filmeTitle = v.findViewById(R.id.filme_title);

        }
    }



    @NonNull
    @Override
    public FilmesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        CardView v = (CardView) LayoutInflater.from(mContext)
                .inflate(R.layout.filme_list,viewGroup,false);
        MyViewHolder vh = new MyViewHolder((CardView) v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.filmeTitle.setText(myDataset.getMovies().get(i).getTitle());
        myViewHolder.filmeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click","clicou no título");
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDataset.getMovies().size();
    }
}

package com.scm.app.marvelapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.MyViewHolder> {
    private Context mContext;
    private String[] myDataset;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        /*public TextView textView;
        public MyViewHolder(TextView v){
            super(v);
            textView = v;
        }*/

        //public View view;
        public CardView view;
        public TextView textView;
        public MyViewHolder(CardView v){
            super(v);
            view = v;
            textView = v.findViewById(R.id.textView);
        }
    }

    public FilmesAdapter(Context context,String[] myDataset){
        this.myDataset = myDataset;
        this.mContext = context;
    }

    @NonNull
    @Override
    public FilmesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        /*TextView v = (TextView) LayoutInflater.from(mContext)
                .inflate(R.layout.filme_list,viewGroup,false);
        MyViewHolder vh = new MyViewHolder((TextView) v);
        return vh;*/

        CardView v = (CardView) LayoutInflater.from(mContext)
                .inflate(R.layout.filme_list,viewGroup,false);
        MyViewHolder vh = new MyViewHolder((CardView) v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //myViewHolder.textView.setText(myDataset[i]);
        myViewHolder.textView.setText(myDataset[i]);
    }


    @Override
    public int getItemCount() {
        return myDataset.length;
    }
}

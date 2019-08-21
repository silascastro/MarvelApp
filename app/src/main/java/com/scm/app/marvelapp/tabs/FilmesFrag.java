package com.scm.app.marvelapp.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scm.app.marvelapp.FilmesAdapter;
import com.scm.app.marvelapp.R;

import java.util.Objects;

public class FilmesFrag extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filmes_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        String[] myDataset = new String[6];
        myDataset[0] = "teste 1";
        myDataset[1] = "teste 2";
        myDataset[2] = "teste 3";
        myDataset[3] = "teste 4";
        myDataset[4] = "teste 5";

        mAdapter = new FilmesAdapter(getActivity(),myDataset);
        //recyclerView.setLayoutManager(new LinearLayoutManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}

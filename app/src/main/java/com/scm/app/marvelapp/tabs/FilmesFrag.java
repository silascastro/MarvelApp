package com.scm.app.marvelapp.tabs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import com.google.gson.Gson;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scm.app.marvelapp.FilmesAdapter;
import com.scm.app.marvelapp.R;
import com.scm.app.marvelapp.Response;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

public class FilmesFrag extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String api_key = "73d1fdaddad5145a868ff28476e74b06";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    URL githubEndPoint = new URL("https://api.themoviedb.org/3/movie/popular?api_key="+api_key);
                    HttpsURLConnection myConnection = (HttpsURLConnection) githubEndPoint.openConnection();
                    myConnection.setRequestProperty("Content-Type","application/json");

                    if(myConnection.getResponseCode()==200){
                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        Gson json = new Gson();
                        Response r = json.fromJson(responseBodyReader,Response.class);
                        for(int e=0;e<r.getMovies().size();e++){
                            Log.d("response "+e,r.getMovies().get(e).getTitle());
                        }
                       // Log.d("response", String.valueOf(r.getMovies().get(0).getTitle()));

                    }else{
                        Log.d("erro","erro ao fazer conexão");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filmes_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        String[] myDataset = new String[6];
        myDataset[0] = "teste 1";
        myDataset[1] = "teste 2";
        myDataset[2] = "teste 3";
        myDataset[3] = "teste 4";
        myDataset[4] = "teste 5";
        myDataset[5] = "teste 6";

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

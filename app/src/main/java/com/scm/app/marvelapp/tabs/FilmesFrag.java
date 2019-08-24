package com.scm.app.marvelapp.tabs;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.google.gson.Gson;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scm.app.marvelapp.FilmesAdapter;
import com.scm.app.marvelapp.MainActivity;
import com.scm.app.marvelapp.Movies;
import com.scm.app.marvelapp.R;
import com.scm.app.marvelapp.Response;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class FilmesFrag extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String api_key = "73d1fdaddad5145a868ff28476e74b06";
    private Response response;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public void getMovies(){
        AsyncTaskClass taskClass = new AsyncTaskClass(response,getContext());
        try {
            Response res = taskClass.execute("teste").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        getMovies();
        if(response != null){
            v = inflater.inflate(R.layout.filmes_layout, container, false);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
            mAdapter = new FilmesAdapter(getActivity(),response);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(mAdapter);

            swipeRefreshLayout = v.findViewById(R.id.swiperefresh);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getMovies();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        else{
            v = inflater.inflate(R.layout.fail, container, false);
        }

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @SuppressLint("StaticFieldLeak")
    public class AsyncTaskClass extends AsyncTask<String, Integer, Response> {
        private String api_key = "73d1fdaddad5145a868ff28476e74b06";
        private Response res;
        private Context context;
        private ProgressDialog load;

        public AsyncTaskClass(Response res, Context context){
            this.res = res;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*load = ProgressDialog.show(getActivity(), "Por favor Aguarde ...",
                    "Baixando Imagem ...");*/

        }

        @Override
        protected Response doInBackground(String... strings) {
            try {
                //API
                URL githubEndPoint = new URL("https://api.themoviedb.org/3/movie/popular?api_key="+api_key);
                HttpsURLConnection myConnection = (HttpsURLConnection) githubEndPoint.openConnection();
                myConnection.setRequestProperty("Content-Type","application/json");

                if(myConnection.getResponseCode()==200){
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    Gson json = new Gson();

                    response =  json.fromJson(responseBodyReader,Response.class);
                    return response;
                }else{

                    //swipeRefreshLayout.setRefreshing(false);
                    Log.d("erro","erro ao fazer conexão");
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                //swipeRefreshLayout.setRefreshing(false);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Response resp) {
            super.onPostExecute(response);
            //load.dismiss();
            //setResponse(resp);

        }
    }

}

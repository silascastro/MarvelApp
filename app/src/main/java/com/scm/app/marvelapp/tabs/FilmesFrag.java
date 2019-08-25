package com.scm.app.marvelapp.tabs;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.google.gson.Gson;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.scm.app.marvelapp.FilmesAdapter;
import com.scm.app.marvelapp.R;
import com.scm.app.marvelapp.Response;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import static com.scm.app.marvelapp.R.drawable.ic_grid;
import static com.scm.app.marvelapp.R.drawable.ic_list;

public class FilmesFrag extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String api_key = "73d1fdaddad5145a868ff28476e74b06";
    private Response response;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int icon = 1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v;



            v = inflater.inflate(R.layout.filmes_layout, container, false);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
            mAdapter = new FilmesAdapter(getActivity());


            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(mAdapter);

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);


        MenuItem item = menu.getItem(1);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                if(icon == 1){
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    item.setIcon(ic_grid);
                    icon = 0;
                }
                if(icon==0){
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    item.setIcon(ic_list);
                    icon = 1;
                }



                //recyclerView.setLayoutManager(new GridLayoutManager(getActivity()));
                return false;
            }
        });



    }

        /*public void getMovies(){
        AsyncTaskClass taskClass = new AsyncTaskClass(response,getContext());
        try {
            Response res = taskClass.execute("teste").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/


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

package com.scm.app.marvelapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.MyViewHolder> {
    private Context mContext;
    private Response myDataset;
    private ArrayList<Movies> movies = new ArrayList<>();

    public FilmesAdapter(Context context){
        this.mContext = context;
        this.getMovies();
    }

    public void getMovies(){
        AsyncTaskClass taskClass = new AsyncTaskClass(myDataset,mContext);
        try {
            taskClass.execute("teste").get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder,final int i) {
        //getMovies();

       // Log.d("response", String.valueOf(movies!=null));

        myViewHolder.filmeTitle.setText(movies.get(i).getTitle());
        myViewHolder.filmeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Item",movies.get(i).getTitle());
                movies.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i,movies.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        /*if(myDataset == null){
            return 0;
        }*/
        //return myDataset.getMovies().size();
        return movies!=null?movies.size():0;

    }

    @SuppressLint("StaticFieldLeak")
    public class AsyncTaskClass extends AsyncTask<String, Integer, ArrayList<Movies>> {
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
        protected ArrayList<Movies> doInBackground(String... strings) {
            try {
                //API
                URL githubEndPoint = new URL("https://api.themoviedb.org/3/movie/popular?api_key="+api_key);
                HttpsURLConnection myConnection = (HttpsURLConnection) githubEndPoint.openConnection();
                myConnection.setRequestProperty("Content-Type","application/json");

                if(myConnection.getResponseCode()==200){
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    Gson json = new Gson();
                    Log.d("asynctask","funcionou");
                    myDataset =  json.fromJson(responseBodyReader,Response.class);
                    //movies.addAll(myDataset.getMovies());
                    for(int i=0;i<myDataset.getMovies().size();i++){
                        movies.add(myDataset.getMovies().get(i));
                        notifyItemInserted(getItemCount());
                    }
                    return movies;
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
        protected void onPostExecute(ArrayList<Movies> response) {
            super.onPostExecute(response);
            notifyItemInserted(getItemCount());
        }
    }

}

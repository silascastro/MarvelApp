package com.scm.app.marvelapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncTaskClass extends AsyncTask<String, Integer, Response> {
    private String api_key = "73d1fdaddad5145a868ff28476e74b06";
    private String title;
    private Context context;

    public AsyncTaskClass(String title, Context context){
        this.title = title;
        this.context = context;
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
                return json.fromJson(responseBodyReader,Response.class);
                //response = new Response(aux.getPage(),aux.getTotal_results(),aux.getTotal_pages(),aux.getMovies());
                 //response;
            }else{
                Log.d("erro","erro ao fazer conexão");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);


    }
}

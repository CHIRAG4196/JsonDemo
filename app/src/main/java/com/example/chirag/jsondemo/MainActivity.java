package com.example.chirag.jsondemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.chirag.jsondemo.model.Geoname;
import com.example.chirag.jsondemo.model.MainResponse;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRvGeo;
    public static final String BASE_URL ="http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo";
    private ArrayList<Geoname> geonameArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvGeo = (RecyclerView) findViewById(R.id.rv_geo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRvGeo.setLayoutManager(layoutManager);

        GeoLoadingTast geoLoadingTast = new GeoLoadingTast();
        geoLoadingTast.execute(BASE_URL);
    }

    public class GeoLoadingTast extends AsyncTask <String , Void, MainResponse>{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this,"Data Loading","");

        }

        @Override
        protected MainResponse doInBackground(String... params) {

            try {
                String json = getJsonData(params[0]);
                Log.e("TEST", "" + json);
                try {
                    return jsonUsingGson(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private MainResponse jsonUsingGson(String json) throws JSONException{
            Gson gson = new Gson();
            MainResponse mainResponse = gson.fromJson(json,MainResponse.class);
            return mainResponse;
        }

        @Override
        protected void onPostExecute(MainResponse mainResponse) {
            super.onPostExecute(mainResponse);
            progressDialog.dismiss();
            geonameArrayList = mainResponse.getGeonames();

            Log.e("array", "onPostExecute: "+geonameArrayList);

        }
    }
    public String getJsonData(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}

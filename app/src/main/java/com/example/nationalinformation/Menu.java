package com.example.nationalinformation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class Menu extends AppCompatActivity{

    private final String path = "http://api.geonames.org/countryInfoJSON?&username=aporter&fbclid=IwAR0j87GHmLFfwEK6Xz6e0AE0JvL0bKzgPpoxc_PNyS0pZw2s9iZmF8x6OIk";
    String responseText;
    StringBuffer response = new StringBuffer();
    URL url;
    Activity activity;
    ArrayList<CountryEntity> countries = new ArrayList<CountryEntity>();
    ConnectivityManager conMgr;
    ListView listView;
    private Button btnSubmit;
    private ProgressDialog progressDialog;
    Context a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        new GetServerData().execute();

        listView.setOnItemClickListener((AdapterView.OnItemClickListener) (a, v, position, id) -> {
            Object o = listView.getItemAtPosition(position);
            CountryEntity country = (CountryEntity) o;
            Intent intent = new Intent(com.example.nationalinformation.Menu.this, Country.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Country", (Serializable) country);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    class GetServerData extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(com.example.nationalinformation.Menu.this);
            progressDialog.setMessage("Fetching country data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                listView.setAdapter(new customListAdapter(com.example.nationalinformation.Menu.this, countries));
            }
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return getWebServiceResponseData();
        }

        protected Void getWebServiceResponseData() {

            try {

                ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(conn.getInputStream()));
                        String output;

                        while ((output = in.readLine()) != null) {
                            response.append(output);
                        }
                        in.close();
                    }
                } else{
                    View view = findViewById(R.id.listView);
                    Snackbar snackbar = Snackbar
                            .make(view, "No internet", Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                    throw new Exception("No internet");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            responseText = response.toString();
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(responseText);
                    JSONArray jsonarray = jsonObject.getJSONArray("geonames");
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String coutryCode = jsonobject.getString("countryCode");
                        String name = jsonobject.getString("countryName");
                        float population = Float.parseFloat(jsonobject.getString("population"));
                        float acreage = Float.parseFloat(jsonobject.getString("areaInSqKm"));
                        CountryEntity countryObj = new CountryEntity(coutryCode, name, population, acreage);
                        countries.add(countryObj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
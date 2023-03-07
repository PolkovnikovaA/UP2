package com.example.up;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Glavnaya extends AppCompatActivity {
    View v;
    private Adapterfeelings FeelingsAdapter;
    private List<Maskfeelings> feelingsMaska = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavnaya);

        getSupportActionBar().hide();

        v = findViewById(com.google.android.material.R.id.ghost_view);

        RecyclerView Nastroenie = findViewById(R.id.List_Horizontal);
        //Nastroenie.setHasFixedSize(true);
        //Nastroenie.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        FeelingsAdapter = new Adapterfeelings(feelingsMaska,Glavnaya.this);
        Nastroenie.setAdapter(FeelingsAdapter);

        new Get_feeling().execute();

    }

    public void onClickListnet(View v) {
        Intent intent = new Intent(this, Listnet.class);
        startActivity(intent);
    }

    public void onClickProfile(View v) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    private class Get_feeling extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/feelings");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                feelingsMaska.clear();
                FeelingsAdapter.notifyDataSetChanged();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject Json = tempArray.getJSONObject(i);
                    Maskfeelings temp = new Maskfeelings(
                            Json.getInt("id"),
                            Json.getString("title"),
                            Json.getString("image"),
                            Json.getInt("position")
                    );
                    feelingsMaska.add(temp);
                    FeelingsAdapter.notifyDataSetChanged();
                }
                //feelingsMaska.sort(Comparator.comparing(Maskfeelings::getPosition));
                //FeelingsAdapter.notifyDataSetChanged();
            }
            catch (Exception exception)
            {
                //Toast.makeText(Glavnaya.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
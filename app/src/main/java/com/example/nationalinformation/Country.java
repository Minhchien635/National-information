package com.example.nationalinformation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class Country extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            CountryEntity country = (CountryEntity) bundle.get("Country");

            String imageUri = "https://flagpedia.net/data/flags/normal/" + country.getCountryCode().toLowerCase() + ".png";
            ImageView ivBasicImage = findViewById(R.id.img);
            Picasso.get().load(imageUri).into(ivBasicImage);

            TextView tvName = findViewById(R.id.name);
            tvName.setText("Country name: " + country.getName());

            TextView tvPopulation = findViewById(R.id.population);
            tvPopulation.setText("Population: " + country.getPopulation() + " ( people )");

            TextView tvAcreage = findViewById(R.id.acreage);
            tvAcreage.setText("Acreage: " + country.getAcreage() + " ( square kilometre )");

        }
    }
}

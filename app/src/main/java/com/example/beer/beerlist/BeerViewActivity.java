package com.example.beer.beerlist;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beer.R;

public class BeerViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_view);

        getBeerName();
    }

    private void getBeerName() {

        String beerName;

        if (getIntent().hasExtra("beer_name")) {

            beerName = getIntent().getStringExtra("beer_name");

            setBeerName(beerName);
        }
    }

    private void setBeerName(String beerName) {

        TextView name = findViewById(R.id.beer_name);
        name.setText(beerName);
    }
}

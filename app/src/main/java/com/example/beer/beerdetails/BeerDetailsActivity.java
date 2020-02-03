package com.example.beer.beerdetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.beer.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BeerDetailsActivity extends AppCompatActivity {

    public String beerImage, beerName, beerTagline, beerBoilVolumeUnit, beerDescription, maltName;
    public Double beerAbv, beerIbu, beerEbc;
    public Integer beerBoilVolumeValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_view);

        getBeerImage();
        getBeerName();
        getBeerTagline();
        getBeerAbv();
        getBeerIbu();
        getBeerEbc();
        getBeerDescription();
        getBeerBoilVolumeValue();
        getBeerBoilVolumeUnit();
        getMaltName();
    }

    public void getBeerImage() {

        if ( getIntent().hasExtra("beer_image")) {

            beerImage = getIntent().getStringExtra("beer_image");

            setBeerImage(beerImage);
        }
    }

    public void setBeerImage(String beerImage) {

        ImageView image = findViewById(R.id.beer_list_image);

        Glide.with(this).load(beerImage).into(image);
    }

    private void getBeerName() {

        if (getIntent().hasExtra("beer_name")) {

            beerName = getIntent().getStringExtra("beer_name");

            setBeerName(beerName);
        }
    }

    private void setBeerName(String beerName) {

        TextView name = findViewById(R.id.beer_list_name);
        name.setText(beerName);
    }

    private void getBeerTagline() {

        if (getIntent().hasExtra("beer_tagline")) {

            beerTagline = getIntent().getStringExtra("beer_tagline");

            setBeerTagline(beerTagline);
        }
    }

    private void setBeerTagline(String beerTagline) {

        TextView tagline = findViewById(R.id.beer_list_tagline);
        tagline.setText(beerTagline);
    }

    private void getBeerAbv() {

        if (getIntent().hasExtra("beer_abv")) {

            beerAbv = getIntent().getDoubleExtra("beer_abv", 0.0);

            setBeerAbv(beerAbv);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setBeerAbv(Double beerAbv) {

        TextView abv = findViewById(R.id.beer_list_abv);
        abv.setText("ABV: " + beerAbv);
    }

    public void getBeerIbu() {

        if (getIntent().hasExtra("beer_ibu")) {

            beerIbu = getIntent().getDoubleExtra("beer_ibu", 0.0);

            setBeerIbu(beerIbu);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setBeerIbu(Double beerIbu) {

        TextView ibu = findViewById(R.id.beer_list_ibu);

        if (beerIbu == 0.0) {
            ibu.setVisibility(View.GONE);
        }else {
            ibu.setText("IBU: " + beerIbu);
        }
    }

    public void getBeerEbc() {

        if (getIntent().hasExtra("beer_ebc")) {

            beerEbc = getIntent().getDoubleExtra("beer_ebc", 0.0);

            setBeerEbc(beerEbc);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setBeerEbc(Double beerEbc) {

        TextView ebc = findViewById(R.id.beer_list_ebc);

        if (beerEbc == 0.0) {
            ebc.setVisibility(View.GONE);
        }else {
            ebc.setText("EBC: " + beerEbc);
        }
    }

    public void getBeerBoilVolumeValue() {

        if (getIntent().hasExtra("beer_boil_volume_value")) {

            beerBoilVolumeValue = getIntent().getIntExtra("beer_boil_volume_value", 0);

            setBeerBoilVolumeValue(beerBoilVolumeValue);
        }
    }

    public void setBeerBoilVolumeValue(Integer beerBoilVolumeValue) {

        TextView value = findViewById(R.id.beer_list_boil_volume_value);
        value.setText("Value: " + beerBoilVolumeValue);
    }

    public void getBeerBoilVolumeUnit() {

        if (getIntent().hasExtra("beer_boil_volume_unit")) {

            beerBoilVolumeUnit = getIntent().getStringExtra("beer_boil_volume_unit");

            setBeerBoilVolumeUnit(beerBoilVolumeUnit);
        }
    }

    public void setBeerBoilVolumeUnit(String beerBoilVolumeUnit) {

        TextView unit = findViewById(R.id.beer_list_boil_volume_unit);
        unit.setText("Unit: " + beerBoilVolumeUnit);
    }

    public void getBeerDescription() {

        if (getIntent().hasExtra("beer_description")) {

            beerDescription = getIntent().getStringExtra("beer_description");

            setBeerDescription(beerDescription);
        }
    }

    public void setBeerDescription(String beerDescription) {

        TextView description = findViewById(R.id.beer_list_description);
        description.setText(beerDescription);
    }

    public void getMaltName() {

        if (getIntent().hasExtra("yeast")) {

            maltName = getIntent().getStringExtra("yeast");

            setMaltName("Malt name: " + maltName);
        }
    }

    public void setMaltName(String maltName) {

        TextView name = findViewById(R.id.beer_list_malt_name);
        name.setText(maltName);
    }
}

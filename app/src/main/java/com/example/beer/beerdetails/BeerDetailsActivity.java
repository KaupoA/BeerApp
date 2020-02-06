package com.example.beer.beerdetails;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.beer.R;
import com.example.beer.model.dto.BeerDto;

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

        getMaltName();

        Intent intent = getIntent();
        BeerDto beerDto = intent.getParcelableExtra("beer");

        beerImage = beerDto.getImage_url();
        beerName = beerDto.getName();
        beerTagline = beerDto.getTagline();
        beerAbv = beerDto.getAbv();
        beerIbu = beerDto.getIbu();
        beerEbc = beerDto.getEbc();
        beerBoilVolumeValue = beerDto.getBoil_volume().getValue();
        beerBoilVolumeUnit = beerDto.getBoil_volume().getUnit();
        beerDescription = beerDto.getDescription();

        ImageView image = findViewById(R.id.beer_list_image);
        Glide.with(this).load(beerImage).into(image);

        TextView name = findViewById(R.id.beer_list_name);
        name.setText(beerName);

        TextView tagline = findViewById(R.id.beer_list_tagline);
        tagline.setText(beerTagline);

        TextView abv = findViewById(R.id.beer_list_abv);
        abv.setText("ABV: " + beerAbv);

        TextView ibu = findViewById(R.id.beer_list_ibu);
        ibu.setText("IBU: " + beerIbu);

        TextView ebc = findViewById(R.id.beer_list_ebc);
        ebc.setText("EBC: " + beerEbc);

        TextView boilVolumeValue = findViewById(R.id.beer_list_boil_volume_value);
        boilVolumeValue.setText("Value: " +beerBoilVolumeValue);

        TextView boilVolumeUnit = findViewById(R.id.beer_list_boil_volume_unit);
        boilVolumeUnit.setText("Unit: " + beerBoilVolumeUnit);

        TextView description = findViewById(R.id.beer_list_description);
        description.setText(beerDescription);
    }

    private void getMaltName() {

        LinearLayout maltLayout = findViewById(R.id.beer_list_ingridients_layout);

        Intent intent = getIntent();
        BeerDto beerDto = intent.getParcelableExtra("beer");

        maltName = beerDto.getIngredients().getHops().get(0).getName();

        for(int i = 0; i < beerDto.getIngredients().getHops().size(); i++) {

            String name = beerDto.getIngredients().getHops().get(i).getName();
            TextView nameView = new TextView(this);
            nameView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            nameView.setText(name);
            maltLayout.addView(nameView);
        }
    }
}

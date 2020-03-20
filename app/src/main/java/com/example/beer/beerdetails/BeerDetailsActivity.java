package com.example.beer.beerdetails;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.beer.R;
import com.example.beer.model.dto.BeerDto;
import com.example.beer.model.dto.HopsDto;
import com.example.beer.model.dto.MaltDto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class BeerDetailsActivity extends AppCompatActivity {

    public String beerImage, beerName, beerTagline, beerBoilVolumeUnit, beerDescription;
    public Double beerAbv, beerIbu, beerEbc;
    public Integer beerBoilVolumeValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_view);

        Intent intent = getIntent();
        BeerDto beerDto = intent.getParcelableExtra("beer");

        if (beerDto != null) {
            setHops(beerDto.getIngredients().getHops());
            setMalt(beerDto.getIngredients().getMalt());

            beerImage = beerDto.getImage_url();
            beerName = beerDto.getName();
            beerTagline = beerDto.getTagline();
            beerAbv = beerDto.getAbv();
            beerIbu = beerDto.getIbu();
            beerEbc = beerDto.getEbc();
            beerBoilVolumeValue = beerDto.getBoil_volume().getValue();
            beerBoilVolumeUnit = beerDto.getBoil_volume().getUnit();
            beerDescription = beerDto.getDescription();
        }

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
        boilVolumeValue.setText("Value: " + beerBoilVolumeValue);

        TextView boilVolumeUnit = findViewById(R.id.beer_list_boil_volume_unit);
        boilVolumeUnit.setText("Unit: " + beerBoilVolumeUnit);

        TextView description = findViewById(R.id.beer_list_description);
        description.setText(beerDescription);
    }

    private void setMalt(List<MaltDto> maltList) {

        TextView maltName = findViewById(R.id.beer_list_ingridients_malt_textview);
        String allMalt = "";

        for (int i = 0; i < maltList.size(); i++) {
            MaltDto malt = maltList.get(i);
            allMalt += malt.getName() + " ";
            allMalt += malt.getAmount().getValue().toString() + " ";
            allMalt += malt.getAmount().getUnit();

            if (i != maltList.size() - 1) {
                allMalt += ", ";
            }

            maltName.setText(allMalt);
        }
    }

    private void setHops(List<HopsDto> hopsList) {

        TextView hopsName = findViewById(R.id.beer_list_ingridients_hops_textview);
        String allHops = "";

        for (int i = 0; i < hopsList.size(); i++) {
            HopsDto hops = hopsList.get(i);
            allHops += hops.getName() + " ";
            allHops += hops.getAmount().getValue().toString() + " ";
            allHops += hops.getAmount().getUnit();

            if (i != hopsList.size() - 1) {
                allHops += ", ";
            }

            hopsName.setText(allHops);
        }
    }
}

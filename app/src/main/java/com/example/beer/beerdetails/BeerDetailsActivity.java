package com.example.beer.beerdetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.beer.App;
import com.example.beer.R;
import com.example.beer.model.BeerRepository;
import com.example.beer.model.dto.BeerDto;
import com.example.beer.model.dto.HopsDto;
import com.example.beer.model.dto.MaltDto;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BeerDetailsActivity extends AppCompatActivity {

    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_details_activity);

        App app = ((App) getApplicationContext());
        BeerRepository beerRepository = app.beerRepository;

        Intent intent = getIntent();
        BeerDto beerDto = intent.getParcelableExtra("beer");
        ImageView image = findViewById(R.id.beer_details_image);
        TextView name = findViewById(R.id.beer_details_name);
        TextView tagline = findViewById(R.id.beer_details_tagline);
        TextView abv = findViewById(R.id.beer_details_abv);
        TextView ibu = findViewById(R.id.beer_details_ibu);
        TextView ebc = findViewById(R.id.beer_details_ebc);
        TextView boilVolumeValue = findViewById(R.id.beer_details_boil_volume_value);
        TextView description = findViewById(R.id.beer_details_description);
        TextView hopsName = findViewById(R.id.beer_details_ingridients_hops_textview);
        TextView maltName = findViewById(R.id.beer_details_ingridients_malt_textview);
        ImageView favouriteButton = findViewById(R.id.beer_details_favourite_button);
        String beerImage = beerDto.getImage_url();
        String beerName = beerDto.getName();
        String beerTagline = beerDto.getTagline();
        Double beerAbv = beerDto.getAbv();
        Double beerIbu = beerDto.getIbu();
        Double beerEbc = beerDto.getEbc();
        Integer beerBoilVolumeValue = beerDto.getBoil_volume().getValue();
        String beerBoilVolumeUnit = beerDto.getBoil_volume().getUnit();
        String beerDescription = beerDto.getDescription();

        Glide.with(this).load(beerImage).into(image);
        name.setText(beerName);
        tagline.setText(beerTagline);
        abv.setText(getString(R.string.beer_details_abv, beerAbv.toString()));
        ibu.setText(getString(R.string.beer_details_ibu, beerIbu.toString()));
        ebc.setText(getString(R.string.beer_details_ebc, beerEbc.toString()));
        boilVolumeValue.setText(getString(
                R.string.beer_details_boil_volume, beerBoilVolumeValue, beerBoilVolumeUnit));
        description.setText(beerDescription);
        hopsName.setText(formatHops(beerDto.getIngredients().getHops()));
        maltName.setText(formatMalt(beerDto.getIngredients().getMalt()));
        favouriteButton.setActivated(beerDto.getFavourite());
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disposable = Completable.fromAction(() -> beerRepository.changeFavourite(beerDto))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .onErrorComplete()
                        .subscribe();
                favouriteButton.setActivated(!favouriteButton.isActivated());
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    private String formatMalt(List<MaltDto> maltList) {
        StringBuilder allMalt = new StringBuilder();
        for (int i = 0; i < maltList.size(); i++) {
            MaltDto malt = maltList.get(i);
            allMalt.append(malt.getName()).append(" ");
            allMalt.append(malt.getAmount().getValue().toString()).append(" ");
            allMalt.append(malt.getAmount().getUnit());

            if (i != maltList.size() - 1) {
                allMalt.append(", ");
            }
        }
        return allMalt.toString();
    }

    private String formatHops(List<HopsDto> hopsList) {
        StringBuilder allHops = new StringBuilder();

        for (int i = 0; i < hopsList.size(); i++) {
            HopsDto hops = hopsList.get(i);
            allHops.append(hops.getName()).append(" ");
            allHops.append(hops.getAmount().getValue().toString()).append(" ");
            allHops.append(hops.getAmount().getUnit());

            if (i != hopsList.size() - 1) {
                allHops.append(", ");
            }
        }
        return allHops.toString();
    }
}

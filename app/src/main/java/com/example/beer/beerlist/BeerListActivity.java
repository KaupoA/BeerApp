package com.example.beer.beerlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beer.R;
import com.example.beer.beerlist.widget.BeerListAdapter;
import com.example.beer.filtersettings.FilterSettingsActivity;
import com.example.beer.model.BeerService;
import com.example.beer.model.dto.BeerDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class BeerListActivity extends AppCompatActivity {

    private int pageNumber = 1;
    private int filterOn = 0;
    private BeerListAdapter beerListAdapter;
    private BeerService beerService;
    private List<BeerDto> beerDtos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        beerService = retrofit.create(BeerService.class);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_retrofit);
        beerListAdapter = new BeerListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager
                (getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(beerListAdapter);

        beerListAdapter.setOnBottomReachedListener(position -> {
            pageNumber++;
            fetchJSON();
        });
        fetchJSON();
    }

    private void fetchJSON() {

        Call<List<BeerDto>> call = beerService.getBeers(pageNumber);

        call.enqueue(new Callback<List<BeerDto>>() {
            @Override
            public void onResponse(Call<List<BeerDto>> call, Response<List<BeerDto>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        beerDtos.addAll(response.body());
                        beerListAdapter.submitBeers(beerDtos);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BeerDto>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.beer_list_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String filterBy = sharedPreferences.getString(getString(R.string.shared_prefs_filter_settings_by), getString(R.string.filter_settings_abv));
        String orderBy = sharedPreferences.getString(getString(R.string.shared_prefs_filter_settings_order), getString(R.string.filter_settings_asc));
        switch (item.getItemId()) {
            case R.id.filter:
                if (filterOn == 0) {
//                    Toast.makeText(this, "Sorted by " + filterBy.toUpperCase() +
//                            " and " + orderBy.toUpperCase() + ".", Toast.LENGTH_SHORT).show();

                    if (filterBy.matches("abv") && orderBy.matches("asc")) {
                        beerListAdapter.submitBeers(returnSortedAbvAscList(beerDtos));
                    }else if (filterBy.matches("ibu") && orderBy.matches("asc")) {
                        beerListAdapter.submitBeers(returnSortedIbuAscList(beerDtos));
                    }else if (filterBy.matches("ebc") && orderBy.matches("asc")) {
                        beerListAdapter.submitBeers(returnSortedEbcAscList(beerDtos));
                    }else if (filterBy.matches("abv") && orderBy.matches("desc")) {
                        beerListAdapter.submitBeers(returnSortedAbvDescList(beerDtos));
                    }else if (filterBy.matches("ibu") && orderBy.matches("desc")) {
                        beerListAdapter.submitBeers(returnSortedIbuDescList(beerDtos));
                    }else if (filterBy.matches("ebc") && orderBy.matches("desc")) {
                        beerListAdapter.submitBeers(returnSortedEbcDescList(beerDtos));
                    }

                    item.setIcon(R.drawable.ic_shopping_cart);
                    filterOn = 1;
                } else {
                    beerListAdapter.submitBeers(beerDtos);

                    item.setIcon(R.drawable.ic_sort);
                    filterOn = 0;
                }
                return true;
            case R.id.settings:
                Intent settingsIntent = new Intent(this, FilterSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static List<BeerDto> returnSortedAbvAscList(List<BeerDto> input) {
        List<BeerDto> beerDto = new ArrayList<>(input);
        Collections.sort(beerDto, new BeerDto.sortByAbvAsc());
        return beerDto;
    }

    public static List<BeerDto> returnSortedIbuAscList(List<BeerDto> input) {
        List<BeerDto> beerDto = new ArrayList<>(input);
        Collections.sort(beerDto, new BeerDto.sortByIbuAsc());
        return beerDto;
    }

    public static List<BeerDto> returnSortedEbcAscList(List<BeerDto> input) {
        List<BeerDto> beerDto = new ArrayList<>(input);
        Collections.sort(beerDto, new BeerDto.sortByEbcAsc());
        return beerDto;
    }

    public static List<BeerDto> returnSortedAbvDescList(List<BeerDto> input) {
        List<BeerDto> beerDto = new ArrayList<>(input);
        Collections.sort(beerDto, new BeerDto.sortByAbvDesc());
        return beerDto;
    }

    public static List<BeerDto> returnSortedIbuDescList(List<BeerDto> input) {
        List<BeerDto> beerDto = new ArrayList<>(input);
        Collections.sort(beerDto, new BeerDto.sortByIbuDesc());
        return beerDto;
    }

    public static List<BeerDto> returnSortedEbcDescList(List<BeerDto> input) {
        List<BeerDto> beerDto = new ArrayList<>(input);
        Collections.sort(beerDto, new BeerDto.sortByEbcDesc());
        return beerDto;
    }
}

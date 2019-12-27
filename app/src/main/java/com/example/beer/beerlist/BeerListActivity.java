package com.example.beer.beerlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beer.R;
import com.example.beer.beerlist.widget.BeerListAdapter;
import com.example.beer.filtersettings.SettingsActivity;
import com.example.beer.model.BeerService;
import com.example.beer.model.dto.BeerDto;

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
    private BeerListAdapter beerListAdapter;
    private BeerService beerService;

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
                        beerListAdapter.submitBeers(response.body());
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
        switch (item.getItemId()) {
            case R.id.filter:
                Toast.makeText(this, "I will sort things for you.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
        }
        return true;
    }
}

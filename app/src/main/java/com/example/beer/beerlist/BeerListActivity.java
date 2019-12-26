package com.example.beer.beerlist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beer.BeerAdapter;
import com.example.beer.R;
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

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_retrofit);

        fetchJSON();
    }

    private void fetchJSON() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        BeerService beerService = retrofit.create(BeerService.class);

        Call<List<BeerDto>> call = beerService.getBeers();

        call.enqueue(new Callback<List<BeerDto>>() {
            @Override
            public void onResponse(Call<List<BeerDto>> call, Response<List<BeerDto>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        BeerAdapter beerAdapter = new BeerAdapter(getApplicationContext(), response.body());
                        recyclerView.setLayoutManager(new LinearLayoutManager
                                (getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(beerAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BeerDto>> call, Throwable t) {

            }
        });
    }
}

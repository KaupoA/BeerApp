package com.example.beer;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beer.model.BeerService;
import com.example.beer.model.dto.Beer;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

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

        BeerService beerService = retrofit.create(BeerService.class);
        Call<List<Beer>> call = beerService.getBeers();
        call.enqueue(new Callback<List<Beer>>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<List<Beer>> call, Response<List<Beer>> response) {
                if (response.body() != null) {
                    Log.d("Response", response.body().toString());
                }
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<List<Beer>> call, Throwable t) {
                Log.d("Response", t.toString());
            }
        });
    }
}

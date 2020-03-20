package com.example.beer;

import android.app.Application;

import androidx.room.Room;

import com.example.beer.model.BeerRepository;
import com.example.beer.model.BeerService;
import com.example.beer.model.database.AppDatabase;
import com.example.beer.model.database.BeerDao;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class App extends Application {

    public BeerRepository beerRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeBeerRepository();
    }

    private void initializeBeerRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(getString(R.string.api_base_url))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        BeerService beerService = retrofit.create(BeerService.class);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Database").build();
        BeerDao beerDao = db.beerDao();
        beerRepository = new BeerRepository(beerDao, beerService);
    }
}

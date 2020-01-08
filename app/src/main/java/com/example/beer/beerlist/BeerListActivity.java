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
import androidx.room.Room;

import com.example.beer.R;
import com.example.beer.beerlist.widget.BeerListAdapter;
import com.example.beer.filtersettings.FilterSettingsActivity;
import com.example.beer.model.BeerRepository;
import com.example.beer.model.BeerService;
import com.example.beer.model.database.AppDatabase;
import com.example.beer.model.database.BeerDao;
import com.example.beer.model.dto.BeerDto;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static com.example.beer.utlis.SortUtils.returnSortedAbvAscList;
import static com.example.beer.utlis.SortUtils.returnSortedAbvDescList;
import static com.example.beer.utlis.SortUtils.returnSortedEbcAscList;
import static com.example.beer.utlis.SortUtils.returnSortedEbcDescList;
import static com.example.beer.utlis.SortUtils.returnSortedIbuAscList;
import static com.example.beer.utlis.SortUtils.returnSortedIbuDescList;

public class BeerListActivity extends AppCompatActivity {

    private int pageNumber = 1;
    private int filterOn = 0;
    private BeerListAdapter beerListAdapter;
    private BeerService beerService;
    private List<BeerDto> beerDtos = new ArrayList<>();
    private BeerRepository beerRepository;
    private Disposable disposable;

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        beerService = retrofit.create(BeerService.class);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Database").build();
        BeerDao beerDao = db.beerDao();
        beerRepository = new BeerRepository(beerDao, beerService);

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

        disposable = beerRepository.get()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(beerDtos1 -> {
                    beerDtos = beerDtos1;
                    beerListAdapter.submitBeers(beerDtos);
                });
    }

    private void fetchJSON() {
        beerRepository.refresh(pageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorComplete()
                .subscribe();
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
                    if (filterBy.matches(getString(R.string.filter_settings_abv))
                            && orderBy.matches(getString(R.string.filter_settings_asc))) {
                        beerListAdapter.submitBeers(returnSortedAbvAscList(beerDtos));
                    } else if (filterBy.matches(getString(R.string.filter_settings_ibu))
                            && orderBy.matches(getString(R.string.filter_settings_asc))) {
                        beerListAdapter.submitBeers(returnSortedIbuAscList(beerDtos));
                    } else if (filterBy.matches(getString(R.string.filter_settings_ebc))
                            && orderBy.matches(getString(R.string.filter_settings_asc))) {
                        beerListAdapter.submitBeers(returnSortedEbcAscList(beerDtos));
                    } else if (filterBy.matches(getString(R.string.filter_settings_abv))
                            && orderBy.matches(getString(R.string.filter_settings_desc))) {
                        beerListAdapter.submitBeers(returnSortedAbvDescList(beerDtos));
                    } else if (filterBy.matches(getString(R.string.filter_settings_ibu))
                            && orderBy.matches(getString(R.string.filter_settings_desc))) {
                        beerListAdapter.submitBeers(returnSortedIbuDescList(beerDtos));
                    } else if (filterBy.matches(getString(R.string.filter_settings_ebc))
                            && orderBy.matches(getString(R.string.filter_settings_desc))) {
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

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}

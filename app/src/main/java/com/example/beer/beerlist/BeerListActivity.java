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

import com.example.beer.App;
import com.example.beer.R;
import com.example.beer.beerdetails.BeerDetailsActivity;
import com.example.beer.beerlist.widget.BeerListAdapter;
import com.example.beer.filtersettings.FilterSettingsActivity;
import com.example.beer.homework.BeerListActivityCallback;
import com.example.beer.model.BeerRepository;
import com.example.beer.model.dto.BeerDto;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.beer.utlis.SortUtils.returnSortedAbvAscList;
import static com.example.beer.utlis.SortUtils.returnSortedAbvDescList;
import static com.example.beer.utlis.SortUtils.returnSortedEbcAscList;
import static com.example.beer.utlis.SortUtils.returnSortedEbcDescList;
import static com.example.beer.utlis.SortUtils.returnSortedIbuAscList;
import static com.example.beer.utlis.SortUtils.returnSortedIbuDescList;

public class BeerListActivity extends AppCompatActivity implements BeerListActivityCallback {

    private int pageNumber = 1;
    private int filterOn = 0;
    private boolean favouriteFilter = false;
    private BeerListAdapter beerListAdapter;
    private List<BeerDto> beerDtos = new ArrayList<>();
    private BeerRepository beerRepository;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        App app = ((App) getApplication());
        beerRepository = app.beerRepository;

        RecyclerView recyclerView = findViewById(R.id.recycler_view_retrofit);
        beerListAdapter = new BeerListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager
                (getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(beerListAdapter);
        beerListAdapter.setBeerListActivityCallback(this);

        beerListAdapter.setOnBottomReachedListener(position -> {
            pageNumber++;
            fetchJSON();
        });
        fetchJSON();

        getBeersFromDb();
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
        String[] filterValues = getResources().getStringArray(R.array.filter_settings_filter_by_key);
        String filterBy = sharedPreferences.getString(getString(R.string.shared_prefs_filter_settings_by), filterValues[0]);
        String[] orderValues = getResources().getStringArray(R.array.filter_settings_order_key);
        String orderBy = sharedPreferences.getString(getString(R.string.shared_prefs_filter_settings_order), orderValues[0]);
        switch (item.getItemId()) {
            case R.id.filter:
                if (filterOn == 0) {
                    if (filterBy.matches(filterValues[0])
                            && orderBy.matches(orderValues[0])) {
                        beerListAdapter.submitBeers(returnSortedAbvAscList(beerDtos));
                    } else if (filterBy.matches(filterValues[1])
                            && orderBy.matches(orderValues[0])) {
                        beerListAdapter.submitBeers(returnSortedIbuAscList(beerDtos));
                    } else if (filterBy.matches(filterValues[2])
                            && orderBy.matches(orderValues[0])) {
                        beerListAdapter.submitBeers(returnSortedEbcAscList(beerDtos));
                    } else if (filterBy.matches(filterValues[0])
                            && orderBy.matches(orderValues[1])) {
                        beerListAdapter.submitBeers(returnSortedAbvDescList(beerDtos));
                    } else if (filterBy.matches(filterValues[1])
                            && orderBy.matches(orderValues[1])) {
                        beerListAdapter.submitBeers(returnSortedIbuDescList(beerDtos));
                    } else if (filterBy.matches(filterValues[2])
                            && orderBy.matches(orderValues[1])) {
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
            case R.id.favourite:
                if (!favouriteFilter) {
                    item.setIcon(R.drawable.ic_favorite);
                } else {
                    item.setIcon(R.drawable.ic_unfavorite);
                }
                favouriteFilter = !favouriteFilter;
                getBeersFromDb();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }

    @Override
    public void navigateToBeerDetails(BeerDto beerDto) {
        Intent beerIntent = new Intent(this, BeerDetailsActivity.class);
        beerIntent.putExtra("beer", beerDto);
        startActivity(beerIntent);
    }

    @Override
    public void favouriteButtonClicked(BeerDto beerDto) {
        Completable.fromAction(() -> beerRepository.changeFavourite(beerDto))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorComplete()
                .subscribe();
    }

    public void getBeersFromDb() {
        disposable = beerRepository.get()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(newBeerDtosFromDb -> {
                    if (favouriteFilter) {
                        List<BeerDto> filteredBeers = new ArrayList<>();
                        for (BeerDto beerDto : newBeerDtosFromDb) {
                            if (beerDto.getFavourite()) {
                                filteredBeers.add(beerDto);
                            }
                        }
                        beerDtos = filteredBeers;
                    } else {
                        beerDtos = newBeerDtosFromDb;
                    }
                    beerListAdapter.submitBeers(beerDtos);
                });
    }
}

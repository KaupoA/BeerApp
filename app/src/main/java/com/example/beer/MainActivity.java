package com.example.beer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import android.os.Bundle;
import android.util.Log;

import com.example.beer.model.dto.Beer;
import com.example.beer.model.dto.BeerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_retrofit);

        fetchJSON();
    }

    private void fetchJSON() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BeerInterface.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        BeerInterface api = retrofit.create(BeerInterface.class);

        Call<String> call = api.getString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body());

                        String jsonresponse = response.body();
                        writeRecycler(jsonresponse);
                    } else {
                        Log.e("onEmptyResponse", "Returned empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    private void writeRecycler(String response) {

        try {

            List<Beer> beers = new ArrayList<>();
            JSONArray dataArray = new JSONArray(response);

            for (int i = 0; i < dataArray.length(); i++) {

                Beer beer = new Beer();
                JSONObject dataobj = dataArray.getJSONObject(i);

                beer.setImage_url(dataobj.getString("image_url"));
                beer.setName(dataobj.getString("name"));

                beers.add(beer);
            }

            RetrofitAdapter retrofitAdapter = new RetrofitAdapter(this, beers);
            recyclerView.setLayoutManager(new LinearLayoutManager
                    (getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(retrofitAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

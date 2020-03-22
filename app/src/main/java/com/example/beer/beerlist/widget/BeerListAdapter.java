package com.example.beer.beerlist.widget;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.beer.R;
import com.example.beer.homework.BeerListActivityCallback;
import com.example.beer.model.dto.BeerDto;

import java.util.ArrayList;
import java.util.List;

public class BeerListAdapter extends RecyclerView.Adapter<BeerListAdapter.MyViewHolder> {

    private OnBottomReachedListener onBottomReachedListener;
    private BeerListActivityCallback beerListActivityCallback;
    private List<BeerDto> beerDtos = new ArrayList<>();

    @NonNull
    @Override
    public BeerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_list_item, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BeerListAdapter.MyViewHolder holder, int position) {

        Glide.with(holder.itemView).load(beerDtos.get(position).getImage_url()).into(holder.imageView);
        holder.imageName.setText(beerDtos.get(position).getName());

        if (beerDtos.get(position).getAbv() == null) {
            holder.beerAbv.setVisibility(View.GONE);
        } else {
            holder.beerAbv.setVisibility(View.VISIBLE);
            holder.beerAbv.setText("ABV: " + beerDtos.get(position).getAbv());
        }

        if (beerDtos.get(position).getIbu() == null) {
            holder.beerIbu.setVisibility(View.GONE);
        } else {
            holder.beerIbu.setVisibility(View.VISIBLE);
            holder.beerIbu.setText("IBU: " + beerDtos.get(position).getIbu());
        }

        if (beerDtos.get(position).getEbc() == null) {
            holder.beerEbc.setVisibility(View.GONE);
        } else {
            holder.beerEbc.setVisibility(View.VISIBLE);
            holder.beerEbc.setText("EBC: " + beerDtos.get(position).getEbc());
        }

        holder.favouriteButton.setActivated(beerDtos.get(position).getFavourite());

        holder.favouriteButton.setOnClickListener(v -> {
            beerListActivityCallback.favouriteButtonClicked(beerDtos.get(position));
        });

        if (position == beerDtos.size() - 1) {
            onBottomReachedListener.onBottomReached(position);
        }

        holder.beerListLayout.setOnClickListener(v -> {

            beerListActivityCallback.navigateToBeerDetails(beerDtos.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return beerDtos.size();
    }

    public void submitBeers(List<BeerDto> beerDtos) {
        this.beerDtos = beerDtos;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView imageName;
        TextView beerAbv;
        TextView beerIbu;
        TextView beerEbc;
        ImageView imageView;
        RelativeLayout beerListLayout;
        ImageView favouriteButton;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageName = itemView.findViewById(R.id.beer_name);
            beerAbv = itemView.findViewById(R.id.beer_abv);
            beerIbu = itemView.findViewById(R.id.beer_ibu);
            beerEbc = itemView.findViewById(R.id.beer_ebc);
            imageView = itemView.findViewById(R.id.beer_image);
            beerListLayout = itemView.findViewById(R.id.beer_list_layout);
            favouriteButton = itemView.findViewById(R.id.beer_details_favourite_button);
        }
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
    }

    public void setBeerListActivityCallback(BeerListActivityCallback beerListActivityCallback) {
        this.beerListActivityCallback = beerListActivityCallback;
    }
}

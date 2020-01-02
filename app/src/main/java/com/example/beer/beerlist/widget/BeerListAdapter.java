package com.example.beer.beerlist.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.beer.beerdetails.BeerDetailsActivity;
import com.example.beer.model.dto.BeerDto;

import java.util.ArrayList;
import java.util.List;

public class BeerListAdapter extends RecyclerView.Adapter<BeerListAdapter.MyViewHolder> {

    private OnBottomReachedListener onBottomReachedListener;

    private List<BeerDto> beerDtos = new ArrayList<>();
    private Context mContext;

    public BeerListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public BeerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.beer_list_item, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BeerListAdapter.MyViewHolder holder, int position) {

        Glide.with(holder.itemView).load(beerDtos.get(position).getImage_url()).into(holder.imageView);
        holder.imageName.setText(beerDtos.get(position).getName());
        holder.beerAbv.setText(Double.toString((beerDtos.get(position).getAbv())));

        if (beerDtos.get(position).getIbu() == null) {
            holder.beerIbu.setText(Double.toString(0));
        } else {
            holder.beerIbu.setText(Double.toString((beerDtos.get(position).getIbu())));
        }

        if (beerDtos.get(position).getEbc() == null) {
            holder.beerEbc.setText(Double.toString(0));
        } else {
            holder.beerEbc.setText(Double.toString((beerDtos.get(position).getEbc())));
        }

        if (position == beerDtos.size() - 1) {
            onBottomReachedListener.onBottomReached(position);
        }

        holder.beerListLayout.setOnClickListener(v -> {

            Intent beerIntent = new Intent(mContext, BeerDetailsActivity.class);
            beerIntent.putExtra("beer_name", beerDtos.get(position).getName());
            mContext.startActivity(beerIntent);
        });
    }

    @Override
    public int getItemCount() {
        return beerDtos.size();
    }

    public void submitBeers(List<BeerDto> beerDtos) {
        this.beerDtos.addAll(beerDtos);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView imageName;
        TextView beerAbv;
        TextView beerIbu;
        TextView beerEbc;
        ImageView imageView;
        RelativeLayout beerListLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageName = itemView.findViewById(R.id.beer_name);
            beerAbv = itemView.findViewById(R.id.beer_abv);
            beerIbu = itemView.findViewById(R.id.beer_ibu);
            beerEbc = itemView.findViewById(R.id.beer_ebc);
            imageView = itemView.findViewById(R.id.beer_image);
            beerListLayout = itemView.findViewById(R.id.beer_list_layout);
        }
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
    }
}

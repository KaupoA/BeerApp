package com.example.beer.beerlist.widget;

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
import com.example.beer.beerlist.BeerViewActivity;
import com.example.beer.model.dto.BeerDto;

import java.util.ArrayList;
import java.util.List;

public class BeerListAdapter extends RecyclerView.Adapter<BeerListAdapter.MyViewHolder> {

    private OnBottomReachedListener onBottomReachedListener;

    private LayoutInflater inflater;
    private List<BeerDto> beerDtos = new ArrayList<>();
    private Context mContext;

    public BeerListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public BeerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.beer_list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerListAdapter.MyViewHolder holder, int position) {

        Glide.with(holder.itemView).load(beerDtos.get(position).getImage_url()).into(holder.imageView);
        holder.imageName.setText(beerDtos.get(position).getName());

        if (position == beerDtos.size() - 1) {
            onBottomReachedListener.onBottomReached(position);
        }

        holder.beerListLayout.setOnClickListener(v -> {

            Intent beerIntent = new Intent(mContext, BeerViewActivity.class);
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
        ImageView imageView;
        RelativeLayout beerListLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageName = itemView.findViewById(R.id.image_name);
            imageView = itemView.findViewById(R.id.image);
            beerListLayout = itemView.findViewById(R.id.beer_list_layout);
        }
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
    }
}

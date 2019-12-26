package com.example.beer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.beer.model.dto.BeerDto;

import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<BeerDto> beerDtos;

    public BeerAdapter(Context context, List<BeerDto> beerDtos) {

        inflater = LayoutInflater.from(context);
        this.beerDtos = beerDtos;
    }

    @NonNull
    @Override
    public BeerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.beer_list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerAdapter.MyViewHolder holder, int position) {

        Glide.with(holder.itemView).load(beerDtos.get(position).getImage_url()).into(holder.imageView);
        holder.imageName.setText(beerDtos.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return beerDtos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView imageName;
        ImageView imageView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageName = itemView.findViewById(R.id.image_name);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}

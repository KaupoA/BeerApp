package com.example.beer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.beer.model.dto.Beer;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private List<Beer> beers;

    RetrofitAdapter(Context context, List<Beer> beers) {

        inflater = LayoutInflater.from(context);
        this.beers = beers;
    }

    @NonNull
    @Override
    public RetrofitAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.retrofit_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RetrofitAdapter.MyViewHolder holder, int position) {

        Picasso.get().load(beers.get(position).getImage_url()).into(holder.imageView);
        holder.imageName.setText(beers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView imageName;
        CircleImageView imageView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageName = itemView.findViewById(R.id.image_name);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}

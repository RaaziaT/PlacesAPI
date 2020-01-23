package com.raaziat.pagingexample.ui.maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raaziat.pagingexample.BuildConfig;
import com.raaziat.pagingexample.R;
import com.raaziat.pagingexample.databinding.ListItemRestaurantBinding;
import com.raaziat.pagingexample.model.places.Result;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestaurantAdapter extends
        RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<Result> restaurantList;
    private Context context;

    public RestaurantAdapter(List<Result> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent,
                                         int viewType) {
        ListItemRestaurantBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_restaurant, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Result result = restaurantList.get(position);

        Glide.with(context).load("https://maps.googleapis.com/maps/api/place/photo?" +
                "maxwidth=" +result.getPhotos().get(0).getWidth()+
                "&photoreference="+result.getPhotos().get(0).getPhoto_reference()+"&" +
                "key="+ BuildConfig.API_KEY).into(holder.listItemRestaurantBinding.imageViewRestaurantImage);
        holder.listItemRestaurantBinding.txtViewRestaurantName.setText(result.getName());
        holder.listItemRestaurantBinding.txtViewCity.setText(result.getVicinity());
        holder.listItemRestaurantBinding.ratingBarRestaurant.setRating((float) result.getRating());

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ListItemRestaurantBinding listItemRestaurantBinding;

        ViewHolder(ListItemRestaurantBinding flightItemLayoutBinding) {
            super(flightItemLayoutBinding.getRoot());
            listItemRestaurantBinding = flightItemLayoutBinding;
        }
    }
}
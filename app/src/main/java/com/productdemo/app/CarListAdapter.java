package com.productdemo.app;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;


public class CarListAdapter extends ListAdapter<CarModel, CarListViewHolder> {
    CarActionListener carActionListener;
    public CarListAdapter(@NonNull DiffUtil.ItemCallback<CarModel> diffCallback, CarActionListener carActionListener) {
        super(diffCallback);
        this.carActionListener = carActionListener;
    }
    @NonNull
    @Override
    public CarListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CarListViewHolder.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull CarListViewHolder holder, int position) {
        holder.bind(getItem(position), position, carActionListener);

    }

}



package com.productdemo.app;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.productdemo.app.databinding.CarItemBinding;

import java.io.File;


public class CarListViewHolder extends RecyclerView.ViewHolder {
    CarItemBinding b;

    public CarListViewHolder(@NonNull View itemView, CarItemBinding binding) {
        super(itemView);
        this.b = binding;
    }

    public void bind(CarModel model, int position, CarActionListener carActionListener) {

        b.tvName.setText(model.name);
        b.tvType.setText(model.type);
        b.tvColor.setText(model.color);
        b.tvModel.setText(model.model);
        b.tvPrice.setText(model.price);
        if (model.image != null) {
            Glide.with(itemView).load(new File(model.image)).into(b.ivImage);
        }

        b.ivImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carActionListener.onDelete(position);
            }
        });


    }
    public static CarListViewHolder create(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CarItemBinding b = CarItemBinding.inflate(inflater, parent, false);
        return new CarListViewHolder(b.getRoot(), b);
    }
}


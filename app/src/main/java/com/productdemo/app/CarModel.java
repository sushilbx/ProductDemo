package com.productdemo.app;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.Gson;

public class CarModel {


    public Long id;
    public String name;
    public String type;
    public String model;
    public String price;
    public String color;
    public String image;

    public CarModel(Long id, String name, String type, String model, String price, String color, String image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.model = model;
        this.price = price;
        this.color = color;
        this.image = image;
    }

    public static DiffUtil.ItemCallback<CarModel> PRODUCT_COMPARATOR = new DiffUtil.ItemCallback<CarModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull CarModel oldItem, @NonNull CarModel newItem) {
            return oldItem.name.equals(newItem.name);
        }

        @Override
        public boolean areContentsTheSame(@NonNull CarModel oldItem, @NonNull CarModel newItem) {
            return new Gson().toJson(oldItem).equals(new Gson().toJson(newItem));
        }
    };

}

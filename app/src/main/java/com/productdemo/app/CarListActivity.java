package com.productdemo.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.productdemo.app.databinding.ActivityCartListBinding;

import java.util.List;

public class CarListActivity extends AppCompatActivity {
    ActivityCartListBinding b;
    SessionManger sessionManger;
    CarListAdapter carListAdapter;
    List<CarModel>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityCartListBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);
        sessionManger = new SessionManger(this);
        b.ivAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent =new Intent(CarListActivity.this, ProductActivity.class);
                intent.putExtra("model", new Gson().toJson(list));
                startActivity(intent);
            }
        });

        carListAdapter = new CarListAdapter(CarModel.PRODUCT_COMPARATOR, new CarActionListener() {
            @Override
            public void onDelete(int position) {
                deleteCar(position);

            }

            @Override
            public void onUpdate(int position) {

            }
        });
        list =sessionManger.getCarList();
        carListAdapter.submitList(list);
        b.rvCarList.setAdapter(carListAdapter);
    }

    private void deleteCar(int position) {
        list.remove(position);
        sessionManger.setCarList(list);
        Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        carListAdapter.notifyDataSetChanged();
    }


}

interface CarActionListener {
    void onDelete(int position);
    void onUpdate(int position);
}
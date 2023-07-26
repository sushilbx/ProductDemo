package com.productdemo.app;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.productdemo.app.databinding.ActivityAddCarBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements PickiTCallbacks {
    ActivityAddCarBinding b;
    SessionManger sessionManger;
    String name = "";
    String model = "";
    String type = "";
    String color = "";
    String price = "";
    PickiT pickiT;
    String profileImagePath = "";
    String[] CarName = new String[]{"Select Car Name", "Audi", "Mahindra", "HMT", "Ferari", "Bugati", "Royals Royes"};


    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);

                    pickiT.getPath(uri, Build.VERSION.SDK_INT);
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });
    private String[] permissions = {android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityAddCarBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);
        pickiT = new PickiT(this, this, this);
        sessionManger = new SessionManger(this);
        Intent intent = getIntent();
        String model = intent.getStringExtra("model");
        b.mbSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (checkForm()) {
                    addCar();
                }
            }
        });


        b.carImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
        getCarName();
    }
    private void getCarName() {

        final List<String> carList = new ArrayList<>(Arrays.asList(CarName));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        b.spinner.setAdapter(spinnerArrayAdapter);
        b.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                name = carList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addCar() {
        List<CarModel> list = sessionManger.getCarList();
        list.add(new CarModel(System.currentTimeMillis(), name, type, model, price, color, profileImagePath));
        sessionManger.setCarList(list);
        Intent intent = new Intent(ProductActivity.this, CarListActivity.class);
        Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }

    boolean checkForm() {

        model = b.tietModel.getText().toString().trim();
        type = b.tietType.getText().toString().trim();
        color = b.tietColor.getText().toString().trim();
        price = b.tietPrice.getText().toString().trim();

        if (profileImagePath.isEmpty()) {
            Toast.makeText(this, "Please upload image", Toast.LENGTH_SHORT).show();
            b.carImageview.setFocusableInTouchMode(true);
            b.carImageview.requestFocus();
            return false;
        }

        if (name.isEmpty()) {
            b.tietName.setError("Car Name is empty");
            b.tietName.requestFocus();
            return false;
        } else if (name.length() < 4) {
            b.tietName.setError("Name should be minimum 3 characters");
            b.tietName.requestFocus();
            return false;
        }
        if (model.isEmpty()) {
            Toast.makeText(this, "Enter Car Model", Toast.LENGTH_SHORT).show();
            b.tietModel.setFocusableInTouchMode(true);
            b.tietModel.requestFocus();
            return false;
        } else if (model.length() < 4) {
            Toast.makeText(this, "Model Name should be minimum 4 characters", Toast.LENGTH_SHORT).show();
            b.tietModel.setFocusableInTouchMode(true);
            b.tietModel.requestFocus();
            return false;
        }
        if (type.isEmpty()) {
            Toast.makeText(this, "Enter Type(Ac or Non Ac)", Toast.LENGTH_SHORT).show();
            b.tietType.setFocusableInTouchMode(true);
            b.tietType.requestFocus();
            return false;
        } else if (type.length() < 2) {
            Toast.makeText(this, "Type should be minimum 2 characters", Toast.LENGTH_SHORT).show();
            b.tietType.setFocusableInTouchMode(true);
            b.tietType.requestFocus();
            return false;
        }
        if (color.isEmpty()) {
            Toast.makeText(this, "Enter color", Toast.LENGTH_SHORT).show();
            b.tietColor.setFocusableInTouchMode(true);
            b.tietColor.requestFocus();
            return false;
        } else if (color.length() < 3) {
            Toast.makeText(this, "color should be minimum 3 characters", Toast.LENGTH_SHORT).show();
            b.tietColor.setFocusableInTouchMode(true);
            b.tietColor.requestFocus();
            return false;
        }
        if (price.isEmpty()) {
            Toast.makeText(this, "Enter Price", Toast.LENGTH_SHORT).show();
            b.tietPrice.setFocusableInTouchMode(true);
            b.tietPrice.requestFocus();
            return false;
        } else if (price.length() < 2) {
            Toast.makeText(this, "Price should be minimum 2 characters", Toast.LENGTH_SHORT).show();
            b.tietPrice.setFocusableInTouchMode(true);
            b.tietPrice.requestFocus();
            return false;
        }


        return true;
    }


    void pickImage() {
        Log.e("sushil", "inside image pick");
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }


    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    public void PickiTonUriReturned() {

    }

    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {

    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
        Log.e("sushil", "path: " + path);
        profileImagePath = path;
        Glide.with(ProductActivity.this).load(new File(path)).into(b.carImageview);

    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
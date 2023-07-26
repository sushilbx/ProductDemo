package com.productdemo.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SessionManger {
    public static final String KEY_CAR_LIST = "car_list";
    private static final String PREF_NAME = "userData";
    private static SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    public  SessionManger(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setCarList(List<CarModel> list) {
        Log.e("response", new Gson().toJson(list));
        editor.putString(KEY_CAR_LIST, new Gson().toJson(list));
        editor.commit();
    }

    public List<CarModel> getCarList() {
        Type carListType = new TypeToken<List<CarModel>>() {}.getType();
        return new Gson().fromJson(pref.getString(KEY_CAR_LIST, "[]"), carListType);
    }
}

package com.digiclack.visionstoreadmin;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.digiclack.visionstoreadmin.adapters.ProductAdapter;
import com.digiclack.visionstoreadmin.model.Products;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        SharedPreferences prefs = getSharedPreferences("my_prefes_key", this.MODE_PRIVATE);
        String productsArrayJSON = prefs.getString("my_prefes_key", "");

        ArrayList<Products> productsArray =
                new Gson().fromJson(productsArrayJSON, new TypeToken< ArrayList<Products>>() {
                }.getType());

        Log.e("FavoriteActivity",productsArray.toString());
        ProductAdapter adapter = new ProductAdapter(this, productsArray);
        ListView listView = (ListView) findViewById(R.id.lv_favorite);
        listView.setAdapter(adapter);
    }
}

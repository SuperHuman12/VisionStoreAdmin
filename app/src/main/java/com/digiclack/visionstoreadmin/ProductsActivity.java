package com.digiclack.visionstoreadmin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.digiclack.visionstoreadmin.adapters.ProductAdapter;
import com.digiclack.visionstoreadmin.model.Product;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    ArrayList<Product> mLenseList;
    FloatingActionButton fabAddProduct;
    GridView productGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        initComponent();
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        final ProductAdapter adapter=new ProductAdapter(this,mLenseList);
        productGrid.setAdapter(adapter);

       productGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent=new Intent(ProductsActivity.this,UpdateProductActivity.class);
               intent.putExtra("Name",adapter.getItem(i).getpBrand()+" "+adapter.getItem(i).getpModelName());
               startActivity(intent);
           }
       });

        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductsActivity.this,AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initComponent() {
        mLenseList=new ArrayList<>();
        fabAddProduct= (FloatingActionButton) findViewById(R.id.fab_add_product);
        productGrid= (GridView) findViewById(R.id.gridview_products_activity);
    }

}

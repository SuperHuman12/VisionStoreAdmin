package com.digiclack.visionstoreadmin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.digiclack.visionstoreadmin.adapters.FirebaseProductAdapter;
import com.digiclack.visionstoreadmin.adapters.ProductAdapter;
import com.digiclack.visionstoreadmin.model.Product;
import com.digiclack.visionstoreadmin.model.Products;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    ArrayList<Product> mLenseList;
    FloatingActionButton fabAddProduct;
    GridView productGrid;
    private String mBrandName;
    private String mFrom;
    private static final String TAG = "ProductsActivity";
    private String mCategory;
    Query mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Intent intent=getIntent();
        mBrandName=intent.getStringExtra("BRAND");
        mFrom=intent.getStringExtra("FROM");
        if (mFrom.equals("transparent")|| mFrom.equals("color")) {
            mCategory="lenses";
        }
        if (!mBrandName.isEmpty()) {
            setTitle(mBrandName);
        }
        initComponent();
        /*mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
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
               Intent intent=new Intent(ProductsActivity.this,AddProductActivity.class);
               intent.putExtra("Name","Edit");
               Bundle bundle=new Bundle();
               bundle.putString("Model",adapter.getItem(i).getpModelName());
               bundle.putString("Price",adapter.getItem(i).getpPrice());
               bundle.putInt("Image",adapter.getItem(i).getpImage());
               intent.putExtras(bundle);
               startActivity(intent);
           }
       });*/
        View empty_view=findViewById(R.id.empty_view);
        productGrid.setEmptyView(empty_view);
        FirebaseProductAdapter adapter=new
                FirebaseProductAdapter(this,Products.class,R.layout.grid_single_item,mRef);
        Log.e(TAG,mRef.toString());
        productGrid.setAdapter(adapter);

        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductsActivity.this,AddProductActivity.class);
                intent.putExtra("Name","fab");
                intent.putExtra("BRAND",mBrandName);
                intent.putExtra("FROM",mFrom);
                startActivity(intent);
            }
        });
    }

    public void initComponent() {
        mLenseList=new ArrayList<>();
        fabAddProduct= (FloatingActionButton) findViewById(R.id.fab_add_product);
        productGrid= (GridView) findViewById(R.id.gridview_products_activity);
        mRef= FirebaseDatabase.getInstance().getReference().child("products").child(mCategory).child(mFrom).child(mBrandName);
    }

}

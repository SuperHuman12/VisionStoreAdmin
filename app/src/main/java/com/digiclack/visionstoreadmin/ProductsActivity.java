package com.digiclack.visionstoreadmin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import com.digiclack.visionstoreadmin.adapters.FirebaseProductAdapter;
import com.digiclack.visionstoreadmin.model.Products;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ProductsActivity extends AppCompatActivity {
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
        mCategory=intent.getStringExtra("CATEGORY");
        Log.e(TAG,mCategory+"/"+mFrom+"/");

        if (!mBrandName.isEmpty()) {
            setTitle(mBrandName);
        }
        initComponent();

        View empty_view=findViewById(R.id.empty_view);
        productGrid.setEmptyView(empty_view);
        FirebaseProductAdapter adapter=new
                FirebaseProductAdapter(this,Products.class,R.layout.grid_single_item,mRef,"products",mCategory);
        Log.e(TAG,mRef.toString());
        productGrid.setAdapter(adapter);

        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductsActivity.this,AddProductActivity.class);
                intent.putExtra("Name","fab");
                intent.putExtra("BRAND",mBrandName);
                intent.putExtra("FROM",mFrom);
                intent.putExtra("CATEGORY",mCategory);
                startActivity(intent);
            }
        });
    }

    public void initComponent() {
        fabAddProduct= (FloatingActionButton) findViewById(R.id.fab_add_product);
        productGrid= (GridView) findViewById(R.id.gridview_products_activity);
        mRef= FirebaseDatabase.getInstance().getReference().child("products").child(mCategory).child(mFrom).child(mBrandName);
    }

}

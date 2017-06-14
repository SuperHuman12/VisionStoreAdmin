package com.digiclack.visionstoreadmin;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.digiclack.visionstoreadmin.Utils.Utils;
import com.digiclack.visionstoreadmin.adapters.FirebaseProductAdapter;
import com.digiclack.visionstoreadmin.model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProductsActivity extends AppCompatActivity {
    FloatingActionButton fabAddProduct;
    DatabaseReference databaseFavRating;
    private String unique_id;
    GridView productGrid;
    private String mBrandName;
    private String mFrom;
    private static final String TAG = "ProductsActivity";
    private String mCategory;
    Query mRef;
    FragmentManager manager;
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
        final FirebaseProductAdapter adapter=new
                FirebaseProductAdapter(this,Products.class,R.layout.grid_single_item2,mRef,"products",mCategory);
        Log.e(TAG,mRef.toString());
        productGrid.setAdapter(adapter);

        productGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String key=adapter.getRef(i).getKey();
                intentToAddProduct("edit",key);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_edit_product, menu);
        if (menu.findItem(R.id.action_save) != null)
            menu.findItem(R.id.action_save).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Log.e(TAG,"product activity back actionbar pressed");
                onBackPressed();
                return true;
 /*           case R.id.action_search:
                return true;

            case R.id.action_favorite:

                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_add_to_cart:
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

    public void initComponent() {
        fabAddProduct= (FloatingActionButton) findViewById(R.id.fab_add_product);
        productGrid= (GridView) findViewById(R.id.gridview_products_activity);
        mRef= FirebaseDatabase.getInstance().getReference().child("products").child(mCategory).child(mFrom).child(mBrandName);
    }

    public void intentToAddProduct(String editOrAdd,String key) {
        Intent intent=new Intent(ProductsActivity.this,AddProductActivity.class);
        intent.putExtra("KEY",key);
        intent.putExtra("EDIT_ADD",editOrAdd);
        intent.putExtra("BRAND",mBrandName);
        intent.putExtra("FROM",mFrom);
        intent.putExtra("CATEGORY",mCategory);
        startActivity(intent);
    }

}

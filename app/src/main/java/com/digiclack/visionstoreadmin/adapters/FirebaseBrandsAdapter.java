package com.digiclack.visionstoreadmin.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.digiclack.visionstoreadmin.ProductsActivity;
import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.Utils.Utils;
import com.digiclack.visionstoreadmin.model.Brand;
import com.digiclack.visionstoreadmin.model.Products;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

import java.util.HashMap;

import static java.security.AccessController.getContext;

/**
 * Created by Zar on 6/8/2017.
 */

public class FirebaseBrandsAdapter extends FirebaseListAdapter<Brand> {
        private String fromWhere,mCategory;
    private static final String TAG = "FirebaseBrandsAdapter";
        Activity mActivity;
        Query mRef;

    public FirebaseBrandsAdapter(Activity activity, Class<Brand> modelClass, int modelLayout, Query ref, String fromWhere,String category) {
        super(activity, modelClass, modelLayout, ref);
        this.fromWhere=fromWhere;
        this.mActivity=activity;
        this.mCategory=category;
    }

    @Override
    protected void populateView(View v, Brand model, int position) {
        ViewHolder viewHolder=new ViewHolder();
        final String brandName=getItem(position).getBrandName();

        viewHolder.txtName= (TextView) v.findViewById(R.id.txt_category);
        viewHolder.txtName.setText(brandName);
        viewHolder.gridView= (GridView) v.findViewById(R.id.product_gridview);
        viewHolder.seeAll= (Button) v.findViewById(R.id.btn_seeall);
        mRef= Utils.getDatabase().getReference().child("products").child("lenses").child(fromWhere).child(brandName);
        FirebaseProductAdapter adapter=new FirebaseProductAdapter(mActivity, Products.class,R.layout.grid_single_item,mRef,"brands","none");
        try {
            viewHolder.gridView.setAdapter(adapter);
        }catch (NullPointerException e){}
        viewHolder.empty_view =v.findViewById(R.id.empty_view_main);
        viewHolder.gridView.setEmptyView(viewHolder.empty_view);
        viewHolder.seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mActivity, ProductsActivity.class);
                intent.putExtra("BRAND",brandName);
                intent.putExtra("FROM",fromWhere);
                intent.putExtra("CATEGORY",mCategory);
                mActivity.startActivity(intent);
            }
        });

    }

    static class ViewHolder {
        TextView txtName;
        Button seeAll;
        GridView gridView;
        View empty_view;
    }
}

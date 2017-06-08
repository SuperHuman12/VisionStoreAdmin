package com.digiclack.visionstoreadmin.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.digiclack.visionstoreadmin.ProductsActivity;
import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.Utils.Utils;
import com.digiclack.visionstoreadmin.model.Products;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

import java.util.HashMap;

import static java.security.AccessController.getContext;

/**
 * Created by Zar on 6/8/2017.
 */

public class FirebaseBrandsAdapter extends FirebaseListAdapter<HashMap<String,Object>> {
        private String fromWhere;
        Activity mActivity;
        Query mRef;
    public FirebaseBrandsAdapter(Activity activity, Class<HashMap<String, Object>> modelClass, int modelLayout, Query ref,String fromWhere) {
        super(activity, modelClass, modelLayout, ref);
        this.fromWhere=fromWhere;
    }

    @Override
    protected void populateView(View v, HashMap<String, Object> model, int position) {
        ViewHolder viewHolder=new ViewHolder();

        HashMap<String,Object> hashMap=getItem(position);
        final String brandName=hashMap.get("brand").toString();
        viewHolder.txtName= (TextView) v.findViewById(R.id.txt_category);
        viewHolder.txtName.setText(brandName);
        viewHolder.gridView= (GridView) v.findViewById(R.id.gridview_products_activity);
        viewHolder.seeAll= (Button) v.findViewById(R.id.btn_seeall);
        mRef= Utils.getDatabase().getReference().child("products").child("lenses").child(fromWhere).child(brandName);
        FirebaseProductAdapter adapter=new FirebaseProductAdapter(mActivity, Products.class,R.layout.grid_single_item,mRef);
        viewHolder.gridView.setAdapter(adapter);
        viewHolder.seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mActivity, ProductsActivity.class);
                intent.putExtra("BRAND",brandName);
                intent.putExtra("FROM",fromWhere);
                mActivity.startActivity(intent);
            }
        });

    }

    static class ViewHolder {
        TextView txtName;
        Button seeAll;
        GridView gridView;
    }
}

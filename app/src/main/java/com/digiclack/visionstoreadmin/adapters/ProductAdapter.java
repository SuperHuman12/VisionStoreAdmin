package com.digiclack.visionstoreadmin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.model.Product;

import java.util.ArrayList;

/**
 * Created by Zar on 6/1/2017.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, ArrayList<Product> list) {
        super(context, 0,list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        if(gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_single_item, parent, false);
        }
        Product item=getItem(position);
        ImageView pImage= (ImageView) gridItemView.findViewById(R.id.grid_image_product);
        TextView pName= (TextView) gridItemView.findViewById(R.id.txt_p_name);
        TextView pPrice= (TextView) gridItemView.findViewById(R.id.txt_p_price);
        pImage.setImageResource(item.getpImage());
        pName.setText(item.getpBrand()+" "+item.getpModelName());
        pPrice.setText("Rs. "+item.getpPrice());

        return gridItemView;
    }
}

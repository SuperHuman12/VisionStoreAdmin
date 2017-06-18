package com.digiclack.visionstoreadmin.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.model.Products;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amirdora on 6/15/2017.
 */

public class ProductAdapter extends ArrayAdapter<Products> {

    public ProductAdapter(Context context, ArrayList<Products> productsArrayList) {
        super(context, 0, productsArrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_single_item2, parent, false);
        }


        Products currentProduct = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView productName = (TextView) convertView.findViewById(R.id.txt_p_name);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        productName.setText(currentProduct.getpModelName());

        // Find the TextView in the list_item.xml layout with the ID default_text_view.
        TextView productPrice = (TextView) convertView.findViewById(R.id.txt_p_price);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        productPrice.setText(currentProduct.getpPrice());


        return listItemView;

    }
}

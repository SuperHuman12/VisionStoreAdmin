package com.digiclack.visionstoreadmin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.model.Category;
import com.digiclack.visionstoreadmin.model.Product;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by Zar on 6/1/2017.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {


    public CategoryAdapter(Context context,ArrayList<Category> list ) {
        super(context, 0,list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.main_list_item, parent, false);
        }
        Category item=getItem(position);
        TextView txtName= (TextView) listItemView.findViewById(R.id.txt_category);
        Button seeAll= (Button) listItemView.findViewById(R.id.btn_seeall);
        GridView gridView= (GridView) listItemView.findViewById(R.id.product_gridview);
        txtName.setText(item.getcName());
        ArrayList<Product> products=item.getProducts();
        ProductAdapter adapter=new ProductAdapter(getContext(),products);
        gridView.setAdapter(adapter);

        return listItemView;
    }
}

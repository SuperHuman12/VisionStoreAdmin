package com.digiclack.visionstoreadmin.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.model.Product;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Zar on 6/1/2017.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, ArrayList<Product> list) {
        super(context, 0,list);
    }

    private static final String TAG = "ProductAdapter";
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        if(gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_single_item, parent, false);
        }
        final Product item=getItem(position);
        final ViewHolder holder=new ViewHolder();
        holder.pImage= (ImageView) gridItemView.findViewById(R.id.grid_image_product);
        holder.pName= (TextView) gridItemView.findViewById(R.id.txt_p_name);
        holder.pPrice= (TextView) gridItemView.findViewById(R.id.txt_p_price);
        holder.pImage.setImageResource(item.getpImage());
        holder.pName.setText(item.getpBrand()+" "+item.getpModelName());
       /* holder.remove= (FloatingActionButton) gridItemView.findViewById(R.id.fab_remove_product);*/
        holder.pPrice.setText("Rs. "+item.getpPrice());
        gridItemView.setTag(holder);
      /*  holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            removeProduct(position,item.getpBrand()+" "+item.getpModelName());
            }
        });*/

        return gridItemView; 
    }

    static class ViewHolder {
        ImageView pImage;
        TextView pName;
        TextView pPrice;
        FloatingActionButton remove;
    }

    public void removeProduct(final int position, String currentProduct) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Delete");
        alertDialog.setMessage(currentProduct);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e(TAG,"item removed"+position);
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}

package com.digiclack.visionstoreadmin.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.digiclack.visionstoreadmin.Fragments.navigationActivity.ContentLensesFragment;
import com.digiclack.visionstoreadmin.Fragments.navigationActivity.EyeGlassesFragment;
import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.Utils.Utils;
import com.digiclack.visionstoreadmin.model.Brand;
import com.digiclack.visionstoreadmin.model.Products;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by Zar on 6/1/2017.
 */

public class CategoryAdapter extends ArrayAdapter<Brand> {

    FragmentManager manager;
    Activity mAcitvity;
    public CategoryAdapter(Context context, ArrayList<Brand> list , FragmentManager manager, Activity mActivity) {
        super(context, 0,list);
        this.manager=manager;
        this.mAcitvity=mActivity;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.main_list_item, parent, false);
        }
        final Brand item=getItem(position);
        final ViewHolder holder=new ViewHolder();
        holder.txtName= (TextView) listItemView.findViewById(R.id.txt_category);
        holder.seeAll= (Button) listItemView.findViewById(R.id.btn_seeall);
        holder.gridView= (GridView) listItemView.findViewById(R.id.product_gridview);
        holder.empty_view=listItemView.findViewById(R.id.empty_view_main);
        holder.txtName.setText(item.getBrandName());
        listItemView.setTag(holder);
        String name=item.getBrandName();
        if (name.equals("Contact Lenses")) {
            Query mRef= Utils.getDatabase().getReference().child("margeProducts").child("lenses");
            final FirebaseProductAdapter adapter=new FirebaseProductAdapter(mAcitvity,Products.class,R.layout.grid_single_item,mRef,"home","none");
            holder.gridView.setAdapter(adapter);
            holder.gridView.setEmptyView(holder.empty_view);
        }
        else if (name.equals("Eye Glasses")) {
            Query mRef= Utils.getDatabase().getReference().child("margeProducts").child("eyeGlasses");
            FirebaseProductAdapter adapter=new FirebaseProductAdapter(mAcitvity,Products.class,R.layout.grid_single_item,mRef,"home","none");
            holder.gridView.setAdapter(adapter);
            holder.gridView.setEmptyView(holder.empty_view);
        }
        else if (name.equals("Sun Glasses")) {
            Query mRef= Utils.getDatabase().getReference().child("margeProducts").child("sunGlasses");
            FirebaseProductAdapter adapter=new FirebaseProductAdapter(mAcitvity,Products.class,R.layout.grid_single_item,mRef,"home","none");
            holder.gridView.setAdapter(adapter);
            holder.gridView.setEmptyView(holder.empty_view);
        }

        holder.seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("tag","clicked");
                if (item.getBrandName().equals("Contact Lenses")) {
                    ContentLensesFragment fragment=new ContentLensesFragment();
                    manager.beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
                }
                else if (item.getBrandName().equals("Eye Glasses")) {
                    EyeGlassesFragment fragment=new EyeGlassesFragment();
                    manager.beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
                }

                else if (item.getBrandName().equals("Sun Glasses")) {
                    /*ContentLensesFragment fragment=new ContentLensesFragment();
                    manager.beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();*/
                }


            }
        });

        return listItemView;
    }


    static class ViewHolder {
        TextView txtName;
        Button seeAll;
        GridView gridView;
        View empty_view;
        FloatingActionButton fab;
    }

}

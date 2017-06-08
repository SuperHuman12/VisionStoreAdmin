package com.digiclack.visionstoreadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.digiclack.visionstoreadmin.Fragments.navigationActivity.ContentLensesFragment;
import com.digiclack.visionstoreadmin.Fragments.navigationActivity.EyeGlassesFragment;
import com.digiclack.visionstoreadmin.ProductsActivity;
import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.model.Category;
import com.digiclack.visionstoreadmin.model.Product;
import java.util.ArrayList;

/**
 * Created by Zar on 6/1/2017.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {

    FragmentManager manager;
    String fromWhere;

    public CategoryAdapter(Context context, ArrayList<Category> list , FragmentManager manager,String fromWhere) {
        super(context, 0,list);
        this.manager=manager;
        this.fromWhere=fromWhere;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.main_list_item, parent, false);
        }
        final Category item=getItem(position);
        ViewHolder holder=new ViewHolder();
        holder.txtName= (TextView) listItemView.findViewById(R.id.txt_category);
        holder.seeAll= (Button) listItemView.findViewById(R.id.btn_seeall);
        holder.gridView= (GridView) listItemView.findViewById(R.id.product_gridview);
        holder.txtName.setText(item.getcName());
        listItemView.setTag(holder);
        ArrayList<Product> products=item.getProducts();
        if (products!=null) {
            ProductAdapter adapter=new ProductAdapter(getContext(),products);
            holder.gridView.setAdapter(adapter);
        }
        else {
            Log.e("empty","empty");
        }

        holder.seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("tag","clicked");
                if (item.getcName().equals("Contact Lenses")) {
                    ContentLensesFragment fragment=new ContentLensesFragment();
                    manager.beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
                }
                else if (item.getcName().equals("Eye Glasses")) {
                    EyeGlassesFragment fragment=new EyeGlassesFragment();
                    manager.beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();

                }

                else if (item.getcName().equals("Sun Glasses")) {
                    /*ContentLensesFragment fragment=new ContentLensesFragment();
                    manager.beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();*/
                }
                else {
                    String brandName=item.getcName();
                    Intent intent=new Intent(getContext(), ProductsActivity.class);
                    intent.putExtra("BRAND",brandName);
                    intent.putExtra("FROM",fromWhere);
                    getContext().startActivity(intent);
                }

            }
        });

        return listItemView;
    }


    static class ViewHolder {
        TextView txtName;
        Button seeAll;
        GridView gridView;
    }

}

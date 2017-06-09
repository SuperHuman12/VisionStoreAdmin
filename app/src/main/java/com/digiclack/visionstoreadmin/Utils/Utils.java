package com.digiclack.visionstoreadmin.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.digiclack.visionstoreadmin.model.Brand;
import com.digiclack.visionstoreadmin.model.Category;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Zar on 6/8/2017.
 */

public class Utils {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true); // keeps firebase database offline on cache
        }
        return mDatabase;
    }
    public static void addBrand(final String fromWhere, Context context) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        alertDialog.setMessage("Add New Brand");
        final EditText input=new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setHint("Enter Brand Name");
        alertDialog.setView(input);
        alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name=input.getText().toString();
                if (!name.isEmpty()) {
                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("products").child("lenses").child(fromWhere);
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("brands").child("lenses").child(fromWhere);
                    HashMap<String,Object> hashMap=new HashMap<String, Object>();
                    HashMap<String,Object> hashMapBrands=new HashMap<String, Object>();
                    hashMapBrands.put("brand",name);
                    hashMap.put(name,"added");
                    ref.updateChildren(hashMap);
                    reference.push().setValue(new Brand(name));
                }

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });
        alertDialog.show();
    }
}

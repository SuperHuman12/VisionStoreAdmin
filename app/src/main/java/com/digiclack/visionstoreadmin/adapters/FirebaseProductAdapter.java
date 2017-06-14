package com.digiclack.visionstoreadmin.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.model.Products;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

/**
 * Created by Zar on 6/7/2017.
 */

public class FirebaseProductAdapter extends FirebaseListAdapter<Products> {
    private static final String TAG = "FirebaseProductAdapter";
    private String fromWhere,category;
    private StorageReference mFirebaseStorage;

    public FirebaseProductAdapter(Activity activity, Class<Products> modelClass, int modelLayout, Query ref,String fromWhere,String category) {
        super(activity, modelClass, modelLayout, ref);
        this.fromWhere=fromWhere;
        this.category=category;
    }

    @Override
    protected void populateView(View v, Products model, int position) {
        final Products products=getItem(position);
        final DatabaseReference ref=getRef(position);
        final String key=ref.getKey();
        HashMap<String,Object> imagesUrl=new HashMap<>();
        imagesUrl=products.getImages();
        String url=imagesUrl.get("image0").toString();
        ViewHolder holder=new ViewHolder();
        holder.pImage= (ImageView) v.findViewById(R.id.grid_image_product);
        holder.pName= (TextView) v.findViewById(R.id.txt_p_name);
        holder.pPrice= (TextView) v.findViewById(R.id.txt_p_price);
        holder.remove= (ImageButton) v.findViewById(R.id.remove_product);
        holder.favorite=(ImageButton) v.findViewById(R.id.add_favorite_product);


        // Reference to an image file in Firebase Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);


        // Load the image using Glide
        Glide.with(mActivity)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(holder.pImage);
        holder.pName.setText(products.getpModelName());
        holder.pPrice.setText("Rs. "+products.getpPrice());
        v.setTag(holder);
        if(fromWhere.equals("products")) {

            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteProduct(products.getpModelName(), ref, key);
                }
            });
            holder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteProduct(products.getpModelName(), ref, key);
                }
            });
        }
    }
    static class ViewHolder {
        ImageView pImage;
        TextView pName;
        TextView pPrice;
        ImageButton remove;
        ImageButton favorite;
    }

    public void deleteProduct(String name, final DatabaseReference reference,final String key) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
        alertDialog.setTitle("Delete");
        alertDialog.setMessage("Do you want to delete :"+name);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reference.removeValue();
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("margeProducts").child(category).child(key);
                mFirebaseStorage=FirebaseStorage.getInstance().getReference();
                for (int i=0; i<3;i++) {
                    StorageReference images=mFirebaseStorage.child("images").child(key).child(i+".png");
                    images.delete();
                }
                ref.removeValue();
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

    public void favoriteProduct(String name, final DatabaseReference reference,final String key) {
        Toast.makeText(mActivity, "Favorite Clicked", Toast.LENGTH_SHORT).show();    }

}

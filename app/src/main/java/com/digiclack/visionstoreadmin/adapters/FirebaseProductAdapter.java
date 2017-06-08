package com.digiclack.visionstoreadmin.adapters;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.model.Products;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

/**
 * Created by Zar on 6/7/2017.
 */

public class FirebaseProductAdapter extends FirebaseListAdapter<Products> {
    private static final String TAG = "FirebaseProductAdapter";
    public FirebaseProductAdapter(Activity activity, Class<Products> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View v, Products model, int position) {
        Products products=getItem(position);
        HashMap<String,Object> imagesUrl=new HashMap<>();
        imagesUrl=products.getImages();
        String url=imagesUrl.get("image0").toString();
        Log.e(TAG,"run"+products.getpPrice());
        ViewHolder holder=new ViewHolder();
        holder.pImage= (ImageView) v.findViewById(R.id.grid_image_product);
        holder.pName= (TextView) v.findViewById(R.id.txt_p_name);
        holder.pPrice= (TextView) v.findViewById(R.id.txt_p_price);
        /*holder.pImage.setImageResource(item.getpImage());*/
       /* try {
            URL imageOne=new URL(imagesUrl.get("image0").toString());
            Bitmap bmp = BitmapFactory.decodeStream(imageOne.openConnection().getInputStream());
            holder.pImage.setImageBitmap(bmp);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // Reference to an image file in Firebase Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);


// Load the image using Glide
        Glide.with(mActivity)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(holder.pImage);
        holder.pName.setText(products.getpModelName());
       /* holder.remove= (FloatingActionButton) gridItemView.findViewById(R.id.fab_remove_product);*/
        holder.pPrice.setText("Rs. "+products.getpPrice());
        v.setTag(holder);
      /*  holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            removeProduct(position,item.getpBrand()+" "+item.getpModelName());
            }
        });*/
    }
    static class ViewHolder {
        ImageView pImage;
        TextView pName;
        TextView pPrice;
        FloatingActionButton remove;
    }
}

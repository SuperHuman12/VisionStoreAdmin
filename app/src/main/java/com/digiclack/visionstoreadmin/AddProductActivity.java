package com.digiclack.visionstoreadmin;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.digiclack.visionstoreadmin.Utils.Utils;
import com.digiclack.visionstoreadmin.model.Products;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AddProductActivity";
    Button btnInc,btnDec,selectImages;
    ImageView imgOne,imgTwo,imgThird;
    EditText edtPname,edtPprice,edtPquantity;
    private int i=0;
    private String mProductKey;
    private boolean mCurrentActionEdit=false;
    private DatabaseReference mDatabaseRef;
    private StorageReference mFirebaseStorage;
    boolean mCheckImages=false;
    private int mQuantity;
    private String mBrand,mFrom;
    private List<Uri> mUploadedImages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Intent intent=getIntent();
        mBrand=intent.getStringExtra("BRAND");
        mFrom=intent.getStringExtra("FROM");
        initComponent();
        imgOne.setOnClickListener(this);
        imgTwo.setOnClickListener(this);
        imgThird.setOnClickListener(this);
        btnDec.setOnClickListener(this);
        btnInc.setOnClickListener(this);
        selectImages.setOnClickListener(this);
        String check=intent.getStringExtra("Name");
        Bundle bundle=intent.getExtras();

        //checking that user come from the intent of edit the product(product item click) or want to add new product from add click
        if (check.equals("fab")) {
            setTitle("Add Product");
            mCurrentActionEdit=false;
        }
        else {
            setTitle("Edit Product");
            mCurrentActionEdit=true;
            imgOne.setImageResource(bundle.getInt("Image"));
            edtPname.setText(bundle.getString("Name"));
            edtPprice.setText(bundle.getString("Price"));
        }

    }
    public void initComponent() {
        btnInc= (Button) findViewById(R.id.product_add_btn_inc);
        btnDec= (Button) findViewById(R.id.product_add_btn_inc);
        imgOne= (ImageView) findViewById(R.id.product_add_img_one);
        imgTwo= (ImageView) findViewById(R.id.product_add_img_two);
        imgThird= (ImageView) findViewById(R.id.product_add_img_three);
        edtPname= (EditText) findViewById(R.id.product_add_edt_name);
        edtPprice= (EditText) findViewById(R.id.product_add_edt_price);
        edtPquantity= (EditText) findViewById(R.id.product_add_quantity);
        mDatabaseRef= Utils.getDatabase().getReference();
        mFirebaseStorage= FirebaseStorage.getInstance().getReference();
        selectImages= (Button) findViewById(R.id.product_btn_select_images);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_btn_select_images:
                startImageChooserActivity();
                break;
            case R.id.product_add_btn_inc:
                if (mQuantity>=0) {
                    mQuantity=mQuantity+1;
                    edtPquantity.setText(" "+mQuantity);
            }
                break;
            case R.id.product_add_btn_dec:
                if (mQuantity>0)
                {
                    mQuantity=mQuantity-1;
                    edtPquantity.setText(""+mQuantity);
                }
                break;

        }
    }

    /*
    * inflating custom option menu for adding product or deleting product
    * */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_edit_product,menu);
        MenuItem remove=menu.findItem(R.id.action_delete);
        //check if product is editing add option item to delete the product
        remove.setVisible(mCurrentActionEdit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case R.id.action_save:
                if (mCurrentActionEdit) {
                    updateProduct(mProductKey);// if the user select the existing product product changes will be updated
                }
                else {
                    if (mCheckImages) {
                        addProductToFb();//else user will add completely new product
                        Log.e(TAG,String.valueOf(mUploadedImages.size()));
                    }
                    else {
                        Toast.makeText(this,"Please select minimum 3 images",Toast.LENGTH_LONG).show();
                    }


                }

                return true;
            case R.id.action_delete:
                deleteProductFromFb(mProductKey); //deleting the existing product from firebase
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    * selecting images from gallery
    * */
    private void startImageChooserActivity() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
    }

    /*
    * displaying images selected from gallery
    * */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                    && (null == data.getData())) {
                ClipData clipdata = data.getClipData();
                for (int i = 0; i < 3; i++) {
                    /* */
                    //DO something
                    if (i==0) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), clipdata.getItemAt(i).getUri());
                            imgOne.setImageBitmap(bitmap);
                            imgOne.setVisibility(View.VISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                       /* imgOne.setImageURI(clipdata.getItemAt(i).getUri());*/

                    }
                    else if (i==1) {
                        /*imgTwo.setImageURI(clipdata.getItemAt(i).getUri());*/
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), clipdata.getItemAt(i).getUri());
                            imgTwo.setImageBitmap(bitmap);
                            imgTwo.setVisibility(View.VISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    else if (i==2) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), clipdata.getItemAt(i).getUri());
                            imgThird.setImageBitmap(bitmap);
                            imgThird.setVisibility(View.VISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mCheckImages=true;
                        selectImages.setVisibility(View.INVISIBLE);
                    }

                }

            }
            else {
                Uri image=data.getData();
                imgOne.setImageURI(image);
            }
        }

    }

    /*
    * Adding product to firebase with the reference of product key
    * */
    public void addProductToFb() {
        String name=edtPname.getText().toString();
        String price=edtPprice.getText().toString();
        String quantity=edtPquantity.getText().toString();

        if (name.isEmpty() && price.isEmpty() && quantity.isEmpty()) {
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_LONG).show();
        }
        else {
            final DatabaseReference ref = mDatabaseRef.child("products").child("lenses").child(mFrom).child(mBrand).push();
            mProductKey = ref.getKey();
            final HashMap<String, Object> list = new HashMap<>();
            for (i = 0; i < 3; i++) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if (i == 0) {
                    imgOne.setDrawingCacheEnabled(true);
                    imgOne.buildDrawingCache();
                    Bitmap bitmap = imgOne.getDrawingCache();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                } else if (i == 1) {
                    imgTwo.setDrawingCacheEnabled(true);
                    imgTwo.buildDrawingCache();
                    Bitmap bitmap = imgTwo.getDrawingCache();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                } else if (i == 2) {
                    imgThird.setDrawingCacheEnabled(true);
                    imgThird.buildDrawingCache();
                    Bitmap bitmap = imgThird.getDrawingCache();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                }

                byte[] data = baos.toByteArray();
                StorageReference productImageRef = mFirebaseStorage.child("lenses/"+mFrom+"/"+mBrand+"/" + mProductKey + "/" + i + ".png");
                UploadTask uploadTask = productImageRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Log.e(TAG, "not uploaded" + exception);
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        mUploadedImages.add(downloadUrl);
                        if (mUploadedImages.size() == 3) {

                            Log.e(TAG, String.valueOf(mUploadedImages.size()));
                            for (int i = 0; i < mUploadedImages.size(); i++) {
                                Log.e(TAG, mUploadedImages.get(i).toString());
                                list.put("image" + i, mUploadedImages.get(i).toString());
                            }
                            Products products = new Products(edtPname.getText().toString(), edtPprice.getText().toString(), list,mQuantity);
                            ref.setValue(products);
                            Toast.makeText(AddProductActivity.this,"Product Added Successfully",Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        }
    }

    /*
    * removing product from firebase with the reference of product key
    * */
    public void deleteProductFromFb(String key) {

    }
    /*
    * Updating the exiting product into firebase database
    * */
    public void updateProduct(String key) {

    }
}

package com.digiclack.visionstoreadmin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.digiclack.visionstoreadmin.Utils.Utils;
import com.digiclack.visionstoreadmin.model.Products;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    Button btnInc, btnDec, selectImages;
    ImageView imgOne, imgTwo, imgThird;
    EditText edtPname, edtPprice, edtPquantity;
    private int i = 0;
    private String mProductKey;
    private boolean mCurrentActionEdit = false;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mRefMarge;
    private StorageReference mFirebaseStorage;
    boolean mCheckImages = false;
    private int mQuantity;
    private ProgressDialog mAddingProductDialog;
    private String mBrand, mFrom, mCategory;
    private List<Uri> mUploadedImages = new ArrayList<>();
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Intent intent = getIntent();
        mBrand = intent.getStringExtra("BRAND");
        mFrom = intent.getStringExtra("FROM");
        mCategory = intent.getStringExtra("CATEGORY");
        mProductKey = intent.getStringExtra("KEY");
        initComponent();
        imgOne.setOnClickListener(this);
        imgTwo.setOnClickListener(this);
        imgThird.setOnClickListener(this);
        btnDec.setOnClickListener(this);
        btnInc.setOnClickListener(this);
        selectImages.setOnClickListener(this);
        String check = intent.getStringExtra("EDIT_ADD");

        //checking that user come from the intent of edit the product(product item click) or want to add new product from add click
        if (check.equals("add")) {
            setTitle("Add Product");
            mCurrentActionEdit = false;
        } else {
            setTitle("Edit Product");
            mCurrentActionEdit = true;
            //displaying current selected product on view
            final DatabaseReference refMarge = Utils.getDatabase().getReference().child("margeProducts").child(mCategory).child(mProductKey);
            refMarge.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Products product = dataSnapshot.getValue(Products.class);
                    edtPquantity.setText("" + product.getQuantity());
                    edtPname.setText("" + product.getpModelName());
                    edtPprice.setText("" + product.getpPrice());
                    HashMap<String, Object> hashMap = product.getImages();
                    for (int i = 0; i < 3; i++) {
                        String url = hashMap.get("image" + i).toString();
                        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(url);
                        if (i == 0) {
                            loadImagesOnImageView(imgOne, ref);
                        } else if (i == 1) {
                            loadImagesOnImageView(imgTwo, ref);
                        } else if (i == 2) {
                            loadImagesOnImageView(imgThird, ref);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void initComponent() {
        btnInc = (Button) findViewById(R.id.product_add_btn_inc);
        btnDec = (Button) findViewById(R.id.product_add_btn_dec);
        imgOne = (ImageView) findViewById(R.id.product_add_img_one);
        imgTwo = (ImageView) findViewById(R.id.product_add_img_two);
        imgThird = (ImageView) findViewById(R.id.product_add_img_three);
        edtPname = (EditText) findViewById(R.id.product_add_edt_name);
        edtPprice = (EditText) findViewById(R.id.product_add_edt_price);
        edtPquantity = (EditText) findViewById(R.id.product_add_quantity);
        mDatabaseRef = Utils.getDatabase().getReference();
        mFirebaseStorage = FirebaseStorage.getInstance().getReference();
        selectImages = (Button) findViewById(R.id.product_btn_select_images);
        mAddingProductDialog = new ProgressDialog(this);
        mAddingProductDialog.setTitle("Loading...");
        mAddingProductDialog.setMessage("Adding product to Firebase...");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_btn_select_images:
                startImageChooserActivity();
                break;
            case R.id.product_add_btn_inc:
                if (mQuantity >= 0) {
                    mQuantity = mQuantity + 1;
                    edtPquantity.setText(" " + mQuantity);
                }
                break;
            case R.id.product_add_btn_dec:
                if (mQuantity > 0) {
                    mQuantity = mQuantity - 1;
                    edtPquantity.setText("" + mQuantity);
                }
                break;

        }
    }

    /*
    * inflating custom option menu for adding product or deleting product
    * */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_edit_product, menu);
        MenuItem remove = menu.findItem(R.id.action_delete);
        //check if product is editing add option item to delete the product
        remove.setVisible(mCurrentActionEdit);
        //hide actionbar icons for save and add to cart and search
        if (menu.findItem(R.id.action_favorite) != null)
            menu.findItem(R.id.action_favorite).setVisible(false);
        if (menu.findItem(R.id.action_search) != null)
            menu.findItem(R.id.action_search).setVisible(false);
        if (menu.findItem(R.id.action_add_to_cart) != null)
            menu.findItem(R.id.action_add_to_cart).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                if (mCurrentActionEdit) {
                    mAddingProductDialog.show();
                    productModifyOrAdd("modify");// if the user select the existing product product changes will be updated
                } else {
                    if (mCheckImages) {
                        mAddingProductDialog.show();
                        productModifyOrAdd("add");//else user will add completely new product
                    } else {
                        Toast.makeText(this, "Please select minimum 3 images", Toast.LENGTH_LONG).show();
                    }
                }

                return true;
            case R.id.action_delete:
                mDatabaseRef = mDatabaseRef.child("products").child(mCategory).child(mFrom).child(mBrand).child(mProductKey);
                deleteProductFromFb(mProductKey, mDatabaseRef); //deleting the existing product from firebase
                return true;
            case android.R.id.home:
                Log.e(TAG, "product activity back actionbar pressed");
                onBackPressed();
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
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
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
                    if (i == 0) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), clipdata.getItemAt(i).getUri());
                            imgOne.setImageBitmap(bitmap);
                            imgOne.setVisibility(View.VISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else if (i == 1) {

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), clipdata.getItemAt(i).getUri());
                            imgTwo.setImageBitmap(bitmap);
                            imgTwo.setVisibility(View.VISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else if (i == 2) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), clipdata.getItemAt(i).getUri());
                            imgThird.setImageBitmap(bitmap);
                            imgThird.setVisibility(View.VISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mCheckImages = true;
                        selectImages.setVisibility(View.INVISIBLE);
                    }

                }

            } else {
                Uri image = data.getData();
                imgOne.setImageURI(image);
            }
        }

    }

    /*
    * Adding product to firebase of modifying with the reference of add or modify
    * */
    public void productModifyOrAdd(final String addOrModify) {
        String name = edtPname.getText().toString();
        String price = edtPprice.getText().toString();
        String quantity = edtPquantity.getText().toString();

        if (name.isEmpty() && price.isEmpty() && quantity.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
        } else {
            //final DatabaseReference ref;

            if (addOrModify.equals("add")) {
                mDatabaseRef = mDatabaseRef.child("products").child(mCategory).child(mFrom).child(mBrand).push();
                mProductKey = mDatabaseRef.getKey();
                mRefMarge = Utils.getDatabase().getReference().child("margeProducts").child(mCategory).child(mProductKey);
            } else if (addOrModify.equals("modify")) {
                mDatabaseRef = mDatabaseRef.child("products").child(mCategory).child(mFrom).child(mBrand).child(mProductKey);
                mRefMarge = Utils.getDatabase().getReference().child("margeProducts").child(mCategory).child(mProductKey);
            }

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
                StorageReference productImageRef = mFirebaseStorage.child("images").child(mProductKey).child(i + ".png");
                UploadTask uploadTask = productImageRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        mAddingProductDialog.dismiss();
                        Toast.makeText(AddProductActivity.this, "Faild to upload images", Toast.LENGTH_SHORT).show();
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
                            Products products = new Products(edtPname.getText().toString(), edtPprice.getText().toString(), list, mQuantity);
                            if (addOrModify.equals("add")) {
                                mDatabaseRef.setValue(products);
                                mRefMarge.setValue(products);
                                mAddingProductDialog.dismiss();
                                Toast.makeText(AddProductActivity.this, "Product Added Successfully", Toast.LENGTH_LONG).show();
                            } else if (addOrModify.equals("modify")) {
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("updated", products);
                                mDatabaseRef.updateChildren(hashMap);
                                mRefMarge.updateChildren(hashMap);
                                mAddingProductDialog.dismiss();
                                Toast.makeText(AddProductActivity.this, "Product Updated Successfully", Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                });
            }
        }
    }

    /*
    * removing product from firebase with the reference of product key
    * */
    public void deleteProductFromFb(final String key, final DatabaseReference reference) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddProductActivity.this);
        alertDialog.setTitle("Delete");
        alertDialog.setMessage("Do you want to delete :");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reference.removeValue();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("margeProducts").child(mCategory).child(key);
                mDatabaseRef = mDatabaseRef.child("products").child(mCategory).child(mFrom).child(mBrand).child(key);
                mFirebaseStorage = FirebaseStorage.getInstance().getReference();
                for (int i = 0; i < 3; i++) {
                    StorageReference images = mFirebaseStorage.child("images").child(key).child(i + ".png");
                    images.delete();
                }
                ref.removeValue();
                mDatabaseRef.removeValue();
                Toast.makeText(AddProductActivity.this, "Product deleted Successfully", Toast.LENGTH_SHORT).show();
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


    public void loadImagesOnImageView(ImageView imageView, StorageReference storageReference) {
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);
    }

}
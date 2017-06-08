package com.digiclack.visionstoreadmin.model;


import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Zar on 6/4/2017.
 */

public class Products {
    String pModelName,pPrice;
    int quantity;
    HashMap<String,Object> images=new HashMap<>();
    /*String images;*/


    Products() {

    }
    public Products(String pModelName,String pPrice,HashMap<String,Object> images,int quantity) {
        this.images=images;
        this.pPrice=pPrice;
        this.pModelName=pModelName;
        this.pModelName=pModelName;
        this.quantity=quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getpPrice() {
        return pPrice;
    }

    public String getpModelName() {
        return pModelName;
    }
/*
    public List<Uri> getImages() {
        return images;
    }*/

    public HashMap<String, Object> getImages() {
        return images;
    }
}

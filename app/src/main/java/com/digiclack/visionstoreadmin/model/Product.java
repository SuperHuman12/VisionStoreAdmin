package com.digiclack.visionstoreadmin.model;

/**
 * Created by Zar on 6/1/2017.
 */

public class Product {
    String pBrand,pModelName,pPrice;
    int pImage;

    Product() {

    }
    public Product(String pBrand,String pModelName,String pPrice,int pImage) {
        this.pImage=pImage;
        this.pPrice=pPrice;
        this.pModelName=pModelName;
        this.pBrand=pBrand;
    }

    public String getpPrice() {
        return pPrice;
    }

    public String getpModelName() {
        return pModelName;
    }

    public String getpBrand() {
        return pBrand;
    }

    public int getpImage() {
        return pImage;
    }
}

package com.digiclack.visionstoreadmin.model;

import java.util.ArrayList;

/**
 * Created by Zar on 6/1/2017.
 */

public class Category {
    private ArrayList<Product> products;
    private String cName;

    public Category() {}
    public Category(ArrayList<Product> products,String cName) {
        this.cName=cName;
        this.products=products;
    }
    public Category(String name) {
        this.cName=name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public String getcName() {
        return cName;
    }
}

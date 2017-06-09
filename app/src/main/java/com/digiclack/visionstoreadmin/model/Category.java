package com.digiclack.visionstoreadmin.model;

import java.util.ArrayList;

/**
 * Created by Zar on 6/1/2017.
 */

public class Category {
    private String cName;

    public Category() {}

    public Category(String name) {
        this.cName=name;
    }

    public String getcName() {
        return cName;
    }
}

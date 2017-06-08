package com.digiclack.visionstoreadmin.Utils;

import com.google.firebase.database.FirebaseDatabase;

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
}

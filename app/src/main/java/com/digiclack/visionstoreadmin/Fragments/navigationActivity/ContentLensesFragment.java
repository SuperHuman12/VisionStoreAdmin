package com.digiclack.visionstoreadmin.Fragments.navigationActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digiclack.visionstoreadmin.R;

/**
 * Created by Zar on 6/1/2017.
 */

public class ContentLensesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content_main,container,false);

        return view;
    }
}

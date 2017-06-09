package com.digiclack.visionstoreadmin.Fragments.navigationActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.adapters.CategoryAdapter;
import com.digiclack.visionstoreadmin.model.Brand;

import java.util.ArrayList;

/**
 * Created by Zar on 6/1/2017.
 */

public class HomeFragment extends Fragment {
    ListView mCategories;
    CategoryAdapter mAdapter;
    ArrayList<Brand> mCategoryList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content_main,container,false);
        initComponent(view);
        mCategoryList.add(new Brand("Contact Lenses"));
        mCategoryList.add(new Brand("Eye Glasses"));
        mCategoryList.add(new Brand("Sun Glasses"));
        mAdapter=new CategoryAdapter(getContext(),mCategoryList,getActivity().getSupportFragmentManager(),getActivity());
        mCategories.setAdapter(mAdapter);
        return view;
    }
    public void initComponent(View view) {
        mCategories= (ListView) view.findViewById(R.id.main_list_view);
        mCategoryList=new ArrayList<>();
    }

}

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
import com.digiclack.visionstoreadmin.model.Category;
import com.digiclack.visionstoreadmin.model.Product;

import java.util.ArrayList;

/**
 * Created by Zar on 6/1/2017.
 */

public class HomeFragment extends Fragment {
    ListView mCategories;
    CategoryAdapter mAdapter;
    ArrayList<Product> mLenseList;
    ArrayList<Product> mEyeList;
    ArrayList<Product> mGlassesList;
    ArrayList<Category> mCategoryList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content_main,container,false);
        initComponent(view);
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mLenseList.add(new Product("Nike","786","200",R.drawable.lense));
        mEyeList.add(new Product("Tag","786","300",R.drawable.lense));
        mEyeList.add(new Product("Tag","786","300",R.drawable.lense));
        mEyeList.add(new Product("Tag","786","300",R.drawable.lense));
        mEyeList.add(new Product("Tag","786","300",R.drawable.lense));
        mGlassesList.add(new Product("Rayben","007","1000",R.drawable.lense));
        mGlassesList.add(new Product("Rayben","007","1000",R.drawable.lense));
        mGlassesList.add(new Product("Rayben","007","1000",R.drawable.lense));
        mGlassesList.add(new Product("Rayben","007","1000",R.drawable.lense));
        mCategoryList.add(new Category(mLenseList,"Contact Lenses"));
        mCategoryList.add(new Category(mEyeList,"Eye Glasses"));
        mCategoryList.add(new Category(mGlassesList,"Sun Glasses"));
        mAdapter=new CategoryAdapter(getContext(),mCategoryList,getActivity().getSupportFragmentManager(),"home");
        mCategories.setAdapter(mAdapter);

        return view;
    }
    public void initComponent(View view) {
        mCategories= (ListView) view.findViewById(R.id.main_list_view);
        mCategoryList=new ArrayList<>();
        mLenseList=new ArrayList<>();
        mEyeList=new ArrayList<>();
        mGlassesList=new ArrayList<>();
    }

}

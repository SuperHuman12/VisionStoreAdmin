package com.digiclack.visionstoreadmin.Fragments.navigationActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.digiclack.visionstoreadmin.adapters.CategoryAdapter;
import com.digiclack.visionstoreadmin.adapters.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.model.Category;
import com.digiclack.visionstoreadmin.model.Product;

import java.util.ArrayList;

/**
 * Created by Zar on 6/1/2017.
 *
 * this fragment is using for the transparent and colorless brands of contact lenses
 */

public class ContentLensesFragment extends Fragment {
    TabLayout tabs;
    ViewPager pager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contact_lenses,container,false);
        initComponents(view);
        return view;
    }
    private void initComponents(View view){
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        pager = (ViewPager) view.findViewById(R.id.pager);

        ArrayList<BaseFragment> pages = new ArrayList<>();

        TransparentLenses page1 = new TransparentLenses();
        page1.setTitle("Transparent");
        pages.add(page1);

        ColorLenses page2 = new ColorLenses();
        page2.setTitle("Color");
        pages.add(page2);
        PagerAdapter pagerAdapter=new PagerAdapter(getActivity().getSupportFragmentManager(),pages);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(pages.size());
        tabs.setupWithViewPager(pager);

    }

    public static class TransparentLenses extends BaseFragment {
        ListView mCategories;
        CategoryAdapter mAdapter;
        ArrayList<Product> mLenseList;
        ArrayList<Product> mEyeList;
        ArrayList<Product> mGlassesList;
        ArrayList<Category> mCategoryList;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_transparent_lenses,container,false);
            setFragmentView(view);
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
            mCategoryList.add(new Category(mLenseList,"Brand1 color"));
            mCategoryList.add(new Category(mEyeList,"Brand2 color"));
            mCategoryList.add(new Category(mGlassesList,"Brand3 color"));
            mAdapter=new CategoryAdapter(getContext(),mCategoryList,getActivity().getSupportFragmentManager());
            mCategories.setAdapter(mAdapter);

            return getFragmentView();
        }
        public void initComponent(View view) {
            mCategories= (ListView) view.findViewById(R.id.main_list_view);
            mCategoryList=new ArrayList<>();
            mLenseList=new ArrayList<>();
            mEyeList=new ArrayList<>();
            mGlassesList=new ArrayList<>();
        }
    }

    public static class ColorLenses extends BaseFragment {
        ListView mCategories;
        CategoryAdapter mAdapter;
        ArrayList<Product> mLenseList;
        ArrayList<Product> mEyeList;
        ArrayList<Product> mGlassesList;
        ArrayList<Category> mCategoryList;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_color_lenses,container,false);
            setFragmentView(view);
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
            mCategoryList.add(new Category(mLenseList,"Brand1 transp"));
            mCategoryList.add(new Category(mEyeList,"Brand2 transp"));
            mCategoryList.add(new Category(mGlassesList,"Brand3 transp"));
            mAdapter=new CategoryAdapter(getContext(),mCategoryList,getActivity().getSupportFragmentManager());
            mCategories.setAdapter(mAdapter);

            return getFragmentView();
        }
        public void initComponent(View view) {
            mCategories= (ListView) view.findViewById(R.id.main_list_view);
            mCategoryList=new ArrayList<>();
            mLenseList=new ArrayList<>();
            mEyeList=new ArrayList<>();
            mGlassesList=new ArrayList<>();
        }
    }
}

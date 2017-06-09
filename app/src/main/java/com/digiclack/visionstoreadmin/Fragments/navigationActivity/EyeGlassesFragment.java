package com.digiclack.visionstoreadmin.Fragments.navigationActivity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.Utils.Utils;
import com.digiclack.visionstoreadmin.adapters.FirebaseBrandsAdapter;
import com.digiclack.visionstoreadmin.adapters.PagerAdapter;
import com.digiclack.visionstoreadmin.model.Brand;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EyeGlassesFragment extends Fragment {
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

        MaleEyeGlasses page1 = new MaleEyeGlasses();
        page1.setTitle("Male");
        pages.add(page1);

        FemaleEyeGlasses page2 = new FemaleEyeGlasses();
        page2.setTitle("Female");
        pages.add(page2);

        KidsEyeGlasses page3 = new KidsEyeGlasses();
        page3.setTitle("Kids");
        pages.add(page3);

        PagerAdapter pagerAdapter=new PagerAdapter(getActivity().getSupportFragmentManager(),pages);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(pages.size());
        tabs.setupWithViewPager(pager);

    }

    public static class MaleEyeGlasses extends BaseFragment {
        ListView mCategories;
        FloatingActionButton fabAdd;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_transparent_lenses,container,false);
            setFragmentView(view);
            initComponent(view);
            Query mRef= Utils.getDatabase().getReference().child("brands").child("lenses").child("transparent");
            FirebaseBrandsAdapter adapter=new FirebaseBrandsAdapter(getActivity(),Brand.class,R.layout.main_list_item,mRef,"transparent","lenses");
            mCategories.setAdapter(adapter);
            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.addBrand("transparent",getContext());
                }
            });
            return getFragmentView();
        }
        public void initComponent(View view) {
            mCategories= (ListView) view.findViewById(R.id.main_list_view);
            fabAdd= (FloatingActionButton) view.findViewById(R.id.fab_transparent);
        }

    }

    public static class FemaleEyeGlasses extends BaseFragment {
        ListView mCategories;
        FloatingActionButton fabAdd;
        DatabaseReference mDatabaseRef;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_color_lenses,container,false);
            setFragmentView(view);
            initComponent(view);
            Query mRef=Utils.getDatabase().getReference().child("brands").child("lenses").child("color");
            FirebaseBrandsAdapter adapter=new FirebaseBrandsAdapter(getActivity(),Brand.class,R.layout.main_list_item,mRef,"color","lenses");
            mCategories.setAdapter(adapter);
            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.addBrand("color",getContext());
                }
            });
            return getFragmentView();
        }
        public void initComponent(View view) {
            mCategories= (ListView) view.findViewById(R.id.main_list_view);
            fabAdd= (FloatingActionButton) view.findViewById(R.id.fab_color);
            mDatabaseRef= Utils.getDatabase().getReference().child("products").child("lenses").child("color");
        }

    }
    public static class KidsEyeGlasses extends BaseFragment {
        ListView mCategories;
        FloatingActionButton fabAdd;
        DatabaseReference mDatabaseRef;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_color_lenses,container,false);
            setFragmentView(view);
            initComponent(view);
            Query mRef=Utils.getDatabase().getReference().child("brands").child("lenses").child("color");
            FirebaseBrandsAdapter adapter=new FirebaseBrandsAdapter(getActivity(),Brand.class,R.layout.main_list_item,mRef,"color","lenses");
            mCategories.setAdapter(adapter);
            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.addBrand("color",getContext());
                }
            });
            return getFragmentView();
        }
        public void initComponent(View view) {
            mCategories= (ListView) view.findViewById(R.id.main_list_view);
            fabAdd= (FloatingActionButton) view.findViewById(R.id.fab_color);
            mDatabaseRef= Utils.getDatabase().getReference().child("products").child("lenses").child("color");
        }

    }
}
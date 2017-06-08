package com.digiclack.visionstoreadmin.Fragments.navigationActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.digiclack.visionstoreadmin.Utils.Utils;
import com.digiclack.visionstoreadmin.adapters.CategoryAdapter;
import com.digiclack.visionstoreadmin.adapters.FirebaseBrandsAdapter;
import com.digiclack.visionstoreadmin.adapters.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.model.Category;
import com.digiclack.visionstoreadmin.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

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
        FloatingActionButton fabAdd;
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
            mCategoryList.add(new Category(mLenseList,"Brand1"));
            mCategoryList.add(new Category(mEyeList,"Brand2"));
            mCategoryList.add(new Category(mGlassesList,"Brand3"));
            mAdapter=new CategoryAdapter(getContext(),mCategoryList,getActivity().getSupportFragmentManager(),"transparent");
            mCategories.setAdapter(mAdapter);
            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addBrand();
                }
            });
            return getFragmentView();
        }
        public void initComponent(View view) {
            mCategories= (ListView) view.findViewById(R.id.main_list_view);
            mCategoryList=new ArrayList<>();
            mLenseList=new ArrayList<>();
            mEyeList=new ArrayList<>();
            mGlassesList=new ArrayList<>();
            fabAdd= (FloatingActionButton) view.findViewById(R.id.fab_transparent);
        }
        public void addBrand() {
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());
            alertDialog.setMessage("Add New Brand");
            final EditText input=new EditText(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            input.setHint("Enter Brand Name");
            alertDialog.setView(input);
            /*input.setText(currentName);*/
            alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name=input.getText().toString();
                    if (!name.isEmpty()) {
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("products").child("lenses").child("transparent");
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("brands").child("lenses").child("transparent");
                        HashMap<String,Object> hashMap=new HashMap<String, Object>();
                        HashMap<String,Object> hashMapBrands=new HashMap<String, Object>();
                        hashMapBrands.put("brand",name);
                        hashMap.put(name,"added");
                        ref.updateChildren(hashMap);
                        reference.push().setValue(hashMapBrands);
                        mCategoryList.add(new Category(name));
                    }

                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }

            });
            alertDialog.show();
        }
    }

    public static class ColorLenses extends BaseFragment {
        ListView mCategories;
        CategoryAdapter mAdapter;
        ArrayList<Product> mLenseList;
        ArrayList<Product> mEyeList;
        ArrayList<Product> mGlassesList;
        ArrayList<Category> mCategoryList;
        FloatingActionButton fabAdd;
        DatabaseReference mDatabaseRef;
        ValueEventListener mValuListner;
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
            mCategoryList.add(new Category(mLenseList,"Brand1"));
            mCategoryList.add(new Category(mEyeList,"Brand2"));
            mCategoryList.add(new Category(mGlassesList,"Brand3"));
            mAdapter=new CategoryAdapter(getContext(),mCategoryList,getActivity().getSupportFragmentManager(),"color");
            mCategories.setAdapter(mAdapter);
            /*Query mRef=Utils.getDatabase().getReference().child("brands").child("lenses").child("color");
            HashMap<String,Object>
            FirebaseBrandsAdapter adapter=new FirebaseBrandsAdapter(getActivity(),,R.layout.main_list_item,mRef);*/
            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                addBrand();
                }
            });
            return getFragmentView();
        }
        public void initComponent(View view) {
            mCategories= (ListView) view.findViewById(R.id.main_list_view);
            mCategoryList=new ArrayList<>();
            mLenseList=new ArrayList<>();
            mEyeList=new ArrayList<>();
            mGlassesList=new ArrayList<>();
            fabAdd= (FloatingActionButton) view.findViewById(R.id.fab_color);
            mDatabaseRef= Utils.getDatabase().getReference().child("products").child("lenses").child("color");
        }

        public void readingAllbrands() {
            final ArrayList<String> strings=new ArrayList<>();
            mValuListner=mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data: dataSnapshot.getChildren()) {
                        HashMap<String,Object> hashMap=new HashMap<>();
                        
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void addBrand() {
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());
            alertDialog.setMessage("Add New Brand");
            final EditText input=new EditText(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            input.setHint("Enter Brand Name");
            alertDialog.setView(input);
            /*input.setText(currentName);*/
            alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                String name=input.getText().toString();
                    if (!name.isEmpty()) {
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("products").child("lenses").child("color");
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("brands").child("lenses").child("color");
                        HashMap<String,Object> hashMap=new HashMap<String, Object>();
                        hashMap.put(name,"added");
                        HashMap<String,Object> hashMapBrands=new HashMap<String, Object>();
                        hashMapBrands.put("brand",name);
                        ref.updateChildren(hashMap);
                        reference.push().setValue(hashMap);
                        mCategoryList.add(new Category(name));
                    }

                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }

            });
            alertDialog.show();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }
}

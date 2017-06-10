package com.digiclack.visionstoreadmin.Fragments.navigationActivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.adapters.CategoryAdapter;
import com.digiclack.visionstoreadmin.adapters.ViewPagerSliderAdapter;
import com.digiclack.visionstoreadmin.model.Brand;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Zar on 6/1/2017.
 */

public class HomeFragment extends Fragment {
    ListView mCategories;
    CategoryAdapter mAdapter;
    ArrayList<Brand> mCategoryList;
    boolean doubleBackToExitPressedOnce = false;

    //viewpager adapter initialization
    ViewPager viewPager;
    LinearLayout sliderDotspanel;

    private int dotscount;
    private ImageView[] dots;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content_main,container,false);
        initComponent(view);
        mCategoryList.add(new Brand("Contact Lenses"));
        mCategoryList.add(new Brand("Eye Glasses"));
        mCategoryList.add(new Brand("Sun Glasses"));
        mAdapter=new CategoryAdapter(getContext(),mCategoryList,getActivity().getSupportFragmentManager(),getActivity());
        ViewGroup header= (ViewGroup) inflater.inflate(R.layout.header_listview,mCategories,false);
        mCategories.addHeaderView(header,null,false);
        mCategories.setAdapter(mAdapter);

        //Viewpager adapter code

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        ViewPagerSliderAdapter viewPagerAdapter = new ViewPagerSliderAdapter(getContext());

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
        return view;
    }
    public void initComponent(View view) {
        mCategories= (ListView) view.findViewById(R.id.main_list_view);
        mCategoryList=new ArrayList<>();

    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(viewPager.getCurrentItem() == 0){
                            viewPager.setCurrentItem(1);
                        } else if(viewPager.getCurrentItem() == 1){
                            viewPager.setCurrentItem(2);
                        } else {
                            viewPager.setCurrentItem(0);
                        }

                    }
                });
            } catch (Exception e){
                Log.e("HomeFragment", e.getMessage());
            }


        }
    }

}

package com.digiclack.visionstoreadmin.Fragments.navigationActivity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.digiclack.visionstoreadmin.Fragments.navigationActivity.BaseFragment;
import com.digiclack.visionstoreadmin.R;
import com.digiclack.visionstoreadmin.adapters.PagerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EyeGlassesFragment extends Fragment {
    TabLayout tabs;
    ViewPager pager;

    public EyeGlassesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eye_glasses, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view){
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        pager = (ViewPager) view.findViewById(R.id.pager);

        ArrayList<BaseFragment> pages = new ArrayList<>();

        TabPage page1 = new TabPage();
        page1.setTitle("PARTICIPATION");
        pages.add(page1);

        TabPageTwo page2 = new TabPageTwo();
        page2.setTitle("METRICS");
        pages.add(page2);

        PagerAdapter pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(),pages);

        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(pages.size());
        tabs.setupWithViewPager(pager);
    }

    public static class TabPage extends BaseFragment{
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            setFragmentView(inflater.inflate(R.layout.fragment_transparent_lenses,container,false));

            return getFragmentView();
        }
    }

    public static class TabPageTwo extends BaseFragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            setFragmentView(inflater.inflate(R.layout.fragment_color_lenses, container, false));

            return getFragmentView();
        }
    }

}

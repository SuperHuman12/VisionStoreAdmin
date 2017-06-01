package com.digiclack.visionstoreadmin.Fragments.navigationActivity;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Zar on 6/1/2017.
 */

public class BaseFragment extends Fragment {
    String title;
    View fragmentView;

    public View getFragmentView() {
        return fragmentView;
    }

    public void setFragmentView(View fragmentView) {
        this.fragmentView = fragmentView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public View findViewById(int res) {
        return getFragmentView().findViewById(res);
    }
}

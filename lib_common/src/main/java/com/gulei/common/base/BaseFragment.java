package com.gulei.common.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    public static BaseFragment newInstance(Bundle args) {
        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(args);
        return fragment;
    }


}

package com.avla.app.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avla.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstOnBoardingFragment extends Fragment {


    public FirstOnBoardingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_onboard, container, false);
    }

}

package com.avla.app.fragments.main.profileInnerFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.avla.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventInnerFragment extends Fragment {


    public EventInnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_inner, container, false);
    }

}

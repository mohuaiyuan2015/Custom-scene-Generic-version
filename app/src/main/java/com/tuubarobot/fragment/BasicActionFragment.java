package com.tuubarobot.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuubarobot.bluetoothcommunication.R;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicActionFragment extends Fragment implements CustomBehavior {

    private static final String TAG = "CustomBehavior";


    public BasicActionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic_action, container, false);
    }

    @Override
    public Map<String, String> getData() {
        return null;
    }

    @Override
    public void init() {

    }
}

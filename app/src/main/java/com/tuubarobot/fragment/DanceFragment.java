package com.tuubarobot.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuubarobot.bluetoothcommunication.R;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DanceFragment extends Fragment implements CustomBehavior {

    private static final String TAG = "CustomBehavior";

    private CustomUtils customutils;
    private List<String> dataList;

    private Context mContext;

    public DanceFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dance, container, false);
    }

    @Override
    public Map<String, String> getData() {
        return null;
    }

    @Override
    public void init() {

    }
}

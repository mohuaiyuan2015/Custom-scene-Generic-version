package com.tuubarobot.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.tuubarobot.adapter.DanceActionAdapter;
import com.tuubarobot.bluetoothcommunication.Constants;
import com.tuubarobot.bluetoothcommunication.R;
import com.tuubarobot.tools.CustomTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DanceFragment extends Fragment implements CustomBehavior {

    private static final String TAG = "CustomBehavior";

    private CustomUtils customUtils;
    private List<DanceActionInfo> danceActionList;

    private View view;
    private Spinner danceSpinner;
    private DanceActionAdapter danceActionAdapter;

    private String questionString;
    private String answerString;

    private Context mContext;

    private CustomTools customTools;

    public DanceFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_dance, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
        mContext=getContext();
        
        initUI();
        initData();
        initListener();

    }

    private void initUI() {
        Log.d(TAG, "initUI: ");
        danceSpinner= (Spinner) view.findViewById(R.id.danceSpinner);
    }

    private void initData() {
        Log.d(TAG, "initData: ");
        customTools=new CustomTools();

        try {
            danceActionList=customTools.initDanceActionConfig();
        } catch (Exception e) {
            Log.e(TAG, "初始化 舞蹈 动作 出现 Exception e: "+e.getMessage() );
            e.printStackTrace();
        }
        if (danceActionList!=null){
            for (int i=0;i<danceActionList.size();i++){
                Log.d(TAG, "actionList.get("+i+"): "+danceActionList.get(i));
            }
        }

        danceActionAdapter=new DanceActionAdapter(mContext,danceActionList);
        danceSpinner.setAdapter(danceActionAdapter);


    }

    private void initListener() {
        Log.d(TAG, "initListener: ");
        danceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "danceSpinner onItemSelected: ");
                Log.d(TAG, "position: "+position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    
    @Override
    public Map<String, String> getData() {
        Log.d(TAG, "getData: ");
        Map<String,String> dataMap=new HashMap<>();
        //类别
        dataMap.put(Constants.FRAGMENT_CATEGORY,Constants.FRAGMENT_CATEGORY_DANCE);
        //问题
        int selectItemPosition=danceSpinner.getSelectedItemPosition();
        questionString="舞蹈："+danceActionList.get(selectItemPosition).getName();
        dataMap.put(Constants.QUESTION,questionString);
        //回答
        answerString=danceActionList.get(selectItemPosition).getName();
        dataMap.put(Constants.ANSWER,answerString);
        //动作序列 eg:83:3400#,#66:3000#,#67:3000
        int actionCode=danceActionList.get(selectItemPosition).getId();
        dataMap.put(Constants.ACTION_CODE,String.valueOf(actionCode));

        return dataMap;
    }

    @Override
    public void init() {
        Log.d(TAG, "init: ");

    }

    @Override
    public String getBehaviorCategory() {
        Log.d(TAG, "getBehaviorCategory: ");
        return Constants.FRAGMENT_CATEGORY_DANCE;
    }
}

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

import com.tuubarobot.adapter.BasicActionAdapter;
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
public class BasicActionFragment extends Fragment implements CustomBehavior {

    private static final String TAG = "CustomBehavior";
    private CustomUtils customUtils;
    private List<ActionInfo> basicActionList;

    private View view;
    private Spinner basicActionSpinner;
    private BasicActionAdapter basicActionAdapter;

    private String questionString;
    private String answerString;

    private Context mContext;

    private CustomTools customTools;


    public BasicActionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_basic_action, container, false);
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
        basicActionSpinner= (Spinner) view.findViewById(R.id.basicActionSpinner);
    }

    private void initData() {
        Log.d(TAG, "initData: ");
        customTools=new CustomTools();

        try {
            basicActionList=customTools.initBasicActionConfig();
        } catch (Exception e) {
            Log.e(TAG, "初始化 基本 动作 出现 Exception e: "+e.getMessage() );
            e.printStackTrace();
        }
        if (basicActionList!=null){
            for (int i=0;i<basicActionList.size();i++){
                Log.d(TAG, "actionList.get("+i+"): "+basicActionList.get(i));
            }
        }

        basicActionAdapter=new BasicActionAdapter(mContext,basicActionList);
        basicActionSpinner.setAdapter(basicActionAdapter);


    }

    private void initListener() {
        Log.d(TAG, "initListener: ");
        basicActionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        dataMap.put(Constants.FRAGMENT_CATEGORY,Constants.FRAGMENT_CATEGORY_BASIC_ACTION);
        //问题
        int selectItemPosition=basicActionSpinner.getSelectedItemPosition();
        questionString="基本动作："+basicActionList.get(selectItemPosition).getName();
        dataMap.put(Constants.QUESTION,questionString);
        //回答
        answerString=basicActionList.get(selectItemPosition).getName();
        dataMap.put(Constants.ANSWER,answerString);
        //动作序列 eg:83:3400#,#66:3000#,#67:3000
        int actionCode=basicActionList.get(selectItemPosition).getId();
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
        return Constants.FRAGMENT_CATEGORY_BASIC_ACTION;
    }
}

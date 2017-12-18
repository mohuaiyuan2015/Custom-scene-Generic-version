package com.tuubarobot.bluetoothcommunication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.tuubarobot.fragment.BasicActionFragment;
import com.tuubarobot.fragment.CustomBehavior;
import com.tuubarobot.fragment.CustomDialogFragment;
import com.tuubarobot.fragment.DanceFragment;

import java.util.Map;

public class CustomDialogActivity extends AppCompatActivity {
    private static final String TAG = "CustomDialogActivity";

    private Context mContext;
    private Spinner dialogCategorySpinner;
    private FrameLayout frameLayout;
    private Button save;

    private ArrayAdapter spinnerAdapter ;
    private String[] spinnerData;

    private DanceFragment danceFragment;
    private BasicActionFragment basicActionFragment;
    private CustomDialogFragment customDialogFragment;
    private CustomBehavior customBehavior;

    private Map<String,String> behaviorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);

        mContext=this;

        initUI();
        initData();
        initListener();




    }



    private void initData() {
        spinnerData=mContext.getResources().getStringArray(R.array.dialogCategoryArray);
        spinnerAdapter=new ArrayAdapter(mContext,android.R.layout.simple_list_item_1,spinnerData);
        dialogCategorySpinner.setAdapter(spinnerAdapter);

        danceFragment=new DanceFragment();
        basicActionFragment=new BasicActionFragment();
        customDialogFragment=new CustomDialogFragment();

    }

    private void initUI() {
        dialogCategorySpinner= (Spinner) findViewById(R.id.dialogCategory);
        frameLayout= (FrameLayout) findViewById(R.id.customDialogFramelayout);
        save= (Button) findViewById(R.id.save);

    }

    private void initListener() {
        dialogCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "dialogCategory onItemSelected: ");
                Log.d(TAG, "position: "+position);
                loadFragment(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "dialogCategorySpinner onNothingSelected: ");

            }
        });
        
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                behaviorData=customBehavior.getData();
                Log.d(TAG, "behaviorData: "+behaviorData);
                
            }
        });

    }

    private void loadFragment(int position){
        Log.d(TAG, "loadFragment: ");
        Log.d(TAG, "position: "+position);
        switch (position){
            case 0:
                replaceFragment(danceFragment);
                customBehavior=danceFragment;
                break;
            case 1:
                replaceFragment(basicActionFragment);
                customBehavior=basicActionFragment;
                break;
            case 2:
                replaceFragment(customDialogFragment);
                customBehavior=customDialogFragment;
                break;
            default:
                replaceFragment(danceFragment);
                customBehavior=danceFragment;
                break;
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.customDialogFramelayout,fragment);
        transaction.commit();

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        
    }
}

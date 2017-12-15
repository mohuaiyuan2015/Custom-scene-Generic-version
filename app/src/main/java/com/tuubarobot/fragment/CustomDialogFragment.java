package com.tuubarobot.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tuubarobot.adapter.CustomDialogActionAdapter;
import com.tuubarobot.adapter.CustomDialogActionExecuteMomentAdapter;
import com.tuubarobot.adapter.CustomDialogExpressionAdapter;
import com.tuubarobot.adapter.CustomDialogExpressionExecuteMomentAdapter;
import com.tuubarobot.adapter.ShowActionAdapter;
import com.tuubarobot.adapter.ShowExpressionAdapter;
import com.tuubarobot.bluetoothcommunication.BluetoothUtils;
import com.tuubarobot.bluetoothcommunication.Constants;
import com.tuubarobot.bluetoothcommunication.R;
import com.tuubarobot.tools.CustomTools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomDialogFragment extends Fragment implements CustomBehavior {
    private static final String TAG = "CustomBehavior";

    private EditText et_dialogQuestion;
    private EditText et_dialogAnswer;

    private Spinner actionSpinner;
    private Spinner actionTimeSpinner;
    private Button btn_addAction;
    private RecyclerView showActionRecyclerView;

    private Spinner expressionSpinner;
    private Spinner expressionTimeSpinner;
    private Button btn_addExpression;
    private RecyclerView showExpressionRecyclerView;

    private String questionString;
    private String answerString;
    private int questionLength;
    private int answerLength;

    private QuestionTextWatch questionTextWatch;
    private AnswerTextWatch answerTextWatch;

    private List<ActionInfo> actionList;
    /**
     * 用于计算 数据: 计算动作下发时间点
     */
    private List<ExecuteMoment> actionExecuteMomentList;
    /**用于在Spinner 显示数据:显示动作下发时间点
     */
    private List<ExecuteMoment> actionExecuteMomentListData;
    private List<ActionInfo> showActionList;

    private List<ExpressionInfo> expressionList;
    /**
     * 用于计算 数据：计算表情下发时间点
     */
    private List<ExecuteMoment> expressionExecuteMomentList;
    /** 用于在Spinner 显示数据:显示表情下发时间点
     */
    private List<ExecuteMoment> expressionExecuteMomentListData;
    private List<ExpressionInfo> showExpressionList;

    private CustomDialogActionAdapter actionAdapter;
    private CustomDialogActionExecuteMomentAdapter actionExecuteMomentAdapter;
    private ShowActionAdapter showActionAdapter;

    private CustomDialogExpressionAdapter expressionAdapter;
    private CustomDialogExpressionExecuteMomentAdapter expressionExecuteMomentAdapter;
    private ShowExpressionAdapter showExpressionAdapter;


    private View view;

    private Context mContext;

    private CustomTools customTools;
    private BluetoothUtils bluetoothUtils;
    private CustomUtils customUtils;

    private Handler handler;

    private boolean isFirstInit;

    public CustomDialogFragment() {
        Log.d(TAG, "CustomDialogFragment: ");
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_custom_dialog, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");

        mContext=getContext();
        isFirstInit=true;

        initUI();
        initData();
        initListener();

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                isFirstInit = false;
            }
        }).start();
    }

    private void initUI(){
        Log.d(TAG, "initUI: ");
        et_dialogQuestion= (EditText) view.findViewById(R.id.et_dialogQuestion);
        et_dialogAnswer= (EditText) view.findViewById(R.id.et_dialogAnswer);

        actionSpinner= (Spinner) view.findViewById(R.id.actionSpinner);
        actionTimeSpinner= (Spinner) view.findViewById(R.id.actionTimeSpinner);
        btn_addAction= (Button) view.findViewById(R.id.btn_addAction);
        showActionRecyclerView= (RecyclerView) view.findViewById(R.id.showActionRecyclerView);

        expressionSpinner= (Spinner) view.findViewById(R.id.expressionSpinner);
        expressionTimeSpinner= (Spinner) view.findViewById(R.id.expressionTimeSpinner);
        btn_addExpression= (Button) view.findViewById(R.id.btn_addExpression);
        showExpressionRecyclerView= (RecyclerView) view.findViewById(R.id.showExpressionRecyclerView);
    }

    private void initData(){
        Log.d(TAG, "initData: ");
        questionTextWatch=new QuestionTextWatch();
        answerTextWatch=new AnswerTextWatch();
        customTools=new CustomTools();
        bluetoothUtils=new BluetoothUtils();
        customUtils=new CustomUtils(mContext);

        handler=new Handler();
        actionExecuteMomentListData=new ArrayList<>();
        expressionExecuteMomentListData=new ArrayList<>();

        try {
            actionList=customTools.initCustomDialogActionConfig();
        } catch (Exception e) {
            Log.e(TAG, "初始化 自定义对话 动作 出现 Exception e: "+e.getMessage() );
            e.printStackTrace();
        }
        if (actionList!=null){
            for (int i=0;i<actionList.size();i++){
                Log.d(TAG, "actionList.get("+i+"): "+actionList.get(i));
            }
        }


        try {
            expressionList=customTools.initCustomDialogExpressionConfig();
        } catch (Exception e) {
            Log.e(TAG, "初始化 自定义对话 表情 出现 Exception e: "+e.getMessage() );
            e.printStackTrace();
        }
        if (expressionList!=null){
            for (int i=0;i<expressionList.size();i++){
                Log.d(TAG, "expressionList.get("+i+"): "+expressionList.get(i));
            }
        }


        actionAdapter=new CustomDialogActionAdapter(mContext,actionList);
        actionSpinner.setAdapter(actionAdapter);

        actionExecuteMomentList=new ArrayList<>();
        //mohuaiyuan 原始的代码
//        actionExecuteMomentAdapter=new CustomDialogActionExecuteMomentAdapter(actionExecuteMomentList);
        //mohuaiyuan  :使用另外的list  ，用于显示
        actionExecuteMomentAdapter=new CustomDialogActionExecuteMomentAdapter(actionExecuteMomentListData);
        actionTimeSpinner.setAdapter(actionExecuteMomentAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        showActionRecyclerView.setLayoutManager(linearLayoutManager);
        showActionList=new ArrayList<>();
        showActionAdapter=new ShowActionAdapter(showActionList);
        showActionRecyclerView.setAdapter(showActionAdapter);



        expressionAdapter=new CustomDialogExpressionAdapter(expressionList);
        expressionSpinner.setAdapter(expressionAdapter);

        expressionExecuteMomentList=new ArrayList<>();
        //mohuaiyuan 原始的代码
//        expressionExecuteMomentAdapter=new CustomDialogExpressionExecuteMomentAdapter(expressionExecuteMomentList);
        //mohuaiyuan  :使用另外的list  ，用于显示
        expressionExecuteMomentAdapter=new CustomDialogExpressionExecuteMomentAdapter(expressionExecuteMomentListData);
        expressionTimeSpinner.setAdapter(expressionExecuteMomentAdapter);

        LinearLayoutManager ll=new LinearLayoutManager(mContext);
        showExpressionRecyclerView.setLayoutManager(ll);
        showExpressionList=new ArrayList<>();
        showExpressionAdapter=new ShowExpressionAdapter(showExpressionList);
        showExpressionRecyclerView.setAdapter(showExpressionAdapter);



    }


    private void initListener(){
        Log.d(TAG, "initListener: ");

        et_dialogQuestion.addTextChangedListener(questionTextWatch);
        et_dialogQuestion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "et_dialogQuestion onFocusChange: ");
                Log.d(TAG, "hasFocus: "+hasFocus);
                if (hasFocus){
                    //TODO  mohuaiyuan 若 动作展示列表中有数据  则 et_dialogAnswer 不可编辑
                    if((showActionList!=null && !showActionList.isEmpty())|| (showExpressionList!=null && !showExpressionList.isEmpty())){
                        Toast.makeText(mContext,"若要编辑回答内容,请先删除所有的动作组和表情组 ！",Toast.LENGTH_SHORT).show();
                        setAnswerEditTextUnEdit();
//                        et_dialogAnswer.setInputType(InputType.TYPE_NULL);
                    }else{
                        setAnswerEditTextEdit();
                    }

                }else {

                }

            }
        });

        et_dialogAnswer.addTextChangedListener(answerTextWatch);
        et_dialogAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "et_dialogAnswer onFocusChange: ");
                Log.d(TAG, "hasFocus: "+hasFocus);
                if (hasFocus){
                    //TODO  mohuaiyuan 若 动作展示列表中有数据  则 et_dialogAnswer 不可编辑
                    if((showActionList!=null && !showActionList.isEmpty())|| (showExpressionList!=null && !showExpressionList.isEmpty())){
                        Toast.makeText(mContext,"若要编辑回答内容,请先删除所有的动作组和表情组 ！",Toast.LENGTH_SHORT).show();
                        setAnswerEditTextUnEdit();
//                        et_dialogAnswer.setInputType(InputType.TYPE_NULL);
                    }else{
                        setAnswerEditTextEdit();
                    }

                }else {

                }
            }
        });


        actionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "actionSpinner onItemSelected: ");
                Log.d(TAG, "position: "+position);

                if (!isFirstInit && (questionString == null
                        || questionString.trim().isEmpty()
                        || answerString == null
                        || answerString.trim().isEmpty())) {

                    Toast.makeText(mContext, "请输入问题和回答内容！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //TODO mohuaiyuan  在这里对于 动作下发时刻 进行修改
                changeActionExecuteMoment();
                //显示动作下发时间点
                initActionExecuteMomenSpinnerData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        actionTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "actionTimeSpinner onItemSelected: ");
                Log.d(TAG, "position: "+position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_addAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btn_addAction onClick: ");
                //震动
                bluetoothUtils.vibrate(mContext,40);

                if (questionString==null
                        || questionString.trim().isEmpty()
                        || answerString==null
                        ||answerString.trim().isEmpty()){

                    Toast.makeText(mContext,"请先输入问题和回答内容！",Toast.LENGTH_SHORT).show();
                    return;
                }

                addActionData();

                //设置不可编辑 状态
                setAnswerEditTextUnEdit();
//                et_dialogAnswer.setInputType(InputType.TYPE_NULL);

            }
        });


        showActionAdapter.setOnItemLongClickListener(new ShowActionAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View itemView, int position) {
                Log.d(TAG, " showActionAdapter onLongClick: ");
                //震动
                bluetoothUtils.vibrate(mContext,40);
            }
        });

        showActionAdapter.setOnItemDeleteListener(new ShowActionAdapter.onItemDeleteListener() {
            @Override
            public void onDelete(View itemView, int position) {
                Log.d(TAG, "showActionAdapter onDelete: ");
                showActionList.remove(position);
                refreshShowActionData();
                //震动
                bluetoothUtils.vibrate(mContext,40);

                if ((showActionList != null && !showActionList.isEmpty()) || (showExpressionList != null && !showExpressionList.isEmpty())) {
//                    et_dialogAnswer.setInputType(InputType.TYPE_NULL);'
                    setAnswerEditTextUnEdit();
                } else {
//                    et_dialogAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
                    setAnswerEditTextEdit();
                }
            }
        });

        //-------------------------以下是表情部分---------------------------------------------------------------

        expressionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "expressionSpinner onItemSelected: ");
                Log.d(TAG, "position: "+position);

                if (!isFirstInit && (questionString == null
                        || questionString.trim().isEmpty()
                        || answerString == null
                        || answerString.trim().isEmpty())) {

                    Toast.makeText(mContext, "请输入问题和回答内容！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //TODO mohuaiyuan  在这里对于 表情下发时刻 进行修改
                changeExpressionExecuteMoment();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        expressionTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "expressionTimeSpinner onItemSelected: ");
                Log.d(TAG, "position: "+position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_addExpression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btn_addExpression onClick: ");
                //震动
                bluetoothUtils.vibrate(mContext,40);

                if (questionString==null
                        || questionString.trim().isEmpty()
                        || answerString==null
                        ||answerString.trim().isEmpty()){

                    Toast.makeText(mContext,"请先输入问题和回答内容！",Toast.LENGTH_SHORT).show();
                    return;
                }

                addExpressionData();

                //设置不可编辑 状态
                setAnswerEditTextUnEdit();
//                et_dialogAnswer.setInputType(InputType.TYPE_NULL);

            }
        });

        showExpressionAdapter.setOnItemLongClickListener(new ShowExpressionAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View itemView, int position) {
                Log.d(TAG, "showExpressionAdapter onLongClick: ");
                //震动
                bluetoothUtils.vibrate(mContext,40);
            }
        });

        showExpressionAdapter.setOnItemDeleteListener(new ShowExpressionAdapter.onItemDeleteListener() {
            @Override
            public void onDelete(View itemView, int position) {
                Log.d(TAG, "showExpressionAdapter onDelete: ");
                showExpressionList.remove(position);
                refreshShowExpressionData();
                //震动
                bluetoothUtils.vibrate(mContext,40);

                if ((showActionList != null && !showActionList.isEmpty()) || (showExpressionList != null && !showExpressionList.isEmpty())) {
//                    et_dialogAnswer.setInputType(InputType.TYPE_NULL);'
                    setAnswerEditTextUnEdit();
                } else {
//                    et_dialogAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
                    setAnswerEditTextEdit();
                }
            }
        });
        
    }

    private void changeActionExecuteMoment() {
        Log.d(TAG, "changeActionExecuteMoment: ");
        if (actionExecuteMomentList==null || actionExecuteMomentList.isEmpty()){
            Log.d(TAG, "动作下发时间点 列表 是空的 暂时不需要考虑: ");
            return;
        }

        //初始状态：全部设置为可见
        for (int i=0;i<actionExecuteMomentList.size();i++){
            actionExecuteMomentList.get(i).setAble(true);
        }

        if (showActionList.isEmpty()){  //第一次选择动作

            initActionExecuteMoment();

        }else {

            initActionExecuteMoment(1);

        }

    }

    private void   changeExpressionExecuteMoment(){
        Log.d(TAG, "changeExpressionExecuteMoment: ");
        if (expressionExecuteMomentList==null || expressionExecuteMomentList.isEmpty()){
            Log.d(TAG, "表情下发时间点 列表 是空的 暂时不需要考虑: ");
            return;
        }

        //初始状态：全部设置为可见
        for (int i=0;i<expressionExecuteMomentList.size();i++){
            expressionExecuteMomentList.get(i).setAble(true);
        }

        if (showExpressionList.isEmpty()){  //第一次选择动作

            initExpressionExecuteMoment();

        }else {

        }


    }


    /**
     * 计算 数据: 计算动作下发时间点
     */
    private void initActionExecuteMomentData(){
        Log.d(TAG, "initActionExecuteMomentData: ");

        if (questionString==null
                || questionString.trim().isEmpty()
                || answerString==null
                ||answerString.trim().isEmpty()){

            Toast.makeText(mContext,"请先输入问题和回答内容！",Toast.LENGTH_SHORT).show();
            return;
        }

        //读对话 tts 需要的时间 ，单位秒
        int count=answerLength/ Constants.THE_NUMBER_OF_WORDS_PER_SECOND;
        Log.d(TAG, "count: "+count);

        int size=actionExecuteMomentList.size();
        Log.d(TAG, "size: ");

        //获取当前选择的动作  动作的执行时间 单位秒
        int actionOperationTime=getSelectActionOperationTime();


        if (count>size){ //增加字数
            Log.d(TAG, "增加字数: ");
            int diff=count-size;
            for (int i=0;i<diff;i++){
                ExecuteMoment executeMoment=new ExecuteMoment();
                executeMoment.setMoment(actionExecuteMomentList.size());
                executeMoment.setAble(true);
                actionExecuteMomentList.add(executeMoment);
//                refreshActionExecuteMomentData();
            }

        }else if (count<size){  //减少字数
            Log.d(TAG, "减少字数: ");
            int diff=size-count;
            for (int i=0;i<diff;i++){
                actionExecuteMomentList.remove(actionExecuteMomentList.size()-1);
//                refreshActionExecuteMomentData();
            }

        }

        /**
         * 初始化 动作执行时间列表    此时  动作组 为空
         */
        initActionExecuteMoment();


    }

    /**
     * 计算 数据: 计算表情下发时间点
     */
    private void initExpressionExecuteMomentData(){
        Log.d(TAG, "initExpressionExecuteMomentData: ");
        int count=answerLength/ Constants.THE_NUMBER_OF_WORDS_PER_SECOND;
        Log.d(TAG, "count: "+count);

        int size=expressionExecuteMomentList.size();
        Log.d(TAG, "size: ");

        if (count>size){ //增加字数
            Log.d(TAG, "增加字数: ");
            int diff=count-size;
            for (int i=0;i<diff;i++){
                ExecuteMoment executeMoment=new ExecuteMoment();
                executeMoment.setMoment(expressionExecuteMomentList.size());
                executeMoment.setAble(true);
                expressionExecuteMomentList.add(executeMoment);
            }

        }else if (count<size){  //减少字数
            Log.d(TAG, "减少字数: ");
            int diff=size-count;
            for (int i=0;i<diff;i++){
                expressionExecuteMomentList.remove(expressionExecuteMomentList.size()-1);
            }

        }

//        refreshExpressionExecuteMomentData();

    }

    /**
     * 初始化 动作执行时间列表 ,此时  动作组 为空
     */
    private void initActionExecuteMoment() {
        Log.d(TAG, "initActionExecuteMoment: ");
        //获取当前选择的动作  动作的执行时间 单位秒
        int operationTimeSeconds=getSelectActionOperationTime();
        Log.d(TAG, "changeActionTime: ");
        if (operationTimeSeconds>=actionExecuteMomentList.size()){ //动作执行时间比 对话的时间还长
            //取消所有的动作 下发时间点
            for(int i=0;i<actionExecuteMomentList.size();i++){
                actionExecuteMomentList.get(i).setAble(false);
//                refreshActionExecuteMomentData();
            }

            Toast.makeText(mContext,"当前选择的动作，执行的时间太长，请选择其他的动作 ！",Toast.LENGTH_SHORT).show();

        }else {
            //从动作下发时间点 列表末尾，取消下发的时间点
            for (int i=0;i<operationTimeSeconds;i++){
                int index=actionExecuteMomentList.size()-i-1;
                actionExecuteMomentList.get(index).setAble(false);
//                refreshActionExecuteMomentData();
            }
        }
    }

    /**
     * 初始化 动作执行时间列表 ,此时  动作组 已经有数据
     * @param type
     */
    private void initActionExecuteMoment(int type) {
        Log.d(TAG, "initActionExecuteMoment:----------------------------------- ");

        initActionExecuteMoment();
        if (type<=0){
            return;
        }

        int unableCount=0;
        for (int i=0;i<actionExecuteMomentList.size();i++){
            if (!actionExecuteMomentList.get(i).isAble()){
                unableCount++;
            }
        }
        //已经取消所有的动作下发时间点
        if (unableCount==actionExecuteMomentList.size()){
            return;
        }

        Log.d(TAG, "-----打印 actionExecuteMomentList ----------------: ");
            for (int i=0;i<actionExecuteMomentList.size();i++){
                ExecuteMoment executeMoment=actionExecuteMomentList.get(i);
                Log.d(TAG, "executeMoment: "+executeMoment);
            }

        Log.d(TAG, "------------打印 actionExecuteMomentList ---打印完成 ----: ");

        //获取当前选择的动作  动作的执行时间 单位秒
        int operationTimeSeconds=getSelectActionOperationTime();
        Log.d(TAG, "operationTimeSeconds: "+operationTimeSeconds);
        int temp=actionExecuteMomentList.size()-operationTimeSeconds;
        Log.d(TAG, "temp: "+temp);
//        for (int i=0;i<temp;i++){
            for (int moment=0;moment<showActionList.size();moment++){
                ActionInfo info=showActionList.get(moment);
                int operationTime=info.getOperationTime();
                operationTime=customUtils.convertedIntoSeconds(operationTime);
                Log.d(TAG, "operationTime: "+operationTime);
                int toBeOperationTime=info.getToBeOperationTime();
                Log.d(TAG, "toBeOperationTime: "+toBeOperationTime);

                if (toBeOperationTime<=temp){
                    for (int unable=0;unable<operationTime;unable++){
                        if ((unable+toBeOperationTime)<0 || (unable+toBeOperationTime)>=actionExecuteMomentList.size()){
                            continue;
                        }
                        actionExecuteMomentList.get(unable+toBeOperationTime).setAble(false);
                        Log.d(TAG, "(unable+toBeOperationTime): "+(unable+toBeOperationTime));
                        Log.d(TAG, "(unable+toBeOperationTime).setAble(false): ");
//                        refreshActionExecuteMomentData();
                    }
                    for (int setUnable=1;setUnable<operationTimeSeconds;setUnable++){
                        if ((toBeOperationTime-setUnable)<0 || (toBeOperationTime-setUnable)>=actionExecuteMomentList.size()){
                            continue;
                        }

                        actionExecuteMomentList.get(toBeOperationTime-setUnable).setAble(false);
                        Log.d(TAG, "(toBeOperationTime-setUnable): "+(toBeOperationTime-setUnable));
                        Log.d(TAG, "(toBeOperationTime-setUnable).setAble(false): ");
//                        refreshActionExecuteMomentData();
                    }

                }else {
                    continue;
                }

            }
//        }


        Log.d(TAG, "-----打印 actionExecuteMomentList ----------------: ");
        for (int i=0;i<actionExecuteMomentList.size();i++){
            ExecuteMoment executeMoment=actionExecuteMomentList.get(i);
            Log.d(TAG, "executeMoment: "+executeMoment);
        }

        Log.d(TAG, "------------打印 actionExecuteMomentList ---打印完成 ----: ");

    }


    /**
     * 初始化 动作执行时间列表 ,此时  动作组 为空
     */
    private void initExpressionExecuteMoment() {
        Log.d(TAG, "initExpressionExecuteMoment: ");
        //获取当前选择的表情  表情的执行时间 单位秒
        int operationTimeSeconds=getSelectExpressionOperationTime();
        Log.d(TAG, "changeActionTime: ");
        if (operationTimeSeconds>=expressionExecuteMomentList.size()){ //表情执行时间比 对话的时间还长
            for(int i=0;i<expressionExecuteMomentList.size();i++){
                expressionExecuteMomentList.get(i).setAble(false);
//                refreshExpressionExecuteMomentData();
            }

            Toast.makeText(mContext,"当前选择的表情，执行的时间太长，请选择其他的表情 ！",Toast.LENGTH_SHORT).show();

        }else {
            for (int i=0;i<operationTimeSeconds;i++){
                int index=expressionExecuteMomentList.size()-i-1;
                expressionExecuteMomentList.get(index).setAble(false);
//                refreshExpressionExecuteMomentData();
            }
        }
    }

    /**
     * 显示动作下发时间点
     */
    private void initActionExecuteMomenSpinnerData(){
        Log.d(TAG, "initActionExecuteMomenSpinnerData: ");
        if (actionExecuteMomentListData==null){
            actionExecuteMomentListData=new ArrayList<>();
        }
        if (!actionExecuteMomentListData.isEmpty()){
            actionExecuteMomentListData.clear();
        }
        for (int i=0;i<actionExecuteMomentList.size();i++){
            ExecuteMoment executeMoment=actionExecuteMomentList.get(i);
            if (executeMoment.isAble()){
                actionExecuteMomentListData.add(executeMoment);
                refreshActionExecuteMomentData();
            }

        }

    }

    /**
     * 显示表情下发时间点
     */
    private void initExpressionExecuteMomenSpinnerData(){
        Log.d(TAG, "initExpressionExecuteMomenSpinnerData: ");
        if (expressionExecuteMomentListData==null){
            expressionExecuteMomentListData=new ArrayList<>();
        }
        if (!expressionExecuteMomentListData.isEmpty()){
            expressionExecuteMomentListData.clear();
        }
        for (int i=0;i<expressionExecuteMomentList.size();i++){
            ExecuteMoment executeMoment=expressionExecuteMomentList.get(i);
            if (executeMoment.isAble()){
                expressionExecuteMomentListData.add(executeMoment);
                refreshExpressionExecuteMomentData();
            }

        }

    }


    /**
     * 获取当前选择的动作  动作的执行时间 单位秒
     * @return
     */
    private int getSelectActionOperationTime(){
        Log.d(TAG, "getSelectActionOperationTime: ");
        //当前选择的动作
        int actionPosition=actionSpinner.getSelectedItemPosition();
        //当前动作的信息
        ActionInfo actionInfo=actionList.get(actionPosition);
        //当前动作，动作执行的时间，即此动作需要的时间，单位毫秒
        int operationTime=actionInfo.getOperationTime();
        Log.d(TAG, "operationTime: "+operationTime);

        //时间转换成秒 ，动作需要的时间
        int seconds=operationTime/1000;
        int remainder=operationTime%1000;
        if (remainder>0){
            seconds++;
        }
        Log.d(TAG, "seconds: "+seconds);

        return seconds;
    }
    /**
     * 获取当前选择的表情  表情的执行时间 单位秒
     * @return
     */
    private int getSelectExpressionOperationTime(){
        Log.d(TAG, "getSelectExpressionOperationTime: ");
        //当前选择的表情
        int expressionPosition=expressionSpinner.getSelectedItemPosition();
        //当前表情的信息
        ExpressionInfo expressionInfo=expressionList.get(expressionPosition);
        //当前表情，表情执行的时间，即此表情需要的时间，单位毫秒
        int operationTime=expressionInfo.getOperationTime();
        Log.d(TAG, "operationTime: "+operationTime);

        //时间转换成秒 ，表情需要的时间
        int seconds=operationTime/1000;
        int remainder=operationTime%1000;
        if (remainder>0){
            seconds++;
        }
        Log.d(TAG, "seconds: "+seconds);

        return seconds;
    }

    private void refreshActionExecuteMomentData(){
//        Log.d(TAG, "refreshActionExecuteMomentData: ");
        if (actionExecuteMomentAdapter!=null){
            actionExecuteMomentAdapter.notifyDataSetChanged();
        }
    }

    private void refreshExpressionExecuteMomentData(){
        Log.d(TAG, "refreshExpressionExecuteMomentData: ");
        if (expressionExecuteMomentAdapter!=null){
            expressionExecuteMomentAdapter.notifyDataSetChanged();
        }
    }


    private void refreshShowActionData(){
        Log.d(TAG, "refreshShowActionData: ");
        if (showActionAdapter!=null){
            showActionAdapter.notifyDataSetChanged();
        }

    }

    private void refreshShowExpressionData(){
        Log.d(TAG, "refreshShowExpressionData: ");
        if (showExpressionAdapter!=null){
            showExpressionAdapter.notifyDataSetChanged();
        }
    }


    private void addActionData() {
        Log.d(TAG, "addActionData: ");
        int actionPosition = actionSpinner.getSelectedItemPosition();
        int actionExecutePosition = actionTimeSpinner.getSelectedItemPosition();
        Log.d(TAG, "actionPosition: " + actionPosition);
        Log.d(TAG, "actionExecutePosition: " + actionExecutePosition);

        ActionInfo info = actionList.get(actionPosition);
        Log.d(TAG, "info: " + info);

        //mohuaiyuan    原始的代码
//        ExecuteMoment executeMoment = actionExecuteMomentList.get(actionExecutePosition);

        if( !(actionExecutePosition<actionExecuteMomentListData.size() && actionExecutePosition>=0)){
            Toast.makeText(mContext,"请选择其他的动作或者删除一些动作组合！",Toast.LENGTH_SHORT).show();
            return;
        }

        //mohuaiyuan  使用另外的list 显示 动作下发时间点
        ExecuteMoment executeMoment = actionExecuteMomentListData.get(actionExecutePosition);
        Log.d(TAG, "executeMoment: " + executeMoment);

        ActionInfo actionInfo = new ActionInfo();
        actionInfo.setId(info.getId());
        actionInfo.setName(info.getName());
        actionInfo.setOperationTime(info.getOperationTime());
        actionInfo.setToBeOperationTime(executeMoment.getMoment());


        Log.d(TAG, "-------------打印一些信息-----------------------");
        Log.d(TAG, "actionInfo: " + actionInfo);
        Log.d(TAG, "showActionList.size(): " + showActionList.size());
        for (int i = 0; i < showActionList.size(); i++) {
            Log.d(TAG, "showActionList.get(" + i + "): " + showActionList.get(i));
        }

        Log.d(TAG, "----------------------------------信息打印结束--");

        //mohuaiyuan  不能添加重复的
        if (showActionList.contains(actionInfo)) {
            Toast.makeText(mContext, "不能添加相同的动作和动作下发时间", Toast.LENGTH_SHORT).show();
            return;
        }
        showActionList.add(actionInfo);
        Log.d(TAG, "showActionList.size(): " + showActionList.size());
        refreshShowActionData();

    }

    private void addExpressionData(){
        Log.d(TAG, "addExpressionData: ");
        int expressionPosition = expressionSpinner.getSelectedItemPosition();
        int expressionExecutePosition = expressionTimeSpinner.getSelectedItemPosition();
        Log.d(TAG, "expressionPosition: " + expressionPosition);
        Log.d(TAG, "expressionExecutePosition: " + expressionExecutePosition);

        ExpressionInfo info = expressionList.get(expressionPosition);
        Log.d(TAG, "info: " + info);

        //mohuaiyuan    原始的代码
//        ExecuteMoment executeMoment = expressionExecuteMomentList.get(expressionExecutePosition);
        //mohuaiyuan  使用另外的list 显示 动作下发时间点
        ExecuteMoment executeMoment = expressionExecuteMomentListData.get(expressionExecutePosition);
        Log.d(TAG, "executeMoment: " + executeMoment);

        ExpressionInfo expressionInfo=new ExpressionInfo();
        expressionInfo.setId(info.getId());
        expressionInfo.setName(info.getName());
        expressionInfo.setOperationTime(info.getOperationTime());
        expressionInfo.setToBeOperationTime(executeMoment.getMoment());

        Log.d(TAG, "-------------打印一些信息-----------------------");
        Log.d(TAG, "expressionInfo: " + expressionInfo);
        Log.d(TAG, "showExpressionList.size(): " + showExpressionList.size());
        for (int i = 0; i < showExpressionList.size(); i++) {
            Log.d(TAG, "showExpressionList.get(" + i + "): " + showExpressionList.get(i));
        }

        Log.d(TAG, "----------------------------------信息打印结束--");

        Log.d(TAG, "showExpressionList==null: "+(showExpressionList==null));
        Log.d(TAG, "showExpressionList.contains(expressionInfo): "+(showExpressionList.contains(expressionInfo)));

        //mohuaiyuan  不能添加重复的
            if (showExpressionList.contains(expressionInfo)) {
            Toast.makeText(mContext, "不能添加相同的表情和表情下发时间", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "showExpressionList.size(): " + showExpressionList.size());
        showExpressionList.add(expressionInfo);
        Log.d(TAG, "showExpressionList.size(): " + showExpressionList.size());
        refreshShowExpressionData();

    }

    private Runnable executeMomentDelayRun = new Runnable() {

        @Override
        public void run() {
            //TODO mohuaiyuan 在这里 修改 动作执行时刻的数据
            Log.d(TAG, "修改 动作执行时刻的数据 : ");
            //计算动作下发时间点
            initActionExecuteMomentData();
            //显示动作下发时间点
            initActionExecuteMomenSpinnerData();

            //计算表情下发时间点
            initExpressionExecuteMomentData();
            //显示表情下发时间点
            initExpressionExecuteMomenSpinnerData();

        }
    };

    /**
     * 设置不可编辑状态
     */
    private void setAnswerEditTextUnEdit(){
        et_dialogAnswer.setFocusable(false);
        et_dialogAnswer.setFocusableInTouchMode(false);
    }

    /**
     * 设置可编辑状态
     */
    private void setAnswerEditTextEdit(){
        et_dialogAnswer.setFocusableInTouchMode(true);
        et_dialogAnswer.setFocusable(true);
//        et_dialogAnswer.requestFocus();

    }

    @Override
    public Map<String, String> getData() {
        Log.d(TAG, "getData: ");
        return null;
    }

    @Override
    public void init() {
        Log.d(TAG, "init: ");

    }

    class QuestionTextWatch implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            questionString=s.toString();
            questionLength=questionString.length();

        }
    }

    class AnswerTextWatch implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            answerString=s.toString();
            answerLength=answerString.length();
            Log.d(TAG, "answerLength: "+answerLength);

            if(executeMomentDelayRun!=null){
                //每次editText有变化的时候，则移除上次发出的延迟线程
                handler.removeCallbacks(executeMomentDelayRun);
            }

            //延迟800ms，如果不再输入字符，则执行该线程的run方法
            handler.postDelayed(executeMomentDelayRun, 800);

        }
    }

}

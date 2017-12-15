package com.tuubarobot.tools;

import android.os.Environment;
import android.util.Log;

import com.tuubarobot.bluetoothcommunication.Constants;
import com.tuubarobot.fragment.ActionInfo;
import com.tuubarobot.fragment.ExpressionInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YF-04 on 2017/12/11.
 */

public class CustomTools {
    private static final String TAG = "CustomTools";


    private volatile List<Map<String ,String>> customActionList;
    private volatile List<Map<String ,String>> customExpressionList;

    public CustomTools(){

    }


    public synchronized List<Map<String ,String >> initCustomActionConfig() throws Exception{
        Log.d(TAG, "initCustomActionConfig: ");
        String configFileName= Constants.CUSTOM_SCENE_DIR+ File.separator+Constants.CUSTOM_DIALOG_ACTION_INFO_FILE_NAME;
        File file=getSDcardFile(configFileName);
        Log.d(TAG, "file.getAbsolutePath(): "+file.getAbsolutePath());

        return initCustomActionConfig(file);
    }
    public synchronized List<Map<String ,String >> initCustomExpressionConfig() throws Exception{
        Log.d(TAG, "initCustomExpressionConfig: ");
        String configFileName= Constants.CUSTOM_SCENE_DIR+ File.separator+Constants.CUSTOM_DIALOG_EXPRESSION_INFO_FILE_NAME;
        File file=getSDcardFile(configFileName);
        Log.d(TAG, "file.getAbsolutePath(): "+file.getAbsolutePath());

        return initCustomActionConfig(file);
    }

    public  synchronized List<Map<String,String>> initCustomActionConfig(File fileName)throws Exception{
        if (fileName == null) {
            throw new Exception("Illegal fileName: fileName is null!");
        }
        if (!fileName.exists()) {
            throw new Exception("Illegal fileName:  fileName is not exist!");
        }

        List<Map<String ,String>> actionList=new ArrayList<>();

        FileInputStream is = null;
        BufferedReader br = null;
        String line = "";
        try {
            is = new FileInputStream(fileName);
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                String[] item = line.split(",");
                if (item != null ) {
                    Map<String,String> map=new HashMap<>();
                    for (int i=0;i<item.length;i++){
                        Log.d(TAG, "item["+i+"]: "+item[i]);
                        String[] parameter=item[i].trim().split(":");
                        if (parameter!=null && parameter.length==2){
                            map.put(parameter[0].trim(),parameter[1].trim());
                        }else {
                            throw new Exception("There are some errors in your configuration file:" + fileName.getName());
                        }
                    }

                    actionList.add(map);

                } else {
                    throw new Exception("There are some errors in your configuration file:" + fileName.getName());
                }
            }
            br.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return actionList;

    }

    public synchronized List<ActionInfo>initCustomDialogActionConfig()throws Exception {
        Log.d(TAG, "initCustomDialogActionConfig: ");
        List<ActionInfo> actionInfos = new ArrayList<>();
        customActionList=initCustomActionConfig();

        if (customActionList != null && !customActionList.isEmpty()) {
            Log.d(TAG, "customActionList.size(): " + customActionList.size());
            for (int i = 0; i < customActionList.size(); i++) {
                Map<String, String> map = customActionList.get(i);
                ActionInfo actionInfo = new ActionInfo();
                int id = Integer.valueOf(map.get(ActionInfo.ACTION_INFO_ID));
                String name = map.get(ActionInfo.ACTION_INFO_NAME);
                int operationTime = Integer.valueOf(map.get(ActionInfo.ACTION_INFO_OPERATION_TIME));

                actionInfo.setId(id);
                actionInfo.setName(name);
                actionInfo.setOperationTime(operationTime);

                actionInfos.add(actionInfo);
            }
            return actionInfos;
        } else {
            Log.e(TAG, "customActionList == null || customActionList.isEmpty() ");
        }
        return null;
    }

    public synchronized List<ExpressionInfo>initCustomDialogExpressionConfig()throws Exception {
        Log.d(TAG, "initCustomDialogExpressionConfig: ");
        List<ExpressionInfo> expressionInfos=new ArrayList<>();
        customExpressionList=initCustomExpressionConfig();

        if (customExpressionList != null && !customExpressionList.isEmpty()) {
            Log.d(TAG, "customExpressionList.size(): " + customExpressionList.size());
            for (int i = 0; i < customExpressionList.size(); i++) {
                Map<String, String> map = customExpressionList.get(i);
                ExpressionInfo expressionInfo=new ExpressionInfo();
                String id=map.get(ExpressionInfo.EXPRESSION_INFO_ID);
                String name = map.get(ExpressionInfo.EXPRESSION_INFO_NAME);
                int operationTime = Integer.valueOf(map.get(ExpressionInfo.EXPRESSION_INFO_OPERATION_TIME));

                expressionInfo.setId(id);
                expressionInfo.setName(name);
                expressionInfo.setOperationTime(operationTime);

                expressionInfos.add(expressionInfo);
            }
            return expressionInfos;
        } else {
            Log.e(TAG, "customActionList == null || customActionList.isEmpty() ");
        }
        return null;
    }



    /**
     * 根据文件名 提供完整的SD Card 文件路径
     * @param fileName
     * @return
     */
    public File getSDcardFile(String fileName){
        Log.d(TAG, "getSDcardFile: ");
        File file=null;
        File sdCardDir = Environment.getExternalStorageDirectory();
        Log.d(TAG, "sdCardDir: "+sdCardDir);
        file= new File(sdCardDir.getPath()+ File.separator + fileName);
        if (file==null || !file.exists()){
            Log.e(TAG, "file==null || !file.exists(): ");
            return null;
        }
        Log.d(TAG, "file: "+file);
        return file;
    }


}

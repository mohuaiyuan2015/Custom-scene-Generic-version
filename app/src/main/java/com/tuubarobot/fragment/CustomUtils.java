package com.tuubarobot.fragment;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YF-04 on 2017/12/6.
 */

public class CustomUtils {

    private Context mContext;
    private  volatile List<String> dataList;

    public CustomUtils(Context context){
        this.mContext=context;
    }

    public synchronized List<String> getConfig(File fileName)throws Exception{
        if (dataList==null){
            dataList=new ArrayList<>();
        }
        if (!dataList.isEmpty()){
            dataList.clear();
        }
        FileInputStream is = null;
        BufferedReader br = null;
        String line = "";
        try {
            is = new FileInputStream(fileName);
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
               dataList.add(line);
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
        return dataList;
    }

    /**
     *  毫秒转换成秒,采用进一法处理余数（尾数）部分
     * @param millisecond
     * @return
     */
    public long convertedIntoSeconds(long millisecond)throws Exception{
        long seconds=0;
        if (millisecond<0){
            new Exception("Parameter not legal:parameter is less than zero !");
            return -1;
        }
        seconds=millisecond/1000;
        long remainder=millisecond%1000;
        if (remainder!=0){
            seconds++;
        }
        return seconds;
    }

    /**
     *  毫秒转换成秒，采用进一法处理余数（尾数）部分
     * @param millisecond
     * @return
     */
    public int convertedIntoSeconds(int millisecond){
        int seconds=0;
        if (millisecond<0){
            new Exception("Parameter not legal:parameter is less than zero !");
            return -1;
        }
        seconds=millisecond/1000;
        int remainder=millisecond%1000;
        if (remainder!=0){
            seconds++;
        }
        return seconds;

    }

    /**
     *  秒转换成毫秒
     * @param second
     * @return
     */
    public long convertedIntoMilliSeconds(long second){
        long milliSeconds=0;
        milliSeconds=second*1000;
        return milliSeconds;
    }

    /**
     *  秒转换成毫秒
     * @param second
     * @return
     */
    public int convertedIntoMilliSeconds(int second){
        int milliSeconds=0;
        milliSeconds=second*1000;
        return milliSeconds;
    }



}

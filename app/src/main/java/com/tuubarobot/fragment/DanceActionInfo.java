package com.tuubarobot.fragment;

/**
 * Created by YF-04 on 2017/12/16.
 */

public class DanceActionInfo {
    private static final String TAG = "DanceActionInfo";

    public static final String DANCE_ACTION_INFO_ID="id";
    public static final String DANCE_ACTION_INFO_NAME="name";
    public static final String DANCE_ACTION_INFO_OPERATION_TIME="operationTime";

    private int id;
    private String name;
    /**
     *  动作执行的时间，即此动作需要的时间，单位秒（s）
     */
    private int operationTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(int operationTime) {
        this.operationTime = operationTime;
    }

    @Override
    public String toString() {
        return "DanceActionInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", operationTime=" + operationTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DanceActionInfo that = (DanceActionInfo) o;

        if (id != that.id) return false;
        if (operationTime != that.operationTime) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + operationTime;
        return result;
    }
}

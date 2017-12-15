package com.tuubarobot.fragment;

/**
 * Created by YF-04 on 2017/12/13.
 */

public class ExpressionInfo {
    private static final String TAG = "ExpressionInfo";

    public static final String EXPRESSION_INFO_ID="id";
    public static final String EXPRESSION_INFO_NAME="name";
    public static final String EXPRESSION_INFO_OPERATION_TIME="operationTime";
    public static final String EXPRESSION_INFO_TO_BE_OPERATION_TIME="toBeOperationTime";

    private String id;
    private String name;
    /**
     *  动作执行的时间，即此动作需要的时间，单位毫秒（ms）
     */
    private int operationTime;

    /**
     *  动作执行的时刻（从零秒开始），单位：秒（s）
     *  eg: 0 秒，3秒，8秒 等等
     */
    private int toBeOperationTime;

    public ExpressionInfo(){

    }

    public static String getTAG() {
        return TAG;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getToBeOperationTime() {
        return toBeOperationTime;
    }

    public void setToBeOperationTime(int toBeOperationTime) {
        this.toBeOperationTime = toBeOperationTime;
    }

    @Override
    public String toString() {
        return "ExpressionInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", operationTime=" + operationTime +
                ", toBeOperationTime=" + toBeOperationTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpressionInfo that = (ExpressionInfo) o;

        if (operationTime != that.operationTime) return false;
        if (toBeOperationTime != that.toBeOperationTime) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + operationTime;
        result = 31 * result + toBeOperationTime;
        return result;
    }
}

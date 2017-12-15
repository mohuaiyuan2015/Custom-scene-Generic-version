package com.tuubarobot.fragment;

/**
 * Created by YF-04 on 2017/12/12.
 */

public class ExecuteMoment {

    /**
     * 动作执行时刻，单位秒
     */
    private int moment;
    private boolean able;

    public int getMoment() {
        return moment;
    }

    public void setMoment(int moment) {
        this.moment = moment;
    }

    public boolean isAble() {
        return able;
    }

    public void setAble(boolean able) {
        this.able = able;
    }

    @Override
    public String toString() {
        return "ExecuteMoment{" +
                "moment=" + moment +
                ", able=" + able +
                '}';
    }
}

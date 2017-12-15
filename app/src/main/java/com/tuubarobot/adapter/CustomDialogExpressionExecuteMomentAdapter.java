package com.tuubarobot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tuubarobot.bluetoothcommunication.R;
import com.tuubarobot.fragment.ExecuteMoment;

import java.util.List;

/**
 * Created by YF-04 on 2017/12/13.
 */

public class CustomDialogExpressionExecuteMomentAdapter extends BaseAdapter {

    private List<ExecuteMoment> executeMoments;

    public CustomDialogExpressionExecuteMomentAdapter(List<ExecuteMoment>executeMoments){
        this.executeMoments=executeMoments;

    }

    @Override
    public int getCount() {
        return executeMoments==null?0:executeMoments.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExecuteMoment executeMoment= executeMoments.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expression_execute_moment_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.executeMomentDescription = (TextView) view.findViewById(R.id.actionDescription);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.executeMomentDescription.setText(String.valueOf(executeMoment.getMoment()));
        if (executeMoment.isAble()){
            viewHolder.executeMomentDescription.setVisibility(View.VISIBLE);
        }else {
            viewHolder.executeMomentDescription.setVisibility(View.GONE);
        }

        return view;
    }

    class ViewHolder{
        TextView executeMomentDescription;
    }

}

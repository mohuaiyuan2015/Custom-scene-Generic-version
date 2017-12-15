package com.tuubarobot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.tuubarobot.bluetoothcommunication.R;
import com.tuubarobot.fragment.ExpressionInfo;

import java.util.List;

/**
 * Created by YF-04 on 2017/12/11.
 */

public class CustomDialogExpressionAdapter extends BaseAdapter {

    private List<ExpressionInfo> expressionInfos;

    public CustomDialogExpressionAdapter(List<ExpressionInfo> expressionInfos){
        this.expressionInfos=expressionInfos;

    }

    @Override
    public int getCount() {
        return expressionInfos==null?0:expressionInfos.size();
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
        ExpressionInfo expressionInfo = expressionInfos.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expression_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.expressionDescription = (TextView) view.findViewById(R.id.actionDescription);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.expressionDescription.setText(expressionInfo.getName());

        return view;
    }

    class ViewHolder{
        TextView expressionDescription;
    }

}

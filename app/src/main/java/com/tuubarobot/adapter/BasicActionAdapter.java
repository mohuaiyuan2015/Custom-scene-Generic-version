package com.tuubarobot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tuubarobot.bluetoothcommunication.R;
import com.tuubarobot.fragment.ActionInfo;

import java.util.List;

/**
 * Created by YF-04 on 2017/12/11.
 */

public class BasicActionAdapter extends BaseAdapter{

    private List<ActionInfo> actionInfoList;
    private Context mContext;

    public BasicActionAdapter(Context context,List<ActionInfo> actionInfoList){
        this.mContext=context;
        this.actionInfoList=actionInfoList;
    }

    @Override
    public int getCount() {
        return actionInfoList==null?0:actionInfoList.size();
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
        ActionInfo actionInfo = actionInfoList.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basic_action_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.actionDescription = (TextView) view.findViewById(R.id.basicActionDescription);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.actionDescription.setText(actionInfo.getName());

        return view;
    }

    class ViewHolder{
        TextView actionDescription;
    }
}

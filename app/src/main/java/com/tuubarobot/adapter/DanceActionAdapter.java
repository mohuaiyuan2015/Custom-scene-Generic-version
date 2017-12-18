package com.tuubarobot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tuubarobot.bluetoothcommunication.R;
import com.tuubarobot.fragment.ActionInfo;
import com.tuubarobot.fragment.DanceActionInfo;

import java.util.List;

/**
 * Created by YF-04 on 2017/12/11.
 */

public class DanceActionAdapter extends BaseAdapter{

    private List<DanceActionInfo> danceActionInfoList;
    private Context mContext;

    public DanceActionAdapter(Context context, List<DanceActionInfo> danceActionInfoList) {
        this.mContext = context;
        this.danceActionInfoList = danceActionInfoList;
    }

    @Override
    public int getCount() {
        return danceActionInfoList==null?0:danceActionInfoList.size();
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
        DanceActionInfo danceActionInfo = danceActionInfoList.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dance_action_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.actionDescription = (TextView) view.findViewById(R.id.danceActionDescription);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.actionDescription.setText(danceActionInfo.getName());

        return view;
    }

    class ViewHolder{
        TextView actionDescription;
    }
}

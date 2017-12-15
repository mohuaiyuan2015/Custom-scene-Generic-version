package com.tuubarobot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tuubarobot.bluetoothcommunication.R;
import com.tuubarobot.fragment.ExecuteMoment;

import java.util.List;

/**
 * Created by YF-04 on 2017/12/11.
 */

public class CustomDialogActionExecuteMomentAdapter extends BaseAdapter{

    private List<ExecuteMoment> executeMoments;

    public CustomDialogActionExecuteMomentAdapter(List<ExecuteMoment>executeMoments){
        this.executeMoments=executeMoments;

    }

    @Override
    public int getCount() {
//        int count=-1;
//        boolean isContainsAble=false;
//        if (executeMoments==null ||executeMoments.isEmpty()){
//            count=0;
//        }else {
//            for (int i=0;i<executeMoments.size();i++){
//                if (executeMoments.get(i).isAble()){
//                    count++;
//                    isContainsAble=true;
//                }
//            }
//            if (!isContainsAble){
//                count=0;
//            }
//
//        }
//
//        return count;

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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.action_execute_moment_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.executeMomentItemLayout= (RelativeLayout) view.findViewById(R.id.executeMomentItemLayout);
            viewHolder.executeMomentDescription = (TextView) view.findViewById(R.id.actionDescription);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.executeMomentDescription.setText(String.valueOf(executeMoment.getMoment()));
        if (executeMoment.isAble()){
            viewHolder.executeMomentItemLayout.setVisibility(View.VISIBLE);
        }else {
//            view.setVisibility(View.GONE);
            viewHolder.executeMomentItemLayout.setVisibility(View.GONE);
        }

        return view;
    }

    class ViewHolder{
        TextView executeMomentDescription;
        RelativeLayout executeMomentItemLayout;
    }

}

package com.tuubarobot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tuubarobot.bluetoothcommunication.R;
import com.tuubarobot.fragment.ActionInfo;
import com.tuubarobot.fragment.CustomUtils;

import java.util.List;

/**0
 * Created by YF-04 on 2017/12/11.
 */

public class CustomDialogActionAdapter  extends BaseAdapter{

    private List<ActionInfo>actionInfos;

    private Context context;
    private CustomUtils customUtils;

    public CustomDialogActionAdapter(Context context, List<ActionInfo> actionInfos){
        this.actionInfos=actionInfos;
        this.context=context;
        customUtils=new CustomUtils(context);
    }

    @Override
    public int getCount() {
        return actionInfos==null?0:actionInfos.size();
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
        ActionInfo actionInfo = actionInfos.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.action_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.actionDescription = (TextView) view.findViewById(R.id.actionDescription);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

        }
        int operationTimeSeconds=customUtils.convertedIntoSeconds(actionInfo.getOperationTime());
        viewHolder.actionDescription.setText(actionInfo.getName()+"--"+operationTimeSeconds);

        return view;
    }

    class ViewHolder{
        TextView actionDescription;
    }

}

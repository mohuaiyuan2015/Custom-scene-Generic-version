package com.tuubarobot.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tuubarobot.bluetoothcommunication.MyAdapter;
import com.tuubarobot.bluetoothcommunication.R;
import com.tuubarobot.fragment.ActionInfo;

import java.util.List;

/**
 * Created by YF-04 on 2017/12/12.
 */

public class ShowActionAdapter extends RecyclerView.Adapter<ShowActionAdapter.ViewHolder> {
    private static final String TAG = "ShowActionAdapter";

    private List<ActionInfo> actionInfoList ;


    private OnItemLongClickListener onItemLongClickListener;
    private onItemDeleteListener onItemDeleteListener;

    public ShowActionAdapter(List<ActionInfo> actionInfoList){
        this.actionInfoList=actionInfoList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_action_item_layout,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (viewHolder.tv_delete.getVisibility()==View.GONE){
                    viewHolder.tv_delete.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.tv_delete.setVisibility(View.GONE);
                }
                if (onItemLongClickListener!=null){
                    onItemLongClickListener.onLongClick(v, (Integer) v.getTag());
                }

                return true;
            }
        });

        viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemDeleteListener!=null){
                    onItemDeleteListener.onDelete(v, (Integer) v.getTag());
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder cellViewHolder = (ViewHolder) holder;

        cellViewHolder.itemView.setTag(position);
        cellViewHolder.tv_delete.setTag(position);

        ActionInfo actionInfo=actionInfoList.get(position);
        String name=actionInfo.getName();
        Log.d(TAG, "动作名称: "+name);
        holder.name.setText("动作名称:"+name);
        int toBeoperationTime=actionInfo.getToBeOperationTime();
        Log.d(TAG, "动作下发时刻: "+toBeoperationTime);
        holder.toBeOperationTime.setText("动作下发时刻："+toBeoperationTime);
        //初始值为 不显示
        holder.tv_delete.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return actionInfoList==null?0:actionInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView toBeOperationTime;
        TextView tv_delete;

        public ViewHolder(final View view) {
            super(view);
            name= (TextView) view.findViewById(R.id.name);
            toBeOperationTime= (TextView) view.findViewById(R.id.toBeOperationTime);
            tv_delete= (Button) view.findViewById(R.id.tv_delete);
        }
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public ShowActionAdapter.onItemDeleteListener getOnItemDeleteListener() {
        return onItemDeleteListener;
    }

    public void setOnItemDeleteListener(ShowActionAdapter.onItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    public interface OnItemLongClickListener{
        public void onLongClick(View itemView, int position);
    }

    public interface onItemDeleteListener{
        public void onDelete(View itemView, int position);
    }

}

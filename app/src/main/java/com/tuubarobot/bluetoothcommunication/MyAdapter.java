package com.tuubarobot.bluetoothcommunication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;
import java.util.Map;

/**
 * Created by YF-04 on 2017/7/22.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static final String TAG = "MyAdapter";
    private List<Map<String,String>> list;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener  onItemLongClickListener;
    private onItemDeleteListener onItemDeleteListener;

    public MyAdapter(List<Map<String,String>>list){
        this.list=list;

    }

    public void clear() {
        list.clear();
    }




    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView question;
        TextView answer;
        Button tv_delete;

        public ViewHolder(final View view) {
            super(view);
            question= (TextView) view.findViewById(R.id.questionTextView);
            answer= (TextView) view.findViewById(R.id.answerTextView);
            tv_delete= (Button) view.findViewById(R.id.tv_delete);
        }
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dialog_item_layout,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });

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
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        ViewHolder cellViewHolder = (ViewHolder) holder;

        cellViewHolder.itemView.setTag(position);
        cellViewHolder.tv_delete.setTag(position);

        Map<String,String>map=list.get(position);
        holder.question.setText(map.get(Constants.QUESTION));
        holder.answer.setText(map.get(Constants.ANSWER));
        //初始值为 不显示
        holder.tv_delete.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public interface OnItemClickListener {
        public void onItemClick(View itemView, int position);
    }

    public interface OnItemLongClickListener{
        public void onLongClick(View itemView, int position);
    }

    public interface onItemDeleteListener{
        public void onDelete(View itemView, int position);
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemDeleteListener(MyAdapter.onItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }
}

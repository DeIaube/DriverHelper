package com.example.administrator.olddriverpromotionexam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.bean.Experience;

import java.util.List;

/**
 * Created by Administrator on 2017/5/13 0013.
 */

public class SimpleListViewAdapter extends RecyclerView.Adapter<SimpleListViewAdapter.MyViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    private List<Experience> experienceList;
    private OnClockListener onClockListener;

    public SimpleListViewAdapter(Context context, List<Experience> experienceList) {
        this.context = context;
        this.experienceList = experienceList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_text, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(experienceList.get(position).getTitle());
        holder.position = position;
    }

    public void setOnClockListener(OnClockListener onClockListener) {
        this.onClockListener = onClockListener;
    }

    public interface OnClockListener{
        void click(View v, int position);
    }

    @Override
    public int getItemCount() {
        return experienceList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        int position;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClockListener != null){
                        onClockListener.click(v, position);
                    }
                }
            });
        }
    }
}

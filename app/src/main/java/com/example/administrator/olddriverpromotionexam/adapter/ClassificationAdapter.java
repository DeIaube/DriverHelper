package com.example.administrator.olddriverpromotionexam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.bean.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class ClassificationAdapter extends RecyclerView.Adapter<ClassificationAdapter.MyViewHolder> {

    private Context context;
    private Map<String, List<Question>> data;
    private LayoutInflater inflater;
    private List<String> keys;
    private OnClickListener onClickListener;

    public ClassificationAdapter(Context context, Map<String, List<Question>> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        keys = new ArrayList<>(data.keySet());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_classification, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.position = position;
        holder.type.setText(keys.get(position));
        holder.number.setText(String.format("共%d道题目", data.get(keys.get(position)).size()));
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView type;
        TextView number;
        int position;
        public MyViewHolder(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.type);
            number = (TextView) itemView.findViewById(R.id.number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null){
                        onClickListener.onClick(v, keys.get(position));
                    }
                }
            });
        }
    }


    public interface OnClickListener{
        void onClick(View v, String key);
    }
}

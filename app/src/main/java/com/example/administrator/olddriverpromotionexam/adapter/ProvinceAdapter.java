package com.example.administrator.olddriverpromotionexam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.olddriverpromotionexam.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/14 0014.
 */

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.MyViewHolder> {


    private Context context;
    private List<String> data;
    private LayoutInflater inflater;
    private OnClickListener onClickListener;

    public ProvinceAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_province, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(data.get(position));
        holder.position = position;
    }

    public interface OnClickListener{
        void click(View v, int position);
    }

    @Override
    public int getItemCount() {
        return data.size();
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
                    if(onClickListener != null){
                        onClickListener.click(v, position);
                    }
                }
            });
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}

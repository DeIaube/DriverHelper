package com.example.administrator.olddriverpromotionexam.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.olddriverpromotionexam.R;

import java.util.List;


/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class OptionsArrayAdapter extends RecyclerView.Adapter<OptionsArrayAdapter.MyViewHolder> {

    private List<String> options;
    private Context context;
    private LayoutInflater inflater;
    private OnClickListener onClickListener;
    private int choise = -1;

    public OptionsArrayAdapter(Context context, List<String> options) {
        this.context = context;
        this.options = options;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_option, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.position = position;
        holder.tv.setText(((char)(position + 'A')) + " " + options.get(position));
        if(position == choise){
            holder.tv.setTextColor(context.getColor(R.color.colorAccent));
        }else{
            holder.tv.setTextColor(context.getColor(R.color.black));
        }
    }

    public void setChoise(int choise) {
        this.choise = choise;
    }

    public void clearChoise(){
        this.choise = -1;
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        private int position;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null){
                        onClickListener.onClick(v, position);
                        choise = position;
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onClick(View view, int choise);
    }
}

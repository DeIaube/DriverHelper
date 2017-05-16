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
 * Created by Administrator on 2017/5/16 0016.
 */

public class ChoiseQuestionNumberAdapter extends RecyclerView.Adapter<ChoiseQuestionNumberAdapter.MyViewHolder> {

    private Context context;
    private int currentChoise;
    private List<Integer> data;
    private LayoutInflater inflater;
    private OnClickListener onClickListener;

    public ChoiseQuestionNumberAdapter(Context context, List<Integer> data, int currentChoise) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.currentChoise = currentChoise;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_choise_question_number, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(data.get(position) + "道");
        holder.setIsRecyclable(false);
        holder.position = position;
        if(data.get(position) == currentChoise){
            holder.tv.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnClickListener{
        void click(View v, int position);
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

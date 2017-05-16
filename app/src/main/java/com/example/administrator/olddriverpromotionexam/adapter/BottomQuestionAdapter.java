package com.example.administrator.olddriverpromotionexam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.bean.QuestionState;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class BottomQuestionAdapter extends RecyclerView.Adapter<BottomQuestionAdapter.MyViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private QuestionState[] data;
    private int index;
    private OnClickListener onClickListener;

    public BottomQuestionAdapter(Context context, QuestionState[] data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.index = 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_bottom_question, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        holder.tv.setText(String.valueOf(position + 1));
        holder.position = position;
        if(data[position] == QuestionState.NORMAL){
            holder.tv.setTextColor(context.getResources().getColor(R.color.gray));
        }else if(data[position] == QuestionState.CORRECT){
            holder.tv.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else if(data[position] == QuestionState.ERROR){
            holder.tv.setTextColor(context.getResources().getColor(R.color.red));
        }


        if(position == index){
            holder.tv.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    public void update(int index, QuestionState[] data){
        this.index = index;
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void click(View view, int position);
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
}

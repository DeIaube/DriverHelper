package com.example.administrator.olddriverpromotionexam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.bean.QuestionRecoder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public class RecoderArrayAdapter  extends RecyclerView.Adapter<RecoderArrayAdapter.MyViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<QuestionRecoder> recoderList;

    public RecoderArrayAdapter(Context context, List<QuestionRecoder> recoderList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.recoderList = recoderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_recode, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(position > 0){
            QuestionRecoder recoder = recoderList.get(position-1);
            holder.result.setText(Integer.valueOf(recoder.getGrade()) >= 90 ? "及格" : "不及格");
            holder.grade.setText(recoder.getGrade());
            holder.timeConsum.setText(recoder.getTimeConsum());
            holder.submitDate.setText(recoder.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return recoderList.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView result;
        TextView grade;
        TextView timeConsum;
        TextView submitDate;
        public MyViewHolder(View itemView) {
            super(itemView);
            result = (TextView) itemView.findViewById(R.id.result);
            grade = (TextView) itemView.findViewById(R.id.grade);
            timeConsum = (TextView) itemView.findViewById(R.id.time_consum);
            submitDate = (TextView) itemView.findViewById(R.id.submit_date);
        }
    }
}

package com.example.administrator.diandianservice.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.diandianservice.R;

/**
 * Created by Administrator on 2017/12/10.
 */

public class EvaluationListAdapter extends RecyclerView.Adapter<EvaluationListAdapter.ViewHolder> {
    private String OAE[] = new String[20];
    public EvaluationListAdapter(String oae[]){
        this.OAE = oae;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluation, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_on.setText("订单号："+OAE[position+position]);
        holder.tv_eva.setText(OAE[position+position+1]);
    }

    @Override
    public int getItemCount() {
        return OAE == null ? 0 : OAE.length/2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_on,tv_eva;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_on = itemView.findViewById(R.id.item_evaluation_on);
            tv_eva = itemView.findViewById(R.id.item_evaluation);
        }
    }
}

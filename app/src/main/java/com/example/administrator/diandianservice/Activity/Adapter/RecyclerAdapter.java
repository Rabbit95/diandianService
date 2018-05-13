package com.example.administrator.diandianservice.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.diandianservice.R;

/**
 * Created by Administrator on 2017/12/10.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public int images[] = {R.mipmap.order,R.mipmap.pswd,R.mipmap.exit};
    public String text[] = {"查看评价","修改密码","退出账号"};
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    private RecyclerAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(RecyclerAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_section_head, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIv.setImageResource(images[position]);
        holder.mTv.setText(text[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return text.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv;
        private ImageView mIv;
        public ViewHolder(View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.icon);
            mTv = itemView.findViewById(R.id.text);
        }
    }
}

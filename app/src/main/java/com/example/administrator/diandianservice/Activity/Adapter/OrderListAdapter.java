package com.example.administrator.diandianservice.Activity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.diandianservice.Activity.Data.Order;
import com.example.administrator.diandianservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/10.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private Context context;
    private int pos;
    private List<Order> orders = new ArrayList<>();
    public OrderListAdapter(int pos,List<Order> orders){
        this.orders = orders;
        this.pos = pos;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    private OrderListAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OrderListAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_olist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String status = null;
        switch (orders.get(position).getOStatus()){
            case "0":
                status = "待送达";
                break;
            case "1":
                status = "已完成";
                break;
            case "2":
                status = "已取消";
                break;
        }
        holder.tv_oNumber.setText("订单号："+orders.get(position).getONumber());
        holder.tv_oStatus.setText("订单状态："+status);
        holder.tv_oTprice.setText("总价："+orders.get(position).getOTPrice());
        holder.tv_oSeat.setText("座位号："+orders.get(position).getOSNumber());
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
        return orders == null ? 0 : orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_oNumber,tv_oStatus,tv_oTprice,tv_oSeat;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_oNumber = itemView.findViewById(R.id.item_on);
            tv_oStatus = itemView.findViewById(R.id.item_status);
            tv_oTprice = itemView.findViewById(R.id.item_otprice);
            tv_oSeat = itemView.findViewById(R.id.item_seat);
        }
    }
}

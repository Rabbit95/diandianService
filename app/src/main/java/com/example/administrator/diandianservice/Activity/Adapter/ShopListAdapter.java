package com.example.administrator.diandianservice.Activity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.diandianservice.Activity.Data.ListUtils;
import com.example.administrator.diandianservice.R;

/**
 * created by yhao on 2017/8/18.
 */


public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.MyViewHolder> {


    private Context mContext;

    private SlidingMenu mOpenMenu;
    private SlidingMenu mScrollingMenu;

    SlidingMenu getScrollingMenu() {
        return mScrollingMenu;
    }

    public void setScrollingMenu(SlidingMenu scrollingMenu) {
        mScrollingMenu = scrollingMenu;
    }

    void holdOpenMenu(SlidingMenu slidingMenu) {
        mOpenMenu = slidingMenu;
    }

    void closeOpenMenu() {
        if (mOpenMenu != null && mOpenMenu.isOpen()) {
            mOpenMenu.closeMenu();
            mOpenMenu = null;
        }
    }

    public ShopListAdapter( Context context) {
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(mContext).load(ListUtils.menus.get(position).getImgURL()).into(holder.imageView);
        holder.name.setText(ListUtils.menus.get(position).getName());
        holder.dishesID.setText("类别:"+ListUtils.dishesIDList.get(ListUtils.menus.get(position).getDishesID()));
        holder.price.setText(ListUtils.menus.get(position).getPrice()+"元/份");
        holder.menuText.setText("删除");
        holder.menuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeOpenMenu();
                if (mOnClickListener != null) {
                    mOnClickListener.onMenuClick(position);
                }
            }
        });
        holder.slidingMenu.setCustomOnClickListener(new SlidingMenu.CustomOnClickListener() {
            @Override
            public void onClick() {
                if (mOnClickListener != null) {
                    mOnClickListener.onContentClick(position);
                }
            }
        });

    }

    public interface OnClickListener {
        void onMenuClick(int position);

        void onContentClick(int position);
    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }


    @Override
    public int getItemCount() {
        return ListUtils.menus.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menuText;
        TextView name,dishesID,price;
        ImageView imageView;
        LinearLayout content;
        SlidingMenu slidingMenu;


        MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_menu_imag);
            menuText = (TextView) itemView.findViewById(R.id.menuText);
            name = (TextView) itemView.findViewById(R.id.item_menu_name);
            dishesID = (TextView) itemView.findViewById(R.id.item_menu_dishesID);
            price = (TextView) itemView.findViewById(R.id.item_menu_price);
            content = (LinearLayout) itemView.findViewById(R.id.content);
            slidingMenu = (SlidingMenu) itemView.findViewById(R.id.slidingMenu);
        }
    }

}

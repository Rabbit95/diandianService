package com.example.administrator.diandianservice.Activity.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRole;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.administrator.diandianservice.Activity.Adapter.OrderListAdapter;
import com.example.administrator.diandianservice.Activity.Data.ListUtils;
import com.example.administrator.diandianservice.Activity.Data.Order;
import com.example.administrator.diandianservice.R;

import java.util.Arrays;
import java.util.List;

//import com.example.administrator.diandianservice.Activity.Adapter.OrderListAdapter;

/**
 * Created by Administrator on 2018/2/19.
 */

public class OrderActivity extends Fragment {
    private RecyclerView recyclerView;
    private OrderListAdapter orderListAdapter;
    private Spinner spinner;
    private int option;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    orderListAdapter = new OrderListAdapter(option,ListUtils.orders);
                    orderListAdapter.setOnItemClickListener(new OrderListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent();
                            intent.setClass(getContext(),OrderItemActivity.class);
                            intent.putExtra("position",position);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(orderListAdapter);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.activity_order,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spinner = getActivity().findViewById(R.id.spinner);
        recyclerView = getActivity().findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                switch (pos){
                    case 0:
                        //待送达
                        option = pos;
                        getData(option);
                        break;
                    case 1:
                        //已完成
                        option = pos;
                        getData(option);
                        break;
                    case 2:
                        //已取消
                        option = pos;
                        getData(option);
                        break;
                    case 3:
                        //全部
                        option = pos;
                        getData(option);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        ListUtils.getDshesIDList();
        initACL();
    }
    public void getData(final int option){
        ListUtils.orders.clear();
        AVQuery avQuery = new AVQuery("Order");
        avQuery.selectKeys(Arrays.asList("UserName","phoneNumber","UserID","OContent", "OTPrice","ONumber","OStatus","OSNumber","evaluation","OTime"));
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    if(Integer.valueOf(avObject.getString("OStatus")) == option || option == 3){
                        ListUtils.orders.add(new Order(avObject.getString("UserName"),avObject.getString("phoneNumber"),avObject.getString("ONumber"),avObject.getString("OContent"),avObject.getString("OTPrice"),avObject.getString("OStatus"),avObject.getString("OSNumber"),avObject.getString("evaluation"),avObject.getString("OTime")));
                    }
                }
                Message msg = handler.obtainMessage(0);
                handler.sendMessage(msg);
            }
        });
    }
    public void initACL(){
        final AVQuery<AVRole> roleQuery =new AVQuery<AVRole>("_Role");
        roleQuery.whereEqualTo("name","Administrator");
        roleQuery.findInBackground(new FindCallback<AVRole>() {
            @Override
            public void done(List<AVRole> list, AVException e) {
                // 如果角色存在
                if (list.size() > 0){
                    final AVRole administratorRole = list.get(0);
                    roleQuery.whereEqualTo("users", AVUser.getCurrentUser());
                    roleQuery.findInBackground(new FindCallback<AVRole>() {
                        @Override
                        public void done(List<AVRole> list, AVException e) {
                            if (list.size()  == 0){
                                administratorRole.getUsers().add(AVUser.getCurrentUser());// 赋予角色
                                administratorRole.saveInBackground();
                            }
                        }
                    });
                }else {
                    // 角色不存在，就新建角色
                    AVRole administratorRole=new AVRole("Administrator");
                    administratorRole.getUsers().add(AVUser.getCurrentUser());// 赋予角色
                    administratorRole.saveInBackground();
                }
            }
        });
    }
}

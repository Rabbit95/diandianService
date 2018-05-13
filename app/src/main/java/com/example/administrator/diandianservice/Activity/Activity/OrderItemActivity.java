package com.example.administrator.diandianservice.Activity.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.diandianservice.Activity.Adapter.OrderAdapter;
import com.example.administrator.diandianservice.Activity.Data.ListUtils;
import com.example.administrator.diandianservice.R;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2018/2/20.
 */

public class OrderItemActivity extends FragmentActivity {
    private TextView name,phoneNumber,ONumber,OSNumber,OTprice,OEvaluation,OTime;
    private Button complete;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private int option;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);
        option = getIntent().getIntExtra("position",0);
        initView();
    }
    public void initView(){
        complete = findViewById(R.id.id_order_complete);
        if(Integer.valueOf(ListUtils.orders.get(option).getOStatus()) != 0){
            complete.setEnabled(false);
        }
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVQuery avQuery = new AVQuery("Order");
                avQuery.whereContains("ONumber",ListUtils.orders.get(option).getONumber());
                avQuery.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        for (AVObject avObject : list) {
//                            Toasty.info(OrderItemActivity.this,list.size()+"", Toast.LENGTH_LONG).show();
                            avObject.put("OStatus","1");
                            avObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if( e!=null ) {
                                        Log.d("sc", e.toString());
                                    }else{
                                        Toasty.info(OrderItemActivity.this,"订单已完成", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });

                        }
                    }
                });
            }
        });
        recyclerView = findViewById(R.id.id_order_rv);
        name = findViewById(R.id.id_order_name);
        phoneNumber = findViewById(R.id.id_order_phone);
        ONumber = findViewById(R.id.id_order_ONumber);
        OSNumber = findViewById(R.id.id_order_salt);
        OTprice = findViewById(R.id.id_order_Tprice);
        OEvaluation = findViewById(R.id.id_order_evaluation);
        OTime = findViewById(R.id.id_order_OTime);
        name.setText(ListUtils.orders.get(option).getuserName());
        phoneNumber.setText(ListUtils.orders.get(option).getphoneNumber());
        ONumber.setText("订单号："+ListUtils.orders.get(option).getONumber());
        OSNumber.setText(ListUtils.orders.get(option).getOSNumber());
        OTime.setText("下单时间："+ListUtils.orders.get(option).getOTime());
        OTprice.setText(ListUtils.orders.get(option).getOTPrice()+"元");
        String NAN[] = ListUtils.orders.get(option).getOContent().split(":|,");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(NAN);
        recyclerView.setAdapter(orderAdapter);
        if(ListUtils.orders.get(option).getOEvaluation()!=null){
            OEvaluation.setText(ListUtils.orders.get(option).getOEvaluation());
        }
    }
}

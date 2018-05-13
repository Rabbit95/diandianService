package com.example.administrator.diandianservice.Activity.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.administrator.diandianservice.Activity.Adapter.EvaluationListAdapter;
import com.example.administrator.diandianservice.R;

import java.util.List;

/**
 * Created by Administrator on 2018/2/19.
 */

public class EvaluationActivity extends FragmentActivity {
    private ImageView back;
    private RecyclerView recyclerView;
    private EvaluationListAdapter evaluationListAdapter;
    private StringBuffer oAe_sb = new StringBuffer();
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    String OAE[] = oAe_sb.toString().split(",");
                    evaluationListAdapter = new EvaluationListAdapter(OAE);
                    recyclerView.setAdapter(evaluationListAdapter);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        initView();
    }
    private void initView(){
        back = findViewById(R.id.id_evaluation_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.id_evaluation_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(EvaluationActivity.this,
                DividerItemDecoration.VERTICAL));
        AVQuery<AVObject> query = new AVQuery<>("Order");
        query.whereExists("evaluation");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    oAe_sb.append(avObject.getString("ONumber")+","+avObject.getString("evaluation")+",");
                }
                Message msg = handler.obtainMessage(0);
                handler.sendMessage(msg);
            }
        });
    }
}


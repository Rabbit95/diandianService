package com.example.administrator.diandianservice.Activity.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.example.administrator.diandianservice.Activity.Adapter.RecyclerAdapter;
import com.example.administrator.diandianservice.R;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2018/1/1.
 */

public class MyActivity extends FragmentActivity {
    private ImageView back;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    Intent intent = new Intent();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initView();
    }
    public void initView(){
        recyclerView = findViewById(R.id.id_my_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        intent.setClass(MyActivity.this,MyInfoActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
//                        intent.setClass(MyActivity.this,OldOrderListActivity.class);
//                        startActivity(intent);
                        break;
                    case 2:
                        showDialog();
                        break;
                    case 3:
                        userOut();
                        break;
                }
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        back = findViewById(R.id.id_my_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void userOut(){
        AVUser.logOut();// 清除缓存用户对象
        AVUser currentUser = AVUser.getCurrentUser();// 现在的 currentUser 是 null 了
        Toasty.info(MyActivity.this,"登陆退出", Toast.LENGTH_LONG).show();
        finish();
        intent.setClass(MyActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    public void showDialog() {
        AlertDialog.Builder dialog =
                new AlertDialog.Builder(MyActivity.this);
        final View dialogView = LayoutInflater.from(MyActivity.this)
                .inflate(R.layout.dialog, null);
        dialog.setView(dialogView);
        dialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取EditView中的输入内容
                        EditText edit_name =
                                (EditText) dialogView.findViewById(R.id.id_dialog_nowpswd);
                        EditText edit_pswd =
                                (EditText) dialogView.findViewById(R.id.id_dialog_nowpswd);
                        AVUser.logInInBackground(edit_name.getText().toString(), edit_pswd.getText().toString(), new LogInCallback<AVUser>() {
                                    @Override
                                    public void done(AVUser avUser, AVException e) {
//                                        Toast.makeText(MyActivity.this,AVUser.getCurrentUser().getEmail(),Toast.LENGTH_LONG).show();
                                        AVUser.requestPasswordResetInBackground(AVUser.getCurrentUser().getEmail(), new RequestPasswordResetCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                if (e == null) {
                                                    Toasty.info(MyActivity.this,"邮件已发送，请注意查收", Toast.LENGTH_LONG).show();
                                                } else {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                }
                        );
                    }
                });
        dialog.show();
    }
}

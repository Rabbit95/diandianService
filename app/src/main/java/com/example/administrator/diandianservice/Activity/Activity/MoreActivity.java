package com.example.administrator.diandianservice.Activity.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.example.administrator.diandianservice.Activity.Adapter.RecyclerAdapter;
import com.example.administrator.diandianservice.R;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2018/2/19.
 */

public class MoreActivity extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    Intent intent = new Intent();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.activity_more,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    public void initView(){
        recyclerView = getActivity().findViewById(R.id.id_more_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        intent.setClass(getContext(),EvaluationActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        showDialog();
                        break;
                    case 2:
                        userOut();
                        break;
                }
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
    }
    public void userOut(){
        AVUser.logOut();// 清除缓存用户对象
        AVUser currentUser = AVUser.getCurrentUser();// 现在的 currentUser 是 null 了
        Toasty.info(getContext(),"登陆退出", Toast.LENGTH_LONG).show();
        intent.setClass(getContext(),LoginActivity.class);
        startActivity(intent);
    }
    public void showDialog() {
        AlertDialog.Builder dialog =
                new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getContext())
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
                                                    Toasty.info(getContext(),"邮件已发送，请注意查收", Toast.LENGTH_LONG).show();
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

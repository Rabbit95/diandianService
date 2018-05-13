package com.example.administrator.diandianservice.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.administrator.diandianservice.R;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/12/27.
 */

public class LoginActivity extends FragmentActivity {
    private TextView name,pswd;
    private Button login;
//    private Button login,registered,forget;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AVOSCloud.useAVCloudCN();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "TADkVyABvXnuFKUvQdKAHE06-gzGzoHsz", "Ond1vLfmL9pEUhJLFsvaIxNH");
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            //用户缓存不为空，跳转到个人中心
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        } else {
            //用户缓存为空，跳转到登陆
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this,LoginActivity.class);
//            startActivity(intent);
        }
        initView();
    }
    private void initView(){
        name = findViewById(R.id.id_login_name);
        pswd = findViewById(R.id.id_login_pswd);
        login = findViewById(R.id.id_login_login);
//        registered = findViewById(R.id.id_login_registered);
//        forget = findViewById(R.id.id_login_forget);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(TextUtils.isEmpty(name.getText()))&&!(TextUtils.isEmpty(pswd.getText()))) {
                    login(name.getText().toString(),pswd.getText().toString());
                }else{
                    Toasty.error(LoginActivity.this,"用户名或密码不得为空", Toast.LENGTH_LONG).show();
                }
            }
        });
//        registered.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(LoginActivity.this,RegisteredActivity.class);
//                startActivity(intent);
//            }
//        });
//        forget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(LoginActivity.this,ForgetActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    public void login(final String name, String pswd){
        AVUser.logInInBackground(name, pswd, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if(e == null) {
                    if(name.equals("admin")){
                        Toasty.info(LoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toasty.error(LoginActivity.this, "该用户没有登陆权限", Toast.LENGTH_LONG).show();
                    }
                }else{
                    switch (e.getCode()){
                        case 210:
                            Toasty.error(LoginActivity.this, "用户名密码不匹配", Toast.LENGTH_LONG).show();break;
                        case 211:
                            Toasty.error(LoginActivity.this, "用户不存在", Toast.LENGTH_LONG).show();break;
                        case 200:
                            Toasty.error(LoginActivity.this, "用户名或密码不的为空", Toast.LENGTH_LONG).show();break;
                        case 201:
                            Toasty.error(LoginActivity.this, "用户名或密码不的为空", Toast.LENGTH_LONG).show();break;
                        default:
                            Toasty.error(LoginActivity.this, "未知错误", Toast.LENGTH_LONG).show();break;
                    }
                }
            }
        });
    }
}

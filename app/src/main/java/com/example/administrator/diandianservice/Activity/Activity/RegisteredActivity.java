package com.example.administrator.diandianservice.Activity.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.administrator.diandianservice.R;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2018/1/1.
 */

public class RegisteredActivity extends FragmentActivity {
    private TextView name,pswd,pswdan,email;
    private Button registered;
    private ImageView back;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        initView();
    }
    public void initView(){
        name = findViewById(R.id.id_registered_name);
        pswd = findViewById(R.id.id_registered_pswd);
        pswdan = findViewById(R.id.id_registered_pswdan);
        email = findViewById(R.id.id_registered_email);
        registered = findViewById(R.id.id_registered_registered);
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pswd.getText().toString().equals(pswdan.getText().toString()) ){
                    registered(name.getText().toString(),pswd.getText().toString(),email.getText().toString());
                }else{
                    Toasty.error(RegisteredActivity.this,"密码不一致，重新输入", Toast.LENGTH_LONG).show();
                }
            }
        });
        back = findViewById(R.id.id_registered_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    } public void registered(String name, String pswd, String email){
        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername(name);// 设置用户名
        user.setPassword(pswd);// 设置密码
        user.setEmail(email);//设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                    Toasty.success(RegisteredActivity.this,"注册成功", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    switch (e.getCode()){
                        case 217:
                            Toasty.error(RegisteredActivity.this,"用户名或密码不得为空", Toast.LENGTH_LONG).show();break;
                        case 218:
                            Toasty.error(RegisteredActivity.this,"用户名或密码不得为空", Toast.LENGTH_LONG).show();break;
                        case 202:
                            Toasty.error(RegisteredActivity.this,"用户名已被占用", Toast.LENGTH_LONG).show();break;
                        default:
                            Toasty.error(RegisteredActivity.this,"未知错误", Toast.LENGTH_LONG).show();break;
                    }
                }
            }
        });

    }

}

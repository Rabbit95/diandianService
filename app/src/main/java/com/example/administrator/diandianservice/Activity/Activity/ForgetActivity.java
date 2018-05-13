package com.example.administrator.diandianservice.Activity.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.example.administrator.diandianservice.R;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ForgetActivity extends FragmentActivity {
    TextView email;
    Button forget;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initView();
    }
    public void initView(){
        email = findViewById(R.id.id_forget_email);
        forget = findViewById(R.id.id_forget_forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(email.getText())){
                    Toasty.error(ForgetActivity.this,"邮箱地址不得为空", Toast.LENGTH_LONG).show();
                }else{
                    AVUser.requestPasswordResetInBackground(email.getText().toString(), new RequestPasswordResetCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Toasty.info(ForgetActivity.this,"邮件已发送，请注意查收", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}



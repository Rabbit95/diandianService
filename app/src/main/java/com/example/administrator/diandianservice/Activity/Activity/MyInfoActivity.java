package com.example.administrator.diandianservice.Activity.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.diandianservice.R;

/**
 * Created by Administrator on 2018/1/9.
 */

public class MyInfoActivity extends FragmentActivity {
    private EditText name,phoneNumber;
    private ImageView back;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
    }
    public void initView(){
        name = findViewById(R.id.id_info_name);
        phoneNumber = findViewById(R.id.id_info_phoneNumber);
        back = findViewById(R.id.id_info_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(TextUtils.isEmpty(name.getText())) && !(TextUtils.isEmpty(phoneNumber.getText()))){
                    AVUser.getCurrentUser().put("age", 25);
                    AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            AVUser.getCurrentUser().put("name", name.getText());
                            AVUser.getCurrentUser().put("phoneNumber", phoneNumber.getText());
                            AVUser.getCurrentUser().saveInBackground();
                            finish();
                        }
                    });
                }
            }
        });

    }

}

package com.example.administrator.diandianservice.Activity.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.example.administrator.diandianservice.Activity.Data.ListUtils;
import com.example.administrator.diandianservice.R;

public class MainActivity extends FragmentActivity {
    private Fragment f_order;
    private Fragment f_shop;
    private Fragment f_more;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    hideFragment(transaction);
                    transaction = manager.beginTransaction();
                    f_order  = new OrderActivity();
                    transaction.replace(R.id.id_layout_fl, f_order);
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    hideFragment(transaction);
                    transaction = manager.beginTransaction();
                    f_shop = new ShopActivity();
                    transaction.replace(R.id.id_layout_fl, f_shop);
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    hideFragment(transaction);
                    transaction = manager.beginTransaction();
                    f_more = new MoreActivity();
                    transaction.replace(R.id.id_layout_fl, f_more);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        f_order = new OrderActivity();
        transaction.replace(R.id.id_layout_fl, f_order);
        transaction.commit();
        ListUtils.getDshesIDList();
    }
    private void hideFragment(FragmentTransaction transaction) {
        if (f_order != null) {
            //transaction.hide(f1);隐藏方法也可以实现同样的效果，不过我一般使用去除
            transaction.remove(f_order);
        }
        if (f_shop != null) {
            //transaction.hide(f2);
            transaction.remove(f_shop);
        }
        if (f_more != null) {
            //transaction.hide(f3);
            transaction.remove(f_more);
        }
    }
}

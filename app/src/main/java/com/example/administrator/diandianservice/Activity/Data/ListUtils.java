package com.example.administrator.diandianservice.Activity.Data;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/7.
 */

public class ListUtils {
    public static Map<String,String> dishesIDList = new HashMap<>();
    public static List<Order> orders = new ArrayList<>();
    public static List<Menu> menus = new ArrayList<>();
    public static List slist = new ArrayList<String>();
    public static List<Shop> shops = new ArrayList<>();


    public void oadd(Order order){
        orders.add(order);
    }
    public static int osize(){
        return orders.size();
    }
    public static String getONumber(int i){
        return orders.get(i).getONumber();
    }

    public void mAdd(Menu menu){
        menus.add(menu);
    }
    public void mRemove(int pos) { menus.remove(pos);}
    public static int mSize(){
        return menus.size();
    }

    public static void getDshesIDList() {
        ListUtils.slist.clear();
        ListUtils.dishesIDList.clear();
        AVQuery<AVObject> DIDquery = new AVQuery<>("DishesID");
        DIDquery.whereExists("DishesID");
        DIDquery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    ListUtils.dishesIDList.put(String.valueOf(avObject.get("DishesID")),avObject.getString("IDName"));
                    Log.d("getlistsize",ListUtils.dishesIDList.size()+"");
                }
            }
        });
    }

    public void shopsAdd(Shop shop){
        shops.add(shop);
    }
}

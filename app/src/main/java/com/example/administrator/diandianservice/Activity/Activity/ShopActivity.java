package com.example.administrator.diandianservice.Activity.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.example.administrator.diandianservice.Activity.Adapter.ShopListAdapter;
import com.example.administrator.diandianservice.Activity.Data.ListUtils;
import com.example.administrator.diandianservice.Activity.Data.Menu;
import com.example.administrator.diandianservice.Activity.Data.Shop;
import com.example.administrator.diandianservice.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/19.
 */

public class ShopActivity extends Fragment {
    private ImageView add,logo;
    private TextView name,description;
    private RecyclerView mRecyclerView;
    private ShopListAdapter shopListAdapter;
    private View dialogView;
    private ImageView iv_img;
    private String imagePath;
    private Menu menu = new Menu();
    private String ImgURL;
    private int option;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    shopListAdapter  = new ShopListAdapter(getContext());
                    mRecyclerView.setAdapter(shopListAdapter);
                    shopListAdapter.setOnClickListener(new ShopListAdapter.OnClickListener() {
                        @Override
                        public void onMenuClick(int position) {
                            deleteMenu(position);
                            shopListAdapter.notifyItemRemoved(position);
                            shopListAdapter.notifyItemRangeChanged(0,ListUtils.menus.size());
                        }

                        @Override
                        public void onContentClick(int position) {
//                            Toast.makeText(getContext(), "click pos = " + position, Toast.LENGTH_SHORT).show();
                            showDialog(position);
                        }
                    });
                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            shopListAdapter.setScrollingMenu(null);
                        }
                    });
                    break;
                case 1:
                    AVObject avObject = new AVObject("Dishes");
                    avObject.put("Name",menu.getName());
                    avObject.put("Price",menu.getPrice());
                    avObject.put("DishesID",menu.getDishesID());
                    avObject.put("ImageURL",menu.getImgURL());
                    AVACL acl = new AVACL();
                    acl.setPublicReadAccess(true);   //此处设置的是所有人的可读权限
                    acl.setWriteAccess(AVUser.getCurrentUser(), true);   //而这里设置了 Post 创建者的写权限
                    avObject.setACL(acl);//设置 ACL
                    avObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
//                                            Log.d("e",e.toString());
                            shopListAdapter  = new ShopListAdapter(getContext());
                            ListUtils.menus.add(menu);
//                            shopListAdapter.notifyItemRangeChanged(0,ListUtils.menus.size());
                            mRecyclerView.setAdapter(shopListAdapter);
                            shopListAdapter.setOnClickListener(new ShopListAdapter.OnClickListener() {
                                @Override
                                public void onMenuClick(int position) {
                                    deleteMenu(position);
                                    shopListAdapter.notifyItemRemoved(position);
                                    shopListAdapter.notifyItemRangeChanged(0,ListUtils.menus.size());
                                }

                                @Override
                                public void onContentClick(int position) {
//                            Toast.makeText(getContext(), "click pos = " + position, Toast.LENGTH_SHORT).show();
                                    showDialog(position);
                                }
                            });
                            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                    shopListAdapter.setScrollingMenu(null);
                                }
                            });
                            if (e != null) {
                                Log.d("e", e.toString());
                            }
                        }
                    });
                    break;
                case 2:
                    final AVQuery avQuery = new AVQuery("Dishes");
                    avQuery.whereEqualTo("ID", Integer.valueOf(ListUtils.menus.get(option).getID()));
                    avQuery.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            for (AVObject avObject : list) {
                                avObject.put("Name",ListUtils.menus.get(option).getName());
                                avObject.put("Price",ListUtils.menus.get(option).getPrice());
                                avObject.put("DishesID",ListUtils.menus.get(option).getDishesID());
                                if(ImgURL != null) {
                                    avObject.put("ImageURL", ImgURL);
                                }
                                AVACL acl = new AVACL();
                                acl.setPublicReadAccess(true);   //此处设置的是所有人的可读权限
                                acl.setWriteAccess(AVUser.getCurrentUser(), true);   //而这里设置了 Post 创建者的写权限
                                avObject.setACL(acl);//设置 ACL
                                avObject.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
//                                            Log.d("e",e.toString());
                                        shopListAdapter  = new ShopListAdapter(getContext());
//                            shopListAdapter.notifyItemRangeChanged(0,ListUtils.menus.size());
                                        mRecyclerView.setAdapter(shopListAdapter);
                                        shopListAdapter.setOnClickListener(new ShopListAdapter.OnClickListener() {
                                            @Override
                                            public void onMenuClick(int position) {
                                                deleteMenu(position);
                                                shopListAdapter.notifyItemRemoved(position);
                                                shopListAdapter.notifyItemRangeChanged(0,ListUtils.menus.size());
                                            }

                                            @Override
                                            public void onContentClick(int position) {
//                            Toast.makeText(getContext(), "click pos = " + position, Toast.LENGTH_SHORT).show();
                                                showDialog(position);
                                            }
                                        });
                                        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                            @Override
                                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                                super.onScrollStateChanged(recyclerView, newState);
                                                shopListAdapter.setScrollingMenu(null);
                                            }
                                        });
                                        if (e != null) {
                                            Log.d("c", e.toString());
                                        }
                                    }
                                });
                            }
                        }
                    });
                    break;
                case 3:
                    Glide.with(getContext()).load(ListUtils.shops.get(0).getShop_Logo_Url()).into(logo);
                    name.setText(ListUtils.shops.get(0).getShop_Name());
                    description.setText(ListUtils.shops.get(0).getShop_Description());
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.activity_shop,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }
    public void initData(){
        ListUtils.menus.clear();
        AVQuery<AVObject> query = new AVQuery<>("Dishes");
        query.whereExists("ID");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    ListUtils.menus.add(new Menu(avObject.getString("ImageURL"),avObject.getString("Name"),avObject.getString("Price"),avObject.getString("DishesID"),String.valueOf(avObject.get("ID"))));

                }
                for (Menu menu1 : ListUtils.menus) {
                }
                Message msg = handler.obtainMessage(0);
                handler.sendMessage(msg);
            }
        });
        AVQuery<AVObject> query1 = new AVQuery<>("ShopInfo");
        query1.whereExists("ShopName");
        query1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for(AVObject avObject : list){
                    ListUtils.shops.add(new Shop(avObject.getString("ShopLogo"),avObject.getString("ShopName"),avObject.getString("ShopDescription")));
                }
                Log.d("shop", ListUtils.shops.get(0).getShop_Logo_Url());
                Message msg = handler.obtainMessage(3);
                handler.sendMessage(msg);
            }
        });
    }
    public void initView(){
        logo = getActivity().findViewById(R.id.id_shop_logo);
        name = getActivity().findViewById(R.id.id_shop_name);
        description = getActivity().findViewById(R.id.id_shop_description);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
        add = getActivity().findViewById(R.id.id_shop_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionDialog();
            }
        });
        mRecyclerView = getActivity().findViewById(R.id.id_shop_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
    }
    public void selectionDialog(){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_selection,null);
        dialogBuilder.setView(dialogView);
        final AlertDialog dialog  = dialogBuilder.show();
        Button bt1 = dialogView.findViewById(R.id.bt1);
        Button bt2 = dialogView.findViewById(R.id.bt2);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("bt", "onClick: bt1");
                showDialog(-1);
                dialog.dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("bt", "onClick: bt2");
                dialog.dismiss();
                aDIDDialog();
            }
        });
    }
        public void aDIDDialog(){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_newdishes,null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText ed_dishes = dialogView.findViewById(R.id.dialog_dishes);
                AVObject avObject = new AVObject("DishesID");
                avObject.put("IDName",ed_dishes.getText());
                AVACL acl = new AVACL();
                acl.setPublicReadAccess(true);   //此处设置的是所有人的可读权限
                acl.setWriteAccess(AVUser.getCurrentUser(), true);   //而这里设置了 Post 创建者的写权限
                avObject.setACL(acl);//设置 ACL
                avObject.saveInBackground();
                ListUtils.getDshesIDList();
            }
        });
        dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
        dialogBuilder.show();


    }
    public void showDialog(final int postion) {
        final String[] DishesID = new String[1];
        final AlertDialog.Builder dialog =
                new AlertDialog.Builder(getContext());
        dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_menu, null);
        final Spinner spinner = dialogView.findViewById(R.id.dialog_menu_dishesID);
        final ArrayAdapter<String> adapter;
        if(ListUtils.dishesIDList.size()>ListUtils.slist.size()) {
            Iterator iter = ListUtils.dishesIDList.entrySet().iterator();
            while ((iter.hasNext())) {
                Map.Entry entry = (Map.Entry) iter.next();
                ListUtils.slist.add(entry.getValue());
            }
        }
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.simple_item,ListUtils.slist);
        adapter.setDropDownViewResource(R.layout.simple_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //(String) spinner.getSelectedItem()

                DishesID[0] = getKey((String) spinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        iv_img = dialogView.findViewById(R.id.dialog_menu_img);
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
        final EditText ed_name = dialogView.findViewById(R.id.dialog_menu_name);
        final EditText ed_price = dialogView.findViewById(R.id.dialog_menu_price);
        if(postion >= 0) {
            Glide.with(getContext()).load(ListUtils.menus.get(postion).getImgURL()).into(iv_img);
            ed_name.setText(ListUtils.menus.get(postion).getName());
            ed_price.setText(ListUtils.menus.get(postion).getPrice());
        }

        dialog.setView(dialogView);
        dialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(postion >= 0) {
                            ListUtils.menus.get(postion).setName(ed_name.getText().toString());
                            ListUtils.menus.get(postion).setPrice(ed_price.getText().toString());
                            ListUtils.menus.get(postion).setDishesID(String.valueOf(DishesID[0]));
                            option = postion;
                            Message msg = handler.obtainMessage(2);
                            handler.sendMessage(msg);
                        }else{
//                            Log.d("name", "onClick: "+ed_name.getText());

                            menu.setName(ed_name.getText().toString());
                            menu.setPrice(ed_price.getText().toString());
                            menu.setDishesID(String.valueOf(DishesID[0]));
                            Message msg = handler.obtainMessage(1);
                            handler.sendMessage(msg);
                        }
                    }
                });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContext().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            imagePath = c.getString(columnIndex);
            c.close();
            putImg(imagePath);
        }
    }
    public void putImg(final String imagePath){
        String []name = imagePath.split("/");
        try {
            final AVFile file  = AVFile.withAbsoluteLocalPath(name[name.length-1], imagePath);
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
//                    Log.d("imgUrl", file.getUrl());//返回一个唯一的 Url 地址
                    ImgURL = file.getUrl();
                    menu.setImgURL(file.getUrl());
                    Glide.with(getContext()).load(file.getUrl()).into(iv_img);
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void deleteMenu(int postion){
        final AVQuery avQuery = new AVQuery("Dishes");
        avQuery.whereEqualTo("ID", Integer.valueOf(ListUtils.menus.get(postion).getID()));
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    avObject.deleteInBackground();
                }
            }
        });
        ListUtils.menus.remove(postion);
    }
    private String getKey(String value) {
        ArrayList<String> keyList = new ArrayList<String>();
        String key = null;
        Set<Map.Entry<String, String>> set = ListUtils.dishesIDList.entrySet();// entrySet()方法就是把map中的每个键值对变成对应成Set集合中的一个对象.
        // set对象中的内容如下:[3=c, 2=b, 1=a, 5=e, 4=c]
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            // entry中的内容就是set集合中的每个对象(map集合中的一个键值对)3=c....
            // Map.Entry就是一种类型,专值map中的一个键值对组成的对象.
            if (entry.getValue().equals(value)){
                key = (String) entry.getKey();
            }
        }
        return key;
    }
}

package com.example.administrator.diandianservice.Activity.Data;

/**
 * Created by Administrator on 2018/5/10.
 */

public class Shop {
    private String Shop_Logo_Url;
    private String Shop_Name;
    private String Shop_Description;

    public Shop(String slu, String sname, String sd){
        this.Shop_Logo_Url = slu;
        this.Shop_Name = sname;
        this.Shop_Description = sd;
    }

    public String getShop_Logo_Url() {
        return Shop_Logo_Url;
    }

    public void setShop_Logo_Url(String shop_Logo_Url) {
        Shop_Logo_Url = shop_Logo_Url;
    }

    public String getShop_Name() {
        return Shop_Name;
    }

    public void setShop_Name(String shop_Name) {
        Shop_Name = shop_Name;
    }

    public String getShop_Description() {
        return Shop_Description;
    }

    public void setShop_Description(String shop_Description) {
        Shop_Description = shop_Description;
    }
}

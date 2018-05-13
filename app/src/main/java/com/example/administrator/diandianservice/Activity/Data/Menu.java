package com.example.administrator.diandianservice.Activity.Data;

/**
 * Created by Administrator on 2018/2/24.
 */

public class Menu {
    private String imgURL;
    private String name;
    private String price;
    private String dishesID;
    private String ID;
    public Menu(){

    }
    public Menu(String imgurl, String name, String price, String dishesID,String id){
        this.imgURL = imgurl;
        this.name = name;
        this.price = price;
        this.dishesID = dishesID;
        this.ID = id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDishesID() {
        return dishesID;
    }

    public void setDishesID(String dishesID) {
        this.dishesID = dishesID;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "imgURL='" + imgURL + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", dishesID='" + dishesID + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }
}

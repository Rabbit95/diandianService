package com.example.administrator.diandianservice.Activity.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/6.
 */

public class Order implements Serializable {
    private String userName;
    private String phoneNumber;
    private String OContent;
    private String ONumber;
    private String OTPrice;
    private String OStatus;
    private String OSNumber;
    private String OEvaluation;
    private String OTime;

    public String getOTime() {
        return OTime;
    }

    public void setOTime(String OTime) {
        this.OTime = OTime;
    }

    public Order(){

    }
    public Order(String name,String phoneNumber,String oNumber, String oContent, String oTPrice, String oStatus, String oSNumber,String oEvaluation,String oTime){
        this.userName = name;
        this.phoneNumber = phoneNumber;
        this.ONumber = oNumber;
        this.OContent = oContent;
        this.OTPrice = oTPrice;
        this.OStatus = oStatus;
        this.OSNumber = oSNumber;
        this.OEvaluation = oEvaluation;
        this.OTime = oTime;
    }

    public String getOEvaluation() {
        return OEvaluation;
    }

    public void setOEvaluation(String OEavluation) {
        this.OEvaluation = OEavluation;
    }

    public String getOSNumber() {
        return OSNumber;
    }

    public void setOSNumber(String OSNumber) {
        this.OSNumber = OSNumber;
    }

    public String getOContent() {
        return OContent;
    }

    public void setOContent(String OContent) {
        this.OContent = OContent;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String name) {
        this.userName = name;
    }

    public String getphoneNumber() {
        return phoneNumber;
    }

    public void setphoneNumber(String number) {
        this.phoneNumber = number;
    }

    public String getOTPrice() {
        return OTPrice;
    }

    public void setOTPrice(String OTPrice) {
        this.OTPrice = OTPrice;
    }

    public String getOStatus() {
        return OStatus;
    }

    public void setOStatus(String OStatus) {
        this.OStatus = OStatus;
    }

    public String getONumber() {
        return ONumber;
    }

    public void setONumber(String ONumber) {
        this.ONumber = ONumber;
    }
}

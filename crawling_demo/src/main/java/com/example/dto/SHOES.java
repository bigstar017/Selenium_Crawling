package com.example.dto;

public class SHOES {

    int SHOES_ID;
    String BRAND;
    String CATEGORY;
    String COLOR;
    String SEASON;
    String TEXTILE;
    String ITEM;
    int PRICE;
    String IMG;
    String URL;

    public SHOES() {
    }

    public SHOES(int SHOES_ID, String BRAND, String CATEGORY, String COLOR, String SEASON, String TEXTILE, String ITEM, int PRICE, String IMG, String URL) {
        this.SHOES_ID = SHOES_ID;
        this.BRAND = BRAND;
        this.CATEGORY = CATEGORY;
        this.COLOR = COLOR;
        this.SEASON = SEASON;
        this.TEXTILE = TEXTILE;
        this.ITEM = ITEM;
        this.PRICE = PRICE;
        this.IMG = IMG;
        this.URL = URL;
    }

    public int getSHOES_ID() {
        return SHOES_ID;
    }

    public void setSHOES_ID(int SHOES_ID) {
        this.SHOES_ID = SHOES_ID;
    }

    public String getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND = BRAND;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(String COLOR) {
        this.COLOR = COLOR;
    }

    public String getSEASON() {
        return SEASON;
    }

    public void setSEASON(String SEASON) {
        this.SEASON = SEASON;
    }

    public String getTEXTILE() {
        return TEXTILE;
    }

    public void setTEXTILE(String TEXTILE) {
        this.TEXTILE = TEXTILE;
    }

    public String getITEM() {
        return ITEM;
    }

    public void setITEM(String ITEM) {
        this.ITEM = ITEM;
    }

    public int getPRICE() {
        return PRICE;
    }

    public void setPRICE(int PRICE) {
        this.PRICE = PRICE;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "SHOES{" +
                "SHOES_ID=" + SHOES_ID +
                ", BRAND='" + BRAND + '\'' +
                ", CATEGORY='" + CATEGORY + '\'' +
                ", COLOR='" + COLOR + '\'' +
                ", SEASON='" + SEASON + '\'' +
                ", TEXTILE='" + TEXTILE + '\'' +
                ", ITEM='" + ITEM + '\'' +
                ", PRICE=" + PRICE +
                ", IMG='" + IMG + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }
}

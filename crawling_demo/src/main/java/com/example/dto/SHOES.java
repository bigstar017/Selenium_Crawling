package com.example.dto;

public class SHOES {

    String brand;
    String category;
    String color;
    String season;
    String textile;
    String itemName;
    int price;
    String img;
    String URL;

    public SHOES() {
    }

    public SHOES(String brand, String category, String color, String season, String textile, String itemName, int price, String img, String URL) {
        this.brand = brand;
        this.category = category;
        this.color = color;
        this.season = season;
        this.textile = textile;
        this.itemName = itemName;
        this.price = price;
        this.img = img;
        this.URL = URL;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getTextile() {
        return textile;
    }

    public void setTextile(String textile) {
        this.textile = textile;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
                "brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", color='" + color + '\'' +
                ", season='" + season + '\'' +
                ", textile='" + textile + '\'' +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }
}

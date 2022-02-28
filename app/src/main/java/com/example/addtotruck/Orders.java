package com.example.addtotruck;

public class Orders {

    String name;
    String price;
    String quantity;
    String imageurl;
    String total;



    Orders(){

    }

    public Orders(String name, String price, String quantity, String imageurl, String total) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageurl = imageurl;
        this.total = total;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}

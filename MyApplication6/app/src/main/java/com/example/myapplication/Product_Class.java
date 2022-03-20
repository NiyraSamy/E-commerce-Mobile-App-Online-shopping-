package com.example.myapplication;

public class Product_Class {
    private int product_id, product_quantity, category_id;
    private String product_name;
    private byte[] product_image;
    private double price;

    public byte [] getProImage() {
        return product_image;
    }

    public void setProImage(byte [] product_image) {
        this.product_image = product_image;
    }

    public Product_Class(int product_quantity, int category_id, String product_name, byte [] product_image, double price) {

        this.product_quantity = product_quantity;
        this.category_id = category_id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.price = price;
    }

    public int getPro_id() {
        return product_id;
    }

    public void setPro_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPro_quantity() {
        return product_quantity;
    }

    public void setPro_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public int getCatId() {
        return category_id;
    }

    public void setCatId(int category_id) {
        this.category_id = category_id;
    }

    public String getProName() {
        return product_name;
    }

    public void setProName(String product_name) {
        this.product_name = product_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}

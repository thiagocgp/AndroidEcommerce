package br.ufg.ecommerce;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String uid;
    private String name;
    private String description;
    private double price;
    private ArrayList<Integer> image;
    private String contactName;
    private String contactTel;

    public Product() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Integer> getImage() {
        return image;
    }

    public void setImage(ArrayList<Integer> image) {
        this.image = image;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

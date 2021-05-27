package com.mobdeve.leej.thejuanpc.model;

import java.io.Serializable;

public class PSU implements Serializable {

    private String image,model,modularity,rating;
    private double price;
    private int wattage;


    public PSU() {
    }

    public PSU(String image, String model, String modularity, String rating, double price, int wattage) {
        this.image = image;
        this.model = model;
        this.modularity = modularity;
        this.rating = rating;
        this.price = price;
        this.wattage = wattage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModularity() {
        return modularity;
    }

    public void setModularity(String modularity) {
        this.modularity = modularity;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getWattage() {
        return wattage;
    }

    public void setWattage(int wattage) {
        this.wattage = wattage;
    }
}

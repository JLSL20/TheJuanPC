package com.mobdeve.leej.thejuanpc.model;

import java.io.Serializable;

public class Cases implements Serializable {
    private String model;
    private String form_factor;
    private double price;
    private String image;

    public Cases(String model, String form_factor, double price, String image) {
        this.model = model;
        this.form_factor = form_factor;
        this.price = price;
        this.image = image;
    }

    public Cases() {

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

    public String getForm_factor() {
        return form_factor;
    }

    public void setForm_factor(String form_factor) {
        this.form_factor = form_factor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
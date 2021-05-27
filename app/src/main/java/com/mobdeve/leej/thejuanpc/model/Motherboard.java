package com.mobdeve.leej.thejuanpc.model;

import java.io.Serializable;

public class Motherboard implements Serializable {
    private String brand, chipset, form_factor, image, model,socket;
    private double price;
    private int reco_ram_speed,wattage;


    public Motherboard() {
    }

    public Motherboard(String brand, String chipset, String form_factor, String image, String model, String socket, double price, int reco_ram_speed, int wattage) {
        this.brand = brand;
        this.chipset = chipset;
        this.form_factor = form_factor;
        this.image = image;
        this.model = model;
        this.socket = socket;
        this.price = price;
        this.reco_ram_speed = reco_ram_speed;
        this.wattage = wattage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getForm_factor() {
        return form_factor;
    }

    public void setForm_factor(String form_factor) {
        this.form_factor = form_factor;
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

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getReco_ram_speed() {
        return reco_ram_speed;
    }

    public void setReco_ram_speed(int reco_ram_speed) {
        this.reco_ram_speed = reco_ram_speed;
    }

    public int getWattage() {
        return wattage;
    }

    public void setWattage(int wattage) {
        this.wattage = wattage;
    }
}

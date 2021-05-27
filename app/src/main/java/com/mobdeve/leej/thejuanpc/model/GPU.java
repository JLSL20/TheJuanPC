package com.mobdeve.leej.thejuanpc.model;

import java.io.Serializable;

public class GPU implements Serializable {
    private String brand, image, model;
    private int  memory_size,wattage;
    private double clock_rate,price;

    public GPU() {
    }

    public GPU(String model, String brand, double clock_rate, int memory_size, int wattage, double price, String image) {
        this.model = model;
        this.brand = brand;
        this.clock_rate = clock_rate;
        this.memory_size = memory_size;
        this.wattage = wattage;
        this.price = price;
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public double getClock_rate() {
        return clock_rate;
    }

    public void setClock_rate(double clock_rate) {
        this.clock_rate = clock_rate;
    }

    public int getMemory_size() {
        return memory_size;
    }

    public void setMemory_size(int memory_size) {
        this.memory_size = memory_size;
    }

    public int getWattage() {
        return wattage;
    }

    public void setWattage(int wattage) {
        this.wattage = wattage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

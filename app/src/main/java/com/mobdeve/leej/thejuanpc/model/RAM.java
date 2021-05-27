package com.mobdeve.leej.thejuanpc.model;

import java.io.Serializable;

public class RAM implements Serializable {
    private String image, model;
    private int capacity, num_sticks, wattage, speed;
    private double price;

    public RAM() {
    }

    public RAM(String image, String model, int capacity, int num_sticks, int wattage, int speed, double price) {
        this.image = image;
        this.model = model;
        this.capacity = capacity;
        this.num_sticks = num_sticks;
        this.wattage = wattage;
        this.speed = speed;
        this.price = price;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNum_sticks() {
        return num_sticks;
    }

    public void setNum_sticks(int num_sticks) {
        this.num_sticks = num_sticks;
    }

    public int getWattage() {
        return wattage;
    }

    public void setWattage(int wattage) {
        this.wattage = wattage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

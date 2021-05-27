package com.mobdeve.leej.thejuanpc.model;

import java.io.Serializable;

public class Storage implements Serializable {
    private int  wattage;
    private double price,capacity, write_speed, read_speed;
    private String image, model;


    public Storage() {
    }

    public Storage(String model, double capacity, double read_speed, double write_speed, int wattage, double price, String image) {
        this.model = model;
        this.capacity = capacity;
        this.read_speed = read_speed;
        this.write_speed = write_speed;
        this.wattage = wattage;
        this.price = price;
        this.image = image;
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

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getWrite_speed() {
        return write_speed;
    }

    public void setWrite_speed(double write_speed) {
        this.write_speed = write_speed;
    }

    public double getRead_speed() {
        return read_speed;
    }

    public void setRead_speed(double read_speed) {
        this.read_speed = read_speed;
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
}

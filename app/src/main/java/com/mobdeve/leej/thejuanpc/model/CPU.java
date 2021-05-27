package com.mobdeve.leej.thejuanpc.model;

import java.io.Serializable;

public class CPU implements Serializable {
    private double base_clockspeed,max_clockspeed,price;
    private int num_cores,num_threads,wattage;
    private String socket, model, image;

    public CPU() {
    }

    public CPU(double base_clockspeed, double max_clockspeed, double price, int num_cores, int num_threads, int wattage, String socket, String model, String image) {
        this.base_clockspeed = base_clockspeed;
        this.max_clockspeed = max_clockspeed;
        this.price = price;
        this.num_cores = num_cores;
        this.num_threads = num_threads;
        this.wattage = wattage;
        this.socket = socket;
        this.model = model;
        this.image = image;
    }


    public double getBase_clockspeed() {
        return base_clockspeed;
    }

    public void setBase_clockspeed(double base_clockspeed) {
        this.base_clockspeed = base_clockspeed;
    }

    public double getMax_clockspeed() {
        return max_clockspeed;
    }

    public void setMax_clockspeed(double max_clockspeed) {
        this.max_clockspeed = max_clockspeed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum_cores() {
        return num_cores;
    }

    public void setNum_cores(int num_cores) {
        this.num_cores = num_cores;
    }

    public int getNum_threads() {
        return num_threads;
    }

    public void setNum_threads(int num_threads) {
        this.num_threads = num_threads;
    }

    public int getWattage() {
        return wattage;
    }

    public void setWattage(int wattage) {
        this.wattage = wattage;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

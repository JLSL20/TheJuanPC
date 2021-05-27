package com.mobdeve.leej.thejuanpc.model;

import java.io.Serializable;

public class Builds implements Serializable {
    private String build_name;
    private String username;
    private int totalWattage;
    private double totalEstimatePrice;
    private String cpu;
    private String motherboard;
    private String ram;
    private String gpu;
    private String psu;
    private String storage;
    private String pc_case;

    public Builds() {
    }

    public Builds(String build_name, String username, int totalWattage, double totalEstimatePrice, String cpu, String motherboard, String ram, String gpu, String psu, String storage, String pc_case) {
        this.build_name = build_name;
        this.username = username;
        this.totalWattage = totalWattage;
        this.totalEstimatePrice = totalEstimatePrice;
        this.cpu = cpu;
        this.motherboard = motherboard;
        this.ram = ram;
        this.gpu = gpu;
        this.psu = psu;
        this.storage = storage;
        this.pc_case = pc_case;
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        this.build_name = build_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalWattage() {
        return totalWattage;
    }

    public void setTotalWattage(int totalWattage) {
        this.totalWattage = totalWattage;
    }

    public double getTotalEstimatePrice() {
        return totalEstimatePrice;
    }

    public void setTotalEstimatePrice(double totalEstimatePrice) {
        this.totalEstimatePrice = totalEstimatePrice;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getPsu() {
        return psu;
    }

    public void setPsu(String psu) {
        this.psu = psu;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getPc_case() {
        return pc_case;
    }

    public void setPc_case(String pc_case) {
        this.pc_case = pc_case;
    }
}

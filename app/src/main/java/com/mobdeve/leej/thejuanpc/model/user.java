package com.mobdeve.leej.thejuanpc.model;

import java.io.Serializable;

public class user implements Serializable {

    private String first, last, password, username;
    private String status = "regular";



    public user(String first, String last, String password, String username) {
        this.first = first;
        this.last = last;
        this.password = password;
        this.username = username;
    }

    public user(){

    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

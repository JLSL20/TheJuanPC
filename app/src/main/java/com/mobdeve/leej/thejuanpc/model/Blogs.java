package com.mobdeve.leej.thejuanpc.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Blogs implements Serializable {

    private String blog_title;
    private String username;
    private String featured_image;
    private String content;
    private String build_name;
    private String blog_status;
    private int upvotes;
    private String date_created;
    private ArrayList<String> liked_users = new ArrayList<String>();

    public Blogs() {
    }

    public Blogs(String blog_title, String username, String featured_image, String content, String build_name, String blog_status, int upvotes, String date_created) {
        this.blog_title = blog_title;
        this.username = username;
        this.featured_image = featured_image;
        this.content = content;
        this.build_name = build_name;
        this.blog_status = blog_status;
        this.upvotes = upvotes;
        this.date_created = date_created;
    }

    public String getBlog_title() {
        return blog_title;
    }

    public void setBlog_title(String blog_title) {
        this.blog_title = blog_title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeatured_image() {
        return featured_image;
    }

    public void setFeatured_image(String featured_image) {
        this.featured_image = featured_image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        this.build_name = build_name;
    }

    public String getBlog_status() {
        return blog_status;
    }

    public void setBlog_status(String blog_status) {
        this.blog_status = blog_status;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public ArrayList<String> getLiked_users() {
        return liked_users;
    }

    public void setLiked_users(ArrayList<String> liked_users) {
        this.liked_users = liked_users;
    }
}

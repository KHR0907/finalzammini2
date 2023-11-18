package com.example.finalzammini;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelUserVo {

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @SerializedName("role")
    private String role;

    @SerializedName("content")
    private String content;

}
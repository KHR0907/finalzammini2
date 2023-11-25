package com.example.finalzammini;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageEntity {
    @Expose
    @SerializedName("role")
    private String role;
    @Expose
    @SerializedName("content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

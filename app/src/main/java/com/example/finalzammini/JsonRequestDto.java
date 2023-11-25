package com.example.finalzammini;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonRequestDto {
    @Expose
    @SerializedName("model")
    private String model;
    @Expose
    @SerializedName("messages")
    private MessageEntity[] messages;
    public MessageEntity[] getMessage() {
        return messages;
    }

    public void setMessage(MessageEntity[] messages) {
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}

package com.example.finalzammini;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChoicesEntity {

    @Expose
    @SerializedName("index")
    private int index;
    @Expose
    @SerializedName("message")
    private MessageEntity message;
    @Expose
    @SerializedName("finish_reason")
    private String finishReason;

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    public MessageEntity getMessage() {
        return message;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

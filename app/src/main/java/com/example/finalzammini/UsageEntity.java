package com.example.finalzammini;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsageEntity {

    @Expose
    @SerializedName("prompt_tokens")
    private int promptTokens;
    @Expose
    @SerializedName("completion_tokens")
    private int completionTokens;
    @Expose
    @SerializedName("total_tokens")
    private int totalTokens;

    public int getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(int totalTokens) {
        this.totalTokens = totalTokens;
    }

    public int getCompletionTokens() {
        return completionTokens;
    }

    public void setCompletionTokens(int completionTokens) {
        this.completionTokens = completionTokens;
    }

    public int getPromptTokens() {
        return promptTokens;
    }

    public void setPromptTokens(int promptTokens) {
        this.promptTokens = promptTokens;
    }
}

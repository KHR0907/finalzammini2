package com.example.finalzammini;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class JsonResponseDto {

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("object")
    private String object;
    @Expose
    @SerializedName("created")
    private int created;
    @Expose
    @SerializedName("model")
    private String model;
    @Expose
    @SerializedName("choices")
    private ChoicesEntity[] choices;
    @Expose
    @SerializedName("usage")
    private UsageEntity usage;


    public UsageEntity getUsage() {
        return usage;
    }

    public void setUsage(UsageEntity usage) {
        this.usage = usage;
    }

    public ChoicesEntity[] getChoices() {
        return choices;
    }

    public void setChoices(ChoicesEntity[] choices) {
        this.choices = choices;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

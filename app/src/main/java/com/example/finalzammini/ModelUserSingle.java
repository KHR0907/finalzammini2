package com.example.finalzammini;

public class ModelUserSingle {

    private ModelUser data;

    public ModelUser getData() { return data; }

    public void setData(ModelUser data) { this.data = data; }

    @Override
    public String toString() {
        return "ModelUserSingle {\ndata=" + data.toString() + "\n}";
    }
}
package com.example.finalzammini;

public class ModelUserSingle {

    private ModelUserVo data;

    public ModelUserVo getData() { return data; }

    public void setData(ModelUserVo data) { this.data = data; }

    @Override
    public String toString() {
        return "ModelUserSingle {\n" + data.toString() + "\n}";
    }
}

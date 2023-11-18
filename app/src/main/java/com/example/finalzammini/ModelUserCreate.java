package com.example.finalzammini;

public class ModelUserCreate {

    private String id;
    private String createdAt;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "ModelUserCreate {" +
                "\n  id=\"" + id + "\"" +
                "\n, createdAt=\"" + createdAt + "\"" +
                "\n}";
    }
}

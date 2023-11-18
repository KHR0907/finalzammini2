package com.example.finalzammini;

import com.google.gson.annotations.SerializedName;

public class ModelUser {

    @SerializedName("id")
    private int userId;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    private String avatar;

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAvatar() { return avatar; }

    public void setAvatar(String avatar) { this.avatar = avatar; }

    @Override
    public String toString() {
        return "ModelUser {" +
                "\n  userId=" + userId +
                "\n, firstName=\"" + firstName + "\"" +
                "\n, lastName=\"" + lastName + "\"" +
                "\n, avatar=\"" + avatar + "\"" +
                "\n}\n";
    }
}
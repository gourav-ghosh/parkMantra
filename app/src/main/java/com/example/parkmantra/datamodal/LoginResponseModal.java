package com.example.parkmantra.datamodal;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModal {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("userId")
    private int userId;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}

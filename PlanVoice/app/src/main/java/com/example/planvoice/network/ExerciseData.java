package com.example.planvoice.network;

import com.google.gson.annotations.SerializedName;

public class ExerciseData {

    @SerializedName("plan_name")
    private String planName;

    @SerializedName("total_time")
    private int totalTime;

    @SerializedName("chest_time")
    private int chestTime;

    @SerializedName("shoulder_time")
    private int shoulderTime;

    @SerializedName("arm_time")
    private int armTime;

    @SerializedName("back_time")
    private int backTime;

    @SerializedName("leg_time")
    private int legTime;

    @SerializedName("timestamp")
    private String timestamp;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getChestTime() {
        return chestTime;
    }

    public void setChestTime(int chestTime) {
        this.chestTime = chestTime;
    }

    public int getShoulderTime() {
        return shoulderTime;
    }

    public void setShoulderTime(int shoulderTime) {
        this.shoulderTime = shoulderTime;
    }

    public int getArmTime() {
        return armTime;
    }

    public void setArmTime(int armTime) {
        this.armTime = armTime;
    }

    public int getBackTime() {
        return backTime;
    }

    public void setBackTime(int backTime) {
        this.backTime = backTime;
    }

    public int getLegTime() {
        return legTime;
    }

    public void setLegTime(int legTime) {
        this.legTime = legTime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

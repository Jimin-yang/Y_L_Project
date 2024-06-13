package com.example.planvoice.network;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ExercisePlanResponse {

    @SerializedName("planName")
    private String planName;

    @SerializedName("exercises")
    private List<ExerciseResponse> exercises;

    // Getters and setters
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public List<ExerciseResponse> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseResponse> exercises) {
        this.exercises = exercises;
    }
}

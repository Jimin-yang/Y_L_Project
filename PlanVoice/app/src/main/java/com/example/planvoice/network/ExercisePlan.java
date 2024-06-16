package com.example.planvoice.network;

import java.util.List;

public class ExercisePlan {
    private String planName;
    private List<ExerciseResponse> exercises;

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

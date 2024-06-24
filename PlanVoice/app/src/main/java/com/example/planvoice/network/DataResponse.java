package com.example.planvoice.network;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class DataResponse {

    @SerializedName("exerciseByBodyPart")
    private Map<String, Integer> exerciseByBodyPart;

    @SerializedName("exerciseByDate")
    private Map<String, Integer> exerciseByDate;

    public Map<String, Integer> getExerciseByBodyPart() {
        return exerciseByBodyPart;
    }

    public Map<String, Integer> getExerciseByDate() {
        return exerciseByDate;
    }
}

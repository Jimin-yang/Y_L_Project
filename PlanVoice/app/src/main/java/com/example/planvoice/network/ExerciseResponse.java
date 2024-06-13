package com.example.planvoice.network;

import com.google.gson.annotations.SerializedName;

public class ExerciseResponse {

    @SerializedName("ID")
    private int id;

    @SerializedName("ExerciseName")
    private String exerciseName;

    @SerializedName("BodyPart")
    private String bodyPart;

    @SerializedName("ExerciseDescription")
    private String exerciseDescription;

    @SerializedName("imageURL")
    private String imageURL;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
